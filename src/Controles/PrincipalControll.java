package Controles;

import Modelos.Empleado;
import Modelos.EmpleadosDAO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrincipalControll implements Initializable {

    private final EmpleadosDAO model = new EmpleadosDAO();
    Empleado empleado = null;

    @FXML
    private Button btnVolver;

    @FXML
    private Button btnNuevoEmple;

    @FXML
    private Button btnRefrescar;

    @FXML
    private TableView<Empleado> tablaEmpleados;

    @FXML
    private TableColumn<Empleado, String> ftoCol;

    @FXML
    private TableColumn<Empleado, String> nomCol;

    @FXML
    private TableColumn<Empleado, String>  apPaterCol;

    @FXML
    private TableColumn<Empleado, String>  puestoCol;

    @FXML
    private TableColumn<Empleado, String>  accionesCol;

    ObservableList<Empleado> listaEmpleado = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarDatos();
    }

    @FXML
    private void refrescarTabla() {
        listaEmpleado.clear();

        listaEmpleado= model.obtenerEmpleados();

        tablaEmpleados.setItems(listaEmpleado);
    }
    private void cargarDatos() {

        refrescarTabla();

        ftoCol.setCellValueFactory(new PropertyValueFactory<>("imgSrc"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        apPaterCol.setCellValueFactory(new PropertyValueFactory<>("ap_paterno"));
        puestoCol.setCellValueFactory(new PropertyValueFactory<>("puesto"));

        //AGREGAR LA CELDA DE BOTON EDITAR
        Callback<TableColumn<Empleado, String>, TableCell<Empleado, String>> cellFoctory = (TableColumn<Empleado, String> param) -> {
            // HACER QUE LA CELDA CONTENGA BOTONES
            final TableCell<Empleado, String> cell = new TableCell<Empleado, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //CELDA CREADA SOLO EN CELDAS NO BASIAS
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        ImageView editarIcon = new ImageView("/imgs/userEdit32-white.png");
                        editarIcon.setFitHeight(32);
                        editarIcon.setFitWidth(32);

                        ImageView eliminarIcon = new ImageView("/imgs/userDel32-white.png");
                        eliminarIcon.setFitHeight(32);
                        eliminarIcon.setFitWidth(32);

                        eliminarIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#ff1744;"
                        );
                        editarIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#00E676;"
                        );
                        eliminarIcon.setOnMouseClicked((MouseEvent event) -> {
                            empleado= tablaEmpleados.getSelectionModel().getSelectedItem();
                            System.out.println("Boton de: "+empleado.getId());
                            refrescarTabla();

                        });
                        editarIcon.setOnMouseClicked((MouseEvent event) -> {

                            empleado = tablaEmpleados.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("/Vistas/agregarEmpleado.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(PrincipalControll.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            aggEmpleControll addEmpleado = loader.getController();
                            addEmpleado.setUpdate(true);
                            addEmpleado.setTextField(empleado.getNombre(), empleado.getAp_paterno(), empleado.getAp_materno(), empleado.getPuesto(), empleado.getTelefono());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.resizableProperty().setValue(Boolean.FALSE);
                            stage.show();
                        });

                        HBox managebtn = new HBox(editarIcon, eliminarIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(eliminarIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editarIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }
            };
            return cell;
        };
        accionesCol.setCellFactory(cellFoctory);
        tablaEmpleados.setItems(listaEmpleado);

    }

    @FXML
    private void eventAction(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnVolver)){
            loadStage("/Vistas/Login.fxml", event, 1);
        }else if(evt.equals(btnNuevoEmple)){
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/Vistas/agregarEmpleado.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.resizableProperty().setValue(Boolean.FALSE);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
