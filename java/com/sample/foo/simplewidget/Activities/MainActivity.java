package com.sample.foo.simplewidget.Activities;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.kogitune.activity_transition.ActivityTransitionLauncher;
import com.sample.foo.simplewidget.Adapter.FileListAdapter;
import com.sample.foo.simplewidget.Adapter.PrinterListAdapter;
import com.sample.foo.simplewidget.Libraries.DribSearchView;
import com.sample.foo.simplewidget.Libraries.DynamicListView;
import com.sample.foo.simplewidget.Libraries.ShowcaseList;
import com.sample.foo.simplewidget.Libraries.SwipeListView.SwipeMenu;
import com.sample.foo.simplewidget.Libraries.SwipeListView.SwipeMenuCreator;
import com.sample.foo.simplewidget.Libraries.SwipeListView.SwipeMenuItem;
import com.sample.foo.simplewidget.Libraries.SwipeListView.SwipeMenuListView;
import com.sample.foo.simplewidget.Listener.DrawerListener;
import com.sample.foo.simplewidget.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.iwgang.countdownview.CountdownView;

/**
 * Main activity of the apk, contains all you need
 * Has a drawer and a toolbar
 * Is the listener for the drawer
 */
public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";
    //All xml used with Butterknife
    @Bind(R.id.list_printer)    DynamicListView printer_list;
    @Bind(R.id.list_file)       SwipeMenuListView file_list;
    @Bind(R.id.toolbar)         Toolbar toolbar;
    @Bind(R.id.drawer_layout)   DrawerLayout drawer;
    @Bind(R.id.spinner)         MaterialSpinner spinner;
    @Bind(R.id.editview)        EditText editview;
    @Bind(R.id.dribSearchView)  DribSearchView dribSearchView;
    @Bind(R.id.countdownView)   CountdownView mCvCountdownView;

    //Adapter des deux listview
    private PrinterListAdapter printer_adapter;
    private FileListAdapter file_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_1_nav_drawer);
        ButterKnife.bind(this);

        //All the setup functions
        setupDrawerAndToolbar();
        setupFileList();
        setupPrinterList();
        setupTabHost();
        setupCountDown();
        setupSpinner();
     }

    /**
     * Close the drawer when you press back button
     */
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Generate a notification.
     * USED FOR TEST ONLY
     */
    public void genNotif(){
        NotificationCompat.Builder mBuilder =
                 new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_print_white_24dp)
                        .setContentTitle("Imprimante compta 2")
                        .setContentText("Résultat_2016.pdf a été fini d'imprimer à 16h22");
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on. Here it's 9999
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

        if (id == R.id.action_settings) {//if you push the settings button inside the top right button
            Log.v(TAG,"SETTINGS IN PROGRESS");
        }
        else if(id == R.id.action_gen_notif) {//if you push the gen notif button inside the top right button
            Log.v(TAG,"Je génère une notif");
            genNotif();
        }
        else if(id == R.id.action_gen_showcase) {//if you push the gen showcase button inside the top right button
            Log.v(TAG,"Je génère un showcase");
            ShowcaseList showcase=new ShowcaseList(this);
            showcase.addView(file_list);//add a view for a showcase
            showcase.addView(printer_list);
            showcase.addView(dribSearchView);

            showcase.start();//start a showcase
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Convert dp unit into px unit
     * @param dp dp you want to convert
     * @return the px linked with the dp
     */
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    /**
     * Setup the spinner and its content.
     */
    public void setupSpinner(){
        spinner.setItems("Filtre","Vos impressions");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Setup the countdown
     */
    public void setupCountDown(){
        mCvCountdownView.start(95550000); //setup the countdown view
    }

    /**
     * Setup the toolbar and the drawer.
     */
    public void setupDrawerAndToolbar(){
        toolbar.setTitle(R.string.activity_main_title);
        toolbar.setSubtitle(R.string.activity_main_subtitle);
        setSupportActionBar(toolbar);
        //noinspection deprecation
        drawer.setDrawerListener(new DrawerListener(this,drawer,dribSearchView,editview,toolbar));//Contains a search view writen in DribSearchView
    }

    /**
     * Setup a file list, create the adapter and handle the click and the swipe and the drag and drop
     */
    public void setupFileList(){
        file_adapter=new FileListAdapter(this,(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE));

        //Handle the click with a listener.
        file_list.setOnItemClickListener((new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, FileActivity.class);
                intent.putExtra("nomDoc",((TextView)view.findViewById(R.id.copy_name)).getText());
                //This is a animated transition made with ActivityTransitionLauncher
                ActivityTransitionLauncher.with(MainActivity.this).from(view.findViewById(R.id.copy_loading)).launch(intent);

            }
        }));

        //Set the direction of the qcype
        file_list.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);

        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem transferItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                transferItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xED, 0xFF)));
                // set item width
                transferItem.setWidth(dp2px(80));
                transferItem.setIcon(R.drawable.ic_send_black_24dp);
                // add to menu
                menu.addMenuItem(transferItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xEC, 0x28, 0x28)));
                // set item width
                deleteItem.setWidth(dp2px(80));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete_black_36dp);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        file_list.setMenuCreator(creator);

        // step 2. listener item click event
        FileListAdapter.SwipeListener listener=new FileListAdapter.SwipeListener(this,file_list);
        file_list.setOnMenuItemClickListener(listener);

        // make the swiped item bounce
        file_list.setCloseInterpolator(new BounceInterpolator());

        file_list.setAdapter(file_adapter);//THIS NEED TO BE AFTER ANY CHANGE IN THE FILE LIST (LSS PUT IT AT THE END)
    }

    /**
     * Setup the printer list, handle the click and the drag and drop
     */
    public void setupPrinterList(){
        printer_adapter=new PrinterListAdapter(this,(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE));

        printer_list.setAdapter(printer_adapter);
        printer_list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        //Handle the click with a animation (Ripple effect) and a selected item
        printer_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(view.getTag()!=printer_adapter.getTag_printer_item_selected()){//If the item selected is not already selected

                    for(View child:parent.getTouchables()){//we get the item that we can see
                        if(child.getTag()!=view.getTag()){//we take only the item not selected and we make them normal
                            child.setBackgroundColor(Color.WHITE);//Background is now white
                            FrameLayout selected_bar=(FrameLayout)child.findViewById(R.id.selected_bar);
                            if(selected_bar!=null){
                                selected_bar.setVisibility(View.INVISIBLE);//The right bar is now not visible
                            }
                        }
                    }
                    //Now we can make the selected item with the proper background
                    //noinspection deprecation
                    view.setBackgroundColor(getResources().getColor(R.color.Samsung_blue_light_light));
                    //And the bar selected
                    FrameLayout selected_bar=(FrameLayout)view.findViewById(R.id.selected_bar);
                    selected_bar.findViewById(R.id.selected_bar).setVisibility(View.VISIBLE);
                    //LikeButton is animated
                    //noinspection deprecation
                    view.findViewById(R.id.star_button).setBackgroundColor(getResources().getColor(R.color.Samsung_blue_light_light));

                    //You have to tell the adapter that the uitem is selected, otherwise if you scroll it will disappear
                    printer_adapter.setItemSelectedWithTag(""+view.getTag());
                    Log.v(TAG,""+view.getTag());
                }
            }
        });
    }

    /**
     * Setup the tabhost on the left and right side
     */
    public void setupTabHost(){
        final TabHost host = (TabHost)findViewById(R.id.tabHostLeft);
        setupBeginTabHost(host);//Set the color
        //Tab 1
        addTab(host,"Tab One",R.id.list_fav,"Favoris");//Add a tab
        //Tab 2
        addTab(host,"Tab Two",R.id.linearLayout3,"Tout");//Add a tab
        setupEndTabHost(host);//set the color og the selected and the new textview inside tabhost

//        Second host
        final TabHost host2 = (TabHost)findViewById(R.id.tabHostRight);
        setupBeginTabHost(host2);//Set the color
        //Tab 1
        addTab(host2,"Tab One",R.id.accueil,"Accueil");//Add a tab
        //Tab 2
        addTab(host2,"Tab Two",R.id.details,"Details imprimante");//Add a tab
        setupEndTabHost(host2);//set the color og the selected and the new textview inside tabhost
    }

    /**
     * Set the color of the new tabhost and use setup()
     * @param tabHost the tabhost used
     */
    public void setupBeginTabHost(final TabHost tabHost){
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {

                for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
                    //noinspection deprecation
                    tabHost.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.Samsung_blue_light)); // unselected
                    TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
                    //noinspection deprecation
                    tv.setTextColor(getResources().getColor(R.color.Samsung_blue_light_light));
                }

                TextView tv = (TextView) tabHost.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
                tv.setTextColor(Color.parseColor("#FFFFFF"));

            }
        });
        tabHost.setup();
    }

    /**
     * add the color to the new textview
     * @param tabHost the tabhost used
     */
    public void setupEndTabHost(final TabHost tabHost){
        TextView tv;
        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {
            tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            //noinspection deprecation
            tv.setTextColor(getResources().getColor(R.color.Samsung_blue_light_light));
        }
        tv = (TextView) tabHost.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
        tv.setTextColor(Color.parseColor("#FFFFFF"));
    }

    /**
     * Add a tab to the tabhost
     * @param host the tabhost used
     * @param tab name of the tab
     * @param id_content id of the content of the new tab
     * @param tab_name name of the new tab, shown
     */
    public void addTab(TabHost host,String tab, int id_content,String tab_name){
        TabHost.TabSpec spec = host.newTabSpec(tab);
        spec.setContent(id_content);
        spec.setIndicator(tab_name);
        host.addTab(spec);
    }

    /**
     * remove an item inside filedapater. Used by swipelistener because it can cause some lag if you pass the context
     * to swipelistener so it can use it.
     * @param index index of the item deleted
     */
    public void removeWithIndex(int index){
        file_adapter.remove(index);
        file_adapter.notifyDataSetChanged();
    }




}
