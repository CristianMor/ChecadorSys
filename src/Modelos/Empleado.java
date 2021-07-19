package Modelos;

public class Empleado {

    private int id_empleado;
    private String nombre;
    private String ap_paterno;
    private String ap_materno;
    private String puesto;
    private String fecha_nacimiento;
    private String telefono;
    private String nip;
    private String imgSrc;
    private int depto;
    private int estado;

    public Empleado(){

    }

    public Empleado(int id, String nombre, String ap_paterno, String ap_materno, String puesto, String fecha_nacimiento, String telefono, String nip, String imgSrc, int depto, int estado){
        this.id_empleado= id;
        this.nombre= nombre;
        this.ap_paterno= ap_paterno;
        this.ap_materno= ap_materno;
        this.puesto= puesto;
        this.fecha_nacimiento= fecha_nacimiento;
        this.telefono= telefono;
        this.nip = nip;
        this.imgSrc= imgSrc;
        this.depto= depto;
        this.estado= estado;
    }

    public int getId(){
        return id_empleado;
    }

    public void setId(int id){
        this.id_empleado= id;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre= nombre;
    }

    public void setAp_paterno(String ap_paterno){
        this.ap_paterno= ap_paterno;
    }

    public String getAp_paterno(){
        return ap_paterno;
    }

    public void setAp_materno(String ap_materno){
        this.ap_materno= ap_materno;
    }

    public String getAp_materno(){
        return ap_materno;
    }

    public void setPuesto(String puesto){
        this.puesto= puesto;
    }

    public String getPuesto(){
        return puesto;
    }

    public void setFecha_nacimiento(String fecha_nacimiento){
        this.fecha_nacimiento= fecha_nacimiento;
    }

    public String getFecha_nacimiento(){
        return fecha_nacimiento;
    }

    public void setTelefono(String telefono){
        this.telefono= telefono;
    }

    public String getTelefono(){
        return telefono;
    }

    public void setNip(String nip){
        this.nip= nip;
    }

    public String getNip(){
        return nip;
    }

    public String getImgSrc(){
        return imgSrc;
    }

    public void setImgSrc(String imgSrc){
        this.imgSrc= imgSrc;
    }

    public void setDepto(int depto){
        this.depto= depto;
    }

    public int getDepto(){
        return depto;
    }

    public void setEstado(int estado){
        this.estado= estado;
    }

    public int getEstado(){
        return estado;
    }

}
