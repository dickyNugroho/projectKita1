package event.apps.Home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import event.apps.Home.fra_home_event;
import event.apps.Home.fra_home_kategori;
import event.apps.Home.fra_home_map;

/**
 * Created by rejak on 3/5/2017.
 */

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public TabPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0 :
                fra_home_map tab1 = new fra_home_map();
                return tab1;
            case 1 :
                fra_home_event tab2 = new fra_home_event();
                return tab2;
            case 2 :
                fra_home_kategori tab3 = new fra_home_kategori();
                return tab3;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
