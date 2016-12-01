package com.example.anthonykim.kim;

/**
 * Created by anthony on 2016. 11. 29..
 */

public interface TicTacToeContract {
    interface PublishToGameTable{
        void showHumanMark(int idx);
        void showTicphagoMark(int idx);
        void showDialog(String text);
        //void showToast(String text);
        void showOverlapToast();
        void resetGameTable();
    }

    interface PublishToStatus{
        void showTime(String time);
        void finishApp();
    }

    interface ForwardGameTableInteractionToPresenter{
        void onGameTableItemClick(int idx);
        void aiStart();
        void onDialogContinueClick();
        void onDialogEndClick();
    }

    interface ForwardStatusInteractionToPresenter{
        void onResetButtonClick();
    }
}
