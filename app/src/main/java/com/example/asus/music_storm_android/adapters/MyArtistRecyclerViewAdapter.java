package com.example.asus.music_storm_android.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.music_storm_android.R;
import com.example.asus.music_storm_android.atys.ArtistFragment.OnListFragmentInteractionListener;
import com.example.asus.music_storm_android.dummy.DummyArtistContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyArtistRecyclerViewAdapter extends RecyclerView.Adapter<MyArtistRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyArtistRecyclerViewAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_artist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mAvatarView.setBackgroundResource(mValues.get(position).avatar);
        holder.mNameView.setText(mValues.get(position).name);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListArtistFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mAvatarView;
        public final TextView mNameView;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mAvatarView = (ImageView) view.findViewById(R.id.view_result_avatar);
            mNameView = (TextView) view.findViewById(R.id.text_result_artist_name);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}
