package com.sample.foo.simplewidget.Widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sample.foo.simplewidget.R;

import java.util.ArrayList;
import java.util.List;

public class myRemoteViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new myRemoteViewFactory(this.getApplicationContext(), intent);
    }

    class myRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
            List<String> mCollections = new ArrayList<String>();
            private String TAG="MyRemoteViewFactory";
            private final int list_item_layout= R.layout.list_item_1;

            Context mContext = null;
            /*





             */
            public myRemoteViewFactory(Context context, Intent intent) {
                mContext = context;
            }

            @Override
            public int getCount() {
                return mCollections.size();
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public RemoteViews getLoadingView() {
                Log.i(TAG, "getLoadingView()");
                return new RemoteViews(getPackageName(), R.layout.loading);
            }

            @Override
            public RemoteViews getViewAt(int position) {
                RemoteViews mView = new RemoteViews(mContext.getPackageName(),list_item_layout);
                mView.setTextViewText(R.id.textView, mCollections.get(position));

                final Intent fillInIntent = new Intent();
                fillInIntent.setAction(SimpleWidgetProvider.ACTION_TOAST);
                final Bundle bundle = new Bundle();
                bundle.putString(SimpleWidgetProvider.EXTRA_STRING,mCollections.get(position));
                fillInIntent.putExtras(bundle);
                mView.setOnClickFillInIntent(R.id.imageButtonHaut, fillInIntent);

                return mView;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }

            @Override
            public void onCreate() {
                initData();
            }

            @Override
            public void onDataSetChanged() {
                initData();
            }

            private void initData() {
                mCollections.clear();
                for (int i = 1; i <= 10; i++) {
                    mCollections.add("ListView item " + i);
                }
            }

            @Override
            public void onDestroy() {

            }

        }
}
