package Controles;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CheckControll implements Initializable {
    @FXML
    private Label lblFecha;

    @FXML
    private Label lblHora;

    @FXML
    private Button btnSalir;

    @FXML
    private void eventAction(ActionEvent event){
        Object evt = event.getSource();

        if(evt.equals(btnSalir)){
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Autorización");
            dialog.setHeaderText("Ingrese la contraseña del admin.");

            //ESTABLESCO EL ICONO ESTE DEBE ESTAR INCLUIDO EN EL PROYECTO
            dialog.setGraphic(new ImageView(this.getClass().getResource("/imgs/AuthAdmin.png").toString()));
            Stage stage= (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(this.getClass().getResource("/imgs/keys.png").toString()));

            //SE CONFIGURA Y SE AGREGAN LOS TIPOS DE BOTONES.
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            //CREE LAS ETIUQUETAS Y LOS CAMPOS
            GridPane grid= new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            PasswordField pass= new PasswordField();
            pass.setPromptText("Contraseña");

            grid.add(new Label("Autorización: "), 0, 0);
            grid.add(pass, 1, 0);

            //ACTIVAR / DESACTIVAR EL BOTON DE OK DEPENDIENDO DE SI SE INGRESO UNA CONTRASE;A
            Node okButton = dialog.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setDisable(true);

            //REALIZA LA VALIDACION UTILIZANDO LA SINTAXIS LAMBDA DE JAVA 8.
            pass.textProperty().addListener((observable, valorViejo, valorNuevo)->{
                okButton.setDisable(valorNuevo.trim().isEmpty());
            });

            dialog.getDialogPane().setContent(grid);

            //FOCUS EN EL CAMPO PASSWORD
            Platform.runLater(()-> pass.requestFocus());

            //CONVERTIMOS EL RESULTADO EN UN PAR DE CONTRASE;A CUANDO SE HACE CLICK EN OK
            dialog.setResultConverter(dialogButton ->{
                if(dialogButton == ButtonType.OK){
                    return new String(pass.getText());
                }
                return null;
            });
            Optional<String> result = dialog.showAndWait();

            if(result.isPresent()){
                System.out.println("Password: "+result.get());
                if(result.get().equals("admin")){
                    loadStage("/Vistas/Login.fxml", event, 1);
                    System.out.println("Correcto");
                }else{
                    Alert alert= new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Datos incorrectos");
                    alert.setHeaderText(null);
                    alert.setContentText("Ooops, los datos son incorrectos!");
                    alert.showAndWait();
                }
            }

            result.ifPresent(passw -> System.out.println("Password: "+passw));
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void loadStage(String url, Event event, int screen){
        try{

            ((Node) (event.getSource())).getScene().getWindow().hide();

            /*Object eventSource = event.getSource();
            Node sourceAsNode = (Node) eventSource;
            Scene oldScene = sourceAsNode.getScene();
            Window window = oldScene.getWindow();
            Stage stage = (Stage) window;
            stage.hide();*/

            Parent root = FXMLLoader.load(getClass().getResource(url));
            Scene scene = new Scene(root);
            Stage nuevoStage= new Stage();

            nuevoStage.setScene(scene);
            if(screen > 0){
                nuevoStage.resizableProperty().setValue(Boolean.FALSE);
            }else{
                nuevoStage.initStyle(StageStyle.UNDECORATED);
            }
            nuevoStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
                @Override
                public void handle(WindowEvent e){
                    e.consume();
                    cerrar(nuevoStage);
                }
            });
            nuevoStage.show();
        }catch(IOException ex){
            Logger.getLogger(LoginControll.class.getName()).log(Level.SEVERE, null, ex);
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
