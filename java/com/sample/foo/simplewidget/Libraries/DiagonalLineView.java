package com.sample.foo.simplewidget.Libraries;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.sample.foo.simplewidget.R;

/**
 * Used to pain a diagonal line inside a view. Used to show fractions
 */
public class DiagonalLineView extends View {

    private int dividerColor;
    private Paint paint;

    public DiagonalLineView(Context context)
    {
        super(context);
        init(context);
    }

    public DiagonalLineView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public DiagonalLineView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context)
    {
        Resources resources = context.getResources();
        dividerColor = Color.BLACK;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(dividerColor);
        paint.setStrokeWidth(resources.getDimension(R.dimen.vertical_divider_width));
    }

    /**
     * Draw a the view then a line
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawLine(getWidth()/8, (float)(getHeight()*0.875), (float)(getWidth()*0.875), getHeight()/8, paint);
    }

}