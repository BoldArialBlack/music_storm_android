package com.example.asus.music_storm_android.atys;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.asus.music_storm_android.R;
import com.example.asus.music_storm_android.adapters.MyHistoryRecylerViewAdapter;
import com.example.asus.music_storm_android.utils.HistoryDao;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private MyHistoryRecylerViewAdapter adapter;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private TextView deleteTxt;
    private TextView oneTxt, twoTxt, threeTxt;

    private HistoryDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.list_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyHistoryRecylerViewAdapter(this, new MyHistoryRecylerViewAdapter.OnHistorySuggestionListener() {
            @Override
            public void onHistorySuggestionListener(String str) {
                searchView.setQuery(str, true);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        dao = new HistoryDao(this);
        adapter.addAll(dao.getHistory());

        deleteTxt = (TextView) findViewById(R.id.text_delete);
        deleteTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dao.deleteAll();
                adapter.delete();
            }
        });

        oneTxt = (TextView) findViewById(R.id.text_top_one);
        twoTxt = (TextView) findViewById(R.id.text_top_two);
        threeTxt = (TextView) findViewById(R.id.text_top_three);
        oneTxt.setOnClickListener(this);
        twoTxt.setOnClickListener(this);
        threeTxt.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.onActionViewExpanded();
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dao.add(query);
                adapter.add(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View view) {
        String str = null;
        switch (view.getId()) {
            case R.id.text_top_one:
                str = oneTxt.getText().toString();
                break;
            case R.id.text_top_two:
                str = twoTxt.getText().toString();
                break;
            case R.id.text_top_three:
                str = threeTxt.getText().toString();
                break;
        }
        searchView.setQuery(str, true);
    }
}
