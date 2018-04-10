package com.example.asus.music_storm_android.atys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.asus.music_storm_android.Config;
import com.example.asus.music_storm_android.R;
import com.example.asus.music_storm_android.adapters.MyCommentRecyclerViewAdapter;
import com.example.asus.music_storm_android.entities.Comment;
import com.example.asus.music_storm_android.net.GetComment;

import java.util.List;

public class CommentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyCommentRecyclerViewAdapter adapter;

    private String phone_md5, token, msgId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.list_comments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyCommentRecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        Intent data = getIntent();
        phone_md5 = data.getStringExtra(Config.KEY_PHONE_MD5);
        token = data.getStringExtra(Config.KEY_TOKEN);
        msgId = data.getStringExtra(Config.KEY_MSG_ID);

        final ProgressDialog pd = ProgressDialog.show(CommentActivity.this, getString(R.string.connecting), getString(R.string.connecting_to_server));
        new GetComment(phone_md5, token, msgId, 1, 20, new GetComment.SuccessCallback() {
            @Override
            public void onSuccess(String msgId, int page, int perpage, List<Comment> comments) {
                pd.dismiss();
                adapter.clear();
                adapter.addAll(comments);
                Toast.makeText(CommentActivity.this, R.string.success_to_load, Toast.LENGTH_SHORT).show();
            }
        }, new GetComment.FailCallback() {
            @Override
            public void onFail(int errorCode) {
                pd.dismiss();
                adapter.clear();
                Toast.makeText(CommentActivity.this, R.string.fail_to_load, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
