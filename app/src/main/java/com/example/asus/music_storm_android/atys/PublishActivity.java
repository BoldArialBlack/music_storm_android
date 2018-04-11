package com.example.asus.music_storm_android.atys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.music_storm_android.Config;
import com.example.asus.music_storm_android.R;
import com.example.asus.music_storm_android.net.Publish;

public class PublishActivity extends AppCompatActivity {

    private EditText publishEdt;
    private Button publishBtn;

    private String phone_md5, token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        initToolbar("发布");

        Intent intent = getIntent();
        phone_md5 = intent.getStringExtra(Config.KEY_PHONE_MD5);
        token = intent.getStringExtra(Config.KEY_TOKEN);

        publishEdt = (EditText) findViewById(R.id.edit_publish);
        publishBtn = (Button) findViewById(R.id.btn_publish);
        publishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (token == null) {
                    Toast.makeText(PublishActivity.this, R.string.please_sign_in_first, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!TextUtils.isEmpty(publishEdt.getText())) {
                    new Publish(phone_md5, token, publishEdt.getText().toString(), new Publish.SuccessCallback() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(PublishActivity.this, R.string.success_to_publish, Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }, new Publish.FailCallback() {
                        @Override
                        public void onFail(int errorCode) {
                            Toast.makeText(PublishActivity.this, R.string.fail_to_publish, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void initToolbar(String title) {
        TextView titleL, titleM, titleR;

        titleL = (TextView) findViewById(R.id.txt_left_title);
        titleM = (TextView) findViewById(R.id.txt_main_title);
        titleR = (TextView) findViewById(R.id.txt_right_title);

        titleL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        titleM.setText(title);

        titleR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PublishActivity.this, SearchActivity.class));
            }
        });
    }

}
