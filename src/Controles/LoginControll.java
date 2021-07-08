package Controles;

import Modelos.Usuarios;
import Modelos.UsuariosDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginControll implements Initializable {

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
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){

        UsuariosDAO model= new UsuariosDAO();
        Usuarios usuario = new Usuarios();

        /*usuario.setUsuario("Prueba1");
        usuario.setUltimoInicio("2022/07/01");

        if(model.addUsuario(usuario)){
            System.out.println("Guardado con exito!");
        }else{
            System.out.println("No guardado!");
        }*/

        ArrayList<Usuarios> listArr= model.selectUsuario("USUARIO", null);
        String [] users = new String[5];

        for(int i=0; i < listArr.size(); i++){
            users[i]= listArr.get(i).getUsuario();
            System.out.println("Usuario: "+listArr.get(i).getUsuario());
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
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/GetStarted.fxml"));

                Parent root = loader.load();

                Scene escena = new Scene(root);
                Stage stage = new Stage();

                stage.setScene(escena);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.show();

                Stage myStage = (Stage) this.btnEntrar.getScene().getWindow();
                myStage.close();

            }catch(IOException ioe){
                Logger.getLogger(LoginControll.class.getName()).log(Level.SEVERE, null, ioe);
            }
        }else if(evt.equals(btnEntrar)){
            if(!cajaPass.getText().isEmpty()){

                String user= userCBox.getSelectionModel().getSelectedItem();
                String pass= cajaPass.getText();

                System.out.println("USUARIO: "+user+"\nPASSWORD: "+pass);
            }else{
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setHeaderText("Error al iniciar sesion");
                alerta.setContentText("Datos de acceso incorrectos!");
                alerta.show();
            }
        }
    }
}
