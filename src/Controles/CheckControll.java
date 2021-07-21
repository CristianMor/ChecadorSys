package Controles;


import Modelos.Checado;
import Modelos.ChecadoDAO;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CheckControll implements Initializable{
    private final ChecadoDAO model = new ChecadoDAO();

    private Calendar calendar;
    private Timeline lineaTiempo = new Timeline();
    private Timeline lineaSecundaria = new Timeline();

    @FXML
    private Label lblFecha;

    @FXML
    private Label lblHora;

    @FXML
    private Button btnSalir;

    @FXML
    private Button btnCheck;

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
                    lineaTiempo.stop();
                    lineaSecundaria.stop();

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
        else if(evt.equals(btnCheck)){
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("ACCESO");
            dialog.setHeaderText("Ingrese su nip para registrar.");

            //ESTABLESCO EL ICONO ESTE DEBE ESTAR INCLUIDO EN EL PROYECTO
            dialog.setGraphic(new ImageView(this.getClass().getResource("/imgs/nip.png").toString()));
            Stage stage= (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(this.getClass().getResource("/imgs/keys.png").toString()));

            //SE CONFIGURA Y SE AGREGAN LOS TIPOS DE BOTONES.
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            //CREE LAS ETIUQUETAS Y LOS CAMPOS
            GridPane grid= new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            PasswordField cajaNip= new PasswordField();
            cajaNip.setPromptText("Nip");

            grid.add(new Label("N I P: "), 0, 0);
            grid.add(cajaNip, 1, 0);

            //ACTIVAR / DESACTIVAR EL BOTON DE OK DEPENDIENDO DE SI SE INGRESO UNA CONTRASE;A
            Node okButton = dialog.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setDisable(true);

            //REALIZA LA VALIDACION UTILIZANDO LA SINTAXIS LAMBDA DE JAVA 8.
            cajaNip.textProperty().addListener((observable, valorViejo, valorNuevo)->{
                okButton.setDisable(valorNuevo.trim().isEmpty());
            });

            dialog.getDialogPane().setContent(grid);

            //FOCUS EN EL CAMPO PASSWORD
            Platform.runLater(()-> cajaNip.requestFocus());

            //CONVERTIMOS EL RESULTADO EN UN PAR DE CONTRASE;A CUANDO SE HACE CLICK EN OK
            dialog.setResultConverter(dialogButton ->{
                if(dialogButton == ButtonType.OK) {
                    return new String(cajaNip.getText());
                }
                return null;
            });

            Optional<String> result = dialog.showAndWait();
            int existe = model.log(result.get());

            if(existe != -1){
                if(existe == 1){
                    System.out.println("nip: "+result.get());
                    System.out.println("El nip si existe en la bd");
                    System.out.println("Le pertenece al empleado con id: "+ model.id_Empleado(result.get()));

                    String formato = "yyyy-MM-dd";
                    DateTimeFormatter formateador = DateTimeFormatter.ofPattern(formato);
                    LocalDateTime ahora = LocalDateTime.now();

                    String fecha = formateador.format(ahora);

                    Checado chek = new Checado();
                    System.out.println("La fecha es: "+fecha);

                    int yaRegistrado = model.exists(fecha, model.id_Empleado(result.get()));
                    System.out.println("EXISTS: "+yaRegistrado);
                    if(yaRegistrado != -1){
                        if(yaRegistrado == 1){
                            System.out.println("YA EXISTE");
                            chek.setHra_Salida(obtenerHora());
                            System.out.println("Hora salida registro: "+chek.getHra_Salida());
                            System.out.println("La hora de salida= "+obtenerHora());
                            model.updateHraSalida(chek.getHra_Salida(), fecha, model.id_Empleado(result.get()));

                            Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Registro");
                            alert.setHeaderText(null);
                            alert.setContentText("Su SALIDA se registro con exito!");
                            ImageView icon = new ImageView("/imgs/okAlert.png");
                            icon.setFitHeight(64);
                            icon.setFitWidth(64);
                            alert.getDialogPane().setGraphic(icon);
                            alert.showAndWait();
                        }else{
                            System.out.println("NO EXISTE");
                            chek.setFecha(fecha);
                            System.out.println("Fecha nuevo reg: "+chek.getFecha());
                            chek.setEmpleado(model.id_Empleado(result.get()));
                            System.out.println("Empleado nuevo reg: "+model.id_Empleado(result.get()));
                            chek.setHra_Entrada(obtenerHora());
                            System.out.println("La hora de entrada= "+chek.getHra_Entrada());

                            model.checar(chek);
                            System.out.println("Valor hora entrada: "+chek.getHra_Entrada());
                            System.out.println("Valor fecha: "+chek.getFecha());
                            System.out.println("Valor empleado: "+chek.getEmpleado());

                            Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Registro");
                            alert.setHeaderText(null);
                            alert.setContentText("Su ENTRADA se registro con exito!");
                            ImageView icon = new ImageView("/imgs/okAlert.png");
                            icon.setFitHeight(64);
                            icon.setFitWidth(64);
                            alert.getDialogPane().setGraphic(icon);
                            alert.showAndWait();
                        }
                    }
                }else{
                    Alert alert= new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Datos incorrectos");
                    alert.setHeaderText(null);
                    alert.setContentText("Ooops, el nip es incorrecto!");
                    ImageView icon = new ImageView("/imgs/errorAlert.png");
                    icon.setFitHeight(64);
                    icon.setFitWidth(64);
                    alert.getDialogPane().setGraphic(icon);
                    alert.showAndWait();
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        calendar = Calendar.getInstance();
        actuFecha();
        actuaReloj();
        ejecutaReloj();
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

    private String obtenerHora(){
        String hra="";
        Calendar calendario = new GregorianCalendar();
        int hora, min;

        hora= calendario.get(Calendar.HOUR_OF_DAY);
        min= calendario.get(Calendar.MINUTE);

        hra=hora+":"+min;
        return hra;
    }

    private void actuaReloj(){

        Date fechaHoraActual= new Date();
        String hr, min, seg, amPm;

        calendar.setTime(fechaHoraActual);
        amPm= calendar.get(Calendar.AM_PM) == Calendar.AM?"AM":"PM";

        if(amPm.equals("PM")){
            int h= calendar.get(Calendar.HOUR_OF_DAY)-12;
            hr = h>9?""+h:"0"+h;
        }else{
            hr= calendar.get(Calendar.HOUR_OF_DAY)>9 ? ""+calendar.get(Calendar.HOUR_OF_DAY) : "0"+calendar.get(Calendar.HOUR_OF_DAY);
        }
        min= calendar.get(Calendar.MINUTE)>9 ? ""+calendar.get(Calendar.MINUTE) : "0"+calendar.get(Calendar.MINUTE);
        seg= calendar.get(Calendar.SECOND)>9 ? ""+calendar.get(Calendar.SECOND) : "0"+calendar.get(Calendar.SECOND);

        System.out.println("HORA: "+hr+":"+min+":"+seg+" "+amPm);
        lblHora.setText(hr+":"+min+":"+seg+" "+amPm);
    }

    private void ejecutaReloj(){

        lineaSecundaria.setCycleCount(Timeline.INDEFINITE);

        KeyFrame keyPrimario = new KeyFrame(
                new Duration(1000 - (calendar.get(Calendar.MILLISECOND)% 1000)),
                (event) -> {
                    actuaReloj();
                    actuFecha();
                    lineaSecundaria.play();
                }
        );

        KeyFrame keySecundario = new KeyFrame(
                Duration.seconds(1),
                (event) -> {
                    actuFecha();
                    actuaReloj();
                }
        );

        lineaTiempo.getKeyFrames().add(keyPrimario);
        lineaSecundaria.getKeyFrames().add(keySecundario);
        lineaTiempo.play();
    }

    private void actuFecha(){
        String dia= String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String [] mes = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        String ano= String.valueOf(calendar.get(Calendar.YEAR));

        System.out.println("Mazatlán, Sinaloa "+dia+" de "+mes[calendar.get(Calendar.MONTH)]+" del "+ano+".");
        lblFecha.setText("Mazatlán, Sinaloa "+dia+" de "+mes[calendar.get(Calendar.MONTH)]+" del "+ano+".");
    }
}

