package com.example.asus.music_storm_android.atys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.asus.music_storm_android.Config;
import com.example.asus.music_storm_android.R;
import com.example.asus.music_storm_android.dummy.DummyArtistContent;
import com.example.asus.music_storm_android.entities.Music;

public class ResultActivity extends AppCompatActivity implements SongFragment.OnListFragmentInteractionListener,
        ArtistFragment.OnListFragmentInteractionListener, TabLayout.OnTabSelectedListener {

    private SongFragment songFrag;
    private ArtistFragment artistFrag;
    private Fragment[] fragments;
    private int lastShowFragment = 0;

    private String search;

    private TextView title_l, title_m;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        title_m = (TextView) findViewById(R.id.txt_main_title);
        title_m.setText("搜索结果");

        TabLayout tabLayout = (TabLayout)findViewById(R.id.layout_tab_search);
        tabLayout.addOnTabSelectedListener(this);

        search = getIntent().getStringExtra(Config.KEY_MUSIC_NAME);

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
    public void onListSongFragmentInteraction(Music item) {
        Log.e("Activity", item.toString());
        Intent intent = new Intent(ResultActivity.this, ListenActivity.class);
        intent.putExtra(Config.KEY_URL, item.getUrl());
        startActivity(intent);
    }

    @Override
    public void onListArtistFragmentInteraction(DummyArtistContent.DummyItem item) {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                return false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public String getSearch() {
        return search;
    }
}
