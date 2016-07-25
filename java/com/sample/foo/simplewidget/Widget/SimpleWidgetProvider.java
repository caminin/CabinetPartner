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

import com.sample.foo.simplewidget.Activities.MainActivity;
import com.sample.foo.simplewidget.R;

/**
 * A widget provider to show a simple listview with all the print job.
 * It will launch the activity if you click on the buttons, and if you click on the jobs then
 * you will go to the detail's job
 */
public class SimpleWidgetProvider extends AppWidgetProvider {
    public static final String GOTO_PRINTER_CHOICE = "com.partner.cabinet.widget.printer_choice";
    public static final String GOTO_TRANSFER_FILE = "com.partner.cabinet.widget.transfer";
    public static final String GOTO_DELETE_FILE = "com.partner.cabinet.widget.delete";

    public static final String ACTION_TOAST = "com.partner.cabinet.widget.toast";
    public static final String EXTRA_STRING = "com.partner.cabinet.widget.EXTRA_STRING";

    private String TAG="SimpleWidget";
    RemoteViews remoteViews;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.v(TAG,"onUpdate");
        int main_layout = R.layout.widget_layout;
        remoteViews = new RemoteViews(context.getPackageName(), main_layout);
        widgetMenu(context,appWidgetManager,appWidgetIds);
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    /**
     * When you click on something, the action is stored and a switch search if the widget know the action.
     * If it does, then it does something.
     * Otherwise, a log is shown with UNKNOWN
     * @param context context from where the activity starts, and where the toast is showned
     * @param intent intent that is received
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action=intent.getAction();
        Intent newIntent;
        switch (action) {
            case GOTO_PRINTER_CHOICE:
                Log.v(TAG, "Onreceive Printerchoice");
                newIntent=new Intent(context, MainActivity.class);
                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(newIntent);
                break;
            case GOTO_TRANSFER_FILE:
                Log.v(TAG, "Onreceive Transfer");
                newIntent=new Intent(context, MainActivity.class);
                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(newIntent);
                break;
            case GOTO_DELETE_FILE:
                Log.v(TAG, "Onreceive Delete");
                newIntent=new Intent(context, MainActivity.class);
                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(newIntent);
                break;
            case ACTION_TOAST:
                Log.v(TAG, "Onreceive toast");
                String item = intent.getExtras().getString(EXTRA_STRING);
                Toast.makeText(context, item, Toast.LENGTH_LONG).show();
                onUpdate(context);
                break;
            default:
                Log.v(TAG, "Receive UNKNOWN :" + action);
                break;
        }



    }

    /**
     * Build the menu for the widget and add some listener to handle the click
     * @param context
     * @param appWidgetManager
     * @param appWidgetIds
     */
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
            final PendingIntent onClickPendingIntent = PendingIntent.getBroadcast(context, widgetId, onItemClick,PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setPendingIntentTemplate(R.id.listView, onClickPendingIntent);


            //handle the click on the list (TOP RIGHT)
            Intent onClickIntent = new Intent(context, MainActivity.class);
            onClickIntent.setAction(SimpleWidgetProvider.GOTO_PRINTER_CHOICE);
            remoteViews.setPendingIntentTemplate(R.id.list_printer, getPendingSelfIntent(context, GOTO_PRINTER_CHOICE));
            remoteViews.setOnClickPendingIntent(R.id.list_printer,getPendingSelfIntent(context,GOTO_PRINTER_CHOICE));

            //handle the click on the delete icon (TOP LEFT)
            onClickIntent = new Intent(context, MainActivity.class);
            onClickIntent.setAction(SimpleWidgetProvider.GOTO_DELETE_FILE);
            remoteViews.setPendingIntentTemplate(R.id.button_delete_file, getPendingSelfIntent(context, GOTO_DELETE_FILE));
            remoteViews.setOnClickPendingIntent(R.id.button_delete_file,getPendingSelfIntent(context,GOTO_DELETE_FILE));

            //handle the click on the transfer button (TOP MIDDLE LEFT
            onClickIntent = new Intent(context, MainActivity.class);
            onClickIntent.setAction(SimpleWidgetProvider.GOTO_TRANSFER_FILE);
            remoteViews.setPendingIntentTemplate(R.id.button_transfer_file, getPendingSelfIntent(context, GOTO_TRANSFER_FILE));
            remoteViews.setOnClickPendingIntent(R.id.button_transfer_file,getPendingSelfIntent(context,GOTO_TRANSFER_FILE));

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
