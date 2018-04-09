package com.example.asus.music_storm_android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.music_storm_android.R;
import com.example.asus.music_storm_android.atys.SongFragment.OnListFragmentInteractionListener;
import com.example.asus.music_storm_android.entities.Music;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Music} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MySongRecyclerViewAdapter extends RecyclerView.Adapter<MySongRecyclerViewAdapter.ViewHolder> {

    private final OnListFragmentInteractionListener mListener;
    private List<Music> mValues = new ArrayList<Music>();
    private Context context;

    public MySongRecyclerViewAdapter(Context context, OnListFragmentInteractionListener listener) {
        this.context = context;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_song, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public void addAll(List<Music> musicList) {
        mValues.clear();
        mValues.addAll(musicList);
        notifyDataSetChanged();
    }

    public void clear() {
        mValues.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).getAlbumName());
        holder.mArtistView.setText(String.format("%s - %s", mValues.get(position).getArtistName(), mValues.get(position).getAlbumName()));

        if (mValues.get(position).getThirdParty().equals("163")) {
            holder.mThirdPartyView.setImageResource(R.drawable.ic_music_platform_netease);
        } else if (mValues.get(position).getThirdParty().equals("baidu")) {
            holder.mThirdPartyView.setImageResource(R.drawable.ic_music_platform_baidu);
        } else if (mValues.get(position).getThirdParty().equals("kugou")) {
            holder.mThirdPartyView.setImageResource(R.drawable.ic_music_platform_kugou);
        } else if (mValues.get(position).getThirdParty().equals("kuwo")) {
            holder.mThirdPartyView.setImageResource(R.drawable.ic_music_platform_kuwo);
        } else if (mValues.get(position).getThirdParty().equals("qq")) {
            holder.mThirdPartyView.setImageResource(R.drawable.ic_music_platform_qq);
        } else if (mValues.get(position).getThirdParty().equals("xiami")) {
            holder.mThirdPartyView.setImageResource(R.drawable.ic_music_platform_xiami);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListSongFragmentInteraction(holder.mItem);
                    Log.e("holder.mView", Integer.toString(position)+": is clicked");
                }
            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.e("holder.mView", Integer.toString(position)+": is clicked");
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mArtistView;
        public final ImageView mThirdPartyView;
        public final ImageView mOtherView;

        public Music mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.text_result_name);
            mArtistView = (TextView) view.findViewById(R.id.text_result_artist);
            mThirdPartyView = (ImageView) view.findViewById(R.id.view_result_logo);
            mOtherView = (ImageView) view.findViewById(R.id.view_more);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}
