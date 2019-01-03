package com.lotusbin.myapplication;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lotusbin.myapplication.tabhelper.TabLayoutFragmentAdaptor;

public class TabLayoutActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);

        tabLayout = findViewById(R.id.my_tab_layout_id);
        viewPager = findViewById(R.id.my_view_pager);

        tabLayout.addTab(tabLayout.newTab().setText("Home"));
        tabLayout.addTab(tabLayout.newTab().setText("Daily Work"));
        tabLayout.addTab(tabLayout.newTab().setText("Finance"));
        tabLayout.addTab(tabLayout.newTab().setText("TODO"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        TabLayoutFragmentAdaptor adaptor = new TabLayoutFragmentAdaptor(getSupportFragmentManager(), 4);
        viewPager.setAdapter(adaptor);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
