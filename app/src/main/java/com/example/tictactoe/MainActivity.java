package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView winnerMessage;
    // 0 means empty, 1 means cross, 2 means zero
    int[][] grid = {{0,0,0}, {0,0,0}, {0,0,0}};

    //ImageViews
    ImageView i1, i2, i3, i4, i5, i6, i7, i8, i9;
    boolean player1 = true; // cross
    boolean player2 = false; // zero
    String player1Name = "";
    String player2Name = "";
    boolean isGameGoing = true;
    int turns = 0;

    public void isWinnerHelper(int num) {
        if (num == 1) winnerMessage.setText(player1Name + " is the winner");
        else winnerMessage.setText(player2Name + " is the winner");
    }

    public boolean isWinner() {
        // checking diagonal with the first element [0][0]
        int n = grid[0][0];

        if (n != 0) {
            for (int i = 1; i <= 2; i++) {
                if (grid[i][i] != n) break;
                if (i == 2) {
                    isWinnerHelper(n);
                    return false;
                }
            }
        }

        // checking diagonal with the first element [0][2]
        n = grid[0][2];
        int k = 0;
        if (n != 0) {
            for (int i = 1; i >= 0; i--) {
                if (grid[++k][i] != n) break;
                if (i == 0) {
                    isWinnerHelper(n);
                    return false;
                }
            }
        }

        //checking horizontal lines
        outer:
        for (int i = 0; i <= 2; i++) {
            n = grid[i][0];
            if (n != 0) {
                for (int j = 1; j <= 2; j++) {
                    if (grid[i][j] != n) continue outer;
                    if (j == 2) {
                        isWinnerHelper(n);
                        return false;
                    }
                }
            }
        }

        //checking vertical lines
        outer:
        for (int i = 0; i <= 2; i++) {
            n = grid[0][i];
            if (n != 0) {
                for (int j = 1; j <= 2; j++) {
                    if (grid[j][i] != n) continue outer;
                    if (j == 2) {
                        isWinnerHelper(n);
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public void select(ImageView iv, int row, int column) {
        if (grid[row][column] == 0 && turns != 9 && isGameGoing) {
            if (player1) {
                iv.setImageResource(R.drawable.cross);
                grid[row][column] = 1;
                player1 = false;
                player2 = true;
            } else {
                iv.setImageResource(R.drawable.zero);
                grid[row][column] = 2;
                player2 = false;
                player1 = true;
            }

            isGameGoing = isWinner();
            turns++;

            if (turns == 9) winnerMessage.setText("It's a draw!");
            if (turns == 9 || !isGameGoing) {
                LinearLayout l = null;
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    l = findViewById(R.id.gameGoingContainer);
                } else {
                    l = findViewById(R.id.leftSide);
                }
                Button b = (Button) LayoutInflater.from(this).inflate(R.layout.reset_button, null);
                b.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                b.setOnClickListener((View v) -> { winnerMessage.setText("");
                    winnerMessage.setText("");
                    b.setVisibility(View.GONE);
                    isGameGoing = true;
                    turns = 0;
                    i1.setImageResource(R.drawable.transparent_back);
                    i2.setImageResource(R.drawable.transparent_back);
                    i3.setImageResource(R.drawable.transparent_back);
                    i4.setImageResource(R.drawable.transparent_back);
                    i5.setImageResource(R.drawable.transparent_back);
                    i6.setImageResource(R.drawable.transparent_back);
                    i7.setImageResource(R.drawable.transparent_back);
                    i8.setImageResource(R.drawable.transparent_back);
                    i9.setImageResource(R.drawable.transparent_back);
                    for (int i = 0; i <= 2; i++) for (int j = 0; j <=2; j++) grid[i][j] = 0;
                });
                l.addView(b);

                TextView scoreFirstPlayer = findViewById(R.id.playerOneScore);
                TextView scoreSecondPlayer = findViewById(R.id.playerTwoScore);
                if (!player1) {
                    int plusOne = Integer.parseInt(String.valueOf(scoreFirstPlayer.getText())) + 1;
                    scoreFirstPlayer.setText(String.valueOf(plusOne));
                } else if (!player2) {
                    int plusOne = Integer.parseInt(String.valueOf(scoreSecondPlayer.getText())) + 1;
                    scoreSecondPlayer.setText(String.valueOf(plusOne));
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        winnerMessage = findViewById(R.id.winner);
        final TextView playerOneName = findViewById(R.id.playerOneName);
        final TextView playerTwoName = findViewById(R.id.playerTwoName);

        player1Name = getIntent().getStringExtra("playerOne");
        player2Name = getIntent().getStringExtra("playerTwo");
        playerOneName.setText(player1Name);
        playerTwoName.setText(player2Name);

        i1 = findViewById(R.id.i1);
        i2 = findViewById(R.id.i2);
        i3 = findViewById(R.id.i3);
        i4 = findViewById(R.id.i4);
        i5 = findViewById(R.id.i5);
        i6 = findViewById(R.id.i6);
        i7 = findViewById(R.id.i7);
        i8 = findViewById(R.id.i8);
        i9 = findViewById(R.id.i9);

        i1.setOnClickListener((View v) -> {select(i1, 0, 0);});
        i2.setOnClickListener((View v) -> {select(i2, 0, 1);});
        i3.setOnClickListener((View v) -> {select(i3, 0, 2);});
        i4.setOnClickListener((View v) -> {select(i4, 1, 0);});
        i5.setOnClickListener((View v) -> {select(i5, 1, 1);});
        i6.setOnClickListener((View v) -> {select(i6, 1, 2);});
        i7.setOnClickListener((View v) -> {select(i7, 2, 0);});
        i8.setOnClickListener((View v) -> {select(i8, 2, 1);});
        i9.setOnClickListener((View v) -> {select(i9, 2, 2);});
    }
}