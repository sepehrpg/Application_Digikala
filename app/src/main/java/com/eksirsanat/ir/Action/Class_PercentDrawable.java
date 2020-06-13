package com.eksirsanat.ir.Action;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import com.eksirsanat.ir.R;

public class Class_PercentDrawable extends Drawable {

    public  int percent; // is percent that we want paint
    public Paint paint;  // for draw paint
    Context context;
    public Class_PercentDrawable(int percent,Context context){
        super();
        this.percent=percent;
        this.context=context;
        this.paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        this.paint.setColor(context.getResources().getColor(R.color.sabzporrang)); //color that draw
    }

    @Override
    public void draw(Canvas canvas) {

        canvas.drawRect(0,0,percent*canvas.getWidth()/100,canvas.getHeight(),paint); //first get with and change to 100 step with /100 and then * percent
        // if draw left to right and you want reverse this use code rotate(180) for object
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
