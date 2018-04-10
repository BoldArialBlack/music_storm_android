package com.example.asus.music_storm_android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.music_storm_android.R;
import com.example.asus.music_storm_android.entities.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2018/4/10.
 */

public class MyCommentRecyclerViewAdapter extends RecyclerView.Adapter<MyCommentRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Comment> mValues = new ArrayList<Comment>();

    public MyCommentRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyCommentRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_list_comment, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyCommentRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameText.setText(mValues.get(position).getUserName());
        holder.mContentText.setText(mValues.get(position).getContent());
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void addAll(List<Comment> comments) {
        mValues.clear();
        mValues.addAll(comments);
        notifyDataSetChanged();
    }

    public void clear() {
        mValues.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameText;
        public final TextView mContentText;

        public Comment mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameText = (TextView) view.findViewById(R.id.text_comment_user);
            mContentText = (TextView) view.findViewById(R.id.text_comment_content);
        }

        @Override
        public String toString() {
            return "ViewHolder{" +
                    "mView=" + mView +
                    ", mNameText=" + mNameText +
                    ", mContentText=" + mContentText +
                    '}';
        }
    }
}
