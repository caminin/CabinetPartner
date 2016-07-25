package com.sample.foo.simplewidget.Activities;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kogitune.activity_transition.ActivityTransition;
import com.kogitune.activity_transition.ExitActivityTransition;
import com.sample.foo.simplewidget.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Used to show the infos of a file in the job list.
 * Use some animation with library AnimationClass, so it contains an enter animation and an exit animation
 */
public class FileActivity extends AppCompatActivity   {

    private String TAG="FileActivity";
    //All xml used with Butterknife
    @Bind(R.id.file_title)  TextView file_title;//File_name
    @Bind(R.id.image)       View mImageView;//Image
    @Bind(R.id.toolbar)     Toolbar toolbar;
    @Bind(R.id.file_details)LinearLayout file_details; //All the content


    private int duration=1000; // duration of the enter and exit animation
    private ExitActivityTransition exitTransition; //the exit animation is stored here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //If SDK is too high, AnimationClass may need this
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        setContentView(R.layout.activity_file_details);
        ButterKnife.bind(this);

        //Run the enter animation
        new AnimationClass().execute(savedInstanceState,null,null);

        //set the title of the toolbar
        if (toolbar != null) {
            toolbar.setTitle(R.string.activity_file_title);
            toolbar.setSubtitle(R.string.activity_file_subtitle);
        }
        setSupportActionBar(toolbar);
        file_title.setText(getIntent().getStringExtra("nomDoc"));
    }

    /**
     * AsyncTask to wait some time, needed to have a good animation (The picture floats to the left,
     * then (and it waits here) the text appears. This need to be ASync to use wait.
     * You have to pass Bundle to launch the animation
     */
    private class AnimationClass extends AsyncTask<Bundle, Void, Void> {
        @Override
        protected Void doInBackground(Bundle... params) {
            synchronized(this){
                try {
                    //run the animation and store the exit animation
                    exitTransition = ActivityTransition.with(getIntent()).to(mImageView).duration(duration).start(params[0]);
                    wait((long)(duration/1.5));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            file_details.setVisibility(View.VISIBLE);
        }
    }

    /**
     * When you exit, you need to launch the exit animation
     */
    @Override
    public void onBackPressed() {
        file_details.setVisibility(View.INVISIBLE);
        exitTransition.exit(this);
        super.onBackPressed();
    }
};
