package com.example.asus.music_storm_android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.music_storm_android.R;
import com.example.asus.music_storm_android.SquareFragment.OnListFragmentInteractionListener;
import com.example.asus.music_storm_android.entities.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link com.example.asus.music_storm_android.entities.Post} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final OnListFragmentInteractionListener mListener;
    private Context context;
    private List<Post> mValues = new ArrayList<Post>();

    public MyItemRecyclerViewAdapter(Context context, OnListFragmentInteractionListener listener) {
        this.context = context;
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_square, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.mNameView.setText(mValues.get(position).getUserName());
        holder.mAvatarView.setImageResource(R.mipmap.ic_launcher_round);
        holder.mContentView.setText(mValues.get(position).getMsg());
        holder.mLikesView.setText(String.format("%s个喜欢", Integer.toString(mValues.get(position).getLikes())));
        holder.mCommentsView.setText(String.format("%s条评论", Integer.toString(mValues.get(position).getCommentNum())));
        holder.mDateView.setText(mValues.get(position).getTime());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    public void addAll(List<Post> data) {
        mValues.addAll(data);
        notifyDataSetChanged();
    }

    public void clear() {
        mValues.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mAvatarView;
        public final TextView mNameView;
        public final TextView mContentView;
        public final TextView mLikesView;
        public final TextView mCommentsView;
        public final TextView mDateView;


        public Post mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mAvatarView = (ImageView) view.findViewById(R.id.users_avatar);
            mNameView = (TextView) view.findViewById(R.id.text_users_name);
            mContentView = (TextView) view.findViewById(R.id.text_users_content);
            mLikesView = (TextView) view.findViewById(R.id.text_like_num);
            mCommentsView = (TextView) view.findViewById(R.id.text_comment_num);
            mDateView = (TextView) view.findViewById(R.id.text_date);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
