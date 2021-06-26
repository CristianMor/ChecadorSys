package Controles;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../Vistas/GetStarted.fxml"));
            Pane ventana = loader.load();

            Scene escena = new Scene(ventana);
            stage.setScene(escena);
            stage.setResizable(false);
            stage.show();

        }catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }
    }

    public static void main(String[] args){
        launch(args);
    }
}
