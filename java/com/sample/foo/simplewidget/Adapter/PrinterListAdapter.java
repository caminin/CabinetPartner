package com.sample.foo.simplewidget.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.view.LayoutInflater;

import com.github.lzyzsd.circleprogress.CircleProgress;
import com.sample.foo.simplewidget.Libraries.DynamicListView;
import com.sample.foo.simplewidget.R;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;



import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.sample.foo.simplewidget.Libraries.DynamicListView;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class PrinterListAdapter extends BaseAdapter implements MyAdapter {

    private String tag_invisible_view;
    private String tag_printer_item_selected;

    final int INVALID_ID = -1;
    Context context;
    LayoutInflater inflater;
    private String TAG="PrinterListAdapter";

    ArrayList<String> objects=new ArrayList<>();
    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

    // constructor
    public PrinterListAdapter(Context context,LayoutInflater inflater) {
        this.context=context;
        this.inflater=inflater;
        tag_invisible_view="";
        addItem("item 1");
        addItem("item 2");
        addItem("item 3");
        addItem("item 4");
        addItem("item 5");

    }

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

    @Override
    public long getItemId(int position) {
        if (position < 0 || position >= mIdMap.size()) {
            return INVALID_ID;
        }
        String item = (String)getItem(position);
        return mIdMap.get(item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        View view = inflater.inflate(R.layout.list_item_printer_list, parent, false);
        holder = new ViewHolder(view);

        holder.text.setText("Printer number : "+objects.get(position));
        view.setTag(objects.get(position));
        Random r=new Random();
        holder.circle_progress.setProgress(r.nextInt(100));

        if(view.getTag()==tag_printer_item_selected){
            view.setBackgroundColor(Color.WHITE);
        }

        if(view.getTag()==tag_invisible_view){
            Log.v(TAG,view.getTag()+"mis invisible");
            view.setVisibility(View.INVISIBLE);
        }

        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void swap(int index1, int index2) {
        String temp = objects.get(index1);
        objects.set(index1, objects.get(index2));
        objects.set(index2, temp);
        for(int i=0;i<getCount();i++){
            Log.v(TAG,""+getItem(i));
        }
        Log.v(TAG,"___________");

        notifyDataSetChanged();
    }

    @Override
    public void setInvisibleView(String tag_invisible_view) {
        this.tag_invisible_view=tag_invisible_view;
    }

    public void setItemSelectedWithTag(String tag){
        tag_printer_item_selected=tag;
    }

    public String getTag_printer_item_selected() {
        return tag_printer_item_selected;
    }

    static class ViewHolder{
        @Bind(R.id.printer_name) TextView text;
        @Bind(R.id.circle_progress) CircleProgress circle_progress;

        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}
/*
public class PrinterListAdapter extends BaseAdapter implements DynamicListView.SwappableListAdapter  {

    final int INVALID_ID = -1;

    public static int printer_item_selected;
    public static int MYPOSITION=0;
    private String TAG="PrinterListAdapter";
    private ArrayList<Integer> mylist;

    HashMap<Integer, Integer> mIdMap = new HashMap<Integer, Integer>();
    LayoutInflater inflater;
    Context context;

    public PrinterListAdapter(Context context,LayoutInflater inflater){
        this.inflater = inflater;
        this.context=context;

        mylist=new ArrayList<>();
        for(int i=0;i<9;i++){
            addItem(i);
        }
    }

    public void addItem(Integer item) {
        // Add the string into the objects
        mylist.add(item);

        // size of objects should be added by one after add() above
        int index = mylist.size();
        mIdMap.put(item, index);
    }

    @Override
    public long getItemId(int position) {
        if (position < 0 || position >= mIdMap.size()) {
            return INVALID_ID;
        }
        int item = getItem(position);
        return mIdMap.get(item);
    }

    @Override
    public int getCount() {
        return mylist.size();
    }

    @Override
    public Integer getItem(int position) {
        return mylist.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        View view = inflater.inflate(R.layout.list_item_printer_list, parent, false);
        holder = new ViewHolder(view);

        holder.text.setText("Printer number : "+position);

        if(position==printer_item_selected){
            view.setBackgroundColor(context.getResources().getColor(R.color.Samsung_blue_light_light));
        }

        view.setTag(position);

        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void swap(int index1, int index2) {
        int temp = mylist.get(index1);
        mylist.set(index1, mylist.get(index2));
        mylist.set(index2, temp);
        for(int i=0;i<getCount();i++){
            Log.v(TAG,""+getItem(i));
        }
        Log.v(TAG,"___________");

        notifyDataSetChanged();
    }

    static class ViewHolder{
        @Bind(R.id.printer_name) TextView text;

        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}*/
