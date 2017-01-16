package com.AnA.anthonykim.kim.Presenter;

import com.AnA.anthonykim.kim.Model.GameTableModel;
import com.AnA.anthonykim.kim.Model.TicTacToe;
import com.AnA.anthonykim.kim.TicTacToeContract;

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
        if(GameTableModel.getInstance().getLockGameTable()){
            return;
        }
        ticTacToe.inputHuman(idx);
    }

    @Override
    public void setSingleMode(boolean value) {
        GameTableModel.getInstance().setSingleMode(value);
    }

    @Override
    public void setHumanFirst(boolean value) {
        GameTableModel.getInstance().setFirstTurn(value);
    }

    @Override
    public Boolean getHumanFirst() {
        return GameTableModel.getInstance().getFirstTurn();
    }


    @Override
    public void saveSetting() {
        GameTableModel.getInstance().setLockGameTable(false);
        if (GameTableModel.getInstance().getSingleMode() && !GameTableModel.getInstance().getFirstTurn()){
           aiStart();
       }
    }

    @Override
    public void aiStart() {
        ticTacToe.inputTicphago();
    }

    private void prepareFirstGame(){
        publishToStatus.selectGameMode();
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
        publishToStatus.selectGameMode();
    }
}
