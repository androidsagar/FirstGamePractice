package com.mindaro.firstgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.constraint.solver.widgets.Rectangle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by sagar on 2/3/18.
 */

public class FirstGame extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread mainThread;
    private PlayerObject playerObject;
    private Rect r=new Rect();
    private Point playerPoint;
    private ObstacleManager obstacleManager;
    private boolean movingPlayer=false, gameOver=false;
    private long gameOverTime;

    public FirstGame(Context context) {
        super(context);
        getHolder().addCallback(this);
        mainThread=new MainThread(getHolder(),this);
        setFocusable(true);
      init();
    }

    private void init(){
        playerObject=new PlayerObject(new Rect(100,100,200,200), Color.RED);
        playerPoint=new Point(Constants.SCREEN_WIDTH/2,3*Constants.SCREEN_HEIGHT/4);
        playerObject.update(playerPoint);
        obstacleManager=new ObstacleManager(200,350,75,Color.BLACK);
        movingPlayer = false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mainThread=new MainThread(getHolder(),this);
        mainThread.setRunning(true);
        mainThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry=true;
        while (retry){
            try {
                mainThread.setRunning(false);
                mainThread.join();
                retry =false;
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(!gameOver && playerObject.getRectangle().contains((int)event.getX(),(int)event.getY()))
                    movingPlayer =true;

                if(gameOver && (System.currentTimeMillis() - gameOverTime) > 2000 ){
                    init();
                    gameOver=false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(!gameOver && movingPlayer)
                playerPoint.set((int)event.getX(),(int)event.getY());
                break;

            case MotionEvent.ACTION_UP:
                movingPlayer=false;
                break;
        }
        return true;
    }

    public void update(){
        if(!gameOver){
            playerObject.update(playerPoint);
            obstacleManager.update();

            if(obstacleManager.playerCollide(playerObject)){
                gameOver=true;
                gameOverTime=System.currentTimeMillis();
            }

        }

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.WHITE);
        playerObject.draw(canvas);
        obstacleManager.draw(canvas);
        if(gameOver){
            Paint paint=new Paint();
            paint.setColor(Color.YELLOW);
            Rect rect=new Rect();
            int left=canvas.getWidth()/10;
            int right=canvas.getWidth()-left;
            int top = (canvas.getHeight()-(right-left))/2;
            int bottom=top+(right-left);
            rect.set(left,top, right, bottom);
            canvas.drawRect(rect,paint);

            paint.setTextSize(rect.width()/10);
            paint.setColor(Color.RED);
            drawCenterText(canvas,r,paint,"GAME OVER!");
        }
    }

    private void drawCenterText(Canvas canvas, Rect r ,Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
    }
}
