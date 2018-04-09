package com.example.asus.music_storm_android;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.asus.music_storm_android.adapters.MyItemRecyclerViewAdapter;
import com.example.asus.music_storm_android.entities.Post;
import com.example.asus.music_storm_android.net.Timeline;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class SquareFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    RecyclerView recyclerView;
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private MyItemRecyclerViewAdapter adapter;
    private List<Post> posts = new ArrayList<Post>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SquareFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SquareFragment newInstance(int columnCount) {
        SquareFragment fragment = new SquareFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_square_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        // Set the adapter
//        if (view instanceof RecyclerView) {
            Context context = view.getContext();

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
        adapter = new MyItemRecyclerViewAdapter(getActivity(), mListener);
        recyclerView.setAdapter(adapter);
//        }

        getPosts(1, 20);

        return view;
    }

    private void getPosts(int page, int perpage) {
        Timeline timeline = new Timeline("", "", page, perpage, new Timeline.SuccessCallback() {
            @Override
            public void onSuccess(int page, int perpage, List<Post> timeline) {
                adapter.clear();
                adapter.addAll(timeline);
                Toast.makeText(getActivity(), R.string.success_to_load, Toast.LENGTH_LONG).show();
            }
        }, new Timeline.FailCallback() {
            @Override
            public void onFail(int errorCode) {
                Toast.makeText(getActivity(), R.string.fail_to_load, Toast.LENGTH_LONG).show();
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
        public void onListFragmentInteraction(Post item);
    }
}
