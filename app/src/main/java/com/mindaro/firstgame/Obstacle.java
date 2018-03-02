package com.mindaro.firstgame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by sagar on 2/3/18.
 */

public class Obstacle implements GameObject {
    private Rect rectangle;
    private Rect rectangle2;
    private int color;
    private Paint paint;

    public void incrementSpeed(float speed){
       rectangle.top += speed;
       rectangle.bottom += speed;
       rectangle2.top += speed;
       rectangle2.bottom += speed;
    }

    public Rect getRectangle(){
        return rectangle;
    }

    public Obstacle(int obstacleHeight, int color, int startX, int startY, int playerGap) {
        this.color = color;
        paint = new Paint();

        rectangle=new Rect(0,startY,startX,startY+obstacleHeight);
        rectangle2=new Rect(startX+playerGap, startY, Constants.SCREEN_WIDTH,startY+obstacleHeight );
    }
    public boolean playerCollide(PlayerObject playerObject){
        return Rect.intersects(rectangle,playerObject.getRectangle())||Rect.intersects(rectangle2,playerObject.getRectangle());
    }

    @Override
    public void draw(Canvas canvas) {
         paint.setColor(color);
         canvas.drawRect(rectangle,paint);
         canvas.drawRect(rectangle2,paint);
    }

    @Override
    public void update() {

    }
}
