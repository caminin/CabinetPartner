package com.sample.foo.simplewidget.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.WrapperListAdapter;

import com.sample.foo.simplewidget.Activities.MainActivity;
import com.sample.foo.simplewidget.Libraries.MyBitmapFactory;
import com.sample.foo.simplewidget.Libraries.RoundImageView;
import com.sample.foo.simplewidget.Libraries.SwipeListView.SwipeMenu;
import com.sample.foo.simplewidget.Libraries.SwipeListView.SwipeMenuCreator;
import com.sample.foo.simplewidget.Libraries.SwipeListView.SwipeMenuItem;
import com.sample.foo.simplewidget.Libraries.SwipeListView.SwipeMenuLayout;
import com.sample.foo.simplewidget.Libraries.SwipeListView.SwipeMenuListView;
import com.sample.foo.simplewidget.Libraries.SwipeListView.SwipeMenuView;
import com.sample.foo.simplewidget.R;
import com.tengchong.android.CircleLoadingView;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Adapter to show list and implements needed interface to use drag and drop and swipe
 */
public class FileListAdapter extends BaseAdapter implements DragAndDropAdapter,
                                                            WrapperListAdapter,
                                                            SwipeMenuView.OnSwipeItemClickListener,
                                                            SwipeAdapter{

    private String TAG="FileListAdapter";
    private String tag_invisible_view;//The invisible view when you drag a view
    private final int INVALID_ID = -1;// The id of an invalid item
    private Context context;
    private LayoutInflater inflater;
    private Bitmap image;

    private ArrayList<String> objects=new ArrayList<>();
    private HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

    private SwipeMenuCreator mMenuCreator;
    private SwipeMenuListView.OnMenuItemClickListener mOnMenuItemClickListener;
    private SwipeMenuLayout mTouchView;

    // constructor
    public FileListAdapter(Context context,LayoutInflater inflater) {
        this.context=context;
        this.inflater=inflater;


        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        int dp_width =(int)( context.getResources().getDimension(R.dimen.file_image_size_width) / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
        int dp_height =(int)( context.getResources().getDimension(R.dimen.file_image_size_height) / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
        //This is used to transform a high resolution image into a icon.
        image=MyBitmapFactory.decodeSampledBitmapFromResource(context.getResources(),R.drawable.example,dp_width,dp_height);

        addItem("1");
        addItem("2");
        addItem("3");
        addItem("4");
        addItem("5");
    }

    //setter to mMenuCreator
    public void setmMenuCreator(SwipeMenuCreator mMenuCreator) {
        this.mMenuCreator = mMenuCreator;
    }

    //setter to listener. Used to handle click inside the menu swiped
    public void setmOnMenuItemClickListener(SwipeMenuListView.OnMenuItemClickListener mOnMenuItemClickListener) {
        this.mOnMenuItemClickListener = mOnMenuItemClickListener;
    }

    public void setmTouchView(SwipeMenuLayout mTouchView) {
        this.mTouchView = mTouchView;
    }

    /**
     * Add an item inside the adapter. You need to add inside the idMap and the objectArray
     * @param item the item added
     */
    public void addItem(String item) {
        // Add the string into the objects
        objects.add(item);

        // size of objects should be added by one after add() above
        int index = objects.size();
        mIdMap.put(item, index);
    }


    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    /**
     * To get the id, you need to check the idMap and not the index inside objectArray
     * @param position position of the item
     * @return return the id
     */
    @Override
    public long getItemId(int position) {
        if (position < 0 || position >= mIdMap.size()) {
            return INVALID_ID;
        }
        String item = (String)getItem(position);
        return mIdMap.get(item);
    }

    /**
     * Used by the listview to get the number of view getCount returned.
     * Here it creates the view if needed, and set the wright data inside
     * @param position position of the view inside the listview
     * @param convertView the view it was before, can be null
     * @param parent the parent of the futur view
     * @return returns the new view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        View view = inflater.inflate(R.layout.list_item_file_list, parent, false);
        holder = new ViewHolder(view);
        //the tag to know what kind of view it is.
        view.setTag(objects.get(position));

        //load the image, but only a transformed one because it can be too heavy
        holder.loading.setImageBitmap(image);
        holder.loading.setPercent(88);

        //Here it is just a test to have five different items
        switch((String)view.getTag()){
            case "1":
                holder.copy_number.setText(objects.get(position));
                holder.copy_name.setText("Résultat_compa_2016.docx");
                holder.copy_user_and_time.setText("Charlot Rodolphe à 13h58");
                holder.copy_counter.setText("1");
                holder.copy_type.setText("Pages Couleurs");
                holder.copy_end_time.setText("13h59");
                break;
            case "2":
                holder.copy_number.setText(objects.get(position));
                holder.copy_picture_lock.setImageResource(R.drawable.unlock_icon);
                holder.copy_name.setText("Résultat_compa_2015.docx");
                holder.copy_user_and_time.setText("Charlot Rodolphe à 13h58");
                holder.copy_counter.setText("3");
                holder.copy_type.setText("Pages Couleurs");
                holder.copy_end_time.setText("13h58");
                break;
            case "3":
                holder.copy_number.setText(objects.get(position));
                holder.copy_name.setText("My_secret_really_secret");
                holder.copy_user_and_time.setText("Inconnu à 13h58");
                holder.copy_counter.setText("125");
                holder.copy_type.setText("Pages Noir et Blanc");
                holder.copy_end_time.setText("14h07");
                break;
            case "4":
                holder.copy_number.setText(objects.get(position));
                holder.copy_picture_lock.setImageResource(R.drawable.unlock_icon);
                holder.copy_name.setText("Images_de_chatons.png");
                holder.copy_user_and_time.setText("Nicolas Cocuaud à 13h58");
                holder.copy_counter.setText("8");
                holder.copy_type.setText("Pages Couleurs");
                holder.copy_end_time.setText("14h08");
                break;
            case "5":
                holder.copy_number.setText(objects.get(position));
                holder.copy_picture_lock.setImageResource(R.drawable.unlock_icon);
                holder.copy_name.setText("D0650d005d2xlf4.jpeg");
                holder.copy_user_and_time.setText("Gilles Guerrin");
                holder.copy_counter.setText("1");
                holder.copy_type.setText("Pages Noir et Blanc");
                holder.copy_end_time.setText("14h09");
                break;
        }

        //the swipe layout that will contain the swipe menu and the previous view
        SwipeMenuLayout layout = null;

        SwipeMenu menu = new SwipeMenu(context);
        menu.setViewType(getItemViewType(position));
        createMenu(menu);

        SwipeMenuView menuView = new SwipeMenuView(menu,(SwipeMenuListView) parent);
        menuView.setOnSwipeItemClickListener(this);//file adapter handle the listener

        //the listview parent is used to interpolate. Just use it like that
        SwipeMenuListView listView = (SwipeMenuListView) parent;
        layout = new SwipeMenuLayout(view, menuView,
                listView.getCloseInterpolator(),
                listView.getOpenInterpolator());
        layout.setPosition(position);
        layout.setSwipEnable(true);

        //If the item must be invisible, don't forget to set it invisible. The swipe layout , not the previous view -.-'
        if(((String)layout.getTag()).equals(tag_invisible_view)){
            Log.v(TAG,layout.getTag()+"mis invisible");
            layout.setVisibility(View.INVISIBLE);
        }

        return layout;
    }

    /**
     * The menu created for all the view
     * @param menu the menu you want to add the item
     */
    @Override
    public void createMenu(SwipeMenu menu) {
        if (mMenuCreator != null) {
            Log.v(TAG,"Create New Menu");
            mMenuCreator.create(menu);
        }
        else{
            Log.v(TAG,"Create Test Menu");
            // Test Code
            SwipeMenuItem item = new SwipeMenuItem(context);
            item.setTitle("Item 1");
            item.setBackground(new ColorDrawable(Color.GRAY));
            item.setWidth(300);
            menu.addMenuItem(item);

            item = new SwipeMenuItem(context);
            item.setTitle("Item 2");
            item.setBackground(new ColorDrawable(Color.BLUE));
            item.setWidth(300);
            menu.addMenuItem(item);
        }
    }

    /**
     * Handle the item click
     * @param view the swipe menu that has been clicked
     * @param menu the kind of menu
     * @param index the index
     */
    @Override
    public void onItemClick(SwipeMenuView view, SwipeMenu menu, int index) {
        boolean flag = false;
        if (mOnMenuItemClickListener != null) {
            flag = mOnMenuItemClickListener.onMenuItemClick(
                    view.getPosition(), menu, index);
        }
        if (mTouchView != null && !flag) {
            mTouchView.smoothCloseMenu();
        }
    }

    /**
     * the idMap handle the id, so it's stable
     * @return true because it's true
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * what happen when two cell need to swap
     * @param index1 index of the first one
     * @param index2 index of the second one
     */
    @Override
    public void swap(int index1, int index2) {
        String temp = objects.get(index1);
        objects.set(index1, objects.get(index2));
        objects.set(index2, temp);
        for(int i=0;i<getCount();i++){
            Log.v(TAG,""+getItem(i));
        }
        Log.v(TAG,"___________");

        notifyDataSetChanged();//you need so notify this change
    }

    /**
     * when you need to remove an item
     * @param index index of the item
     */
    public void remove(int index){
        if (index < 0 || index >= mIdMap.size()) {
            Log.v(TAG,"Suppression d'un élément hors du tableau");
        }
        else{
            String myobject=objects.get(index);
            objects.remove(index);
            mIdMap.remove(myobject);
            notifyDataSetChanged();
        }
    }

    /**
     * When the drag and drop listview want to set a view invisible.
     * @param tag_invisible_view the tag of the view that need to be invisible
     */
    @Override
    public void setInvisibleView(String tag_invisible_view) {
        this.tag_invisible_view=tag_invisible_view;
        Log.v(TAG,"J'ai mis "+tag_invisible_view+" invisible");
    }

    @Override
    public ListAdapter getWrappedAdapter() {
        return this;
    }

    /**
     * A viewholder of the view created by this adapter
     */
    static class ViewHolder{
        @Bind(R.id.copy_loading) CircleLoadingView loading;
        @Bind(R.id.copy_number) TextView copy_number;
        @Bind(R.id.copy_name) TextView copy_name;
        @Bind(R.id.copy_user_and_time) TextView copy_user_and_time;
        @Bind(R.id.copy_counter) TextView copy_counter;
        @Bind(R.id.copy_type) TextView copy_type;
        @Bind(R.id.copy_end_time) TextView copy_end_time;
        @Bind(R.id.copy_picture_lock) RoundImageView copy_picture_lock;

        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }

    /**
     * Listener used to handle the click in the swipe menu of file_adapter
     */
    public static class SwipeListener implements SwipeMenuListView.OnMenuItemClickListener {
        private MainActivity context;
        private SwipeMenuListView file_list;

        public SwipeListener(MainActivity context, SwipeMenuListView file_list) {
            this.context = context;
            this.file_list = file_list;
        }

        @Override
        public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
            switch (index) {
                case 0:
                    // open
                    Toast.makeText(context, "Transfert en cours d'implantation", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    //a great dialog
                    new LovelyStandardDialog(context)
                            .setTopColorRes(R.color.holo_pink_light)
                            .setButtonsColorRes(R.color.my_color_orange)
                            .setIcon(R.drawable.ic_delete_black_36dp)
                            .setTitle("Annulation")
                            .setMessage("Etes-vous sûr d'annuler l'impression ?")
                            .setPositiveButton(android.R.string.ok, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    file_list.smoothCloseMenu();
                                    context.removeWithIndex(position);
                                    Toast.makeText(context,"Impression annulée",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton(android.R.string.no, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    file_list.smoothCloseMenu();
                                }
                            })
                            .show();
                    break;
            }
            return false;
        }
    }
}
