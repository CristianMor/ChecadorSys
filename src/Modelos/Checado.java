package Modelos;

public class Checado {

    private int id_Checa;
    private String hra_Entrada;
    private String hra_Salida;
    private int empleado;

    public void Checado(){

    }

    public void Checado(int id_Checa, String fecha, String hra_Entrada, String hra_Salida, int empleado){
        this.id_Checa= id_Checa;
        this.fecha= fecha;
        this.hra_Entrada= hra_Entrada;
        this.hra_Salida= hra_Salida;
        this.empleado= empleado;
    }

    public int getId_Checa() {
        return id_Checa;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHra_Entrada() {
        return hra_Entrada;
    }

    public String getHra_Salida() {
        return hra_Salida;
    }

    public int getEmpleado() {
        return empleado;
    }

    private String fecha;

    public void setId_Checa(int id_Checa) {
        this.id_Checa = id_Checa;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setHra_Entrada(String hra_Entrada) {
        this.hra_Entrada = hra_Entrada;
    }

    public void setHra_Salida(String hra_Salida) {
        this.hra_Salida = hra_Salida;
    }

    public void setEmpleado(int empleado) {
        this.empleado = empleado;
    }
}
