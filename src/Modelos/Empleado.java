package Modelos;

public class Empleado {
    private String id;
    private String nombre;
    private String imgSrc;

    public Empleado(){

    }

    public Empleado(String id, String nombre, String imgSrc){
        this.id= id;
        this.nombre= nombre;
        this.imgSrc= imgSrc;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id= id;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre= nombre;
    }

    public String getImgSrc(){
        return imgSrc;
    }

    public void setImgSrc(String imgSrc){
        this.imgSrc= imgSrc;
    }
}
