package com.example.anthonykim.kim;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by anthony on 2016. 11. 29..
 */

public class TicTacToe {

    private TicTacToeResult ticTacToeResult;
    private static int idx;

    interface TicTacToeResult{
        void onGameTableChanged(int idx, int mark);
        void winPopUp(String who);
        void showToast(String text);
    }

    public void setTicTacToeResultListener(TicTacToeResult ticTacToeResult){
        this.ticTacToeResult = ticTacToeResult;
        idx = 0;
    }

    public void inputHuman(int idx){
        int temp[] = GameTableModel.getInstance().getTable();
        if (temp[idx]==0){
            Log.d("Human","Human Index: "+idx);
            GameTableModel.getInstance().setItemValue(idx, 1);
            ticTacToeResult.onGameTableChanged(idx, 1);
            GameTableModel.getInstance().increaseUserTurn();
            GameTableModel.getInstance().decreaseTotalTurn();

            if (GameTableModel.getInstance().getUserTurn() > 2){
                if(FindWinPoint("HUMAN") == 99){
                    GameTableModel.getInstance().setUserWin(true);
                    ticTacToeResult.winPopUp("HUMAN");
                }
            }

            if(GameTableModel.getInstance().getTotalTurn() !=0 && !GameTableModel.getInstance().getUserWin()){
                inputTicphago();
            }
            else if(GameTableModel.getInstance().getTotalTurn()==0 && !GameTableModel.getInstance().getUserWin()){
                ticTacToeResult.winPopUp("DRAW");
            }
        }
        else {
            ticTacToeResult.showToast("Already Clicked!");
        }
    }

    public void inputTicphago(){
        int winPoint = FindWinPoint("AI");
        int userWinPoint = FindWinPoint("HUMAN");
        int temp[] = GameTableModel.getInstance().getTable();
        Log.d("user Win","User Win Point: "+ userWinPoint);

        if (winPoint != -1) {
            GameTableModel.getInstance().setItemValue(winPoint, 2);
            ticTacToeResult.onGameTableChanged(winPoint, 2);
            Log.d("AI Win","AI Win Point: "+ winPoint);
            GameTableModel.getInstance().setAiWin(true);
        }
        else if (userWinPoint != -1){
            temp[userWinPoint] = 2;
            GameTableModel.getInstance().setItemValue(userWinPoint, 2);
            ticTacToeResult.onGameTableChanged(userWinPoint, 2);
        }
        else{
            int tempIdx = FindEmptyEdge();
            if (tempIdx != -1){
                GameTableModel.getInstance().setItemValue(tempIdx, 2);
                ticTacToeResult.onGameTableChanged(tempIdx, 2);
                Log.d("deleteEdge", "DeletedEdge is "+tempIdx);
            }
            else if (IsCoreEmpty()){
                GameTableModel.getInstance().setItemValue(4, 2);
                ticTacToeResult.onGameTableChanged(4, 2);
            }
            else
            {
                tempIdx = FindEmptyRandomIndex();
                GameTableModel.getInstance().setItemValue(tempIdx, 2);
                ticTacToeResult.onGameTableChanged(tempIdx, 2);
            }
        }
        GameTableModel.getInstance().decreaseTotalTurn();

        if (GameTableModel.getInstance().getAiWin()){
            ticTacToeResult.winPopUp("AI");
        }
        else if(!GameTableModel.getInstance().getAiWin() && GameTableModel.getInstance().getTotalTurn()==0){
            ticTacToeResult.winPopUp("DRAW");
        }
    }

    public int transWhoToInt(String who){
        if (who == "AI"){
            return 2;
        }
        else{
            return 1;
        }
    }

    public int FindWinPoint(String who){
        int cnt = 0;
        int temp[] =  GameTableModel.getInstance().getTable();
        int tempEmptyIndex = -1;
        int index;

        for(int i =0;i<3;i++){
            for(int j=0;j<3;j++){
                index = i*3+j;
                if(temp[index] == transWhoToInt(who)) {
                    cnt++;
                }
                else if(temp[index] == 0){
                    tempEmptyIndex = index;
                }
                else{
                    cnt--;
                }
            }
            if (cnt == 2){
                return tempEmptyIndex;
            }
            else if(cnt == 3){
                return 99;
            }
            else{
                cnt = 0;
            }
        }

        for(int i =0;i<3;i++){
            for(int j=0;j<3;j++){
                index = i+j*3;
                if(temp[index]==transWhoToInt(who)){
                    cnt++;
                }
                else if(temp[index] == 0){
                    tempEmptyIndex = index;
                }
                else{
                    cnt--;
                }
            }
            if (cnt == 2){
                return tempEmptyIndex;
            }
            else if(cnt == 3){
                return 99;
            }
            else{
                cnt = 0;
            }
        }

        int weight = 4;
        for(int i =0;i<2;i++){
            for(int j=0;j<3;j++){

                if (i == 1)
                    weight = 2;

                index = i*2+j*weight;

                if(temp[index]==transWhoToInt(who)){
                    cnt++;
                }
                else if(temp[index] == 0){
                    tempEmptyIndex = index;
                }
                else{
                    cnt--;
                }
            }
            if (cnt == 2){
                return  tempEmptyIndex;
            }
            else if(cnt == 3){
                return 99;
            }
            else{
                cnt = 0;
            }
        }

        return -1;
    }

    public boolean IsCoreEmpty(){
        int temp[] = GameTableModel.getInstance().getTable();
        if (temp[4] != 0)
            return false;

        return true;
    }

    public int FindEmptyRandomIndex(){
        Random rand= new Random();
        int randIndex = 0;
        int temp[] = GameTableModel.getInstance().getTable();
        List<Integer> emptyIdxs= new ArrayList<>();
        for (int i=0;i<temp.length;i++){
            if (temp[i] == 0){
                emptyIdxs.add(i);
            }
        }
        randIndex = rand.nextInt(emptyIdxs.size());
        Log.d("FindRand","FindRand Index: "+emptyIdxs.get(randIndex));
        return emptyIdxs.get(randIndex);
    }

    public int FindEmptyEdge(){
        Random rand= new Random();
        int randIndex = 0;
        int temp[] = GameTableModel.getInstance().getTable();
        List<Integer> emptyEdges= new ArrayList<>();
        for (int i=0;i<temp.length;i+=2){
            if (temp[i] == 0){
                emptyEdges.add(i);
            }
        }

        if(emptyEdges.size() == 0)
            return -1;

        if(emptyEdges.size() == 1)
            return emptyEdges.get(0);

        randIndex = rand.nextInt(emptyEdges.size());
        return emptyEdges.get(randIndex);
    }

    public void resetGameTableData(){
        GameTableModel.getInstance().resetTable();
    }

}
