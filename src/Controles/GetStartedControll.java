package Controles;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GetStartedControll implements Initializable{

    @FXML
    private ImageView imgStarted;

    @FXML
    private AnchorPane scenePane;

    @Override
    public void initialize(URL url, ResourceBundle rb){
    }

    @FXML
    private void click(ActionEvent Ae){
        loadStage("/Vistas/Login.fxml", Ae);
    }

    @FXML
    private void salir(ActionEvent ae){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar");
        alert.setHeaderText("");
        alert.setContentText("Esta seguro que desea salir?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            Platform.exit();
            System.exit(0);
        }
    }

    Image imgStart = new Image(getClass().getResourceAsStream("../imgs/SysCheck.jpeg"));

    public void displayImage(){
        imgStarted.setImage(imgStart);
    }

    private void loadStage(String url, Event event) {

        try{
            ((Node) (event.getSource())).getScene().getWindow().hide();


            Parent root = FXMLLoader.load(getClass().getResource(url));
            Scene scene = new Scene(root);
            Stage nuevoStage = new Stage();

            nuevoStage.setScene(scene);
            nuevoStage.resizableProperty().setValue(Boolean.FALSE);
            nuevoStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
                @Override
                public void handle(WindowEvent e){
                    e.consume();
                    cerrar(nuevoStage);
                }
            });

            nuevoStage.show();

        } catch (IOException ioe) {
            Logger.getLogger(GetStartedControll.class.getName()).log(Level.SEVERE, null, ioe);
        }
    }

    public void cerrar(Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar");
        alert.setHeaderText("");
        alert.setContentText("Esta seguro que desea salir?");

        if(alert.showAndWait().get() == ButtonType.OK){
            System.out.println("Haz salido");
            Platform.exit();
            System.exit(0);
        }
    }

}
