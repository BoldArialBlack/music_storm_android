package com.example.asus.music_storm_android.atys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.asus.music_storm_android.Config;
import com.example.asus.music_storm_android.R;
import com.example.asus.music_storm_android.entities.User;

public class PersonalCenterActivity extends AppCompatActivity {
    private TextView nameView, signView, levelView;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra(Config.KEY_USER);
        Log.e("PERSONAL_CENTER", user.toString());

        nameView = (TextView) findViewById(R.id.text_pc_name);
        signView = (TextView) findViewById(R.id.text_pc_sign);
        levelView = (TextView) findViewById(R.id.text_pc_level);

        nameView.setText(user.getUserName());
        signView.setText(user.getUserProfile());
        levelView.setText(String.format("lv%s", String.valueOf(user.getUserLevel())));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
