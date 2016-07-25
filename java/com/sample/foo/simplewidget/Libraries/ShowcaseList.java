package com.sample.foo.simplewidget.Libraries;

import android.app.Activity;
import android.view.View;

import java.util.ArrayList;

import uk.co.deanwild.materialshowcaseview.IShowcaseListener;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

/**
 * A showcase could be list of some showcases, so it's just an array of showcases that triggers
 * one after the others. Very useful, but need some work
 * TODO : add text to new showcases
 */
public class ShowcaseList implements IShowcaseListener {
    Activity myactivity;
    int current_index;
    ArrayList<View> list_view;

    /**
     * you have to add the activity where the showcase is gonna be
     * @param myactivity the activity where the showcase is gonna be
     */
    public ShowcaseList(Activity myactivity){
        this.myactivity=myactivity;
        current_index=0;
        list_view=new ArrayList<>();
    }

    /**
     * add a view to the showcase
     */
    public void addView(View v){
        list_view.add(v);
    }

    /**
     * Start at the number 0 item and go until it ends
     */
    public void start(){
        current_index=0;
        if(current_index<list_view.size()){
            new MaterialShowcaseView.Builder(myactivity)
                    .setTarget(list_view.get(current_index))
                    .setDismissOnTouch(true)
                    .setContentText("This is some amazing feature you should know about")
                    .setDelay(500) // optional but starting animations immediately in onCreate can make them choppy
                    .setListener(this)
                    .show();
        }
    }

    @Override
    public void onShowcaseDisplayed(MaterialShowcaseView materialShowcaseView) {
    }

    /**
     * When a showcase is over, try to find the next one. If not, then it ends.
     * @param materialShowcaseView
     */
    @Override
    public void onShowcaseDismissed(MaterialShowcaseView materialShowcaseView) {
        current_index++;
        if(current_index<list_view.size()){
            new MaterialShowcaseView.Builder(myactivity)
                    .setTarget(list_view.get(current_index))
                    .setDismissOnTouch(true)
                    .setContentText("This is some amazing feature you should know about")
                    .setDelay(500) // optional but starting animations immediately in onCreate can make them choppy
                    .setListener(this)
                    .show();
        }
    }
};
