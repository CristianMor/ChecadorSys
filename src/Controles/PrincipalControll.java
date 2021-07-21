package Controles;

import Modelos.Empleado;
import Modelos.EmpleadosDAO;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrincipalControll implements Initializable {

    private final EmpleadosDAO model = new EmpleadosDAO();
    @FXML
    private VBox empleadoLayout;

    @FXML private Button btnVolver;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Empleado> listArr = model.obtenerEmpleados();

        for(int i=0; i<listArr.size(); i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../Vistas/Empleado_item.fxml"));

            try{
                HBox hbox = fxmlLoader.load();
                EmpleadoitemControll eic= fxmlLoader.getController();
                eic.setData(listArr.get(i));
                empleadoLayout.getChildren().add(hbox);
            }catch(IOException e){
                e.printStackTrace();
            }

        }
    }

    /*private List<Empleado> empleados(){
        List<Empleado> ls = new ArrayList<>();

        Empleado empleado = new Empleado();
        empleado.setId(1);
        empleado.setImgSrc("../imgs/perfil2.jpg");
        empleado.setNombre("Cristian Martin");
        ls.add(empleado);

        return ls;
    }*/

    @FXML
    private void eventAction(ActionEvent event){
        loadStage("/Vistas/Login.fxml", event, 1);
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
                nuevoStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
                    @Override
                    public void handle(WindowEvent e){
                        e.consume();
                        cerrar(nuevoStage);
                    }
                });
            }else{
                nuevoStage.initStyle(StageStyle.UNDECORATED);
            }
            nuevoStage.show();
        }catch(IOException ex){
            Logger.getLogger(LoginControll.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cerrar(Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar");
        alert.setHeaderText("");
        alert.setContentText("Esta seguro que desea salir?");

        if(alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("Haz salido");
            Platform.exit();
            System.exit(0);
        }
    }
}
