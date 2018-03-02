package com.mindaro.firstgame;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by sagar on 2/3/18.
 */

public class MainThread extends Thread {
    public static final int MAX_FPS = 30;
    public static Canvas canvas;
    private double AVG_FPS;
    private FirstGame firstGame;
    private SurfaceHolder surfaceHolder;
    private boolean isRunning;


    MainThread(SurfaceHolder surfaceHolder, FirstGame firstGame) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.firstGame = firstGame;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    @Override
    public void run() {
        long startTime;
        long timeMillis = 1000 / MAX_FPS;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000 / MAX_FPS;

        while (isRunning) {
            startTime = System.currentTimeMillis();
            canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.firstGame.update();
                    this.firstGame.draw(canvas);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(canvas!=null){
                    try {
                        this.surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            timeMillis=(System.currentTimeMillis()-startTime);
            waitTime=targetTime-timeMillis;
            try{
            if(waitTime>0){
              sleep(waitTime);
            }
            }catch (Exception e){
                e.printStackTrace();
            }
            totalTime += System.currentTimeMillis()-startTime;
            frameCount++;
            if(frameCount==MAX_FPS){
                AVG_FPS=1000/((totalTime/frameCount));
                frameCount=0;
                totalTime=0;
                System.out.println(AVG_FPS);
            }
        }

    }
}
