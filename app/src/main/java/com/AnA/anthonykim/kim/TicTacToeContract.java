package com.AnA.anthonykim.kim;

/**
 * Created by anthony on 2016. 11. 29..
 */

public interface TicTacToeContract {
    interface PublishToGameTable{
        void showHumanMark(int idx);
        void showTicphagoMark(int idx);
        void showDialog(int text);
        void showOverlapToast();
        void resetGameTable();
    }

    interface PublishToStatus{
        void selectGameMode();
        void finishApp();
    }

    interface ForwardGameTableInteractionToPresenter{
        void onGameTableItemClick(int idx);
        void aiStart();
        void onDialogContinueClick();
        void onDialogEndClick();
    }

    interface ForwardStatusInteractionToPresenter{
        void saveSetting(boolean isFirst);
        void onResetButtonClick();
    }
}
