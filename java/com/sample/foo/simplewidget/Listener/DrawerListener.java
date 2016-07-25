package com.sample.foo.simplewidget.Listener;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.sample.foo.simplewidget.Libraries.DribSearchView;
import com.sample.foo.simplewidget.R;

/**
 * A listener for the drawer, only used inside MainActivity.
 * It's only here because you need to synchronize the dribnsearch with the drawer
 */
public class DrawerListener implements DrawerLayout.DrawerListener,NavigationView.OnNavigationItemSelectedListener {

    private MaterialMenuDrawable materialMenu;

    private DrawerLayout drawer;
    private DribSearchView dribSearchView;
    private EditText editview;
    private Toolbar toolbar;


    public DrawerListener(Context context, DrawerLayout drawer, DribSearchView dribSearchView, EditText editview, Toolbar toolbar) {
        materialMenu = new MaterialMenuDrawable(context, Color.WHITE, MaterialMenuDrawable.Stroke.THIN);
        this.drawer = drawer;
        this.dribSearchView = dribSearchView;
        this.editview = editview;
        this.toolbar = toolbar;

        Setup();
    }

    /**
     * The drawer can open when you use the dribsearchView, so you need to synchronize them.
     */
    public void Setup(){
        dribSearchView.setOnClickSearchListener(new DribSearchView.OnClickSearchListener() {
            @Override
            public void onClickSearch() {
                dribSearchView.changeLine();
                if(materialMenu.getIconState() == MaterialMenuDrawable.IconState.BURGER){
                    materialMenu.animateIconState(MaterialMenuDrawable.IconState.ARROW, true);
                }
            }
        });
        dribSearchView.setOnChangeListener(new DribSearchView.OnChangeListener() {
            @Override
            public void onChange(DribSearchView.State state) {
                switch (state) {
                    case LINE:
                        editview.setVisibility(View.VISIBLE);
                        editview.setFocusable(true);
                        editview.setFocusableInTouchMode(true);
                        editview.requestFocus();
                        break;
                    case SEARCH:
                        editview.setVisibility(View.GONE);
                        break;
                }
            }
        });

        //On met un listener sur la toolbar
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                boolean actionMade=false;
                //Si dribsearch est ouvert on le ferme
                if(editview.getVisibility()==View.VISIBLE){
                    materialMenu.animateIconState(MaterialMenuDrawable.IconState.BURGER, true);
                    dribSearchView.changeSearch();
                    actionMade=true;
                }
                //Si le drawer est ouvert on le ferme
                if(drawer.isDrawerOpen(Gravity.LEFT)){
                    drawer.closeDrawers();
                    actionMade=true;
                }

                if(actionMade==false){
                    drawer.openDrawer(Gravity.LEFT);
                }

            }
        });

        toolbar.setNavigationIcon(materialMenu);
        materialMenu.setNeverDrawTouch(true);
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        materialMenu.setTransformationValue(slideOffset);
    }

    @Override
    public void onDrawerOpened(View drawerView) {
    }

    @Override
    public void onDrawerClosed(View drawerView) {
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_print_list) {
            // Handle the camera action
        } else if (id == R.id.nav_printer_list) {

        } else if (id == R.id.nav_personal_print) {

        } else if (id == R.id.nav_account) {

        } else if (id == R.id.nav_options) {

        } else if (id == R.id.nav_stats) {

        } else if (id == R.id.nav_users) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
