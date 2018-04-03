package com.example.asus.music_storm_android.atys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.asus.music_storm_android.R;
import com.example.asus.music_storm_android.dummy.DummyArtistContent;
import com.example.asus.music_storm_android.dummy.DummySongContent;

public class SearchActivity extends AppCompatActivity implements SongFragment.OnListFragmentInteractionListener,
        ArtistFragment.OnListFragmentInteractionListener, TabLayout.OnTabSelectedListener {

    private SongFragment songFrag;
    private ArtistFragment artistFrag;
    private Fragment[] fragments;
    private int lastShowFragment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.layout_tab_search);
        tabLayout.addOnTabSelectedListener(this);

        initFragment();
    }

    private void initFragment() {
        songFrag = new SongFragment();
        artistFrag = new ArtistFragment();
        fragments = new Fragment[] {songFrag, artistFrag};
        lastShowFragment = 0;
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_frame_search, songFrag)
                .show(songFrag)
                .commit();
    }

    private void switchFragment(int lastIndex, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastIndex]);
        if (!fragments[index].isAdded()) {
            transaction.add(R.id.container_frame_search, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }

    @Override
    public void onListSongFragmentInteraction(DummySongContent.DummyItem item) {
        Log.e("Activity", item.toString());
        Intent intent = new Intent();
        intent.setClass(SearchActivity.this, ListenActivity.class);  //从前者跳到后者，特别注意的是，在fragment中，用getActivity()来获取当前的activity
        startActivity(intent);
    }

    @Override
    public void onListArtistFragmentInteraction(DummyArtistContent.DummyItem item) {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
//        TabLayout tabLayout = (TabLayout)findViewById(R.id.layout_tab_search);
//        if (tab == tabLayout.getTabAt(0))
        switch (tab.getPosition()) {
            case 0:
                if (lastShowFragment != 0) {
                    switchFragment(lastShowFragment, 0);
                    lastShowFragment = 0;
                }
                return;
            case 1:
                Log.e("Tab", "onTabSelected: artist" );
                if (lastShowFragment != 1) {
                    switchFragment(lastShowFragment, 1);
                    lastShowFragment = 1;
                }
                return;
            default:
                    return;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
