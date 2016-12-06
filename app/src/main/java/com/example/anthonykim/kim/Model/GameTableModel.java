package com.example.anthonykim.kim.Model;

/**
 * Created by anthony on 2016. 11. 29..
 */
public class GameTableModel {
    private static GameTableModel instance;

    private int table[] = {0,0,0,0,0,0,0,0,0};
    private int limitTime = 30000;
    private int userTurn = 0;
    private int totalTurn = 9;

    private Boolean gameStart = false;
    private Boolean userWin = false;
    private Boolean aiWin = false;

    public static synchronized GameTableModel getInstance() {
        if (instance == null){
            instance = new GameTableModel();
        }
        return instance;
    }

    private GameTableModel() {}

    public Boolean getGameStart() {
        return gameStart;
    }

    public void setGameStart(Boolean gameStart) {
        this.gameStart = gameStart;
    }

    public void setLimitTime(int limitTime) {
        this.limitTime = limitTime;
    }
    public int getLimitTime() {
        return limitTime;
    }

    public int getTimeProgress(int weight){
        return (limitTime/1000 - weight);
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

    public Boolean getUserWin() {
        return userWin;
    }

    public void setUserWin(Boolean userWin) {
        this.userWin = userWin;
    }

    public Boolean getAiWin() {
        return aiWin;
    }

    public void setAiWin(Boolean aiWin) {
        this.aiWin = aiWin;
    }

    public void resetTable(){
        for (int i = 0; i < table.length; i++){
            table[i] = 0;
        }
        userWin = false;
        aiWin = false;
        totalTurn = 9;
        userTurn = 0;
    }
}
