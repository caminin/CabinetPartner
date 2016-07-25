package com.sample.foo.simplewidget.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.sample.foo.simplewidget.Libraries.DynamicListView;

/**
 * Used to implents all the needed functions to trasnform a listview into drang and drop listview
 */
public interface DragAndDropAdapter extends ListAdapter,DynamicListView.SwappableListAdapter{

    void addItem(String item);

    int getCount() ;

    Object getItem(int position) ;

    long getItemId(int position) ;

    View getView(int position, View convertView, ViewGroup parent) ;

    boolean hasStableIds() ;

    void swap(int index1, int index2) ;

    void setInvisibleView(String tag_invisible_view);

    void remove(int index);
}