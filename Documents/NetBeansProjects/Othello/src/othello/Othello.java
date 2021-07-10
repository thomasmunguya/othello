/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package othello;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author MTH
 */
public class Othello extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        
        OthelloViewController othelloViewController = new OthelloViewController();
        Scene scene = new Scene(othelloViewController, 1082, 712);
        
        primaryStage.setTitle("Arzaan's Othello Client");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
