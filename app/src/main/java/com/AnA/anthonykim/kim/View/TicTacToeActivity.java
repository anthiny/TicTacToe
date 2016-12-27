package com.AnA.anthonykim.kim.View;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.AnA.anthonykim.kim.Presenter.TicTacToePresenter;
import com.AnA.anthonykim.kim.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_tictactoe);
        GameTableFragment gameTableFragment =
                (GameTableFragment) getSupportFragmentManager().findFragmentById(R.id.frag_game_table);
        StatusFragment statusFragment =
                (StatusFragment) getSupportFragmentManager().findFragmentById(R.id.frag_status);

        presenter = new TicTacToePresenter(statusFragment, gameTableFragment);
        gameTableFragment.setPresenter(presenter);
        statusFragment.setPresenter(presenter);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().
                //addTestDevice("4DDF2F8B72F6691D240114A7CC6FD2D6").
                build();
        mAdView.loadAd(adRequest);
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


