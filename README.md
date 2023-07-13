# 算法服务器
## 功能介绍
执行算法并用Websocket向安卓端APP推送执行结果。算法服务器采用django+Django REST framework+dwebsocket 技术实现。
## 实现原理
客户端通过RTMP（RealTimeMessagingProtocol，实时消息传输协议）上传视频流到nginx服务器，经过nginx-rtmp-module处理，算法服务器端使用RTMP拉流获得m3u8视频流，进行算法预测。向后端Springboot服务器发送带有预测结果的http请求。
