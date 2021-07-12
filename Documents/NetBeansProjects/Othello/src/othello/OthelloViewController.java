package othello;

import javafx.scene.shape.Rectangle;
import javafx.scene.layout.*;
import java.util.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class OthelloViewController extends FlowPane {
    
    private final int BOARD_SIZE = 8;
    private final ImageView[][] BOARD = new ImageView[BOARD_SIZE][BOARD_SIZE];
    private Button upButton;
    private Button downButton;
    private Button leftButton;
    private Button rightButton;
    private Button moveButton;
    private Button submitButton;
    private TextField consoleTextField;
    private TextArea gameChatTextArea;
    private CheckBox showValidMovesCheckBox;
    private Label player1ScoreLabel;
    private Label player2ScoreLabel;
    GridPane boardGridPane;
    
    public OthelloViewController() {
        
        consoleTextField = new TextField();
        consoleTextField.setPrefHeight(23.0);
        consoleTextField.setPrefWidth(975.0);
        consoleTextField.setPadding(new Insets(5.0, 5.0, 5.0, 5.0));
        
        submitButton = new Button("Submit");
        submitButton.setTextFill(Paint.valueOf("RED"));
        submitButton.setStyle("-fx-background-color: black;");
        submitButton.setPrefSize(96.0, 23.0);
        
        FlowPane consoleFlowPane = new FlowPane();
        consoleFlowPane.setPrefHeight(23.0);
        consoleFlowPane.setPrefWidth(975.0 + 96.0);
        consoleFlowPane.setOrientation(Orientation.HORIZONTAL);
        consoleFlowPane.setBorder(new Border(new BorderStroke(Color.GREY, 
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5.0, 0.0, 0.0, 0.0))));
        consoleFlowPane.getChildren().addAll(consoleTextField, submitButton);
        
        
        this.setPrefHeight(700.0);
        this.setPrefWidth(1075.0);
        this.setStyle("-fx-background-color: rgb(220, 220, 220);");
        this.setOrientation(Orientation.HORIZONTAL);
        this.setRowValignment(VPos.CENTER);
        this.setAlignment(Pos.TOP_LEFT);
        this.setBorder(new Border(new BorderStroke(Color.GREY, 
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5.0, 5.0, 5.0, 5.0))));
        this.getChildren().addAll(setUpMainGridPane(), consoleFlowPane);
        
        
    }
    
    private GridPane setUpMainGridPane() {
        
        BorderPane boardAreaBorderPane = new BorderPane();
        boardAreaBorderPane.setPrefHeight(564.0);
        boardAreaBorderPane.setPrefWidth(640);
        
        List<FlowPane> boardLabelsFlowPanes = setUpBoardLabels();
        boardAreaBorderPane.setTop(boardLabelsFlowPanes.get(0));
        boardAreaBorderPane.setBottom(boardLabelsFlowPanes.get(1));
        boardAreaBorderPane.setCenter(setUpBoardGridPane());
        boardAreaBorderPane.setLeft(boardLabelsFlowPanes.get(2));
        boardAreaBorderPane.setRight(boardLabelsFlowPanes.get(3));
        
        showValidMovesCheckBox = new CheckBox("Show Valid Moves");
        
        ColumnConstraints columnConstraints1 = new ColumnConstraints();
        columnConstraints1.setHgrow(Priority.SOMETIMES);
        columnConstraints1.setMaxWidth(640.0);
        columnConstraints1.setMinWidth(10.0);
        columnConstraints1.setPrefWidth(617.0);
        
        ColumnConstraints columnConstraints2 = new ColumnConstraints();
        columnConstraints2.setHgrow(Priority.SOMETIMES);
        columnConstraints2.setMaxWidth(525.0);
        columnConstraints2.setMinWidth(10.0);
        columnConstraints2.setPrefWidth(450.0);
        
        RowConstraints rowConstraints1 = new RowConstraints();
        rowConstraints1.setMaxHeight(218.0);
        rowConstraints1.setMinHeight(0.0);
        rowConstraints1.setPrefHeight(23.0);
        rowConstraints1.setVgrow(Priority.SOMETIMES);
        
        RowConstraints rowConstraints2 = new RowConstraints();
        rowConstraints2.setMaxHeight(624.0);
        rowConstraints2.setMinHeight(10.0);
        rowConstraints2.setPrefHeight(612.0);
        rowConstraints2.setVgrow(Priority.SOMETIMES);
        
        GridPane mainGridPane = new GridPane();
        mainGridPane.setMaxHeight(Double.NEGATIVE_INFINITY);
        mainGridPane.setMaxWidth(Double.NEGATIVE_INFINITY);
        mainGridPane.setMinHeight(Double.NEGATIVE_INFINITY);
        mainGridPane.setMinWidth(Double.NEGATIVE_INFINITY);
        mainGridPane.setPrefHeight(670.0);
        mainGridPane.setPrefWidth(1067.0);
        mainGridPane.getColumnConstraints().addAll(columnConstraints1, columnConstraints2);
        mainGridPane.setAlignment(Pos.CENTER);
        mainGridPane.getRowConstraints().addAll(rowConstraints1, rowConstraints2);
        mainGridPane.add(boardAreaBorderPane, 0, 1);
        mainGridPane.add(setUpGameChatAndDetailsFlowPane(), 1, 1);
        mainGridPane.add(showValidMovesCheckBox, 1, 0);
        
        return mainGridPane;
        
    }
    
    private GridPane setUpBoardGridPane() {
       
        boardGridPane = new GridPane();
        boardGridPane.setPrefHeight(460.0); 
        boardGridPane.setPrefWidth(483.0);
        boardGridPane.setBorder(new Border(new BorderStroke(Color.GREY, 
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.0, 1.0, 1.0, 1.0))));
        
        for(int i = 0; i < 8; i++) {
            boardGridPane.getColumnConstraints().add(new ColumnConstraints(60.0));
            boardGridPane.getRowConstraints().add(new RowConstraints(60.0));
        }
        
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                
                BorderPane boardSquare = new BorderPane();
                boardSquare.setPrefSize(60.0, 60.0);
                
                
                if((i % 2 == 0) == (j % 2 == 0)) {
                    boardSquare.setStyle("-fx-background-color: WHITE;");
                }
                else {
                    boardSquare.setStyle("-fx-background-color: BLACK;");
                }
                   
                boardGridPane.add(boardSquare, i, j);
                InitializeGame();
//                BOARD[i][j] = boardSquare;
            }
        }
        return boardGridPane;
    }
    
    private List<FlowPane> setUpBoardLabels() {
        
        //Top board labels start here
        Label top1Label = new Label("1");
        top1Label.setFont(new Font(20.0));
        
        Label top2Label = new Label("2");
        top2Label.setFont(new Font(20.0));
        
        Label top3Label = new Label("3");
        top3Label.setFont(new Font(20.0));
        
        Label top4Label = new Label("4");
        top4Label.setFont(new Font(20.0));
        
        Label top5Label = new Label("5");
        top5Label.setFont(new Font(20.0));
        
        Label top6Label = new Label("6");
        top6Label.setFont(new Font(20.0));
        
        Label top7Label = new Label("7");
        top7Label.setFont(new Font(20.0));
        
        Label top8Label = new Label("8");
        top8Label.setFont(new Font(20.0));
        //Top board labels end here
        
        FlowPane topBoardLabelsFlowPane = new FlowPane();
        topBoardLabelsFlowPane.setAlignment(Pos.CENTER_LEFT);
        topBoardLabelsFlowPane.setHgap(49.0);
        topBoardLabelsFlowPane.setPrefHeight(70.0);
        topBoardLabelsFlowPane.setPrefWidth(640.0);
        topBoardLabelsFlowPane.setPadding(new Insets(0, 65.0, 0, 92.0));
        topBoardLabelsFlowPane.getChildren().addAll(top1Label, top2Label, top3Label,
                top4Label, top5Label, top6Label, top7Label, top8Label);
        
        //Bottom board labels start here
        Label bottom1Label = new Label("1");
        bottom1Label.setFont(new Font(20.0));
        
        Label bottom2Label = new Label("2");
        bottom2Label.setFont(new Font(20.0));
        
        Label bottom3Label = new Label("3");
        bottom3Label.setFont(new Font(20.0));
        
        Label bottom4Label = new Label("4");
        bottom4Label.setFont(new Font(20.0));
        
        Label bottom5Label = new Label("5");
        bottom5Label.setFont(new Font(20.0));
        
        Label bottom6Label = new Label("6");
        bottom6Label.setFont(new Font(20.0));
        
        Label bottom7Label = new Label("7");
        bottom7Label.setFont(new Font(20.0));
        
        Label bottom8Label = new Label("8");
        bottom8Label.setFont(new Font(20.0));
        //bottom board labels end here
        
        FlowPane bottomBoardLabelsFlowPane = new FlowPane();
        bottomBoardLabelsFlowPane.setAlignment(Pos.CENTER_LEFT);
        bottomBoardLabelsFlowPane.setHgap(49.0);
        bottomBoardLabelsFlowPane.setPrefHeight(70.0);
        bottomBoardLabelsFlowPane.setPrefWidth(640.0);
        bottomBoardLabelsFlowPane.setPadding(new Insets(0, 65.0, 0, 92.0));
        bottomBoardLabelsFlowPane.getChildren().addAll(bottom1Label, bottom2Label, bottom3Label,
                bottom4Label, bottom5Label, bottom6Label, bottom7Label, bottom8Label);
        
        //Left board labels start here
        Label left1Label = new Label("A");
        left1Label.setFont(new Font(20.0));
        
        Label left2Label = new Label("B");
        left2Label.setFont(new Font(20.0));
        
        Label left3Label = new Label("C");
        left3Label.setFont(new Font(20.0));
        
        Label left4Label = new Label("D");
        left4Label.setFont(new Font(20.0));
        
        Label left5Label = new Label("E");
        left5Label.setFont(new Font(20.0));
        
        Label left6Label = new Label("F");
        left6Label.setFont(new Font(20.0));
        
        Label left7Label = new Label("G");
        left7Label.setFont(new Font(20.0));
        
        Label left8Label = new Label("H");
        left8Label.setFont(new Font(20.0));
        //left board labels end here
        
        FlowPane leftBoardLabelsFlowPane = new FlowPane();
        leftBoardLabelsFlowPane.setAlignment(Pos.TOP_CENTER);
        leftBoardLabelsFlowPane.setVgap(30.0);
        leftBoardLabelsFlowPane.setPrefHeight(640.0);
        leftBoardLabelsFlowPane.setPrefWidth(70.0);
        leftBoardLabelsFlowPane.setColumnHalignment(HPos.CENTER);
        leftBoardLabelsFlowPane.setOrientation(Orientation.VERTICAL);
        leftBoardLabelsFlowPane.setPadding(new Insets(25.0, 0.0, 0.0, 0.0));
        leftBoardLabelsFlowPane.getChildren().addAll(left1Label, left2Label, left3Label,
                left4Label, left5Label, left6Label, left7Label, left8Label);
        
        //Right board labels start here
        Label right1Label = new Label("A");
        right1Label.setFont(new Font(20.0));
        
        Label right2Label = new Label("B");
        right2Label.setFont(new Font(20.0));
        
        Label right3Label = new Label("C");
        right3Label.setFont(new Font(20.0));
        
        Label right4Label = new Label("D");
        right4Label.setFont(new Font(20.0));
        
        Label right5Label = new Label("E");
        right5Label.setFont(new Font(20.0));
        
        Label right6Label = new Label("F");
        right6Label.setFont(new Font(20.0));
        
        Label right7Label = new Label("G");
        right7Label.setFont(new Font(20.0));
        
        Label right8Label = new Label("H");
        right8Label.setFont(new Font(20.0));
        //right board labels end here
        
        FlowPane rightBoardLabelsFlowPane = new FlowPane();
        rightBoardLabelsFlowPane.setAlignment(Pos.TOP_CENTER);
        rightBoardLabelsFlowPane.setVgap(30.0);
        rightBoardLabelsFlowPane.setPrefHeight(564.0);
        rightBoardLabelsFlowPane.setPrefWidth(70.0);
        rightBoardLabelsFlowPane.setColumnHalignment(HPos.CENTER);
        rightBoardLabelsFlowPane.setOrientation(Orientation.VERTICAL);
        rightBoardLabelsFlowPane.setPadding(new Insets(25.0, 0.0, 0.0, 0.0));
        rightBoardLabelsFlowPane.getChildren().addAll(right1Label, right2Label, right3Label,
                right4Label, right5Label, right6Label, right7Label, right8Label);
        
        List<FlowPane> boardLabelsFlowPanes = new ArrayList<>();
        boardLabelsFlowPanes.add(topBoardLabelsFlowPane);
        boardLabelsFlowPanes.add(bottomBoardLabelsFlowPane);
        boardLabelsFlowPanes.add(leftBoardLabelsFlowPane);
        boardLabelsFlowPanes.add(rightBoardLabelsFlowPane);
        
        return boardLabelsFlowPanes;
    }
    /**
     * Sets up the border pane for the game control buttons
     * @return 
     */
    private BorderPane setUpGameControlsBorderPane() {
        //
        ImageView upArrowImageView = new ImageView("images/uparrow.png");
        upArrowImageView.setFitHeight(40.0);
        upArrowImageView.setFitWidth(40.0);
        upArrowImageView.setPickOnBounds(true);
        upArrowImageView.setPreserveRatio(true);
        
        upButton = new Button();
        upButton.setPrefSize(40.0, 40.0);
        upButton.setGraphic(upArrowImageView);
        upButton.setStyle("-fx-background-color: WHITE;");
        upButton.setMaxSize(40.0, 40.0);
        upButton.setBorder(new Border(new BorderStroke(Color.GREY, 
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.0, 1.0, 1.0, 1.0))));
        
        ImageView downArrowImageView = new ImageView("images/downarrow.png");
        downArrowImageView.setFitHeight(40.0);
        downArrowImageView.setFitWidth(40.0);
        downArrowImageView.setPickOnBounds(true);
        downArrowImageView.setPreserveRatio(true);
        
        downButton = new Button();
        downButton.setPrefSize(40, 40);
        downButton.setGraphic(downArrowImageView);
        downButton.setStyle("-fx-background-color: WHITE;");
        downButton.setBorder(new Border(new BorderStroke(Color.GREY, 
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.0, 1.0, 1.0, 1.0))));
        
        ImageView leftArrowImageView = new ImageView("images/leftarrow.png");
        leftArrowImageView.setFitHeight(40.0);
        leftArrowImageView.setFitWidth(40.0);
        leftArrowImageView.setPickOnBounds(true);
        leftArrowImageView.setPreserveRatio(true);
        
        leftButton = new Button();
        leftButton.setPrefSize(40.0, 40.0);
        leftButton.setGraphic(leftArrowImageView);
        leftButton.setStyle("-fx-background-color: WHITE;");
        leftButton.setBorder(new Border(new BorderStroke(Color.GREY, 
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.0, 1.0, 1.0, 1.0))));
        
        ImageView rightArrowImageView = new ImageView("images/rightarrow.png");
        rightArrowImageView.setFitHeight(40.0);
        rightArrowImageView.setFitWidth(40.0);
        rightArrowImageView.setPickOnBounds(true);
        rightArrowImageView.setPreserveRatio(true);
        
        rightButton = new Button();
        rightButton.setPrefSize(40.0, 40.0);
        rightButton.setGraphic(rightArrowImageView);
        rightButton.setStyle("-fx-background-color: WHITE;");
        rightButton.setBorder(new Border(new BorderStroke(Color.GREY, 
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.0, 1.0, 1.0, 1.0))));
        
        moveButton = new Button("Move");
        moveButton.setFont(new Font(10));
        moveButton.setPrefSize(40.0, 40.0);
        moveButton.setStyle("-fx-background-color: WHITE;");
        moveButton.setBorder(new Border(new BorderStroke(Color.GREY, 
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.0, 1.0, 1.0, 1.0))));
        System.out.println(leftButton.getPrefHeight());
        
        BorderPane gameControlsBorderPane = new BorderPane();
        gameControlsBorderPane.setPrefHeight(145.0);
        gameControlsBorderPane.setPrefWidth(157.0);
        gameControlsBorderPane.setTop(upButton);
        gameControlsBorderPane.setBottom(downButton);
        gameControlsBorderPane.setCenter(moveButton);
        gameControlsBorderPane.setLeft(leftButton);
        gameControlsBorderPane.setRight(rightButton);
        BorderPane.setAlignment(upButton, Pos.CENTER);
        BorderPane.setAlignment(downButton, Pos.CENTER);
        BorderPane.setAlignment(leftButton, Pos.CENTER);
        BorderPane.setAlignment(rightButton, Pos.CENTER);
 
        return gameControlsBorderPane;
    }
    
    private FlowPane setUpGameDetailsFlowPane() {
        
        Label player1Label = new Label("Player 1 Pieces:");
        player1Label.setFont(new Font(15.0));
        
        Label player2Label = new Label("Player 2 Pieces:");
        player2Label.setFont(new Font(15.0));
        
        BorderPane playerLabelsBorderPane = new BorderPane();
        playerLabelsBorderPane.setTop(player1Label);
        playerLabelsBorderPane.setBottom(player2Label);
        playerLabelsBorderPane.setPrefHeight(146);
        playerLabelsBorderPane.setPrefWidth(145);
        BorderPane.setAlignment(player1Label, Pos.CENTER);
        BorderPane.setAlignment(player2Label, Pos.CENTER);
        playerLabelsBorderPane.setPadding(new Insets(25.0, 0.0, 25.0, 0.0));
        
        ImageView player1IconImageView = new ImageView("images/black.png");
        player1IconImageView.setFitHeight(36);
        player1IconImageView.setFitWidth(36);
        
        ImageView player2IconImageView = new ImageView("images/white.png");
        player2IconImageView.setFitHeight(36);
        player2IconImageView.setFitWidth(36);
        
        player1ScoreLabel = new Label("240000");
        player1ScoreLabel.setFont(new Font(15.0));
        
        player2ScoreLabel = new Label("240000");
        player2ScoreLabel.setFont(new Font(15.0));
        
        ColumnConstraints columnConstraints1 = new ColumnConstraints();
        columnConstraints1.setHgrow(Priority.SOMETIMES);
        columnConstraints1.setMaxWidth(142.0);
        columnConstraints1.setMinWidth(10.0);
        columnConstraints1.setPrefWidth(45.0);
        
        ColumnConstraints columnConstraints2 = new ColumnConstraints();
        columnConstraints2.setHgrow(Priority.SOMETIMES);
        columnConstraints2.setMaxWidth(101.0);
        columnConstraints2.setMinWidth(0.0);
        columnConstraints2.setPrefWidth(99.0);
        
        RowConstraints rowConstraints1 = new RowConstraints();
        rowConstraints1.setMaxHeight(75.0);
        rowConstraints1.setMinHeight(0.0);
        rowConstraints1.setPrefHeight(67.0);
        rowConstraints1.setVgrow(Priority.SOMETIMES);
        
        RowConstraints rowConstraints2 = new RowConstraints();
        rowConstraints2.setMaxHeight(81.0);
        rowConstraints2.setMinHeight(10.0);
        rowConstraints2.setPrefHeight(63.0);
        rowConstraints2.setVgrow(Priority.SOMETIMES);
        
        GridPane playerIconAndScoreGridPane = new GridPane();
        playerIconAndScoreGridPane.setMaxHeight(Double.NEGATIVE_INFINITY);
        playerIconAndScoreGridPane.setMaxWidth(Double.NEGATIVE_INFINITY);
        playerIconAndScoreGridPane.setMinHeight(Double.NEGATIVE_INFINITY);
        playerIconAndScoreGridPane.setMinWidth(Double.NEGATIVE_INFINITY);
        playerIconAndScoreGridPane.setPrefHeight(145.0);
        playerIconAndScoreGridPane.setPrefWidth(144.0);
        playerIconAndScoreGridPane.getColumnConstraints().addAll(columnConstraints1, columnConstraints2);
        playerIconAndScoreGridPane.getRowConstraints().addAll(rowConstraints1, rowConstraints2);
        
        playerIconAndScoreGridPane.add(player1IconImageView, 0, 0);
        playerIconAndScoreGridPane.add(player2IconImageView, 0, 1);
        playerIconAndScoreGridPane.add(player1ScoreLabel, 1, 0);
        playerIconAndScoreGridPane.add(player2ScoreLabel, 1, 1);
        playerIconAndScoreGridPane.setAlignment(Pos.CENTER);
        
        FlowPane gameDetailsFlowPane = new FlowPane();
        gameDetailsFlowPane.setPrefHeight(199);
        gameDetailsFlowPane.setPrefWidth(451);
        gameDetailsFlowPane.setPadding(new Insets(20.0, 0.0, 0.0, 5.0));
        gameDetailsFlowPane.getChildren().addAll(setUpGameControlsBorderPane(), playerLabelsBorderPane, playerIconAndScoreGridPane);
        
        return gameDetailsFlowPane;
    }
    
    private FlowPane setUpGameChatAndDetailsFlowPane() {
        
        gameChatTextArea = new TextArea();
        gameChatTextArea.setPrefHeight(430.0);
        gameChatTextArea.setPrefWidth(450.0);
        gameChatTextArea.setPadding(new Insets(5.0, 5.0, 5.0, 5.0));
        gameChatTextArea.setBorder(new Border(new BorderStroke(Color.GREY, 
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5.0, 0, 5.0, 0))));
        gameChatTextArea.setStyle("-fx-control-inner-background: rgb(175, 175, 255)");
        
        FlowPane gameChatAndDetailsFlowPane = new FlowPane();
        gameChatAndDetailsFlowPane.setPrefHeight(621.0);
        gameChatAndDetailsFlowPane.setPrefWidth(455.0);
        gameChatAndDetailsFlowPane.getChildren().addAll(setUpGameDetailsFlowPane(), gameChatTextArea);
        gameChatAndDetailsFlowPane.setBorder(new Border(new BorderStroke(Color.GREY, 
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5.0, 5.0, 5.0, 5.0))));
        
        return gameChatAndDetailsFlowPane;
    }
    
    /**
     * Initialises the game
     */
    private void InitializeGame() {
        
        //remove all the pieces from the board
        boardGridPane.getChildren().forEach(square -> ((BorderPane)square).getChildren().clear());
        
        
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                if((i == 4 - 1 && j == 4 - 1) | (i == 5 - 1 && j == 5 - 1) ) {
                    boardGridPane.add(new BorderPane(new ImageView("images/white.png"), null, null, null, null), i, j);
                }
                if((i == 4 - 1 && j == 5 - 1) | (i == 5 - 1 && j == 4 - 1) ) {
                    boardGridPane.add(new BorderPane(new ImageView("images/black.png"), null, null, null, null), i, j);
                }
            }
        }
    }
    
}
