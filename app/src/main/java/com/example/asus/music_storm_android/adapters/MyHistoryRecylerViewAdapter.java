package com.example.asus.music_storm_android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.music_storm_android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2018/4/10.
 */

public class MyHistoryRecylerViewAdapter extends RecyclerView.Adapter<MyHistoryRecylerViewAdapter.ViewHolder> {

    private OnHistorySuggestionListener mListener;
    private List<String> mValues = new ArrayList<>();
    private Context context;

    public MyHistoryRecylerViewAdapter(Context context, OnHistorySuggestionListener listener) {
        this.context = context;
        this.mListener = listener;
    }

    @Override
    public MyHistoryRecylerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_list_history, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHistoryRecylerViewAdapter.ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mHistoryTxt.setText(mValues.get(position));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onHistorySuggestionListener(mValues.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public void add(String str) {
        if (!mValues.contains(str)) {
            mValues.add(str);
            notifyDataSetChanged();
        }
    }

    public void addAll(List<String> list) {
        mValues.clear();
        mValues.addAll(list);
        notifyDataSetChanged();
    }

    public void delete() {
        mValues.clear();
        notifyDataSetChanged();
    }

    public interface OnHistorySuggestionListener {
        public void onHistorySuggestionListener(String str);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mHistoryTxt;
        public final View mView;

        public String mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mHistoryTxt = (TextView) view.findViewById(R.id.text_history);
        }

        @Override
        public String toString() {
            return "ViewHolder{" +
                    "mHistoryTxt=" + mHistoryTxt +
                    '}';
        }
    }
}
