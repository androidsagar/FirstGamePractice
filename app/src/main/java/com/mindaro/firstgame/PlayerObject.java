package com.mindaro.firstgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by sagar on 2/3/18.
 */

public class PlayerObject implements GameObject {

    private Rect rectangle;
    private int color;
    private Paint paint;

    public PlayerObject(Rect rectangle, int color) {
        this.rectangle = rectangle;
        this.color = color;
        init();
    }

    private void init() {
        paint = new Paint();
    }

    public Rect getRectangle(){
        return rectangle;
    }

    @Override
    public void draw(Canvas canvas) {
        paint.setColor(Color.RED);
        canvas.drawRect(rectangle, paint);
    }

    @Override
    public void update() {

    }

    public void update(Point point) {
        rectangle.set(point.x - rectangle.width() / 2, point.y - rectangle.height() / 2,
                point.x + rectangle.width() / 2, point.y + rectangle.height() / 2);
    }
}
