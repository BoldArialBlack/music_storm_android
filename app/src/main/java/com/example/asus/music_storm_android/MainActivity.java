package com.example.asus.music_storm_android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.music_storm_android.atys.CommentActivity;
import com.example.asus.music_storm_android.atys.LoginActivity;
import com.example.asus.music_storm_android.atys.PersonalCenterActivity;
import com.example.asus.music_storm_android.entities.Post;
import com.example.asus.music_storm_android.entities.User;
import com.example.asus.music_storm_android.events.LoginEvent;
import com.example.asus.music_storm_android.utils.MD5Tool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeFragment.OnFragmentInteractionListener,
        NavigationFragment.OnFragmentInteractionListener, SquareFragment.OnListFragmentInteractionListener, View.OnClickListener {

    private HomeFragment homefrag;
    private NavigationFragment navfrag;
    private SquareFragment squarefrag;
    private Fragment[] fragments;
    private int lastShowFragment = 0;

    private ImageView avatarView;
    private TextView nameView;
    private TextView signView;
    private Button checkInBtn;

    private boolean isLogin = false;
    private User user = null;
    private String phone_num;
    private String token;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (lastShowFragment != 0) {
                        switchFragment(lastShowFragment, 0);
                        lastShowFragment = 0;
                    }
                    return true;
                case R.id.navigation_dashboard:
                    if (lastShowFragment != 1) {
                        switchFragment(lastShowFragment, 1);
                        lastShowFragment = 1;
                    }
                    return true;
                case R.id.navigation_notifications:
                    if (lastShowFragment != 2) {
                        switchFragment(lastShowFragment, 2);
                        lastShowFragment = 2;
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        phone_num = Config.getCachedPhoneNum(this);
        token = Config.getCachedToken(this);
        user = Config.getCachedUser(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initDrawer();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        initFragments();

        Log.e("MAIN_ACTIVITY", "phone_num: " + phone_num + ", token: " + token + ", user: " + user.toString());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_login_logout) {
            if (user != null) {
//                user = null;
//                initDrawer();
                logOut();
            } else {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        } else if (id == R.id.nav_center) {
            if (user != null) {
                Intent intent = new Intent(MainActivity.this, PersonalCenterActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Config.KEY_USER, user);
                intent.putExtras(bundle);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, R.string.please_sign_in_first, Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 切换Fragment
     *
     * @param lastIndex 上个显示Fragment的索引
     * @param index     需要显示的Fragment的索引
     */
    private void switchFragment(int lastIndex, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastIndex]);
        if (!fragments[index].isAdded()) {
            transaction.add(R.id.main_activity_frag_container, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }

    private void initFragments() {
        homefrag = new HomeFragment();
        navfrag = new NavigationFragment();
        squarefrag = new SquareFragment();
        fragments = new Fragment[] {homefrag, navfrag, squarefrag};
        lastShowFragment = 0;
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_activity_frag_container, homefrag)
                .show(homefrag)
                .commit();
    }

    private void initDrawer() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        avatarView = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.view_drawer_avatar);
        nameView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.text_drawer_name);
        signView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.text_drawer_sign);
        checkInBtn = (Button) navigationView.getHeaderView(0).findViewById(R.id.btn_drawer_sign_in);

        avatarView.setOnClickListener(this);

        if (user == null) {
            nameView.setText("点击头像以登陆");
            signView.setText("");
            checkInBtn.setVisibility(View.GONE);
        } else {
            logIn(user);
        }
    }

    @Override
    protected void onStart() {
        initEventRecievor();
//        EventBus.getDefault().register(MainActivity.this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(MainActivity.this);
        super.onStop();
    }

    private void initEventRecievor() {
        if (!EventBus.getDefault().isRegistered(MainActivity.this)) {
            EventBus.getDefault().register(MainActivity.this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(MainActivity.this);
    }


    @Override
    public void onHomeFragmentInteraction(Uri uri) {

    }

    @Override
    public void onNavigationFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(Post item) {

        if (user != null) {
            Intent intent = new Intent(MainActivity.this, CommentActivity.class);
            intent.putExtra(Config.KEY_PHONE_MD5, user.getPhone());
            intent.putExtra(Config.KEY_TOKEN, token);
            intent.putExtra(Config.KEY_MSG_ID, item.getMsgId());
            startActivity(intent);
            Log.e("MAIN_ACTIVITY", item + "isClicked");
        } else {
            Toast.makeText(MainActivity.this, R.string.please_sign_in_first, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.view_drawer_avatar) {
            if (user == null) {
                Log.e("Drawer Image", "onClick: " );
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(MainActivity.this, PersonalCenterActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Config.KEY_USER, user);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
    }

    /**
     * 事件响应方法
     * 接收消息
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onLoginEvent(LoginEvent event) {
/*        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        nameView = navigationView.findViewById(R.id.text_drawer_name);
        signView =  navigationView.findViewById(R.id.text_drawer_sign);
        checkInBtn = navigationView.findViewById(R.id.btn_drawer_sign_in);*/

        user = event.getUser();
        logIn(user);

        Log.e("Login", "is recieved");
    }

    public void logIn(User user) {
        nameView.setText(user.getUserName());
        signView.setText(user.getUserProfile());
        checkInBtn.setVisibility(View.VISIBLE);
        checkInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

    }

    public void logOut() {
        user = null;
        avatarView.setOnClickListener(this);
        nameView.setText("点击头像以登陆");
        signView.setText("");
        checkInBtn.setVisibility(View.GONE);
        Toast.makeText(MainActivity.this, R.string.success_to_log_out, Toast.LENGTH_SHORT).show();
    }

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

    public String getPhone() {
        return MD5Tool.md5(phone_num);
    }
}
