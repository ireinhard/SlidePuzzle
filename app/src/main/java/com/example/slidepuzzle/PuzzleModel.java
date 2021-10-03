package com.example.slidepuzzle;

import java.util.Random;

/**
 * This class stores the relevant data for the board
 * shared by the View class and controller class
 */
public class PuzzleModel {
    public Tile[][] board; //board is 2D array of Tile objects
    public int boardSize; //2-9, starts out as 4
    public int tileSize; //will dynamically reset if change num of tiles
    public boolean winOrNah; //holds if user has won game or Not
    static public final Random rand = new Random(); //global variable to be used

    /**
     * Default constructor
     * Citation: https://stackoverflow.com/questions/20190110/2d-int-array-shuffle
     * Citation used for logic on how to randomize board array
     * @param inBoardSize
     * @param inTileSize
     */
    public PuzzleModel(int inBoardSize, int inTileSize){
        boardSize = inBoardSize;
        tileSize = inTileSize;
        board = new Tile[boardSize][boardSize];
        winOrNah = false;
        //initializing array to hold values 0-15
        int count = 1;
        for (int i = 0; i < boardSize; i++){
            for (int j = 0; j < boardSize; j++){
                board[i][j] = new Tile(j*tileSize, i*tileSize, count);
                count++;
            }
        }
        /**
        //then randomize game board
        for(int i = boardSize - 1; i > 0; i--){
            for(int j = boardSize - 1; j > 0; j--){
                int m = rand.nextInt(i + 1);
                int n = rand.nextInt(j + 1);
                int temp = board[i][j].getTileNum();
                board[i][j].setTileNum(board[m][n].getTileNum());
                board[m][n].setTileNum(temp);
            }
        }
        */
        //checks if staring layout is a winning layout...very small odds
        checkWin();
    }

    /**
     * This method iterates through the gameboard and by extracting number of Tile
     * can determine if gameboard configuration is a winning configuration
     */
    public void checkWin(){
        int foundFalse = 0;
        int count = 1;
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                if (count != board[i][j].getTileNum()) {
                foundFalse = 1; //board not in right order
                break;
                }
            count++;
            }
        }
        if (foundFalse == 1){
           winOrNah = false;
        }
        else{ //gameboard is in right order, so update win state to be true
            winOrNah = true;
        }
    }
}