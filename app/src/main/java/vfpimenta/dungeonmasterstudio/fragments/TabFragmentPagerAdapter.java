package vfpimenta.dungeonmasterstudio.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.HashMap;
import java.util.Map;

public class TabFragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
    private String tabTitles[] = new String[] { "Encounter calculator", "Campaign notes", "General resources" };
    private Map<Integer, Fragment> pages;

    public TabFragmentPagerAdapter(FragmentManager fm) {
        super(fm);

        pages = new HashMap<>();
        pages.put(1, EncounterCalculatorFragment.newInstance(1));
        pages.put(2, CampaignNotesFragment.newInstance(2));
        pages.put(3, UnderConstructionFragment.newInstance(3));
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public Fragment getItem(int position) {
        return pages.get(position + 1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}