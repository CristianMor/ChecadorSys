package Controles;

import Modelos.Empleado;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PrincipalControll implements Initializable {

    @FXML
    private VBox empleadoLayout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Empleado> empleados = new ArrayList<>(empleados());
        for(int i=0; i<empleados.size(); i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../Vistas/Empleado_item.fxml"));

            try{
                HBox hbox = fxmlLoader.load();
                EmpleadoitemControll eic= fxmlLoader.getController();
                eic.setData(empleados.get(i));
                empleadoLayout.getChildren().add(hbox);
            }catch(IOException e){
                e.printStackTrace();
            }

        }
    }

    private List<Empleado> empleados(){
        List<Empleado> ls = new ArrayList<>();


        Empleado empleado = new Empleado();
        empleado.setId("P-032");
        empleado.setImgSrc("../imgs/perfil2.jpg");
        empleado.setNombre("Cristian Martin");
        ls.add(empleado);

        empleado = new Empleado();
        empleado.setId("P-033");
        empleado.setImgSrc("../imgs/perfil3.jpg");
        empleado.setNombre("Jessica Guadalupe");
        ls.add(empleado);

        empleado = new Empleado();
        empleado.setId("P-011");
        empleado.setImgSrc("../imgs/perfil4.jpeg");
        empleado.setNombre("Orlando");
        ls.add(empleado);

        empleado = new Empleado();
        empleado.setId("P-022");
        empleado.setImgSrc("../imgs/perfil5.jpeg");
        empleado.setNombre("Beatriz Ignacia");
        ls.add(empleado);

        empleado = new Empleado();
        empleado.setId("P-040");
        empleado.setImgSrc("../imgs/perfil6.jpg");
        empleado.setNombre("Adilene");
        ls.add(empleado);

        empleado = new Empleado();
        empleado.setId("P-044");
        empleado.setImgSrc("../imgs/perfil2.jpg");
        empleado.setNombre("Felipe De Jesus");
        ls.add(empleado);

        empleado = new Empleado();
        empleado.setId("P-049");
        empleado.setImgSrc("../imgs/perfil3.jpg");
        empleado.setNombre("Martin");
        ls.add(empleado);

        empleado = new Empleado();
        empleado.setId("P-050");
        empleado.setImgSrc("../imgs/perfil4.jpeg");
        empleado.setNombre("Maria Esther");
        ls.add(empleado);

        empleado = new Empleado();
        empleado.setId("P-050");
        empleado.setImgSrc("../imgs/perfil5.jpeg");
        empleado.setNombre("Maria Esther");
        ls.add(empleado);

        empleado = new Empleado();
        empleado.setId("P-050");
        empleado.setImgSrc("../imgs/perfil6.jpg");
        empleado.setNombre("Maria Esther");
        ls.add(empleado);

        return ls;
    }
}
