package Modelos;

public class Deptos {

    private int cod_Depto;
    private String nombre_Depto;

    public void Deptos(){

    }

    public void Deptos(int cod_Depto, String nombre_Depto){
        this.cod_Depto= cod_Depto;
        this.nombre_Depto= nombre_Depto;
    }

    public int getCod_Depto() {
        return cod_Depto;
    }

    public String getNombre_Depto() {
        return nombre_Depto;
    }

    public void setCod_Depto(int cod_Depto) {
        this.cod_Depto = cod_Depto;
    }

    public void setNombre_Depto(String nombre_Depto) {
        this.nombre_Depto = nombre_Depto;
    }


}
