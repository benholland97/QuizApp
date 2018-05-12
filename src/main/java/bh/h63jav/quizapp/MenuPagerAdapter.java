package bh.h63jav.quizapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

class MenuPagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();

    MenuPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    void addFragment(Fragment fragment, String title, Bundle extras) {
        fragment.setArguments(extras);
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
    }


    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }


}
