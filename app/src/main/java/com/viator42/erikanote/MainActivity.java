package com.viator42.erikanote;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.viator42.erikanote.activity.AboutActivity;
import com.viator42.erikanote.activity.IncomeSpendActivity;
import com.viator42.erikanote.activity.Settings2Activity;
import com.viator42.erikanote.fragment.HomeFragment;
import com.viator42.erikanote.fragment.IncomeSpendListFragment;
import com.viator42.erikanote.fragment.ScheduleFragment;
import com.viator42.erikanote.model.User;
import com.viator42.erikanote.utils.CommonUtils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        IncomeSpendListFragment.OnFragmentInteractionListener,
        HomeFragment.OnFragmentInteractionListener,
        ScheduleFragment.OnFragmentInteractionListener
{
    private RelativeLayout containerLayout;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Fragment homeFragment = null;
    private Fragment scheduleFragment = null;
    private Fragment incomeSpendFragment = null;

    private TextView navNameTextView;
    private ImageView headImgView;
    private User user;
    private AppContext appContext;
    private Toolbar toolbar;
    private boolean exitConfirm = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appContext = (AppContext) getApplicationContext();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerLayout = navigationView.getHeaderView(0);
        navNameTextView = (TextView) headerLayout.findViewById(R.id.name);

        headImgView = (ImageView) headerLayout.findViewById(R.id.head_img);

        containerLayout = (RelativeLayout) findViewById(R.id.container);

//        Intent intent = new Intent(MainActivity.this, ScheduleService.class);
//        startService(intent);

        if(homeFragment == null)
        {
            homeFragment = new HomeFragment();
            Bundle bundle = new Bundle();
            homeFragment.setArguments(bundle);
        }

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, homeFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(exitConfirm)
            {
                super.onBackPressed();
            }
            else
            {
                Toast.makeText(MainActivity.this, getResources().getString(R.string.click_exit), Toast.LENGTH_SHORT).show();
                exitConfirm = true;
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        user = appContext.user;

        if(appContext.firstOpen) {
            appContext.firstOpen = false;
            //进入设置页面
            Intent intent = new Intent(MainActivity.this, Settings2Activity.class);
            startActivity(intent);
        }
        else {
            if(!CommonUtils.isValueEmpty(user.name))
            {
                navNameTextView.setText(user.name);

            }
            navNameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Settings2Activity.class);
                    startActivity(intent);
                }
            });
            headImgView.setImageDrawable(getResources().getDrawable(R.drawable.icon_s));
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        exitConfirm = false;
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
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, Settings2Activity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_about) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        if(id == android.R.id.home)
        {
            this.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id)
        {
            case R.id.nav_home:
                homeFragment = new HomeFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, homeFragment);
                fragmentTransaction.commit();
                break;

            case R.id.nav_schedule:
                scheduleFragment = new ScheduleFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, scheduleFragment);
                fragmentTransaction.commit();
                break;

            case R.id.nav_income_spend:
                Intent intent = new Intent(MainActivity.this, IncomeSpendActivity.class);
                startActivity(intent);
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }
}
