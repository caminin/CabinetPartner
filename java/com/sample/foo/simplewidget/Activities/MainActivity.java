package com.sample.foo.simplewidget.Activities;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.gordonwong.materialsheetfab.MaterialSheetFabEventListener;
import com.sample.foo.simplewidget.Adapter.FileListAdapter;
import com.sample.foo.simplewidget.Adapter.PrinterListAdapter;
import com.sample.foo.simplewidget.Fab.MyFab;
import com.sample.foo.simplewidget.Libraries.DynamicListView;
import com.sample.foo.simplewidget.R;
import com.sample.foo.simplewidget.Test.ListViewDraggingAnimation;
import com.sample.foo.simplewidget.Widget.CountDownView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.halcyon.squareprogressbar.SquareProgressBar;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "MainActivity";
    @Bind(R.id.list_printer) DynamicListView printer_list;
    @Bind(R.id.list_file) DynamicListView file_list;
    @Bind(R.id.countdownview) CountDownView cdv;

    PrinterListAdapter printer_adapter;
    private MaterialSheetFab materialSheetFab;
    private int statusBarColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ButterKnife.bind(this);



        printer_adapter=new PrinterListAdapter(this,(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE));

        printer_list.setAdapter(printer_adapter);
        printer_list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


        file_list.setAdapter(new FileListAdapter(this,(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE)));
        file_list.setOnItemClickListener((new android.widget.AdapterView.OnItemClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, FileActivity.class);
                i.putExtra("text",((TextView)view.findViewById(R.id.file_name)).getText());

                View sharedViewText=view.findViewById(R.id.file_name);

                Pair<View,String> p1=Pair.create(sharedViewText, getString(R.string.transitionText));

                ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,p1);
                startActivity(i, transitionActivityOptions.toBundle());
            }
        }));

        printer_list.setOnItemClickListener((new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(view.getTag()!=printer_adapter.getTag_printer_item_selected()){

                    view.setBackgroundColor(getResources().getColor(R.color.Samsung_blue_light));
                    for(View child:parent.getTouchables()){
                        if(child.getTag()==printer_adapter.getTag_printer_item_selected()){
                            child.setBackgroundColor(Color.WHITE);
                        }
                    }
                    printer_adapter.setItemSelectedWithTag((String)view.getTag());
                }
            }
        }));


        cdv.setInitialTime(300000); // Initial time of 300 seconds.
        cdv.start();

        setupTabHost();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void genNotif(){
        NotificationCompat.Builder mBuilder =
                 new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_print_white_24dp)
                        .setContentTitle("Imprimante compta 2")
                        .setContentText("Résultat_2016.pdf a été fini d'imprimer à 16h22");
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(9999, mBuilder.build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //Intent intent=new Intent(MainActivity.this,SettingsActivity.class);
            Intent intent=new Intent(MainActivity.this, ListViewDraggingAnimation.class);
            startActivity(intent);
        }
        else if(id == R.id.action_gen_notif) {
            Log.v(TAG,"Je génère une notif");
            genNotif();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setupTabHost(){
        final TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.list_fav);
        spec.setIndicator("Favoris");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.linearLayout3);
        spec.setIndicator("Tout");
        host.addTab(spec);

        for(int i=0;i<host.getTabWidget().getChildCount();i++)
        {
            TextView tv = (TextView) host.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.parseColor("#000000"));
        }

    }
    /*
    private void setupFab() {

        MyFab fab = (MyFab) findViewById(R.id.fab);
        View sheetView = findViewById(R.id.fab_sheet);
        View sheetView = findViewById(R.id.fab_sheet);
        View sheetView = findViewById(R.id.fab_sheet);
        View overlay = findViewById(R.id.overlay);
        int sheetColor = getResources().getColor(R.color.Samsung_blue_light);
        int fabColor = getResources().getColor(R.color.Samsung_blue_light);

        // Create material sheet FAB
        materialSheetFab = new MaterialSheetFab<>(fab, sheetView, overlay, sheetColor, fabColor);

        // Set material sheet event listener
        materialSheetFab.setEventListener(new MaterialSheetFabEventListener() {
            @Override
            public void onShowSheet() {
                // Save current status bar color
                statusBarColor = getStatusBarColor();
                // Set darker status bar color to match the dim overlay
                setStatusBarColor(getResources().getColor(R.color.Samsung_blue_light));
            }

            @Override
            public void onHideSheet() {
                // Restore status bar color
                setStatusBarColor(statusBarColor);
            }
        });



        // Set material sheet item click listeners
        findViewById(R.id.fab_sheet_item_recording).setOnClickListener(this);
        findViewById(R.id.fab_sheet_item_reminder).setOnClickListener(this);
        findViewById(R.id.fab_sheet_item_photo).setOnClickListener(this);
        findViewById(R.id.fab_sheet_item_note).setOnClickListener(this);
    }*/

    private int getStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getWindow().getStatusBarColor();
        }
        return 0;
    }
    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
        }
    }
}
