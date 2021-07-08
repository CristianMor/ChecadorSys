package Modelos;

public class Usuarios {

    //SE ENCAPSULAN LOS DATOS GET Y SET DE LAS COLUMNAS DE LAS TABLAS;
    private int id_usuario;
    private String usuario;
    private String ultimo_inicio;

    public Usuarios(){
    }

    public Usuarios(int id_usuario, String usuario, String ultimo_inicio){
        this.id_usuario= id_usuario;
        this.usuario= usuario;
        this.ultimo_inicio= ultimo_inicio;
    }

    public void setIdUsuario(int id_usuario){
        this.id_usuario= id_usuario;
    }

    public int getIdUsuario(){
        return this.id_usuario;
    }

    public void setUsuario(String Usuario){
        this.usuario= Usuario;
    }

    public String getUsuario(){
        return this.usuario;
    }

    public void setUltimoInicio(String ultimo_inicio){
        this.ultimo_inicio= ultimo_inicio;
    }

    public String getUltimoInicio(){
        return this.ultimo_inicio;
    }
}
