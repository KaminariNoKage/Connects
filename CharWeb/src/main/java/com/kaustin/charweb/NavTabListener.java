package com.kaustin.charweb;

/**
 * Created by kaustin on 12/8/13.
 */

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.widget.Toast;

/**
 * Created by evan on 9/25/13.
 */
public class NavTabListener implements ActionBar.TabListener {

    public Fragment fragment;

    public NavTabListener(Fragment fragment){
        this.fragment = fragment;
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        ft.replace(R.id.fragmentContainer, fragment);
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        ft.remove(fragment);
    }
}