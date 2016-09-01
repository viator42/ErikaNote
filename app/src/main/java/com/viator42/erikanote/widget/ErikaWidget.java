package com.viator42.erikanote.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.viator42.erikanote.R;


/**
 * Created by Administrator on 2016/8/31.
 */
public class ErikaWidget extends View {

    private Bitmap backgroundBitmap;
    private Bitmap foregroundBitmap;
    private Paint paint;
    float posX = 0;
    float posY = 0;

    public ErikaWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        foregroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.widget_foreground);
        paint = new Paint();
        paint.setColor(Color.BLACK);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(foregroundBitmap, 0, 0, paint);
        canvas.drawText(String.valueOf(posX) + ":" + String.valueOf(posY), 60, 30, paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                posX = event.getX();
                posY = event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                posX = event.getX();
                posY = event.getY();
                break;

            case MotionEvent.ACTION_UP:
                break;
        }
        // 重新调用onDraw方法，不断重绘界面
        invalidate();

        return super.onTouchEvent(event);
    }

}
