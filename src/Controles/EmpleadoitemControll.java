package Controles;

import Modelos.Empleado;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    public void setData(Empleado empleado){
        lblId.setText(empleado.getId());

        Image imgProfile = new Image(this.getClass().getResourceAsStream(empleado.getImgSrc()));
        imgView.setImage(imgProfile);

        lblNombre.setText(empleado.getNombre());
        lblApPaterno.setText("Apellidolargo");
        lblPuesto.setText("Puestolargo");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
