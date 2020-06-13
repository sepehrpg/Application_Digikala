package com.eksirsanat.ir.More_Product.Comment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathDashPathEffect;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.SeekBar;

public class Custom_Seekbar extends SeekBar {

    private Paint textPaint;
    private Rect textBound_rect=new Rect();
    private String text="";


    public Custom_Seekbar(Context context) {
        super(context);
        //Init();
        textPaint=new Paint();
        textPaint.setTypeface(Typeface.SANS_SERIF);
        textPaint.setPathEffect(new DashPathEffect(new float[] {10,10},0));

    }

    public Custom_Seekbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        Init();

    }

    public Custom_Seekbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Init();
    }


    public void Init(){
        textPaint=new Paint();
        textPaint.setTypeface(Typeface.SANS_SERIF);
        textPaint.setPathEffect(new DashPathEffect(new float[] {10,10},0));
        textPaint.setColor(Color.BLACK);

    }


    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int progress=getProgress();
        text=progress+"";
        float width=getWidth();
        float height=getHeight();

        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
        textPaint.setTextSize(40);
        textPaint.getTextBounds(text,0,text.length(),textBound_rect);

        float position=(width/getMax()) * getProgress();
        float textStart=position-textBound_rect.centerX();
        float textEnd=position+textBound_rect.centerX();

        if (textStart<=1){
            textStart=20;
        }
        if (textEnd>width) {
            textStart -= (textEnd - width + 30);
        }

        float Ypossion=height;

        canvas.drawText(text,textStart,Ypossion,textPaint);
    }

    public  synchronized void setTextColor(int Color){
        super.drawableStateChanged();
        textPaint.setColor(Color);
        textPaint.setPathEffect(new DashPathEffect(new float[] {10,10},0));
        drawableStateChanged();
    }

}
