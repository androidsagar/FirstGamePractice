package com.mindaro.firstgame;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Created by sagar on 2/3/18.
 */

public class ObstacleManager {

    private ArrayList<Obstacle> obstacles;
    private int playerGap;
    private int obstacleGap;
    private int obstacleHeight;
    private int color;
    private long startTime,initTime;
    private int score=0;
    private Paint paintScore;

    public ObstacleManager(int playerGap, int obstacleGap, int obstacleHeight, int color) {
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;
        obstacles = new ArrayList<>();
        startTime = initTime = System.currentTimeMillis();
        paintScore=new Paint();
        paintScore.setTextSize(100);
        populateObstacles();
    }

    public boolean playerCollide(PlayerObject playerObject){
        for(Obstacle obstacle:obstacles){
            if(obstacle.playerCollide(playerObject))
                return true;

        }
        return false;
    }

    public int getScore(){
        return score;
    }

    private void populateObstacles() {
        int currY = -5 * Constants.SCREEN_HEIGHT / 4;
        while (currY < 0) {
            int startX = (int) (Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(new Obstacle(obstacleHeight, color, startX, currY, playerGap));
            currY += obstacleHeight + obstacleGap;
        }
    }

    public void update() {
       int eplapsedTime=(int)(System.currentTimeMillis()-startTime);
       startTime =System.currentTimeMillis();
       float speed= (float)(Math.sqrt((startTime-initTime)/10000.0))*Constants.SCREEN_HEIGHT/10000.0f;
       for(Obstacle obstacle:obstacles){
            obstacle.incrementSpeed(speed* eplapsedTime);
       }
       if (obstacles.get(obstacles.size()-1).getRectangle().top >= Constants.SCREEN_HEIGHT){
           int startX = (int) (Math.random() * (Constants.SCREEN_WIDTH - playerGap));
           obstacles.add(0,new Obstacle(obstacleHeight,color,startX,
                   obstacles.get(0).getRectangle().top-obstacleHeight-obstacleGap,playerGap));
           obstacles.remove(obstacles.size()-1);
           score++;
       }
    }

    public void draw(Canvas canvas) {
       for(Obstacle obstacle:obstacles)
           obstacle.draw(canvas);
       canvas.drawText(""+score,50,50+(paintScore.descent()-paintScore.ascent()),paintScore);
    }
}
