package com.AnA.anthonykim.kim.View;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.AnA.anthonykim.kim.Presenter.TicTacToePresenter;
import com.AnA.anthonykim.kim.R;
import com.tsengvn.typekit.TypekitContextWrapper;
import android.content.Context;
import android.view.Menu;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioGroup;

import com.inmobi.ads.*;
import com.inmobi.sdk.*;

/**
 * Created by anthony on 2016. 11. 29..
 */

public class TicTacToeActivity extends AppCompatActivity{
    TicTacToePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        InMobiSdk.init(this, "41e93ce89c6543c998ee818dbeca425e");
        InMobiSdk.setLogLevel(InMobiSdk.LogLevel.DEBUG);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_tictactoe);
        GameTableFragment gameTableFragment =
                (GameTableFragment) getSupportFragmentManager().findFragmentById(R.id.frag_game_table);
        StatusFragment statusFragment =
                (StatusFragment) getSupportFragmentManager().findFragmentById(R.id.frag_status);

        presenter = new TicTacToePresenter(statusFragment, gameTableFragment);
        gameTableFragment.setPresenter(presenter);
        statusFragment.setPresenter(presenter);
        InMobiBanner banner = (InMobiBanner)findViewById(R.id.banner);
        banner.load();

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
                final Dialog helpDialog = new Dialog(this);
                helpDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                helpDialog.setContentView(R.layout.help_dialog);
                helpDialog.setCancelable(true);
                helpDialog.setCanceledOnTouchOutside(true);
                helpDialog.show();
                return true;
            case R.id.action_option:
                final Dialog optionDialog = new Dialog(this);
                optionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                optionDialog.setCancelable(false);
                optionDialog.setCanceledOnTouchOutside(false);
                optionDialog.setContentView(R.layout.option_dialog);
                final RadioGroup radioGroup = (RadioGroup)optionDialog.findViewById(R.id.radioGroup);

                if (presenter.getHumanFirst()){
                    radioGroup.check(R.id.radioFirst);
                }
                else{
                    radioGroup.check(R.id.radioSecond);
                }

                final Button playButton = (Button)optionDialog.findViewById(R.id.playButton);
                playButton.setOnClickListener(new Button.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        switch (radioGroup.getCheckedRadioButtonId()){
                            case R.id.radioFirst:
                                presenter.setHumanFirst(true);
                                break;
                            case R.id.radioSecond:
                                presenter.setHumanFirst(false);
                                break;
                        }
                        optionDialog.dismiss();
                    }
                });
                optionDialog.show();
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


