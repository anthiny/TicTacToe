package com.example.anthonykim.kim.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.anthonykim.kim.Presenter.TicTacToePresenter;
import com.example.anthonykim.kim.R;
import com.tsengvn.typekit.TypekitContextWrapper;
import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK){
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onUserLeaveHint(){
        Log.d("pause", "home button click");
        presenter.pauseTimer();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            presenter.restartTimer();
        }
        else{
            presenter.pauseTimer();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));

    }

}


