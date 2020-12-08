package Model;

import GUI.Runner;
import basicpackage.BasicBoard;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class TicTacToe extends BasicBoard implements Game {

    int moveCount;
    private String rules;
    private static String boardtype = "Model.TicTacToe";
    private int currentPlayer;
    private boolean gameEnded = false;
    String text;
    private boolean isKiActive = true;
    private KI ki;
    int size;

    public KI getKi() {
        return ki;
    }

    public boolean isKiActive() {
        return isKiActive;
    }

    private ArrayList<Runner.Cell> cells;

    public ArrayList<Runner.Cell> getCells() {
        return cells;
    }

    public void setCells(ArrayList<Runner.Cell> cells) {
        this.cells = cells;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public void setGameEnded(boolean gameEnded) {
        this.gameEnded = gameEnded;
    }



    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int player) {
        this.currentPlayer=player;
    }

    public void changeCurrentPlayer(int currentPlayer) {
        if (currentPlayer==1)
            this.currentPlayer = currentPlayer+1;
        else  this.currentPlayer = currentPlayer-1;
    }

    @Override
    public String gameStatus() {
        return null;
    }

    public TicTacToe(int size){
        super();
        initBoard(size);
        this.boardname = boardname;
        this.rules = rules;
        this.currentPlayer = 1;
        cells= new ArrayList<>();
        this.size=size;
        if(isKiActive){
            ki=new KI(this, size);
        }
    }

    private int[][] initBoard(int size){
        board = new int [size][size];
        for(int i=0; i<board.length;i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = 0;
            }
        }
        return board;
    }

    public ArrayList<Pairs> freeFields(){
        ArrayList<Pairs> freeFields = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0) {
                    Pairs pair = new Pairs(i, j);
                    freeFields.add(pair);
                }
            }
        }
        return freeFields;
    }




    public int[][] playerMove(int row, int column, int player, Runner.Cell cell, int size){

        if(player==currentPlayer) {

            if (board[row][column] == 0) {
                if (player == 1) {
                    board[row][column] = 1;
                    drawX(cell);
                    setCurrentPlayer(2);
                }
                else {
                    board[row][column] = 2;
                    drawO(cell);
                    setCurrentPlayer(1);
                }
                moveCount++;

            } else {
                this.text="Field is not empty, please choose another one!";
            }

            checkForWinner(row, column, player, size);
            return board;
        }
        else {
            System.out.println("It ist not your Turn!");
            return board;
        }

    }

    private void checkForWinner(int row, int column, int player, int size) {
        //check end conditions

        //check col
        for(int i = 0; i < this.size; i++){
            if(board[row][i] != player)
                break;
            if(i == this.size-1){
                System.out.println("Player "+player+" is the winner!");
                this.text="Player "+player+" is the winner!";
                setGameEnded(true);
            }
        }

        //check row
        for(int i = 0; i < this.size; i++){
            if(board[i][column] != player)
                break;
            if(i == this.size-1){
                System.out.println("Player "+player+" is the winner!");
                this.text="Player "+player+" is the winner!";
                setGameEnded(true);
            }
        }

        //check diag
        if(row == column){
            //we're on a diagonal
            for(int i = 0; i < this.size; i++){
                if(board[i][i] != player)
                    break;
                if(i == this.size-1){
                    System.out.println("Player "+player+" is the winner!");
                    this.text="Player "+player+" is the winner!";
                    setGameEnded(true);
                }
            }
        }

        //check anti diag
        if(row + column == this.size - 1){
            for(int i = 0; i < this.size; i++){
                if(board[i][(this.size-1)-i] != player)
                    break;
                if(i == this.size-1){
                    System.out.println("Player "+player+" is the winner!");
                    this.text="Player "+player+" is the winner!";
                    setGameEnded(true);
                }
            }
        }

        //check draw
        if(moveCount == (Math.pow(this.size, 2))){
            System.out.println("The game is a draw!");
            this.text="The game is a draw!";
            setGameEnded(true);
        }
    }


    public String stringBoard(){
        String string="";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print((board[i][j] + " "));
            }
            System.out.println();
        }
        System.out.println("___________________");
        return string;
    }


    public void drawX(Runner.Cell cell) {
        Line cross1 = new Line(10, 10,
                cell.getWidth() - 10, cell.getHeight() - 10);
        cross1.endXProperty().bind(cell.widthProperty().subtract(10));
        cross1.endYProperty().bind(cell.heightProperty().subtract(10));
        Line cross2 = new Line(10, cell.getHeight() - 10, cell.getWidth() - 10, 10);
        cross2.startYProperty().bind(cell.heightProperty().subtract(10));
        cross2.endXProperty().bind(cell.widthProperty().subtract(10));
        // Add cross to pane
        cell.getChildren().addAll(cross1, cross2);
    }

    public void drawO(Runner.Cell cell) {
        Ellipse ellipse = new Ellipse(cell.getWidth() / 2,
                cell.getHeight() / 2, cell.getWidth() / 2 - 10,
                cell.getHeight() / 2 - 10);
        ellipse.centerXProperty().bind(cell.widthProperty().divide(2));
        ellipse.centerYProperty().bind(cell.heightProperty().divide(2));
        ellipse.radiusXProperty().bind(cell.widthProperty().divide(2).subtract(10));
        ellipse.radiusYProperty().bind(cell.heightProperty().divide(2).subtract(10));
        ellipse.setStroke(Color.GRAY);
        ellipse.setFill(Color.WHITE);

        cell.getChildren().add(ellipse); // Add the ellipse to the pane
    }




}
