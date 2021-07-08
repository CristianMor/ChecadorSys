package Controles;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GetStartedControll implements Initializable {

    @FXML
    private Button btnClose;

    @FXML
    private ImageView imgStarted;

    @FXML
    private Button btnEntrar;

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }
    @FXML
    private void click(ActionEvent Ae){

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/Login.fxml"));

            Parent root = loader.load();

            LoginControll logControll= loader.getController();

            Scene escena = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(escena);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();

            //stage.setOnCloseRequest(e -> logControll.closeWindows());
            Stage myStage = (Stage) this.btnEntrar.getScene().getWindow();
            myStage.close();

        } catch (IOException ioe) {
            Logger.getLogger(GetStartedControll.class.getName()).log(Level.SEVERE, null, ioe);
        }
    }
    @FXML
    void salir(ActionEvent ae){
        Platform.exit();
    }
    Image imgStart = new Image(getClass().getResourceAsStream("../imgs/SysCheck.jpeg"));

    public void displayImage(){
        imgStarted.setImage(imgStart);
    }

}
