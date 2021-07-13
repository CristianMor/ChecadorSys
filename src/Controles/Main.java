package Controles;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage stage){

        try{
            Parent root = FXMLLoader.load(getClass().getResource("/Vistas/GetStarted.fxml"));
            Scene scene = new Scene(root);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();

        }catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }
    }

    public static void main(String[] args){
        launch(args);
    }

}
