package Modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class EmpleadosDAO{

    private PreparedStatement pst;
    private ResultSet rs;

    public boolean agregarEmpleado(Empleado empleados){

        boolean estado= false;
        Connection connect = null;

        try{
            connect= ConnectionPool.getInstance().getConnection();
            if(connect!= null){
                String sql = "INSERT INTO empleados(NOMBRE, AP_PATERNO, AP_MATERNO, PUESTO, FECHA_NACIMIENTO, TELEFONO, NIP, DEPTO, ESTADO) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";

                pst= connect.prepareStatement(sql);
                pst.setString(1, empleados.getNombre());
                pst.setString(2, empleados.getAp_paterno());
                pst.setString(3, empleados.getAp_materno());
                pst.setString(4, empleados.getPuesto());
                pst.setString(5, empleados.getFecha_nacimiento());
                pst.setString(6, empleados.getTelefono());
                pst.setString(7, empleados.getNip());
                pst.setInt(8, empleados.getDepto());
                pst.setInt(9, empleados.getEstado());

                int res = pst.executeUpdate();
                estado = res > 0;

            }else{
                System.out.println("Connection Fallida!");
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }finally{
            try{
                ConnectionPool.getInstance().closeConnection(connect);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        return estado;
    }

    public ArrayList<Empleado> obtenerEmpleados(){

        ArrayList<Empleado> lista = new ArrayList<>();
       Empleado empleados;
        Connection connect = null;

        try{
            connect = ConnectionPool.getInstance().getConnection();

            if(connect!=null){
                String sql= "select * from empleados";
                pst= connect.prepareStatement(sql);
                rs= pst.executeQuery();

                while(rs.next()){
                    empleados = new Empleado();

                    empleados.setId(rs.getInt("ID_EMPLEADO"));
                    empleados.setNombre(rs.getString("NOMBRE"));
                    empleados.setAp_paterno(rs.getString("AP_PATERNO"));
                    empleados.setAp_materno(rs.getString("AP_MATERNO"));
                    empleados.setPuesto(rs.getString("PUESTO"));
                    empleados.setFecha_nacimiento(rs.getString("FECHA_NACIMIENTO"));
                    empleados.setTelefono(rs.getString("TELEFONO"));
                    empleados.setNip(rs.getString("NIP"));
                    empleados.setImgSrc(rs.getString("FOTO"));
                    empleados.setDepto(rs.getInt("DEPTO"));
                    empleados.setEstado(rs.getInt("ESTADO"));

                    lista.add(empleados);
                }
            }else{
                System.out.println("Conexion Fallida!");
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }finally {
            try {
                ConnectionPool.getInstance().closeConnection(connect);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return lista;
    }
}
