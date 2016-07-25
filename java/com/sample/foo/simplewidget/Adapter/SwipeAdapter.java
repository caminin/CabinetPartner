package com.sample.foo.simplewidget.Adapter;

import android.widget.ListAdapter;

import com.sample.foo.simplewidget.Libraries.SwipeListView.SwipeMenu;
import com.sample.foo.simplewidget.Libraries.SwipeListView.SwipeMenuCreator;
import com.sample.foo.simplewidget.Libraries.SwipeListView.SwipeMenuLayout;
import com.sample.foo.simplewidget.Libraries.SwipeListView.SwipeMenuListView;
import com.sample.foo.simplewidget.Libraries.SwipeListView.SwipeMenuView;

/**
 * Used to transform a normal listview into a swipe handler listview
 */
public interface SwipeAdapter extends ListAdapter {

    void onItemClick(SwipeMenuView view, SwipeMenu menu, int index);
    void createMenu(SwipeMenu menu);//Needed to create the menu

    void setmMenuCreator(SwipeMenuCreator mMenuCreator);
    void setmOnMenuItemClickListener(SwipeMenuListView.OnMenuItemClickListener mOnMenuItemClickListener);
    void setmTouchView(SwipeMenuLayout mTouchView);

}
