package Controles;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginControll implements Initializable {
    @FXML
    private Button btnEntrar;

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }

    public void closeWindows(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/GetStarted.fxml"));

            Parent root = loader.load();

            Scene escena = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(escena);
            stage.setResizable(false);
            stage.show();

            Stage myStage = (Stage) this.btnEntrar.getScene().getWindow();
            myStage.close();

        }catch(IOException ioe){
            Logger.getLogger(LoginControll.class.getName()).log(Level.SEVERE, null, ioe);
        }
    }
}
