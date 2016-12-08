package com.example.anthonykim.kim.Model;

/**
 * Created by anthony on 2016. 11. 29..
 */
public class GameTableModel {
    private static GameTableModel instance;

    private int table[] = {0,0,0,0,0,0,0,0,0};
    private int userTurn = 0;
    private int totalTurn = 9;

    private Boolean circleWin = false;
    private Boolean xWin = false;
    private Boolean singleMode = false;
    private Boolean circleTurn = true;

    public static synchronized GameTableModel getInstance() {
        if (instance == null){
            instance = new GameTableModel();
        }
        return instance;
    }

    private GameTableModel() {}

    public Boolean getCircleTurn() {
        return circleTurn;
    }

    public void setCircleTurn(Boolean circleTurn) {
        this.circleTurn = circleTurn;
    }

    public Boolean getSingleMode() {
        return singleMode;
    }

    public void setSingleMode(Boolean singleMode) {
        this.singleMode = singleMode;
    }

    public int[] getTable() {
        return table;
    }

    public void setItemValue(int idx, int mark) {
        this.table[idx] = mark;
    }

    public int getUserTurn() {
        return userTurn;
    }

    public int getTotalTurn() {
        return totalTurn;
    }

    public void increaseUserTurn(){
        userTurn += 1;
    }

    public void decreaseTotalTurn() {
        totalTurn -= 1;
    }

    public Boolean getCircleWin() {
        return circleWin;
    }

    public void setCircleWin(Boolean circleWin) {
        this.circleWin = circleWin;
    }

    public Boolean getxWin() {
        return xWin;
    }

    public void setxWin(Boolean xWin) {
        this.xWin = xWin;
    }

    public void resetTable(){
        for (int i = 0; i < table.length; i++){
            table[i] = 0;
        }
        circleWin = false;
        xWin = false;
        totalTurn = 9;
        userTurn = 0;
    }
}
