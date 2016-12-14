package com.example.anthonykim.kim.Model;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by anthony on 2016. 11. 29..
 */

public class TicTacToe {

    private TicTacToeResult ticTacToeResult;
    private final int CircleFlag = 1;
    private final int XFlag = 2;
    private final int DrawFlag = -2;
    private final int NotFound = -1;
    private final int Found = 99;
    public interface TicTacToeResult{
        void onGameTableChanged(int idx, int mark);
        void winPopUp(int who);
        void showOverlapToast();
    }

    public void setTicTacToeResultListener(TicTacToeResult ticTacToeResult){
        this.ticTacToeResult = ticTacToeResult;
    }

    private void multiple(int idx){
        int flagInfo;

        if (GameTableModel.getInstance().getCircleTurn()){
            flagInfo = CircleFlag;
        }
        else{
            flagInfo = XFlag;
        }

        GameTableModel.getInstance().setItemValue(idx, flagInfo);
        ticTacToeResult.onGameTableChanged(idx, flagInfo);
        GameTableModel.getInstance().decreaseTotalTurn();

        if (GameTableModel.getInstance().getTotalTurn() < 5){
            if(IsWin(flagInfo)){
                switch (flagInfo){
                    case CircleFlag:
                        GameTableModel.getInstance().setCircleWin(true);
                        break;
                    case XFlag:
                        GameTableModel.getInstance().setxWin(true);
                        break;
                }
                GameTableModel.getInstance().setLockGameTable(true);
                ticTacToeResult.winPopUp(flagInfo);
                return;
            }
            else if (GameTableModel.getInstance().getTotalTurn() == 0 && !GameTableModel.getInstance().getCircleWin()
                    && !GameTableModel.getInstance().getxWin()){
                GameTableModel.getInstance().setLockGameTable(true);
                ticTacToeResult.winPopUp(DrawFlag);
                return;
            }
        }

        boolean currentTurn = GameTableModel.getInstance().getCircleTurn();
        GameTableModel.getInstance().setCircleTurn(!currentTurn);
    }

    public void inputHuman(int idx){
        int temp[] = GameTableModel.getInstance().getTable();
        int flagInfo = CircleFlag;
        if (temp[idx]==0){
            //Log.d("Human","Human Index: "+idx);

            if (!GameTableModel.getInstance().getSingleMode()){
                multiple(idx);
                return;
            }

            GameTableModel.getInstance().setItemValue(idx, flagInfo);
            ticTacToeResult.onGameTableChanged(idx, flagInfo);
            GameTableModel.getInstance().increaseUserTurn();
            GameTableModel.getInstance().decreaseTotalTurn();

            if (GameTableModel.getInstance().getUserTurn() > 2){
                if(FindWinPoint(flagInfo) == Found){
                    GameTableModel.getInstance().setCircleWin(true);
                    GameTableModel.getInstance().setLockGameTable(true);
                    ticTacToeResult.winPopUp(flagInfo);
                }
            }

            if(GameTableModel.getInstance().getTotalTurn() !=0 && !GameTableModel.getInstance().getCircleWin()){
                inputTicphago();
            }
            else if(GameTableModel.getInstance().getTotalTurn()==0 && !GameTableModel.getInstance().getCircleWin()){
                GameTableModel.getInstance().setLockGameTable(true);
                ticTacToeResult.winPopUp(DrawFlag);
            }
        }
        else {
            ticTacToeResult.showOverlapToast();
        }
    }

    public void inputTicphago(){
        int winPoint = FindWinPoint(XFlag);
        int userWinPoint = FindWinPoint(CircleFlag);
        int temp[] = GameTableModel.getInstance().getTable();
        //Log.d("user Win","User Win Point: "+ userWinPoint);

        if (winPoint != NotFound) {
            GameTableModel.getInstance().setItemValue(winPoint, XFlag);
            ticTacToeResult.onGameTableChanged(winPoint, XFlag);
            //Log.d("AI Win","AI Win Point: "+ winPoint);
            GameTableModel.getInstance().setxWin(true);
        }
        else if (userWinPoint != NotFound){
            temp[userWinPoint] = XFlag;
            GameTableModel.getInstance().setItemValue(userWinPoint, XFlag);
            ticTacToeResult.onGameTableChanged(userWinPoint, XFlag);
        }
        else{
            int tempIdx = FindEmptyEdge();
            if (tempIdx != NotFound){
                GameTableModel.getInstance().setItemValue(tempIdx, XFlag);
                ticTacToeResult.onGameTableChanged(tempIdx, XFlag);
                //Log.d("deleteEdge", "DeletedEdge is "+tempIdx);
            }
            else if (IsCoreEmpty()){
                GameTableModel.getInstance().setItemValue(4, XFlag);
                ticTacToeResult.onGameTableChanged(4, XFlag);
            }
            else
            {
                tempIdx = FindEmptyRandomIndex();
                GameTableModel.getInstance().setItemValue(tempIdx, XFlag);
                ticTacToeResult.onGameTableChanged(tempIdx, XFlag);
            }
        }
        GameTableModel.getInstance().decreaseTotalTurn();

        if (GameTableModel.getInstance().getxWin()){
            GameTableModel.getInstance().setLockGameTable(true);
            ticTacToeResult.winPopUp(XFlag);
        }
        else if(!GameTableModel.getInstance().getxWin() && GameTableModel.getInstance().getTotalTurn()==0){
            GameTableModel.getInstance().setLockGameTable(true);
            ticTacToeResult.winPopUp(DrawFlag);
        }
    }

    private boolean IsWin(int flag){
        int cnt = 0;
        int temp[] = GameTableModel.getInstance().getTable();
        int index;

        for(int i =0;i<3;i++){
            for(int j=0;j<3;j++){
                index = i*3+j;
                if(temp[index] == flag) {
                    cnt++;
                }
                else{
                    cnt--;
                }
            }
            if(cnt == 3){
                return true;
            }
            else{
                cnt = 0;
            }
        }

        for(int i =0;i<3;i++){
            for(int j=0;j<3;j++){
                index = i+j*3;
                if(temp[index]==flag){
                    cnt++;
                }
                else{
                    cnt--;
                }
            }
           if(cnt == 3){
                return true;
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

                if(temp[index]==flag){
                    cnt++;
                }
                else{
                    cnt--;
                }
            }
            if(cnt == 3){
                return true;
            }
            else{
                cnt = 0;
            }
        }

        return false;
    }

    private int FindWinPoint(int whoFlag){
        int cnt = 0;
        int temp[] = GameTableModel.getInstance().getTable();
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

    private boolean IsCoreEmpty(){
        int temp[] = GameTableModel.getInstance().getTable();

        if (temp[4] != 0)
            return false;
        else
            return true;
    }

    private int FindEmptyRandomIndex(){
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
        //Log.d("FindRand","FindRand Index: "+emptyIdxs.get(randIndex));
        return emptyIdxs.get(randIndex);
    }

    private int FindEmptyEdge(){
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
        GameTableModel.getInstance().setCircleTurn(true);
        GameTableModel.getInstance().setxWin(false);
        GameTableModel.getInstance().setCircleWin(false);
        GameTableModel.getInstance().resetTable();
    }

}
