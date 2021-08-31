package com.example.ticketmaster.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.ticketmaster.Favorites_fragment;
import com.example.ticketmaster.R;
import com.example.ticketmaster.Search_fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.SEARCH, R.string.FAVORITES};
    private final Context mContext;

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();


    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        switch (position) {
            case 0:
//                return PlaceholderFragment.newInstance(position + 1);
                return new Search_fragment();
            case 1:
                return new Favorites_fragment();

            default:
                return null;
        }

//        return PlaceholderFragment.newInstance(position + 1);
    }
    public void addFragment(Fragment fragment, String title) {


        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0 :
                return "SEARCH";
            case 1:
                return "FAVORITES";
        }
//        return mContext.getResources().getString(TAB_TITLES[position]);
        return null;
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}