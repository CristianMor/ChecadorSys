package Controles;

import Modelos.Empleado;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class EmpleadoitemControll implements Initializable{

    @FXML
    private Label lblId;

    @FXML
    private ImageView imgView;

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblApPaterno;

    @FXML
    private Label lblPuesto;

    @FXML
    private Button btnVer;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    @FXML
    void eventAction(ActionEvent event) {
        
    }

    public void setData(Empleado empleado){
        lblId.setText(String.valueOf(empleado.getId()));

        /*Image imgProfile = new Image(this.getClass().getResourceAsStream("../imgs/Perfil2.jpg").toString());
        imgView.setImage(imgProfile);*/

        System.out.println(empleado.getImgSrc());
        lblNombre.setText(empleado.getNombre());
        lblApPaterno.setText(empleado.getAp_paterno());
        lblPuesto.setText(empleado.getPuesto());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
