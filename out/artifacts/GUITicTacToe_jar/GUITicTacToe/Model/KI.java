package Model;

import GUI.Runner;
import Model.TicTacToe;
import java.util.ArrayList;
import java.util.Random;

public class KI {

    private int player = 2;
    private int row;
    private int column;
    private TicTacToe ttt;
    private int size;
    private ArrayList<Runner.Cell> cells;

    public KI(TicTacToe ttt, int size){
        this.ttt=ttt;
        this.cells=ttt.getCells();
        this.size=size;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int randomRow(){
        this.row = new Random().nextInt(size);
        return row;
    }

    public int randomColumn(){
        this.column = new Random().nextInt(size);
        return column;
    }

    public void KIMove() {
        ArrayList<Pairs> freeFields = ttt.freeFields();

        System.out.println(freeFields);
        int move = new Random().nextInt(freeFields.size());
        int row = freeFields.get(move).getX();
        int column = freeFields.get(move).getY();

        Runner.Cell cell = null;

        for (Runner.Cell c : cells) {
            if (c.getX() == row && c.getY() == column) {
                cell = c;
                break;
            }
        }

        ttt.playerMove(row, column, 2, cell, size);
    }

}

