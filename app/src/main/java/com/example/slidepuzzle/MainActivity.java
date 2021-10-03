package com.example.slidepuzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;

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