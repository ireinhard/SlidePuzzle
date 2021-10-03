package com.example.slidepuzzle;

import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;

/**
 * The puzzleController controls functioning of game and is resposible for implementing
 * the various listeners (ontouch, onclick, seekbarchanged). It allows user to drag tiles to switch
 * places, rest the game board, and change the size of the game board.
 */
public class PuzzleController implements View.OnTouchListener, View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    //initializing variables
    private PuzzleModel puzzleModel;
    private PuzzleSurfaceView puzzleView;
    private int[] startCoordinates;
    private int[] endCoordinates;
    private int sizeUpdate;

    /**
     * Default constructor class
     * @param inPuzzleView which is view reprsented by model view and controller share
     */
    public PuzzleController(PuzzleSurfaceView inPuzzleView) {
        puzzleView = inPuzzleView;
        puzzleModel = puzzleView.getPuzzleModel();
        startCoordinates = new int[2];
        endCoordinates = new int[2];
        sizeUpdate = 4; //default is 4
    }

    /**
     * Method responsible for recording coordinates of where user initially clicks
     * and where they let off.
     * Citation: https://stackoverflow.com/questions/65636782/android-studio-how-to-use-implement-swipe-gesture-in-android-game
     * Citation used to develop logic to obtain up/down coordinates to be used to have swipe occur
     * @param view
     * @param motionEvent
     * @return true if event has been handled
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startCoordinates = findTileIndex(motionEvent.getX(), motionEvent.getY());
                break;
            case MotionEvent.ACTION_UP:
                endCoordinates = findTileIndex(motionEvent.getX(), motionEvent.getY());
                makeTileMove(startCoordinates, endCoordinates); //call helper function to makeMove
                puzzleView.invalidate();
                break;
        }
        return true;
    }

    /**
     * Given inputs, returns position of tile in board array
     * to be used in swapping tiles
     * @param inX
     * @param inY
     * @return
     */
    public int[] findTileIndex(float inX, float inY) {
        int index[] = new int[2];
        index[0] = (int) inY / puzzleModel.tileSize;
        index[1] = (int) inX / puzzleModel.tileSize;
        return index;
    }

    /**
     * Called by onTouch method after startCoordinates and endCoordinates of swipe movement have been
     * recorded. If swipe corresponds to valid move, then swap function is called which switches tiles
     * in board array.
     * Citation: https://stackoverflow.com/questions/65636782/android-studio-how-to-use-implement-swipe-gesture-in-android-game
     * Citation used for logic on how to know if swipe left, right, up, or down
     * @param startIndex
     * @param endIndex
     */
    public void makeTileMove(int[] startIndex, int[] endIndex) {
        int rowColBound = puzzleModel.boardSize; //within row & cols
        int outBound = -1; //off the table screen
        //first check move is within bounds
        if (endIndex[0] > outBound && endIndex[0] < rowColBound && endIndex[1] > outBound && endIndex[1] < rowColBound){ //within rows and cols
            //end location is empty tile (i.e. max num tile based on board size)
            if (puzzleModel.board[endIndex[0]][endIndex[1]].getTileNum() == (puzzleModel.boardSize*puzzleModel.boardSize)){
                //swap if on the same row and cols right or left
                if (startIndex[0] == endIndex[0] && ((startIndex[1] == endIndex[1] + 1 && endIndex[1] + 1 < rowColBound) ||
                        (startIndex[1] == endIndex[1] - 1 && endIndex[1] - 1 > outBound))) {
                    swap(startIndex, endIndex); //call helper method to swap tiles
                    puzzleModel.checkWin(); //check if user won game
                }
                //swap if on the same col and row up or down
                else if (startIndex[1] == endIndex[1] && ((startIndex[0] == endIndex[0] + 1 && endIndex[0] + 1 < rowColBound) ||
                                (startIndex[0] == endIndex[0] - 1 && endIndex[0] - 1 > outBound))) {
                    swap(startIndex, endIndex);  //call helper method to swap tiles
                   puzzleModel.checkWin(); //check if user won game
                }
            }
        }
    }

    /**
     * This method given start and end coordinates swaps tiles in board array
     * Citation: https://stackoverflow.com/questions/13280583/array-swap-2d-array/13280668 as
     * well as discussion with James Hurst who spoke to Dr. Tribelhorn on how to implement the logic.
     * @param startIndex
     * @param endIndex
     */
    public void swap(int[] startIndex, int[] endIndex){
        int oldX;
        int oldY;

        //swap x values
        oldX = puzzleModel.board[startIndex[0]][startIndex[1]].getxPos();
        puzzleModel.board[startIndex[0]][startIndex[1]].setxPos(puzzleModel.board[endIndex[0]][endIndex[1]].getxPos());
        puzzleModel.board[endIndex[0]][endIndex[1]].setxPos(oldX);

        //swap y values
        oldY = puzzleModel.board[startIndex[0]][startIndex[1]].getyPos();
        puzzleModel.board[startIndex[0]][startIndex[1]].setyPos(puzzleModel.board[endIndex[0]][endIndex[1]].getyPos());
        puzzleModel.board[endIndex[0]][endIndex[1]].setyPos(oldY);

        //replace with temp value
        Tile temp = puzzleModel.board[startIndex[0]][startIndex[1]];
        puzzleModel.board[startIndex[0]][startIndex[1]] = puzzleModel.board[endIndex[0]][endIndex[1]];
        puzzleModel.board[endIndex[0]][endIndex[1]] = temp;
    }

    /**
     * Method that handles reset button
     * @param view
     */
    @Override
    public void onClick(View view) {
        //create new instance of puzzleModel
        PuzzleModel newPuzzleModel = new PuzzleModel(sizeUpdate, tileSize(sizeUpdate));
        puzzleModel = newPuzzleModel; //update model in controller
        puzzleView.setPuzzleModel(newPuzzleModel); //update model in view
        puzzleView.invalidate(); //invalidate view screen
    }

    /**
     * Method that handles seekBar. Creates new model to be displayed based on progress
     * of seek bar.
     * @param seekBar
     * @param i
     * @param b
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        sizeUpdate = i;
        PuzzleModel newPuzzleModel = new PuzzleModel(sizeUpdate, tileSize(sizeUpdate));
        puzzleModel = newPuzzleModel; //update model in controller
        puzzleView.setPuzzleModel(newPuzzleModel); //update model in view
        puzzleView.invalidate(); //invalidate view screen
    }

    //not implemented but part of SeekBar.OnSeekBarChangeListener implementation
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) { }

    //not implemented but part of SeekBar.OnSeekBarChangeListener implementation
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) { }

    /**
     * Helper function that given boardsize returns the size of tile
     * @param inBoardSize
     * @return
     */
    public int tileSize(int inBoardSize){
        if (inBoardSize <=5){
            return 300;
        }
        else{
            return 150;
        }
    }
}