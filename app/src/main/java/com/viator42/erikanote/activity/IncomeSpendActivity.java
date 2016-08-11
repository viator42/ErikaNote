package com.viator42.erikanote.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.viator42.erikanote.R;
import com.viator42.erikanote.fragment.IncomeSpendListFragment;
import com.viator42.erikanote.utils.StaticValues;

import java.util.ArrayList;
import java.util.List;

public class IncomeSpendActivity extends AppCompatActivity
        implements IncomeSpendListFragment.OnFragmentInteractionListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_spend);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());

        IncomeSpendListFragment incomeFragment = new IncomeSpendListFragment();
        Bundle incomeBundle = new Bundle();
        incomeBundle.putInt("type", StaticValues.INCOME);
        incomeFragment.setArguments(incomeBundle);
        adapter.addFragment(incomeFragment, "Income");

        IncomeSpendListFragment spendFragment = new IncomeSpendListFragment();
        Bundle spendBundle = new Bundle();
        spendBundle.putInt("type", StaticValues.SPEND);
        spendFragment.setArguments(spendBundle);
        adapter.addFragment(spendFragment, "Spend");
//        adapter.addFragment(new TabFragment(), "Category 3");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    static class TabAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(android.support.v4.app.Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

}
