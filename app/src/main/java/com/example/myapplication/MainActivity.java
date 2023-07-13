package com.example.myapplication;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.activity.DrivingActivity;
import com.example.myapplication.activity.register;
import com.example.myapplication.dao.UserDao;
import com.example.myapplication.entity.User;
import com.example.myapplication.utils.PreferenceUtil;

/**
 * function：连接页面加载首页
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "mysql-party-MainActivity";

    private CheckBox checkBox;
    EditText EditTextAccount,EditTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PreferenceUtil.init(this);
        initView();
    }

    private void initView() {
        EditTextAccount = findViewById(R.id.uesrAccount);
        EditTextPassword = findViewById(R.id.userPassword);
        checkBox = findViewById(R.id.checkbox);

        if (PreferenceUtil.getBoolean(Config.IS_STORAGE,false)){
            checkBox.setChecked(true);
            String userName = PreferenceUtil.getString(Config.USER_NAME, "");
            String psw = PreferenceUtil.getString(Config.PASS_WORD, "");
            if (!TextUtils.isEmpty(userName)&&!TextUtils.isEmpty(psw)){
                EditTextAccount.setText(userName);
                EditTextPassword.setText(psw);
            }
        }
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                PreferenceUtil.commitBoolean(Config.IS_STORAGE,true);
            }else{
                PreferenceUtil.commitBoolean(Config.IS_STORAGE,false);
            }
        });
    }

    public void reg(View view){
        startActivity(new Intent(MainActivity.this, register.class));
    }


    /**
     * function: 登录
     * */
    public void login(View view){

        Log.e("567890","567890-");

        new Thread(){
            @Override
            public void run() {
                UserDao userDao = new UserDao();
                int msg = userDao.login(EditTextAccount.getText().toString(),EditTextPassword.getText().toString());
                hand1.sendEmptyMessage(msg);
            }
        }.start();

    }

    @SuppressLint("HandlerLeak")
    final Handler hand1 = new Handler(Looper.myLooper()) {
        @SuppressLint("LongLogTag")
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_LONG).show();
            } else if (msg.what == 1) {
                PreferenceUtil.commitString(Config.USER_NAME,EditTextAccount.getText().toString());
                PreferenceUtil.commitString(Config.PASS_WORD,EditTextPassword.getText().toString());
                startActivity(new Intent(MainActivity.this, DrivingActivity.class));
                Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_LONG).show();


            } else if (msg.what == 2) {
                Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_LONG).show();
            } else if (msg.what == 3) {
                Toast.makeText(getApplicationContext(), "账号不存在", Toast.LENGTH_LONG).show();
            }
        }
    };
}
