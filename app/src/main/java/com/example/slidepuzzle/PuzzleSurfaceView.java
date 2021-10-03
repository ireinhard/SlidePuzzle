package com.example.slidepuzzle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * This class extends surface view and is responsible
 * for drawing the board onto portrait orientation of the screen based on
 * puzzleModel.
 */
public class PuzzleSurfaceView extends SurfaceView {

    //Initializing variables
    private PuzzleModel puzzleModel;
    private Paint textColor;
    private Paint winColor;
    private Paint tileColor;

    //default constructor
    public PuzzleSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        puzzleModel = new PuzzleModel(4, 300); //default based on board size 4
        textColor = new Paint();
        textColor.setColor(Color.BLACK);
        textColor.setTextSize(250);
        tileColor = new Paint();
        tileColor.setColor(Color.RED);
        winColor = new Paint();
        winColor.setColor(Color.BLUE);
    }

    /**
     * Draws all tiles on board
     * also if user has won, changes tile color and displays "you win"
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas){
        //draw game board on screen
        for (int row = 0; row < puzzleModel.board.length; row++){
            for (int col = 0; col < puzzleModel.board[row].length; col++){
                puzzleModel.board[row][col].drawTile(canvas, puzzleModel.tileSize, puzzleModel.boardSize);
            }
        }
        if (puzzleModel.winOrNah){ //if user wins
            //game has been won so display game message
            for (int i = 0; i < puzzleModel.boardSize; i++){
                for (int j = 0; j < puzzleModel.boardSize; j++){
                    puzzleModel.board[i][j].setTileColor(winColor); //change color to blue
                    invalidate();
                }
            }
            canvas.drawText("YOU WIN", 500, 500, textColor); //display you win
        }
        else{ //user has not won
            for (int i = 0; i < puzzleModel.boardSize; i++){
                for (int j = 0; j < puzzleModel.boardSize; j++){
                    puzzleModel.board[i][j].setTileColor(tileColor); //set color to tile color (red)
                    invalidate();
                }
            }
        }
    }
    //setter and get methods for puzzleModel
    public PuzzleModel getPuzzleModel(){
        return puzzleModel;
    }

    public void setPuzzleModel(PuzzleModel inPuzzleModel){
        puzzleModel = inPuzzleModel;
    }
}
