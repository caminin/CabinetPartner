package com.sample.foo.simplewidget.Activities;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;
import com.sample.foo.simplewidget.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FileActivity extends BaseActivity implements ObservableScrollViewCallbacks {

    private String TAG="FileActivity";
    @Bind(R.id.file_title)  TextView file_title;
    @Bind(R.id.image)       View mImageView;
    @Bind(R.id.toolbar)     View mToolbarView;
    @Bind(R.id.scroll)      ObservableScrollView mScrollView;
    private int mParallaxImageHeight;
    private float maxTextSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_layout);
        ButterKnife.bind(this);

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        maxTextSize = getResources().getDimension(R.dimen.text_file_detail)/dm.scaledDensity;

        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.Samsung_blue)));

        mScrollView.setScrollViewCallbacks(this);

        mParallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.parallax_image_height);

        //((NumberProgressBar)findViewById(R.id.number_progress_bar)).setProgress(getIntent().getIntExtra("progress",0));
        //((TextView)findViewById(R.id.file_name)).setText(getIntent().getStringExtra("text"));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onScrollChanged(mScrollView.getCurrentScrollY(), false, false);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        int baseColor = getResources().getColor(R.color.Samsung_blue);
        float alpha = Math.min(1, (float) scrollY / mParallaxImageHeight);
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
        ViewHelper.setTranslationY(mImageView, scrollY / 2);
        float textSize = Math.max(maxTextSize/10, maxTextSize-maxTextSize*alpha/2);
        file_title.setTextSize(textSize);

    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }
}
