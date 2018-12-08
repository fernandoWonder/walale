package com.example.fernandowonder.walale.activitys;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.fernandowonder.walale.Adapters.AdapterViewPagerPrincipal;
import com.example.fernandowonder.walale.Fragmentos;
import com.example.fernandowonder.walale.R;
import com.example.fernandowonder.walale.fragments.FragmentMusicas;
import com.example.fernandowonder.walale.fragments.FragmentsFavoritos;

import java.util.List;

public class Principal extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        setToolbar(toolbar);

        viewPager = (ViewPager)findViewById(R.id.viewPagerChangePrincipal);
        viewPager.setOffscreenPageLimit(1);
        setTab(tabLayout);
        Fragmentos fragmentos = new Fragmentos();
        fragmentos.addFrag(new FragmentMusicas(),"Música");
        fragmentos.addFrag(new FragmentsFavoritos(),"Favoritos");
        viewPager.setAdapter(new AdapterViewPagerPrincipal(getSupportFragmentManager(),fragmentos.getLista()));

    }

    public ViewPager.OnPageChangeListener viewPagerChangePrincipal()
    {
        return new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tabLayout.setSelected(true);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
    }

    public TabLayout.OnTabSelectedListener tabLayoutFavoritoMusica()
    {
        return new TabLayout.OnTabSelectedListener() {
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
        };
    }


    public void setTab(TabLayout tabLayout)
    {
        tabLayout = (TabLayout)findViewById(R.id.tabLay);
        tabLayout.addOnTabSelectedListener(tabLayoutFavoritoMusica());
        tabLayout.setupWithViewPager(viewPager);
    }


    public void tosta(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }


    public void setToolbar(Toolbar toolbar)
    {
        toolbar = (Toolbar)findViewById(R.id.actiobarToolbar);
        setSupportActionBar(toolbar);
    }





}
