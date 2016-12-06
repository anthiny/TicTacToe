package com.example.anthonykim.kim.Presenter;

import android.os.CountDownTimer;
import android.util.Log;

import com.example.anthonykim.kim.Model.GameTableModel;
import com.example.anthonykim.kim.Model.TicTacToe;
import com.example.anthonykim.kim.TicTacToeContract;

/**
 * Created by anthony on 2016. 11. 29..
 */

public class TicTacToePresenter implements TicTacToeContract.ForwardStatusInteractionToPresenter,
        TicTacToeContract.ForwardGameTableInteractionToPresenter, TicTacToe.TicTacToeResult {

    private TicTacToeContract.PublishToGameTable publishToGameTable;
    private TicTacToeContract.PublishToStatus publishToStatus;
    private TicTacToe ticTacToe;
    private CountDownTimer countDownTimer = null;
    private int weight = 0;

    public TicTacToePresenter (TicTacToeContract.PublishToStatus publishToStatus,
                               TicTacToeContract.PublishToGameTable publishToGameTable){
        this.publishToGameTable = publishToGameTable;
        this.publishToStatus = publishToStatus;

        ticTacToe = new TicTacToe();
        ticTacToe.setTicTacToeResultListener(this);

        prepareFirstGame();
    }

    @Override
    public void onGameTableItemClick(int idx) {
        ticTacToe.inputHuman(idx);
    }

    @Override
    public void saveSetting(int timeLimit, boolean isFirst) {
        GameTableModel.getInstance().setLimitTime(timeLimit * 1000);
        publishToStatus.setProgressMax(GameTableModel.getInstance().getTimeProgress(0));
        if (isFirst) {
            if (GameTableModel.getInstance().getTotalTurn() == 9){
                startTimer();
            }
        }
        else {
            aiStart();
        }
    }

    @Override
    public void aiStart() {
        if (GameTableModel.getInstance().getTotalTurn() == 9){
            startTimer();
        }
        ticTacToe.inputTicphago();
    }

    private void prepareFirstGame(){
        publishToStatus.chooseFirst();
        publishToStatus.setProgressMax(GameTableModel.getInstance().getTimeProgress(0));
    }

    private void startTimer() {
        if (countDownTimer == null){
            countDownTimer = new CountDownTimer(GameTableModel.getInstance().getLimitTime(), 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    weight = weight + 1;
                    Log.d("timer", String.valueOf(GameTableModel.getInstance().getTimeProgress(weight)));
                    publishToStatus.changingProgressValue(GameTableModel.getInstance().getTimeProgress(weight));
                    if (GameTableModel.getInstance().getTimeProgress(weight) <= 0){
                        weight = 0;
                        publishToStatus.changingProgressValue(0);
                        countDownTimer.cancel();
                        winPopUp("Time Over !");
                    }
                }

                @Override
                public void onFinish() {
                    Log.d("timer","finish");
                }
            };
        }

        countDownTimer.start();
    }

    public void pauseTimer(){
       if(countDownTimer != null){
           countDownTimer.cancel();
       }
    }

    public void restartTimer(){
        if(countDownTimer != null){
            countDownTimer.start();
        }
    }

    @Override
    public void onDialogContinueClick() {
        onResetButtonClick();
    }

    @Override
    public void onDialogEndClick() {
        publishToStatus.finishApp();
    }


    @Override
    public void onGameTableChanged(int idx, int mark) {
        if (mark == 1){
            publishToGameTable.showHumanMark(idx);
        }
        else if(mark == 2){
            publishToGameTable.showTicphagoMark(idx);
        }
    }

    @Override
    public void winPopUp(String who) {
        pauseTimer();
        publishToGameTable.showDialog(who);
    }

    @Override
    public void showOverlapToast() {
        publishToGameTable.showOverlapToast();
    }

    @Override
    public void onResetButtonClick() {
        pauseTimer();
        weight = 0;
        publishToGameTable.resetGameTable();
        ticTacToe.resetGameTableData();
        publishToStatus.setProgressMax(GameTableModel.getInstance().getTimeProgress(0));
        publishToStatus.chooseFirst();
    }
}
