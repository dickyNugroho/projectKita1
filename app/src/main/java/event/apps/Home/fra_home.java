package event.apps.Home;

/**
 * Created by rejak on 2/27/2017.
 */

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import event.apps.R;

public class fra_home extends Fragment{

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v= inflater.inflate(R.layout.fragment_home,container, false);
        final View ico1=inflater.inflate(R.layout.custom_icon1,container,false);
        final View ico2=inflater.inflate(R.layout.custom_icon,container,false);
        final View ico3=inflater.inflate(R.layout.custom_icon3,container,false);

        final TabLayout tabLayout = (TabLayout) v.findViewById(R.id.tab_layout);


        ico1.findViewById(R.id.map).setBackgroundResource(R.drawable.ic_home_tab1_dark);
        ico2.findViewById(R.id.event).setBackgroundResource(R.drawable.ic_home_tab2);
        ico3.findViewById(R.id.kategori).setBackgroundResource(R.drawable.ic_home_tab3_dark);

        tabLayout.addTab(tabLayout.newTab().setCustomView(ico1));
        tabLayout.getTabAt(0).setCustomView(ico1);
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.getTabAt(1).setCustomView(ico2);
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.getTabAt(2).setCustomView(ico3);

        final ViewPager viewPager = (ViewPager) v.findViewById(R.id.pagerhome);
        final TabPagerAdapter adapter = new TabPagerAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setCurrentItem(1);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition()==0){

                    ico1.findViewById(R.id.map).setBackgroundResource(R.drawable.ic_home_tab1);}
                if(tab.getPosition()==1){ico2.findViewById(R.id.event).setBackgroundResource(R.drawable.ic_home_tab2);}
                if(tab.getPosition()==2){ ico3.findViewById(R.id.kategori).setBackgroundResource(R.drawable.ic_home_tab3);}
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                        ico1.findViewById(R.id.map).setBackgroundResource(R.drawable.ic_home_tab1_dark);
                        ico2.findViewById(R.id.event).setBackgroundResource(R.drawable.ic_home_tab2_dark);
                        ico3.findViewById(R.id.kategori).setBackgroundResource(R.drawable.ic_home_tab3_dark);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return v;
    }

}
