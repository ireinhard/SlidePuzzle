package com.example.slidepuzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;

/**
 * Main activity that controls app running in portrait mode.
 * Name: Isaac Reinhard
 * Date: 10/3/21
 * Additional functionality:
 * -allows user to drag tiles
 * -allows user to change size of game board
 * Note: Copied over from my PuzzleApp repository as I had difficulties with displaying
 * on surface view
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //view works on portrait layout
        setContentView(R.layout.activity_main);

        //creating view and controller for app that share same model
        PuzzleSurfaceView puzzleSurfaceView = (PuzzleSurfaceView) findViewById(R.id.puzzleView);
        PuzzleController puzzleController = new PuzzleController(puzzleSurfaceView);

        //making reset Button and seekBar
        Button resetButton = (Button) findViewById(R.id.resetButton);
        SeekBar sizeBar = (SeekBar) findViewById(R.id.sizeBar);

        //making controller be an onTouch and onClick listener
        puzzleSurfaceView.setOnTouchListener(puzzleController);
        resetButton.setOnClickListener(puzzleController);

        //making controller be for seekBar
        sizeBar.setOnSeekBarChangeListener(puzzleController);
    }
}