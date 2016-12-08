package com.example.anthonykim.kim.Presenter;

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
    public void saveSetting(boolean isFirst) {
       if (GameTableModel.getInstance().getSingleMode() && !isFirst){
           aiStart();
       }
    }

    @Override
    public void aiStart() {
        ticTacToe.inputTicphago();
    }

    private void prepareFirstGame(){
        publishToStatus.chooseFirst();
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
    public void winPopUp(int who) {
        publishToGameTable.showDialog(who);
    }

    @Override
    public void showOverlapToast() {
        publishToGameTable.showOverlapToast();
    }

    @Override
    public void onResetButtonClick() {
        publishToGameTable.resetGameTable();
        ticTacToe.resetGameTableData();
        publishToStatus.chooseFirst();
    }
}
