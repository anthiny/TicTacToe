package com.example.anthonykim.kim;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by anthony on 2016. 11. 29..
 */

public class TicTacToeActivity extends AppCompatActivity{
    TicTacToePresenter presenter;
    //Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tictactoe);

        //vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        GameTableFragment gameTableFragment =
                (GameTableFragment) getSupportFragmentManager().findFragmentById(R.id.frag_game_table);
        StatusFragment statusFragment =
                (StatusFragment) getSupportFragmentManager().findFragmentById(R.id.frag_status);

        presenter = new TicTacToePresenter(statusFragment, gameTableFragment);

        gameTableFragment.setPresenter(presenter);
        statusFragment.setPresenter(presenter);
    }
}


