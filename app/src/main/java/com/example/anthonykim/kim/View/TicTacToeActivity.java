package com.example.anthonykim.kim.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.anthonykim.kim.Presenter.TicTacToePresenter;
import com.example.anthonykim.kim.R;

/**
 * Created by anthony on 2016. 11. 29..
 */

public class TicTacToeActivity extends AppCompatActivity{
    TicTacToePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tictactoe);

        GameTableFragment gameTableFragment =
                (GameTableFragment) getSupportFragmentManager().findFragmentById(R.id.frag_game_table);
        StatusFragment statusFragment =
                (StatusFragment) getSupportFragmentManager().findFragmentById(R.id.frag_status);

        presenter = new TicTacToePresenter(statusFragment, gameTableFragment);

        gameTableFragment.setPresenter(presenter);
        statusFragment.setPresenter(presenter);
    }
}


