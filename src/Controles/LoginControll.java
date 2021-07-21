package Controles;

import Modelos.Usuarios;
import Modelos.UsuariosDAO;
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
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginControll implements Initializable {

    private final UsuariosDAO model = new UsuariosDAO();

    @FXML
    private ComboBox<String> userCBox;

    @FXML
    private Button btnEntrar;

    @FXML
    private Button btnVolver;

    @FXML
    private PasswordField cajaPass;

    @FXML
    private void eventKey(KeyEvent event){
        Object evt= event.getSource();

        if(evt.equals(cajaPass)){
            System.out.println(event.getCharacter());
            if(event.getCharacter().equals(" ")){
                event.consume();
            }
        }
    }

    @FXML
    private void selecionCBox(ActionEvent e){
        Object evt= e.getSource();

        if(evt.equals(userCBox)){
            System.out.println(userCBox.getSelectionModel().getSelectedItem());
            cajaPass.setText("");
            cajaPass.requestFocus();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){

        ArrayList<Usuarios> listArr= model.selectUsuario("USUARIO", null);
        String [] users = new String[2];

        for(int i=0; i < listArr.size(); i++){
            users[i]= listArr.get(i).getUsuario();
        }

        ArrayList<String> listUser = new ArrayList<>();
        Collections.addAll(listUser, users);

        userCBox.getItems().addAll(listUser);
        userCBox.getSelectionModel().selectFirst();
    }

    @FXML
    private void eventAction(ActionEvent event){
        Object evt = event.getSource();

        if(evt.equals(btnVolver)) {
            loadStage("/Vistas/GetStarted.fxml", event, 0);
        }else if(evt.equals(btnEntrar)){
            if(!cajaPass.getText().isEmpty()){

                String user= userCBox.getSelectionModel().getSelectedItem();
                String pass= cajaPass.getText();

                int estado= model.log(user, pass);
                if(estado !=-1){
                    if(estado == 1){
                        String formato = "yyyy-MM-dd HH:mm:ss";
                        DateTimeFormatter formateador = DateTimeFormatter.ofPattern(formato);
                        LocalDateTime ahora = LocalDateTime.now();

                        String ultimoInicio = formateador.format(ahora);

                        Usuarios usuario = new Usuarios();

                        usuario.setIdUsuario((userCBox.getSelectionModel().getSelectedIndex()+1));
                        usuario.setUltimoInicio(ultimoInicio);
                        System.out.println(ultimoInicio);

                        if(model.updateUsuario(usuario)){
                            System.out.println("La fecha se actualizo de forma exitosa!");
                        }else{
                            System.out.println("No se puedo modificar la informacion!");
                        }

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Datos correctos");
                        alert.setHeaderText(null);
                        alert.setContentText("Bienvenido al sistema!");
                        ImageView icon = new ImageView("/imgs/come-in.png");
                        icon.setFitHeight(64);
                        icon.setFitWidth(64);
                        alert.getDialogPane().setGraphic(icon);
                        alert.showAndWait();
                        System.out.println("Bienvenido al sistema!");

                        if(userCBox.getSelectionModel().getSelectedItem().equals("User")){
                            System.out.println("Abre aqui interfaz usuario");
                            loadStage("/Vistas/Check.fxml", event, 0);
                        }else{
                            System.out.println("Abre aqui Admin");
                            loadStage("/Vistas/Principal.fxml",event, 1);
                        }
                    }else{
                        Alert alert= new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Datos incorrectos");
                        alert.setHeaderText(null);
                        alert.setContentText("Ooops, los datos son incorrectos!");
                        ImageView icon = new ImageView("/imgs/errorAlert.png");
                        icon.setFitHeight(64);
                        icon.setFitWidth(64);
                        alert.getDialogPane().setGraphic(icon);
                        alert.showAndWait();

                        System.out.println("Error al iniciar sesion datos de acceso incorrectos.");
                        cajaPass.setText("");
                        cajaPass.requestFocus();
                    }
                }

            }else{
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Datos incompletos");
                alerta.setHeaderText(null);
                alerta.setContentText("Por favor ingrese una contraseña.");
                ImageView icon = new ImageView("/imgs/errorAlert.png");
                icon.setFitHeight(64);
                icon.setFitWidth(64);
                alerta.getDialogPane().setGraphic(icon);
                alerta.showAndWait();

                cajaPass.requestFocus();
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
