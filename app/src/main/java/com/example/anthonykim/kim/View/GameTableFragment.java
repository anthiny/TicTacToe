package com.example.anthonykim.kim.View;

import android.content.DialogInterface;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.os.Bundle;
import android.widget.Toast;

import com.example.anthonykim.kim.Model.GameTableModel;
import com.example.anthonykim.kim.R;
import com.example.anthonykim.kim.TicTacToeContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by anthony on 2016. 11. 29..
 */

public class GameTableFragment extends Fragment implements TicTacToeContract.PublishToGameTable {

    private TicTacToeContract.ForwardGameTableInteractionToPresenter forwardInteraction;
    final long WaitingClickTime = 500;
    private long mLastClickTime = 0;
    private final int CircleFlag = 1;
    private final int XFlag = 2;
    private final int DrawFlag = -2;

    public void setPresenter(TicTacToeContract.ForwardGameTableInteractionToPresenter forwardInteraction){
        this.forwardInteraction = forwardInteraction;
    }

    @BindView(R.id.imageButton1)
    ImageButton imageButton1;
    @BindView(R.id.imageButton2)
    ImageButton imageButton2;
    @BindView(R.id.imageButton3)
    ImageButton imageButton3;
    @BindView(R.id.imageButton4)
    ImageButton imageButton4;
    @BindView(R.id.imageButton5)
    ImageButton imageButton5;
    @BindView(R.id.imageButton6)
    ImageButton imageButton6;
    @BindView(R.id.imageButton7)
    ImageButton imageButton7;
    @BindView(R.id.imageButton8)
    ImageButton imageButton8;
    @BindView(R.id.imageButton9)
    ImageButton imageButton9;

    @OnClick({R.id.imageButton1, R.id.imageButton2, R.id.imageButton3, R.id.imageButton4, R.id.imageButton5,
            R.id.imageButton6, R.id.imageButton7, R.id.imageButton8, R.id.imageButton9})
    public void OnItemClick(View v){
        if(SystemClock.elapsedRealtime() - mLastClickTime < WaitingClickTime){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        if (GameTableModel.getInstance().getxWin() || GameTableModel.getInstance().getCircleWin()){
            return;
        }

        switch (v.getId()){
            case R.id.imageButton1:
                forwardInteraction.onGameTableItemClick(0);
                break;
            case R.id.imageButton2:
                forwardInteraction.onGameTableItemClick(1);
                break;
            case R.id.imageButton3:
                forwardInteraction.onGameTableItemClick(2);
                break;
            case R.id.imageButton4:
                forwardInteraction.onGameTableItemClick(3);
                break;
            case R.id.imageButton5:
                forwardInteraction.onGameTableItemClick(4);
                break;
            case R.id.imageButton6:
                forwardInteraction.onGameTableItemClick(5);
                break;
            case R.id.imageButton7:
                forwardInteraction.onGameTableItemClick(6);
                break;
            case R.id.imageButton8:
                forwardInteraction.onGameTableItemClick(7);
                break;
            case R.id.imageButton9:
                forwardInteraction.onGameTableItemClick(8);
                break;
        }
    }

    public GameTableFragment(){

    }

    public static GameTableFragment newInstance(){
        return new GameTableFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.game_table_fragment, container, false);
        ButterKnife.bind(this, v);
        return v;
    }


    @Override
    public void showHumanMark(int idx) {
        switch (idx){
            case 0:
                imageButton1.setImageResource(R.drawable.o);
                break;
            case 1:
                imageButton2.setImageResource(R.drawable.o);
                break;
            case 2:
                imageButton3.setImageResource(R.drawable.o);
                break;
            case 3:
                imageButton4.setImageResource(R.drawable.o);
                break;
            case 4:
                imageButton5.setImageResource(R.drawable.o);
                break;
            case 5:
                imageButton6.setImageResource(R.drawable.o);
                break;
            case 6:
                imageButton7.setImageResource(R.drawable.o);
                break;
            case 7:
                imageButton8.setImageResource(R.drawable.o);
                break;
            case 8:
                imageButton9.setImageResource(R.drawable.o);
                break;
        }
    }

    @Override
    public void showTicphagoMark(int idx) {
        switch (idx){
            case 0:
                imageButton1.setImageResource(R.drawable.x);
                break;
            case 1:
                imageButton2.setImageResource(R.drawable.x);
                break;
            case 2:
                imageButton3.setImageResource(R.drawable.x);
                break;
            case 3:
                imageButton4.setImageResource(R.drawable.x);
                break;
            case 4:
                imageButton5.setImageResource(R.drawable.x);
                break;
            case 5:
                imageButton6.setImageResource(R.drawable.x);
                break;
            case 6:
                imageButton7.setImageResource(R.drawable.x);
                break;
            case 7:
                imageButton8.setImageResource(R.drawable.x);
                break;
            case 8:
                imageButton9.setImageResource(R.drawable.x);
                break;
        }
    }

    @Override
    public void showOverlapToast() {
        Toast.makeText(getActivity(), getText(R.string.overlap_toast), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDialog(int who) {
        String text = "";
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        switch (who){
            case CircleFlag:
                text = getString(R.string.win_circlelayer);
                break;
            case XFlag:
                text = getString(R.string.win_xplayer);
                break;
            case DrawFlag:
                text = getString(R.string.who_draw);
                break;
        }
        builder.setMessage(text);
        builder.setPositiveButton(getText(R.string.result_dialog_end), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                forwardInteraction.onDialogEndClick();
            }
        });
        builder.setNegativeButton(getText(R.string.result_dialog_continue), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                forwardInteraction.onDialogContinueClick();
            }
        });
        builder.show();
    }

    @Override
    public void resetGameTable(){
        imageButton1.setImageResource(R.drawable.blank);
        imageButton2.setImageResource(R.drawable.blank);
        imageButton3.setImageResource(R.drawable.blank);
        imageButton4.setImageResource(R.drawable.blank);
        imageButton5.setImageResource(R.drawable.blank);
        imageButton6.setImageResource(R.drawable.blank);
        imageButton7.setImageResource(R.drawable.blank);
        imageButton8.setImageResource(R.drawable.blank);
        imageButton9.setImageResource(R.drawable.blank);
    }



}
