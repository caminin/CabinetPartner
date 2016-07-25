package com.sample.foo.simplewidget.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sample.foo.simplewidget.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.halcyon.squareprogressbar.SquareProgressBar;

/**
 * Created by Ugo on 18/04/2016.
 */
public class FileListAdapter extends BaseAdapter implements MyAdapter {

    private String tag_invisible_view;
    final int INVALID_ID = -1;
    Context context;
    LayoutInflater inflater;
    private String TAG="PrinterListAdapter";

    ArrayList<String> objects=new ArrayList<>();
    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

    // constructor
    public FileListAdapter(Context context,LayoutInflater inflater) {
        this.context=context;
        this.inflater=inflater;

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

        View view = inflater.inflate(R.layout.list_item_file_list, parent, false);
        holder = new ViewHolder(view);
        view.setTag(objects.get(position));

        holder.text.setText("My file number : "+objects.get(position));

        if(view.getTag()==tag_invisible_view){
            Log.v(TAG,view.getTag()+"mis invisible");
            view.setVisibility(View.INVISIBLE);
        }

        holder.squareProgressBar.setImage(R.drawable.example);
        Random r=new Random();
        holder.squareProgressBar.setProgress(r.nextInt(100));

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

    static class ViewHolder{
        @Bind(R.id.file_name) TextView text;
        @Bind(R.id.sprogressbar) SquareProgressBar squareProgressBar;

        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}
