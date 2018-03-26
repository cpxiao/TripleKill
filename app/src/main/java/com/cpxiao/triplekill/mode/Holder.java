package com.cpxiao.triplekill.mode;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.cpxiao.gamelib.mode.common.Sprite;

/**
 * @author cpxiao on 2017/11/09.
 */

public class Holder extends Sprite {
    protected Holder(Build build) {
        super(build);
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint) {
        paint.setColor(Color.RED);
        canvas.drawRect(getSpriteRectF(), paint);
        //        super.onDraw(canvas, paint);
    }

    public static class Build extends Sprite.Build {
        public Holder build() {
            return new Holder(this);
        }
    }
}
