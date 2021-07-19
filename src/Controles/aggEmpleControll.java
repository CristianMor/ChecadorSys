package Controles;

import Modelos.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class aggEmpleControll implements Initializable {

    private final EmpleadosDAO model = new EmpleadosDAO();
    private final DeptosDAO modelDeptos = new DeptosDAO();

    @FXML
    private TextField cajaNombre;

    @FXML
    private TextField cajaAPaterno;

    @FXML
    private TextField cajaAMaterno;

    @FXML
    private DatePicker cajaBirth;

    @FXML
    private TextField cajaTelef;

    @FXML
    private TextField cajaPuesto;

    @FXML
    private ComboBox<String> CBDepto;

    @FXML
    private PasswordField cajaNip;

    @FXML
    private PasswordField cajaConfirmNip;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnBorrar;

    private boolean update;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ArrayList<Deptos> listArr= modelDeptos.selectDeptos("NOMBRE_DEPTO", null);
        String [] names = new String[listArr.size()];

        for(int i=0; i < listArr.size(); i++){
            names[i]= listArr.get(i).getNombre_Depto();
        }

        ArrayList<String> listUser = new ArrayList<>();
        Collections.addAll(listUser, names);

        CBDepto.getItems().addAll(listUser);
        //CBDepto.getSelectionModel().selectFirst();
    }

    @FXML
    private void borrar(MouseEvent event) {
        cajaNombre.setText("");
        cajaAPaterno.setText("");
        cajaAMaterno.setText("");
        cajaBirth.setValue(null);
        cajaTelef.setText("");
        cajaPuesto.setText("");
        cajaNip.setText("");
        cajaConfirmNip.setText("");
        CBDepto.setValue("Departamentos");

        cajaNombre.requestFocus();
    }

    @FXML
    private void guardar(MouseEvent event) {
        Empleado empleado = new Empleado();

        System.out.println("Nip: "+cajaNip.getText());
        System.out.println("Confirm Nip: "+cajaConfirmNip.getText());

        if(cajaNip.getText().equals(cajaConfirmNip.getText())){

            if(update == false){
                empleado.setNombre(cajaNombre.getText());
                empleado.setAp_paterno(cajaAPaterno.getText());
                empleado.setAp_materno(cajaAMaterno.getText());
                empleado.setFecha_nacimiento(String.valueOf(cajaBirth.getValue()));
                System.out.println("Fecha naci: "+empleado.getFecha_nacimiento());
                empleado.setTelefono(cajaTelef.getText());
                empleado.setPuesto(cajaPuesto.getText());
                empleado.setDepto(1+CBDepto.getSelectionModel().getSelectedIndex());
                System.out.println("Index CB: "+(1+CBDepto.getSelectionModel().getSelectedIndex()));
                empleado.setNip(cajaNip.getText());
                empleado.setEstado(1);

                model.agregarEmpleado(empleado);

                Alert alert= new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registro");
                alert.setHeaderText(null);
                alert.setContentText("Nuevo empleado guardado con exito!");
                ImageView icon = new ImageView("/imgs/okAlert.png");
                icon.setFitHeight(64);
                icon.setFitWidth(64);
                alert.getDialogPane().setGraphic(icon);
                alert.showAndWait();

                ((Node) (event.getSource())).getScene().getWindow().hide();
            }else{
                empleado.setNombre(cajaNombre.getText());
                empleado.setAp_paterno(cajaAPaterno.getText());
                empleado.setAp_materno(cajaAMaterno.getText());
                empleado.setFecha_nacimiento(String.valueOf(cajaBirth.getValue()));
                System.out.println("Fecha naci: "+empleado.getFecha_nacimiento());
                empleado.setTelefono(cajaTelef.getText());
                empleado.setPuesto(cajaPuesto.getText());
                empleado.setDepto(1+CBDepto.getSelectionModel().getSelectedIndex());
                System.out.println("Index CB: "+(1+CBDepto.getSelectionModel().getSelectedIndex()));
                empleado.setNip(cajaNip.getText());

                boolean updates= model.updateEmpleado(empleado);
                Alert alert= new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registro");
                alert.setHeaderText(null);
                alert.setContentText("El empleado se edito con exito!");
                ImageView icon = new ImageView("/imgs/okAlert.png");
                icon.setFitHeight(64);
                icon.setFitWidth(64);
                alert.getDialogPane().setGraphic(icon);
                alert.showAndWait();
            }

        }else{

            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Datos incorrectos");
            alert.setHeaderText(null);
            alert.setContentText("Ooops, el nip no coincide!");
            ImageView icon = new ImageView("/imgs/errorAlert.png");
            icon.setFitHeight(64);
            icon.setFitWidth(64);
            alert.getDialogPane().setGraphic(icon);
            alert.showAndWait();

            cajaConfirmNip.setText("");
            cajaNip.requestFocus();

        }
    }

    public void setTextField(String nombre, String ap_paterno, String ap_materno, String puesto, String telefono){
        cajaNombre.setText(nombre);
        cajaAPaterno.setText(ap_paterno);
        cajaAMaterno.setText(ap_materno);
        cajaTelef.setText(telefono);
        cajaPuesto.setText(puesto);
    }

    public void setUpdate(boolean update){
        this.update= update;
    }

}

