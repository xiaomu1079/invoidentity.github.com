package com.example.invo.plantidentification.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.invo.plantidentification.R;
import com.example.invo.plantidentification.ui.animal.AnimalFragment;
import com.example.invo.plantidentification.ui.car.CarFragment;
import com.example.invo.plantidentification.ui.dish.DishFragment;
import com.example.invo.plantidentification.ui.plant.PlantFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2,R.string.tab_text_3,R.string.tab_text_4};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }


    /*
    * 在这里返回不同的frament
    * */
    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case 0:
                return new PlantFragment();
            case 1:
                return new AnimalFragment();
            case 2:
                return new DishFragment();
            case 3:
                return new CarFragment();

        }
        return PlaceholderFragment.newInstance(position+1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 4;
    }
}