package event.apps.profil;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import event.apps.Home.fra_home_event;
import event.apps.Home.fra_home_kategori;
import event.apps.Home.fra_home_map;

/**
 * Created by rejak on 3/5/2017.
 */

public class Pager_tab_profile extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public Pager_tab_profile(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0 :
                fra_list tab1 = new fra_list();
                return tab1;
            case 1 :
                fra_acara tab2 = new fra_acara();
                return tab2;


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
