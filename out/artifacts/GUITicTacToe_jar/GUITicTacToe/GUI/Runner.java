package GUI;

import Model.KI;
import Model.TicTacToe;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Runner extends Application {



    TicTacToe ttt;
    KI ki;
    private Button reset= new Button("Reset");
    private Text textPlayer= new Text();
    private Cell[][] cell;
    private GridPane pane;
    private int size = 4;


    public static void main(String[] args) {
        launch(args);
    }



    @Override
    public void start(Stage stage) throws Exception {

        ttt = new TicTacToe(size);
        cell = new Cell[size][size];
        this.pane = initGridPane();
        ki = ttt.getKi();
        updateText(ttt.getCurrentPlayer());
        textPlayer.setStyle("-fx-font: 24 arial;");
        textPlayer.setFont(new Font(50));
        textPlayer.setTextAlignment(TextAlignment.JUSTIFY);


        reset.setMaxSize(200, 200);
        reset.setAlignment(Pos.CENTER);
        reset.setStyle("-fx-font: 24 arial;");

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(pane);;
        borderPane.setBottom(textPlayer);
        borderPane.setTop(reset);


        reset.setOnMouseClicked(e ->{
            ttt = new TicTacToe(size);
            updateText(ttt.getCurrentPlayer());
            pane.getChildren().clear();
            this.pane = initGridPane();
            try {
                this.start(stage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        Scene scene = new Scene(borderPane, 600, 600);
        stage.setTitle("Model.TicTacToe GUI");
        stage.setScene(scene);
        stage.show();
    }

    public static void startGame(String[] args){
        launch(args);
    }



    public GridPane initGridPane(){
        GridPane pane = new GridPane();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cell[i][j] = new Cell(i,j);
                pane.add(cell[i][j], j, i);
                ttt.getCells().add(cell[i][j]);
            }
        }
        return pane;
    }

    public void updateText(int currentPlayer){
        textPlayer.setText("Current Player: "+ttt.getCurrentPlayer());
    }


    public class Cell extends Pane {
        private int state;
        int x;
        int y;

        public int getState() {
            return state;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Cell(int x, int y) {
            setStyle("-fx-border-color: #000000");
            this.setPrefSize(200, 200);
            this.setOnMouseClicked(e -> handleClick());
            this.state=0;
            this.x = x;
            this.y = y;
        }


        private void handleClick() {
            if (!ttt.isGameEnded()) {

                if (state == 0 && ttt.getCurrentPlayer() == 1) {
                    ttt.playerMove(this.x, this.y, ttt.getCurrentPlayer(), this, size);
                    this.state = ttt.getCurrentPlayer();
                    updateText(ttt.getCurrentPlayer());
                    if(ttt.isKiActive()){
                        ki.KIMove();
                        updateText(ttt.getCurrentPlayer());
                        this.state = ttt.getCurrentPlayer();
                    }
                } else if (state == 0 && ttt.getCurrentPlayer() == 2) {
                    if(!ttt.isKiActive()) {
                        ttt.playerMove(this.x, this.y, ttt.getCurrentPlayer(), this, size);
                        this.state = ttt.getCurrentPlayer();
                        updateText(ttt.getCurrentPlayer());
                    }
                    else textPlayer.setText("Wait, it ist not your Turn!");
                } else {
                    textPlayer.setText("Player "+ttt.getCurrentPlayer()+": "+ttt.getText());
                    ttt.playerMove(this.x, this.y, ttt.getCurrentPlayer(), this,size);
                }
            }else {
                System.out.println("Model.Game is over! "+ ttt.getText());
                textPlayer.setText("Model.Game is over! "+ ttt.getText());
            }
        }

    }



}