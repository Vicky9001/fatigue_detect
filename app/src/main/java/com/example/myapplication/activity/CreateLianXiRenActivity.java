package com.example.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.example.myapplication.R;
import com.example.myapplication.dao.LianXiRenDao;
import com.example.myapplication.entity.LianXiRenData;
import com.example.myapplication.utils.ImageUtils;
import com.example.myapplication.utils.ToastUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateLianXiRenActivity extends AppCompatActivity {

    private TextView tvTitle;
    private TextView btCreate;

    private EditText edCourseName;
    private EditText edMoney;
    private ImageView img;
    private LianXiRenData mData;
    RadioGroup rgSex;
    private boolean isAdd = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_news);
        initView();
        initData();
    }

    private void initView() {
        btCreate = findViewById(R.id.bt_success);
        tvTitle = findViewById(R.id.tv_Name);
        edCourseName = findViewById(R.id.ed_courseName);
        edMoney = findViewById(R.id.ed_Money);
        img = findViewById(R.id.img);
        rgSex = findViewById(R.id.rg_sex);
        btCreate.setOnClickListener(v -> {
            String courseName = edCourseName.getText().toString().trim();
            String courseNum = edMoney.getText().toString().trim();

            if (TextUtils.isEmpty(courseName)) {
                ToastUtils.showCustomCenterToast(this, "联系人名称不能为空");
                return;
            }
            if (TextUtils.isEmpty(courseNum)) {
                ToastUtils.showCustomCenterToast(this, "联系人号码不能为空");
                return;
            }

            if (!isMobile(courseNum)) {
                ToastUtils.showCustomCenterToast(this, "请输入正确的手机号");
                return;
            }
            if (isAdd) {
                List<LianXiRenData> lianXiRenData = LianXiRenDao.getInstance(this).queryAll();
                if (lianXiRenData.size()>=5){
                    ToastUtils.showCustomCenterToast(this,"最多可以添加五条联系人");
                    return;
                }
                mData.setName(courseName);
                mData.setPhoneNumber(courseNum);
                if (rgSex.getCheckedRadioButtonId() == R.id.rbMan){
                    mData.setSex(1);
                }else{
                    mData.setSex(2);
                }
                if (TextUtils.isEmpty(mData.getImgPath())){
                    Random random = new Random();
                    int i = random.nextInt(20);
                    mData.setImgPath(ImageUtils.imgList[i]);
                }
                LianXiRenDao.getInstance(this).add(mData);
                ToastUtils.showCustomCenterToast(this,"添加数据成功");
            } else {
                mData.setName(courseName);
                mData.setPhoneNumber(courseNum);
                if (rgSex.getCheckedRadioButtonId() == R.id.rbMan){
                    mData.setSex(1);
                }else{
                    mData.setSex(2);
                }
                LianXiRenDao.getInstance(this).update(mData);
                ToastUtils.showCustomCenterToast(this,"修改数据成功");
            }
            finish();
        });
        img.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, 2);
        });
    }

    private void initData() {
        Intent intent = getIntent();
        mData = intent.getParcelableExtra("item");
        if (mData == null) {
            mData = new LianXiRenData();
            isAdd = true;
            tvTitle.setText("添加紧急联系人");
        } else {
            isAdd = false;
            tvTitle.setText("修改紧急联系人");
            edCourseName.setText(mData.getName());
            edMoney.setText(mData.getPhoneNumber());
            if (mData.getSex() == 1){
                RadioButton radioButton = (RadioButton) rgSex.getChildAt(0);
                radioButton.setChecked(true);
            }else{
                RadioButton radioButton = (RadioButton) rgSex.getChildAt(1);
                radioButton.setChecked(true);
            }
            Glide.with(this)
                    .load(mData.getImgPath())
                    .priority(Priority.LOW)
                    .into(img);
        }
    }

    //校验通过返回true，否则返回false
    public static boolean isMobile(String mobile) {

        String str = mobile;
        String pattern = "^(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57]|19[89]|166)[0-9]{8}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);

        return m.matches();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            // 从相册返回的数据
            if (data != null) {
                Log.e(this.getClass().getName(), "Result:" + data.toString());
                // 得到图片的全路径
                Uri uri = data.getData();
                try {
                    Bitmap bitmap = loadImage(this, uri);
                    saveBitmap(bitmap);
                    img.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 保存bitmap到本地
     *
     * @param bitmap
     */
    public void saveBitmap(Bitmap bitmap) {
        File filePic;
        try {
            String imgPath = getNewPath(this, System.currentTimeMillis() + ".jpg");
            mData.setImgPath(imgPath);
            filePic = new File(imgPath);
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getNewPath(Context context, String fileName) {
        String apkDir = context.getFilesDir() + File.separator + fileName;
        return apkDir;
    }

    public static Bitmap loadImage(Context context, Uri uri) throws IOException {
        InputStream in = context.getContentResolver().openInputStream(uri);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, opts);
        in.close();
        int height = opts.outHeight;
        int width = opts.outWidth;

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        if (width > height) {
            int t = width;
            width = height;
            height = t;
        }

        int scaleX = (int) Math.ceil((1.0 * width) / point.x);
        int scaleY = (int) Math.ceil((1.0 * height) / point.y);

        int scale = 1;
        if (scaleX > 1 && scaleX > scaleY) {
            scale = scaleX;
        }
        if (scaleY > 1 && scaleY >= scaleX) {
            scale = scaleY;
        }
        opts.inSampleSize = scale + 1;
        opts.inJustDecodeBounds = false;
        in = context.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(in, null, opts);
        in.close();
        return bitmap;
    }
}