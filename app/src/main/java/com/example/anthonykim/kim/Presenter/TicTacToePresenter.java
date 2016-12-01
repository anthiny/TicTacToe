package com.example.anthonykim.kim.Presenter;

import com.example.anthonykim.kim.TicTacToe;
import com.example.anthonykim.kim.TicTacToeContract;

/**
 * Created by anthony on 2016. 11. 29..
 */

public class TicTacToePresenter implements TicTacToeContract.ForwardStatusInteractionToPresenter,
        TicTacToeContract.ForwardGameTableInteractionToPresenter, TicTacToe.TicTacToeResult {

    private TicTacToeContract.PublishToGameTable publishToGameTable;
    private TicTacToeContract.PublishToStatus publishToStatus;
    private TicTacToe ticTacToe;

    public TicTacToePresenter (TicTacToeContract.PublishToStatus publishToStatus,
                               TicTacToeContract.PublishToGameTable publishToGameTable){
        this.publishToGameTable = publishToGameTable;
        this.publishToStatus = publishToStatus;

        ticTacToe = new TicTacToe();
        ticTacToe.setTicTacToeResultListener(this);
    }

    @Override
    public void onGameTableItemClick(int idx) {
       ticTacToe.inputHuman(idx);
    }

    @Override
    public void aiStart() {
        ticTacToe.inputTicphago();
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
        publishToGameTable.showDialog(who);
    }

    @Override
    public void showToast(String text) {
        publishToGameTable.showToast(text);
    }

    @Override
    public void onResetButtonClick() {
        publishToGameTable.resetGameTable();
        ticTacToe.resetGameTableData();
    }
}
