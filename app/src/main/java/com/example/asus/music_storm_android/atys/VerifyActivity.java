package com.example.asus.music_storm_android.atys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.asus.music_storm_android.Config;
import com.example.asus.music_storm_android.R;
import com.example.asus.music_storm_android.entities.User;
import com.example.asus.music_storm_android.events.LoginEvent;
import com.example.asus.music_storm_android.net.Login;
import com.example.asus.music_storm_android.utils.MD5Tool;

import org.greenrobot.eventbus.EventBus;

public class VerifyActivity extends AppCompatActivity {

    private Button codeBtn;
    private EditText codeEdt;
    private ProgressBar pb;

    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        codeBtn = (Button) findViewById(R.id.btn_code);
        codeEdt = (EditText) findViewById(R.id.edit_code);
        pb = (ProgressBar) findViewById(R.id.verify_progress);

        phone = getIntent().getStringExtra(Config.KEY_PHONE_NUM);

        codeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(codeEdt.getText())) {
                    Login login = new Login(MD5Tool.md5(phone), codeBtn.getText().toString(), new Login.SuccessCallback() {
                        @Override
                        public void onSuccess(String token, User user) {
                            EventBus.getDefault().postSticky(new LoginEvent(user));

                            Config.cachePhoneNum(VerifyActivity.this, phone);
                            Config.cacheToken(VerifyActivity.this, token);
                            Config.cacheUser(VerifyActivity.this, user);

                            Toast.makeText(VerifyActivity.this, R.string.success_to_login, Toast.LENGTH_SHORT).show();
                            setResult(Config.RESULT_STATUS_SUCCESS);
                            finish();
                        }
                    }, new Login.FailCallback() {
                        @Override
                        public void onFail() {
                            Toast.makeText(VerifyActivity.this, R.string.fail_to_login, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(VerifyActivity.this, R.string.code_can_not_be_empty, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
