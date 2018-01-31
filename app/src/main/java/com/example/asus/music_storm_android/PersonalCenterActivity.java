package com.example.asus.music_storm_android;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.EventLog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class PersonalCenterActivity extends AppCompatActivity {
    private TextView nameView;
    private TextView signView;

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

        nameView = findViewById(R.id.text_pc_name);
        signView = findViewById(R.id.text_pc_sign);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(PersonalCenterActivity.this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(PersonalCenterActivity.this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onLoginEvent(LoginEvent event) {
        Log.e("Recievor", "Personal Center: recieved");
        nameView.setText(event.getUserName());
        signView.setText(event.getSign());
    }
}
