package com.sample.foo.simplewidget.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.github.lzyzsd.circleprogress.CircleProgress;
import com.sample.foo.simplewidget.Libraries.MyBitmapFactory;
import com.sample.foo.simplewidget.R;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Adapter to show list and implements needed interface to use drag and drop
 */
public class PrinterListAdapter extends BaseAdapter implements DragAndDropAdapter {

    public ArrayList<Integer> objects=new ArrayList<>();

    private String tag_invisible_view;//tag of the invisible view with drag and drop
    private String tag_printer_item_selected;//tag of the view that is selected by the user (clicked)
    private Bitmap ricoh;//Bitmpa of a ricoh printer
    private Bitmap samsung;//Bitmap of the samsung printer

    private final int INVALID_ID = -1;
    private Context context;
    private LayoutInflater inflater;
    private String TAG="PrinterListAdapter";
    private HashMap<Integer, Integer> mIdMap = new HashMap<Integer, Integer>();

    // constructor
    public PrinterListAdapter(Context context,LayoutInflater inflater) {
        this.context=context;
        this.inflater=inflater;
        tag_invisible_view="";
        addItem("1");
        addItem("2");
        addItem("3");
        addItem("4");

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int dp =(int)( context.getResources().getDimension(R.dimen.file_image_size_height) / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
        //This is used to transform a high resolution image into a icon.
        ricoh=MyBitmapFactory.decodeSampledBitmapFromResource(context.getResources(),R.drawable.ricoh,dp,dp);
        samsung=MyBitmapFactory.decodeSampledBitmapFromResource(context.getResources(),R.drawable.samsung,dp,dp);

    }

    /**
     * Add an item inside the adapter. You need to add inside the idMap and the objectArray
     * @param item the item added
     */
    public void addItem(String item) {
        // Add the string into the objects
        objects.add(Integer.parseInt(item));

        // size of objects should be added by one after add() above
        int index = objects.size();
        mIdMap.put(Integer.parseInt(item), index);
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
        Integer item = (Integer)getItem(position);
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

        View view = inflater.inflate(R.layout.list_item_printer_list, parent, false);
        holder = new ViewHolder(view);

        view.setTag(""+objects.get(position));

        if(((String)view.getTag()).equals(tag_printer_item_selected)){
            view.setBackgroundColor(context.getResources().getColor(R.color.Samsung_blue_light_light));
            Log.v(TAG,"j'ai vu un item select");
        }

        if(((String)view.getTag()).equals(tag_invisible_view)){
            Log.v(TAG,view.getTag()+"mis invisible");
            view.setVisibility(View.INVISIBLE);
        }

        switch((String)view.getTag()){
            case "1":
                holder.text.setText("Imprimante Comptabilité");
                holder.time.setText("05:27 PM");
                holder.connected_or_printed.setText("05/12 imprimés");
                holder.theme.setText("Compta");
                holder.circle_progress.setProgress(33);
                break;

            case "2":
                holder.text.setText("Imprimante Comptabilité 2");
                holder.time.setText("05:15 PM");
                holder.connected_or_printed.setText("01/24 imprimés");
                holder.theme.setText("Compta");
                holder.circle_progress.setProgress(50);
                break;

            case "3":
                holder.viewswitcher.showNext();
                holder.myimage.setImageBitmap(ricoh);
                holder.text.setText("Imprimante RH");
                holder.time.setVisibility(View.INVISIBLE);
                holder.connected_or_printed.setText("Connectée");
                holder.theme.setText("RH");
                holder.theme.setBackground(context.getResources().getDrawable(R.drawable.theme_orange));
                break;

            case "4":
                holder.viewswitcher.showNext();
                holder.myimage.setImageBitmap(samsung);
                holder.text.setText("Imprimante Accueil");
                holder.time.setVisibility(View.INVISIBLE);
                holder.connected_or_printed.setText("Déconnectée");
                holder.theme.setText("RH");
                holder.theme.setBackground(context.getResources().getDrawable(R.drawable.theme_orange));
                break;

        }
        return view;
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
        Integer temp = objects.get(index1);
        objects.set(index1, objects.get(index2));
        objects.set(index2, temp);
        for(int i=0;i<getCount();i++){
            Log.v(TAG,""+getItem(i));
        }
        Log.v(TAG,"___________");

        notifyDataSetChanged();
    }

    /**
     * when you need to remove an item
     * @param index index of the item
     */
    public void remove(int index){
        Integer myobject=objects.get(index);
        objects.remove(index);
        mIdMap.remove(myobject);
        notifyDataSetChanged();
    }

    /**
     * When the drag and drop listview want to set a view invisible.
     * @param tag_invisible_view the tag of the view that need to be invisible
     */
    @Override
    public void setInvisibleView(String tag_invisible_view) {
        this.tag_invisible_view=tag_invisible_view;
    }

    /**
     * obvious
     * @param tag same
     */
    public void setItemSelectedWithTag(String tag){
        tag_printer_item_selected=tag;
    }

    /**
     * obvious
     * @return same
     */
    public String getTag_printer_item_selected() {
        return tag_printer_item_selected;
    }

    /**
     * A viewholder of the view created by this adapter
     */
    static class ViewHolder{
        @Bind(R.id.myimage) ImageView myimage;
        @Bind(R.id.printer_name) TextView text;
        @Bind(R.id.theme) TextView theme;
        @Bind(R.id.time) TextView time;
        @Bind(R.id.connected_or_print) TextView connected_or_printed;
        @Bind(R.id.circle_progress) CircleProgress circle_progress;
        @Bind(R.id.viewSwitcher) ViewSwitcher viewswitcher;

        public ViewHolder(View view){
             ButterKnife.bind(this, view);
        }
    }
}