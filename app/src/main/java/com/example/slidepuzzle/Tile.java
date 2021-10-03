package com.example.slidepuzzle;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Class that represents a tile on the game board.
 */
public class Tile {
    private int xPos; //xPos on board of tile
    private int yPos; //yPos on board of tile
    private Paint tileColor;
    private Paint textColor;
    private int numTile; //holds number of tile

    //Constructor method
    public Tile(int inXPos, int inYPos, int inNum){
        xPos = inXPos;
        yPos = inYPos;
        numTile = inNum;
        tileColor = new Paint();
        tileColor.setColor(Color.RED);
        textColor = new Paint();
        textColor.setColor(Color.BLACK);
        textColor.setTextSize(50);
    }

    /**
     * This class is utilzied as a helper function to draw a tile object. Based off position
     * of tile and position of tile is able to drawRect in correct location and of correct size. It
     * also draws text representing tileNum, but not for maxNum*maxNum because this blank tile.
     * @param canvas
     * @param inSize
     * @param maxNum
     */
    public void drawTile(Canvas canvas, int inSize, int maxNum){
        float xStop = xPos + inSize;
        float yStop = yPos + inSize;
        canvas.drawRect(xPos, yPos, xStop, yStop, tileColor); //drawing rectangle
        //drawing textBox on top
        if (numTile != (maxNum*maxNum)) {
            canvas.drawText(String.valueOf(numTile), (xPos + xStop) / 2, (yPos + yStop) / 2, textColor);
        }
    }

    //setter and getter methods below
    public void setxPos(int inXpos){
        xPos = inXpos;
    }

    public void setyPos(int inYPos){
        yPos = inYPos;
    }

    public void setTileColor(Paint inPaint){
        tileColor = inPaint;
    }

    public void setTileNum(int inNum){
        numTile = inNum;
    }

    public int getxPos(){ return xPos; }

    public int getyPos(){
        return yPos;
    }

    public int getTileNum(){ return numTile; }
}
