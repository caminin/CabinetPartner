package com.sample.foo.simplewidget.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.sample.foo.simplewidget.R;

public class SimpleWidgetProvider extends AppWidgetProvider {
    public static final String GOTO_PRINTER_CHOICE = "com.partner.cabinet.widget.printer_choice";
    public static final String ACTION_BACK = "com.partner.cabinet.widget.back";
    public static final String GOTO_TRANSFER_FILE = "com.partner.cabinet.widget.transfer";

    public static final String ACTION_TOAST = "com.partner.cabinet.widget.toast";
    public static final String EXTRA_STRING = "com.partner.cabinet.widget..EXTRA_STRING";

    private final int main_layout= R.layout.layout_1;

    private String TAG="SimpleWidget";
    int widget_layout=main_layout;
    RemoteViews remoteViews;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.v(TAG,"onUpdate");
        remoteViews = new RemoteViews(context.getPackageName(),widget_layout);

        switch(widget_layout){
            case main_layout:
                widgetMenu(context,appWidgetManager,appWidgetIds);
                break;
            case R.layout.printer_choice:
                printerChoice(context,appWidgetManager,appWidgetIds);
                break;
            case R.layout.transfer_current_print_choice:
                transferFile(context,appWidgetManager,appWidgetIds);
                break;
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action=intent.getAction();

        switch (action) {
            case GOTO_PRINTER_CHOICE:
                Log.v(TAG, "Onreceive Printerchoice");

                widget_layout = R.layout.printer_choice;
                onUpdate(context);
                break;
            case ACTION_BACK:
                Log.v(TAG, "Onreceive Back");

                widget_layout = main_layout;
                onUpdate(context);
                break;
            case GOTO_TRANSFER_FILE:
                Log.v(TAG, "Onreceive Transfer");

                widget_layout = R.layout.transfer_current_print_choice;
                onUpdate(context);
                break;
            case ACTION_TOAST:
                Log.v(TAG, "Onreceive toast");
                String item = intent.getExtras().getString(EXTRA_STRING);
                Toast.makeText(context, item, Toast.LENGTH_LONG).show();
                break;
            default:
                Log.v(TAG, "Receive UNKNOWN :" + action);
                break;
        }



    }

    private void widgetMenu(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        final int count = appWidgetIds.length;
        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];

            // set intent for widget service that will create the views
            Intent serviceIntent = new Intent(context, myRemoteViewService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME))); // embed extras so they don't get ignored
            remoteViews.setRemoteAdapter(R.id.listView, serviceIntent);

            // Adding collection list item handler
            final Intent onItemClick = new Intent(context, SimpleWidgetProvider.class);
            onItemClick.setAction(ACTION_TOAST);
            onItemClick.setData(Uri.parse(onItemClick.toUri(Intent.URI_INTENT_SCHEME)));
            final PendingIntent onClickPendingIntent = PendingIntent.getBroadcast(context, widgetId, onItemClick,
                            PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setPendingIntentTemplate(R.id.listView, onClickPendingIntent);


            Intent onClickIntent = new Intent(context, SimpleWidgetProvider.class);
            onClickIntent.setAction(SimpleWidgetProvider.GOTO_PRINTER_CHOICE);
            remoteViews.setPendingIntentTemplate(R.id.list_printer, getPendingSelfIntent(context, GOTO_PRINTER_CHOICE));
            remoteViews.setOnClickPendingIntent(R.id.list_printer,getPendingSelfIntent(context,GOTO_PRINTER_CHOICE));

            onClickIntent = new Intent(context, SimpleWidgetProvider.class);
            onClickIntent.setAction(SimpleWidgetProvider.GOTO_TRANSFER_FILE);
            remoteViews.setPendingIntentTemplate(R.id.button_transfer_file, getPendingSelfIntent(context, GOTO_TRANSFER_FILE));
            remoteViews.setOnClickPendingIntent(R.id.button_transfer_file,getPendingSelfIntent(context,GOTO_TRANSFER_FILE));

            appWidgetManager.partiallyUpdateAppWidget(widgetId, remoteViews);
        }
    }

    private void printerChoice(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        final int count = appWidgetIds.length;
        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];

            // set intent for widget service that will create the views
            Intent serviceIntent = new Intent(context, myRemoteViewService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME))); // embed extras so they don't get ignored
            remoteViews.setRemoteAdapter(R.id.listView, serviceIntent);


            final Intent onClickIntent = new Intent(context, SimpleWidgetProvider.class);
            onClickIntent.setAction(SimpleWidgetProvider.ACTION_BACK);
            remoteViews.setPendingIntentTemplate(R.id.button_back, getPendingSelfIntent(context,ACTION_BACK));
            remoteViews.setOnClickPendingIntent(R.id.button_back,getPendingSelfIntent(context,ACTION_BACK));

            appWidgetManager.partiallyUpdateAppWidget(widgetId, remoteViews);
        }
    }

    private void transferFile(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        final int count = appWidgetIds.length;
        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];

            // set intent for widget service that will create the views
            Intent serviceIntent = new Intent(context, myRemoteViewService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME))); // embed extras so they don't get ignored
            remoteViews.setRemoteAdapter(R.id.listViewFile, serviceIntent);

            // set intent for widget service that will create the views
            Intent serviceIntent2 = new Intent(context, myRemoteViewService.class);
            serviceIntent2.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            serviceIntent2.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME))); // embed extras so they don't get ignored
            remoteViews.setRemoteAdapter(R.id.listViewPrinters, serviceIntent2);


            final Intent onClickIntent = new Intent(context, SimpleWidgetProvider.class);
            onClickIntent.setAction(SimpleWidgetProvider.ACTION_BACK);
            remoteViews.setPendingIntentTemplate(R.id.button_back, getPendingSelfIntent(context,ACTION_BACK));
            remoteViews.setOnClickPendingIntent(R.id.button_back,getPendingSelfIntent(context,ACTION_BACK));

            appWidgetManager.partiallyUpdateAppWidget(widgetId, remoteViews);
        }
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    private void onUpdate(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance
                (context);

        // Uses getClass().getName() rather than MyWidget.class.getName() for
        // portability into any App Widget Provider Class
        ComponentName thisAppWidgetComponentName =
                new ComponentName(context.getPackageName(),getClass().getName()
                );
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                thisAppWidgetComponentName);
        onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
