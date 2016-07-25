package com.sample.foo.simplewidget.Libraries.SwipeListView;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Menu of the swipe, let us add an item
 */
public class SwipeMenu {

    private Context mContext;
    private List<SwipeMenuItem> mItems;
    private int mViewType;

    public SwipeMenu(Context context) {
        mContext = context;
        mItems = new ArrayList<SwipeMenuItem>();
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * add an item inside the menu
     * @param item hte item added
     */
    public void addMenuItem(SwipeMenuItem item) {
        mItems.add(item);
    }

    /**
     * remove an item inside the menu
     * @param item the item removed
     */
    public void removeMenuItem(SwipeMenuItem item) {
        mItems.remove(item);
    }

    public List<SwipeMenuItem> getMenuItems() {
        return mItems;
    }

    public SwipeMenuItem getMenuItem(int index) {
        return mItems.get(index);
    }

    public int getViewType() {
        return mViewType;
    }

    public void setViewType(int viewType) {
        this.mViewType = viewType;
    }

}
