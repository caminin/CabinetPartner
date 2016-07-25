/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sample.foo.simplewidget.Test;


import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.sample.foo.simplewidget.Libraries.DynamicListView;

import java.util.HashMap;
import java.util.List;

public class StableArrayAdapter extends ArrayAdapter<String> implements DynamicListView.SwappableListAdapter {

    final int INVALID_ID = -1;
    Context context;

    private String TAG="StableAdpater";
    List<String> objects;
    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

    // constructor
    public StableArrayAdapter(Context context, int rowLayout, List<String> objects) {
        super(context, rowLayout, objects);

        this.context=context;

        this.objects=objects;

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
    public long getItemId(int position) {
        if (position < 0 || position >= mIdMap.size()) {
            return INVALID_ID;
        }
        String item = getItem(position);
        return mIdMap.get(item);
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
            Log.v(TAG,getItem(i));
        }
        Log.v(TAG,"___________");

        notifyDataSetChanged();
    }
}