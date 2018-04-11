package com.example.asus.music_storm_android.atys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.music_storm_android.Config;
import com.example.asus.music_storm_android.R;
import com.example.asus.music_storm_android.SquareFragment;
import com.example.asus.music_storm_android.adapters.MyItemRecyclerViewAdapter;
import com.example.asus.music_storm_android.entities.Post;
import com.example.asus.music_storm_android.entities.User;
import com.example.asus.music_storm_android.net.Timeline;

import java.util.List;

public class PersonalCenterActivity extends AppCompatActivity {
    private TextView nameView, signView, levelView;
    private RecyclerView recyclerView;
    private MyItemRecyclerViewAdapter adapter;

    private User user;
    private String token;

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
        token = intent.getStringExtra(Config.KEY_TOKEN);
        Log.e("PERSONAL_CENTER", user.toString());

        nameView = (TextView) findViewById(R.id.text_pc_name);
        signView = (TextView) findViewById(R.id.text_pc_sign);
        levelView = (TextView) findViewById(R.id.text_pc_level);

        nameView.setText(user.getUserName());
        signView.setText(user.getUserProfile());
        levelView.setText(String.format("lv%s", String.valueOf(user.getUserLevel())));

        recyclerView = (RecyclerView) findViewById(R.id.list_pc_post);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new MyItemRecyclerViewAdapter(this, new SquareFragment.OnListFragmentInteractionListener() {
            @Override
            public void onListFragmentInteraction(Post item) {
                Intent intent = new Intent(PersonalCenterActivity.this, CommentActivity.class);
                intent.putExtra(Config.KEY_PHONE_MD5, user.getPhone());
                intent.putExtra(Config.KEY_TOKEN, token);
                intent.putExtra(Config.KEY_MSG_ID, item.getMsgId());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        getPosts(1, 20);
    }

    private void getPosts(int page, int perpage) {
        Timeline timeline = new Timeline(user.getPhone(), token, page, perpage, new Timeline.SuccessCallback() {
            @Override
            public void onSuccess(int page, int perpage, List<Post> timeline) {
                adapter.clear();
                adapter.addAll(timeline);
                Toast.makeText(PersonalCenterActivity.this, R.string.success_to_load, Toast.LENGTH_LONG).show();
            }
        }, new Timeline.FailCallback() {
            @Override
            public void onFail(int errorCode) {
                Toast.makeText(PersonalCenterActivity.this, R.string.fail_to_load, Toast.LENGTH_LONG).show();
            }
        });
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
