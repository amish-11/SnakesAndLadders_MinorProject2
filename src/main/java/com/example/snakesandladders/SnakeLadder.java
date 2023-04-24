package com.example.snakesandladders;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class SnakeLadder extends Application {
    public static final int tileSize = 40, width =10, height = 10;
    public static final int buttonLine = height*tileSize + 60, infoLine = buttonLine - 30 ;
    private static Dice dice = new Dice();
    private Player playerOne, playerTwo;
    private boolean gameStarted = false, playerOneTurn = false, playerTwoTurn = false;

    private Pane createContent(){
        Pane root = new Pane();
        root.setPrefSize(width*tileSize,height*tileSize + 100);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Tile tile = new Tile(tileSize);
                tile.setTranslateX(j*tileSize);
                tile.setTranslateY(i*tileSize);
                root.getChildren().add(tile);

            }
        }
        Image img = new Image("C:\\Users\\amish\\IdeaProjects\\SnakesAndLadders\\src\\main\\1000_F_110727701_eurhEC16etRtACv5DRMBjijyNYRXrcNL.jpg");
        ImageView board = new ImageView();
        board.setImage(img);
        board.setFitHeight(height*tileSize);
        board.setFitWidth(width*tileSize);

        // Buttons
        Button playerOneButton = new Button("Player One");
        Button playerTwoButton = new Button("Player Two");
        Button startButton = new Button("START");

        playerOneButton.setTranslateY(buttonLine);
        playerOneButton.setTranslateX(30);
        playerOneButton.setDisable(true);
        playerTwoButton.setTranslateY(buttonLine);
        playerTwoButton.setTranslateX(300);
        playerTwoButton.setDisable(true);
        startButton.setTranslateY(buttonLine);
        startButton.setTranslateX(180);

        //Info Display
        Label playerOneLabel = new Label("");
        Label playerTwoLabel = new Label("");
        Label diceLabel = new Label("Start The Game.");

        playerOneLabel.setTranslateY(infoLine);
        playerOneLabel.setTranslateX(30);
        playerTwoLabel.setTranslateY(infoLine);
        playerTwoLabel.setTranslateX(300);
        diceLabel.setTranslateY(infoLine);
        diceLabel.setTranslateX(180);

        playerOne = new Player(tileSize, Color.BLACK,"Amish");
        playerTwo = new Player(tileSize - 5, Color.WHITE,"Chiya");

        //Player Action
        playerOneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (gameStarted){
                    if (playerOneTurn){
                        int diceValue = dice.getRolledDiceValue();
                        diceLabel.setText("Dice Value: " + diceValue);
                        playerOne.movePlayer(diceValue);

                        //Winning Condition
                        if (playerOne.isWinner()){
                            diceLabel.setText(playerOne.getName() + " has won the Game !!");
                            playerOneTurn = false;
                            playerOneButton.setDisable(true);
                            playerOneLabel.setText("");

                            playerTwoTurn = true;
                            playerTwoButton.setDisable(true);
                            playerTwoLabel.setText("");

                            startButton.setDisable(false);
                            startButton.setText("Restart");
                            gameStarted = false;
                        }
                        else {
                            playerOneTurn = false;
                            playerOneButton.setDisable(true);
                            playerOneLabel.setText("");

                            playerTwoTurn = true;
                            playerTwoButton.setDisable(false);
                            playerTwoLabel.setText("Your Turn " + playerTwo.getName());
                        }

                    }
                }
            }
        });

        playerTwoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (gameStarted){
                    if (playerTwoTurn){
                        int diceValue = dice.getRolledDiceValue();
                        diceLabel.setText("Dice Value: " + diceValue);
                        playerTwo.movePlayer(diceValue);

                        //Winning Condition
                        if (playerTwo.isWinner()){
                            diceLabel.setText(playerTwo.getName() + " has won the Game !!");
                            playerOneTurn = false;
                            playerOneButton.setDisable(true);
                            playerOneLabel.setText("");

                            playerTwoTurn = true;
                            playerTwoButton.setDisable(true);
                            playerTwoLabel.setText("");

                            startButton.setDisable(false);
                            startButton.setText("Restart");
                        }
                        else {
                            playerOneTurn = true;
                            playerOneButton.setDisable(false);
                            playerOneLabel.setText("Your Turn " + playerOne.getName());

                            playerTwoTurn = false;
                            playerTwoButton.setDisable(true);
                            playerTwoLabel.setText("");
                        }

                    }
                }
            }
        });

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gameStarted = true;
                diceLabel.setText("Game Started");
                startButton.setDisable(true);

                playerOneTurn = true;
                playerOneLabel.setText("Your Turn " + playerOne.getName());
                playerOneButton.setDisable(false);
                playerOne.startingPosition();

                playerTwoTurn = false;
                playerTwoLabel.setText("");
                playerTwoButton.setDisable(true);
                playerTwo.startingPosition();
            }
        });

        root.getChildren().addAll(board,playerOneButton,playerTwoButton,startButton, playerOneLabel, playerTwoLabel, diceLabel,playerOne.getCoin(),playerTwo.getCoin());

        return root;
    }
    @Override
    public void start(Stage stage) throws IOException {
      //  FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(createContent());
        stage.setTitle("Snakes & Ladders");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}