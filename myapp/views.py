from django.http import JsonResponse
from myapp import run

from imutils import face_utils
import dlib

from models.experimental import attempt_load
from utils.general import (check_img_size, cv2)
from utils.torch_utils import select_device
from django.views import View

stop_flag = False
process = None

# 初始化DLIB的人脸检测器（HOG），然后创建面部标志物预测
print("[INFO] loading facial landmark predictor...")
# 第一步：使用dlib.get_frontal_face_detector() 获得脸部位置检测器
detector = dlib.get_frontal_face_detector()
# 第二步：使用dlib.shape_predictor获得脸部特征位置检测器
predictor = dlib.shape_predictor('E:/data/shape_predictor_68_face_landmarks.dat')

# 第三步：分别获取左右眼面部标志的索引
(lStart, lEnd) = face_utils.FACIAL_LANDMARKS_IDXS["left_eye"]
(rStart, rEnd) = face_utils.FACIAL_LANDMARKS_IDXS["right_eye"]
(mStart, mEnd) = face_utils.FACIAL_LANDMARKS_IDXS["mouth"]

# 第四步：打开cv2 本地摄像头
# cap = cv2.VideoCapture(0)

# 初始化YOLOv5
weights = 'yolov5s.pt'  # 你的模型权重文件路径
imgsz = 640  # 输入图片的大小
device = ''  # cuda device, i.e. 0 or 0,1,2,3 or cpu
conf_thres = 0.25  # confidence threshold
iou_thres = 0.45  # NMS IOU threshold

device = select_device(device)
half = device.type != 'cpu'  # 半精度仅在CUDA设备上可用

# 加载模型
model = attempt_load(weights)  # 加载FP32模型
stride = int(model.stride.max())  # 模型步幅
imgsz = check_img_size(imgsz, s=stride)  # 检查输入图片大小

if half:
    model.half()  # to FP16


def run_algorithm():
    # 从请求中获取视频流 URL
    # stream_url = request.GET.get('stream_url')
    print(cv2.getBuildInformation())
    cap = cv2.VideoCapture('rtmp://101.201.102.217/live/stream')
    if not cap.isOpened():
        print('VideoCapture not opened')
        exit(-1)

    frame_count = 0
    # 从流媒体服务器拉取视频流并执行算法
    while True:
        frame_count += 1
        # 在每一次迭代的开始检查停止标志
        if stop_flag:
            # 如果停止标志被设置，那么结束进程并退出循环
            break
        ret, frame = cap.read()
        if not ret:
            print('frame not read')
            exit(-1)
        # initial_frame_shape = frame.shape[:2]  # 初始化窗口帧大小
        # frame = cv2.resize(frame, initial_frame_shape[::-1])
        # cv2.imshow('Video', frame)
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break
        initial_frame_shape = frame.shape[:2]  # 初始化窗口帧大小

        if frame_count % 50 == 0:
            frame, pred = run.yolov5_detection(frame, model, device, half, imgsz, conf_thres, iou_thres)

        frame, ear, mar, har = run.face_feature_analysis(frame, detector, predictor)
        frame = cv2.resize(frame, initial_frame_shape[::-1])  # 目标检测结束后将窗口大小恢复初始大小

        # 后面是退出的代码
        # 按q退出
        cv2.putText(frame, "Press 'q': Quit", (20, 500), cv2.FONT_HERSHEY_SIMPLEX, 0.7, (84, 255, 159), 2)
        # 窗口显示 show with opencv
        cv2.imshow("Frame", frame)

        # if the `q` key was pressed, break from the loop
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break

    # 释放摄像头 release camera
    cap.release()
    # do a bit of cleanup
    cv2.destroyAllWindows()

    # 返回一个 JSON 响应表示算法已成功运行


def some_view(request):
    # 在这里调用你的 run_algorithm 函数
    run_algorithm()

    # 向后端 Springboot 服务器发送带有预测结果的 HTTP 请求
    # ...（这部分代码取决于你如何实现 HTTP 请求）

    # 然后在这里返回 JsonResponse，告诉浏览器处理已完成
    return JsonResponse({'message': 'Algorithm ran successfully'})


class StopProcessingView(View):
    def post(self, request, *args, **kwargs):
        global stop_flag
        stop_flag = True
        return JsonResponse({'status': 'Processing stopped'})