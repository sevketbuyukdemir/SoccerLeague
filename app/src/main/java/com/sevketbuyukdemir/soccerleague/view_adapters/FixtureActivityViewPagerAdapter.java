package com.sevketbuyukdemir.soccerleague.view_adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import java.util.ArrayList;

/**
 * Hold two arraylist which are titleList and FragmentsList view pager adapter provide usage of
 * ViewPager which provide to swipe between pages.
 */
public class FixtureActivityViewPagerAdapter extends FragmentPagerAdapter {
    ArrayList<String> titleList;
    ArrayList<Fragment> fragmentsList;

    public FixtureActivityViewPagerAdapter(ArrayList<String> titleList,
                                    ArrayList<Fragment> fragmentsList,
                                    @NonNull FragmentManager fragmentManager, int behavior) {
        super(fragmentManager, behavior);
        this.titleList = titleList;
        this.fragmentsList = fragmentsList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentsList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentsList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}