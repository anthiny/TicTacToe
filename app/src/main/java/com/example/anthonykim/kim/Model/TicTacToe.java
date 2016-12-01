package com.example.anthonykim.kim.Model;

import android.util.Log;

import com.example.anthonykim.kim.Model.GameTableModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by anthony on 2016. 11. 29..
 */

public class TicTacToe {

    private TicTacToeResult ticTacToeResult;
    private final int HumanFlag = 1;
    private final int AiFlag = 2;
    private final int NotFound = -1;
    private final int Found = 99;

    public interface TicTacToeResult{
        void onGameTableChanged(int idx, int mark);
        void winPopUp(String who);
        void showOverlapToast();
    }

    public void setTicTacToeResultListener(TicTacToeResult ticTacToeResult){
        this.ticTacToeResult = ticTacToeResult;
    }

    public void inputHuman(int idx){
        int temp[] = GameTableModel.getInstance().getTable();
        if (temp[idx]==0){
            Log.d("Human","Human Index: "+idx);
            GameTableModel.getInstance().setItemValue(idx, HumanFlag);
            ticTacToeResult.onGameTableChanged(idx, HumanFlag);
            GameTableModel.getInstance().increaseUserTurn();
            GameTableModel.getInstance().decreaseTotalTurn();

            if (GameTableModel.getInstance().getUserTurn() > 2){
                if(FindWinPoint(HumanFlag) == Found){
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
            ticTacToeResult.showOverlapToast();
        }
    }

    public void inputTicphago(){
        int winPoint = FindWinPoint(AiFlag);
        int userWinPoint = FindWinPoint(HumanFlag);
        int temp[] = GameTableModel.getInstance().getTable();
        Log.d("user Win","User Win Point: "+ userWinPoint);

        if (winPoint != NotFound) {
            GameTableModel.getInstance().setItemValue(winPoint, AiFlag);
            ticTacToeResult.onGameTableChanged(winPoint, AiFlag);
            Log.d("AI Win","AI Win Point: "+ winPoint);
            GameTableModel.getInstance().setAiWin(true);
        }
        else if (userWinPoint != NotFound){
            temp[userWinPoint] = AiFlag;
            GameTableModel.getInstance().setItemValue(userWinPoint, AiFlag);
            ticTacToeResult.onGameTableChanged(userWinPoint, AiFlag);
        }
        else{
            int tempIdx = FindEmptyEdge();
            if (tempIdx != NotFound){
                GameTableModel.getInstance().setItemValue(tempIdx, AiFlag);
                ticTacToeResult.onGameTableChanged(tempIdx, AiFlag);
                Log.d("deleteEdge", "DeletedEdge is "+tempIdx);
            }
            else if (IsCoreEmpty()){
                GameTableModel.getInstance().setItemValue(4, AiFlag);
                ticTacToeResult.onGameTableChanged(4, AiFlag);
            }
            else
            {
                tempIdx = FindEmptyRandomIndex();
                GameTableModel.getInstance().setItemValue(tempIdx, AiFlag);
                ticTacToeResult.onGameTableChanged(tempIdx, AiFlag);
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

    public int FindWinPoint(int whoFlag){
        int cnt = 0;
        int temp[] =  GameTableModel.getInstance().getTable();
        int tempEmptyIndex = NotFound;
        int index;

        for(int i =0;i<3;i++){
            for(int j=0;j<3;j++){
                index = i*3+j;
                if(temp[index] == whoFlag) {
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
                return Found;
            }
            else{
                cnt = 0;
            }
        }

        for(int i =0;i<3;i++){
            for(int j=0;j<3;j++){
                index = i+j*3;
                if(temp[index]==whoFlag){
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
                return Found;
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

                if(temp[index]==whoFlag){
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
                return Found;
            }
            else{
                cnt = 0;
            }
        }

        return NotFound;
    }

    public boolean IsCoreEmpty(){
        int temp[] = GameTableModel.getInstance().getTable();
        if (temp[4] != 0)
            return false;

        return true;
    }

    public int FindEmptyRandomIndex(){
        Random rand= new Random();
        int randIndex;
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
        int randIndex;
        int temp[] = GameTableModel.getInstance().getTable();
        List<Integer> emptyEdges= new ArrayList<>();
        for (int i=0;i<temp.length;i+=2){
            if (temp[i] == 0){
                emptyEdges.add(i);
            }
        }

        if(emptyEdges.size() == 0)
            return NotFound;

        if(emptyEdges.size() == 1)
            return emptyEdges.get(0);

        randIndex = rand.nextInt(emptyEdges.size());
        return emptyEdges.get(randIndex);
    }

    public void resetGameTableData(){
        GameTableModel.getInstance().resetTable();
    }

}
