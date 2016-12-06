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
    private CountDownTimer countDownTimer;
    private boolean timerFlag = false;
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
        if (GameTableModel.getInstance().getTotalTurn() == 9){
            GameTableModel.getInstance().setGameStart(true);
            startTimer();
        }
        ticTacToe.inputHuman(idx);
    }

    @Override
    public void saveSetting(int timeLimit, boolean isFirst) {
        GameTableModel.getInstance().setLimitTime(timeLimit * 1000);
        publishToStatus.setProgressMax(GameTableModel.getInstance().getTimeProgress(0));
        if (!isFirst){
            aiStart();
        }
    }

    @Override
    public void aiStart() {
        if (GameTableModel.getInstance().getTotalTurn() == 9){
            GameTableModel.getInstance().setGameStart(true);
            startTimer();
        }
        ticTacToe.inputTicphago();
    }

    private void prepareFirstGame(){
        publishToStatus.chooseFirst();
        publishToStatus.setProgressMax(GameTableModel.getInstance().getTimeProgress(0));
    }

    private void startTimer() {
        timerFlag = true;
       countDownTimer = new CountDownTimer(GameTableModel.getInstance().getLimitTime(), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                weight = weight + 1;
                Log.d("timer", String.valueOf(GameTableModel.getInstance().getTimeProgress(weight)));
                publishToStatus.changingProgressValue(GameTableModel.getInstance().getTimeProgress(weight));
            }

            @Override
            public void onFinish() {
                timerFlag = false;
                weight = 0;
                winPopUp("Time Over !");
                publishToStatus.changingProgressValue(0);
                Log.d("timer","finish");
            }
        };
        countDownTimer.start();
    }

    public void pauseTimer(){
        if (timerFlag && GameTableModel.getInstance().getGameStart()){
            countDownTimer.cancel();
            timerFlag = false;
        }
    }

    public void restartTimer(){
        if (!timerFlag && GameTableModel.getInstance().getGameStart()){
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
        GameTableModel.getInstance().setGameStart(false);
        publishToGameTable.showDialog(who);
    }

    @Override
    public void showOverlapToast() {
        publishToGameTable.showOverlapToast();
    }

    @Override
    public void onResetButtonClick() {
        countDownTimer.cancel();
        weight = 0;
        GameTableModel.getInstance().setGameStart(false);
        timerFlag = false;
        publishToGameTable.resetGameTable();
        ticTacToe.resetGameTableData();
        publishToStatus.setProgressMax(GameTableModel.getInstance().getTimeProgress(0));
        publishToStatus.chooseFirst();
    }
}
