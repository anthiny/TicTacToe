package com.example.anthonykim.kim.View;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.anthonykim.kim.Presenter.TicTacToePresenter;
import com.example.anthonykim.kim.R;
import com.tsengvn.typekit.TypekitContextWrapper;
import android.content.Context;
import android.view.Menu;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

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
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_help:
                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.help_dialog);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK){
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));

    }

}


