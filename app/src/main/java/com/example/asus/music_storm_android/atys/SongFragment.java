package com.example.asus.music_storm_android.atys;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.music_storm_android.Config;
import com.example.asus.music_storm_android.R;
import com.example.asus.music_storm_android.adapters.MySongRecyclerViewAdapter;
import com.example.asus.music_storm_android.entities.Music;
import com.example.asus.music_storm_android.net.Search;
import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class SongFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    private TextView searchTxt;
    private String search;
    private RecyclerView recyclerView;
    private MySongRecyclerViewAdapter adapter;
    private RefreshLayout refreshLayout;

    private int curPage = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SongFragment() {
    }

    // TODO: Customize parameter initialization
    public static SongFragment newInstance(int columnCount) {
        SongFragment fragment = new SongFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        search = ((ResultActivity) getActivity()).getSearch();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song_list, container, false);

        searchTxt = (TextView) view.findViewById(R.id.text_result_retry);
        searchTxt.setVisibility(View.GONE);

        Context context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.list_result_song);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        adapter = new MySongRecyclerViewAdapter(context, mListener);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        initRefreshLayout(view, context);

        getResult(1, 20);
        return view;
    }

    private void initRefreshLayout(View view, Context context) {
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshSongLayout);
        refreshLayout.setRefreshHeader(new BezierCircleHeader(context));
        refreshLayout.setRefreshFooter(new BallPulseFooter(context).setSpinnerStyle(SpinnerStyle.Scale));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getResult(1, 20);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                curPage++;
                getResult(curPage, 20);
            }
        });
    }

    private void getResult(int page, int perpage) {
        Search searchConnection = new Search(search, null, page, perpage, new Search.SuccessCallback() {
            @Override
            public void onSuccess(int page, int perpage, List<Music> musics) {
                refreshLayout.finishRefresh(true);
                if (curPage == 1)
                    adapter.clear();
                adapter.addAll(musics);
                Toast.makeText(getActivity(), R.string.success_to_search, Toast.LENGTH_SHORT).show();
            }
        }, new Search.FailCallback() {
            @Override
            public void onFail(int errorCode) {
                refreshLayout.finishRefresh(true);
                adapter.clear();
                Toast.makeText(getActivity(), R.string.fail_to_search, Toast.LENGTH_SHORT).show();
                if (errorCode == Config.RESULT_STATUS_FAIL) {
                    searchTxt.setVisibility(View.VISIBLE);
                    Log.e("SONG_FRAGMENT", "setVisibility");
                }
                Log.e("SONG_FRAGMENT", "onFail");
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListSongFragmentInteraction(Music item);
    }
}
