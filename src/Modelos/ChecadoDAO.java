package Modelos;


Este es el modelo;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChecadoDAO {

    private PreparedStatement pst;
    private ResultSet rs;

    public int exists(String fecha, int empleado){

        Connection connect = null;
        int idCheca= 0;
        System.out.println("FECHA EN METODO: "+fecha);
        System.out.println("EMPLEADO EN METODO: "+empleado);

        try{
            connect = ConnectionPool.getInstance().getConnection();
            if(connect != null){

                String sql= "select * from checado where FECHA=? AND EMPLEADO=?";

                pst = connect.prepareStatement(sql);
                pst.setString(1, fecha);
                pst.setInt(2, empleado);

                rs= pst.executeQuery();
                if(rs.next()){
                    //SE ENCONTRO EN LA BD
                    idCheca= 1;
                }else{
                    //NOSE ENCONTRO INFORMACION ENLA BD
                    idCheca = 0;
                }
            }else{
                System.out.println("Se ah producido un error");
            }
        }catch(HeadlessException | SQLException ex){
            System.out.println("Hubo un error de ejecucion, posibles errores:\n"+ex.getMessage());
        }finally{
            try{
                if(connect != null){
                    ConnectionPool.getInstance().closeConnection(connect);
                }
            }catch(SQLException ex){
                System.err.println(ex.getMessage());
            }
        }
        return idCheca;
    }

    public boolean checar(Checado cheks){

        boolean estado= false;
        Connection connect = null;

        try{
            connect= ConnectionPool.getInstance().getConnection();
            if(connect!= null){
                String sql="INSERT INTO checado(FECHA, EMPLEADO, HR_ENTRADA) values(?, ?, ?)";

                /*if(cheks.getHra_Entrada() != null){
                    sql = "UPDATE checado set HR_SALIDA=? where EMPLEADO=?";
                    pst.setString(1, cheks.getHra_Salida());
                    pst.setInt(2, cheks.getEmpleado());
                }else{
                    sql = "INSERT INTO checado(FECHA, HR_ENTRADA, EMPLEADO) values(?, ?, ?)";
                    pst.setString(1, cheks.getFecha());
                    pst.setString(2, cheks.getHra_Entrada());
                    pst.setInt(3, cheks.getEmpleado());
                }*/
                pst= connect.prepareStatement(sql);
                pst.setString(1,cheks.getFecha());
                pst.setInt(2, cheks.getEmpleado());
                pst.setString(3, cheks.getHra_Entrada());

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

    public boolean updateHraSalida(String hraSalida, String fecha, int empleado){
        boolean estado = false;
        Connection connect = null;

        try{
            connect = ConnectionPool.getInstance().getConnection();

            if(connect != null){
                String sql = "update checado set HR_SALIDA=? where FECHA=? AND EMPLEADO=?";

                pst = connect.prepareStatement(sql);
                pst.setString(1, hraSalida);
                System.out.println("HORA ENTRADA METODO: "+ hraSalida);
                pst.setString(2, fecha);
                System.out.println("FECHA EN METODO: "+ fecha);
                pst.setInt(3, empleado);
                System.out.println("EMPLEADO EN METODO: "+ empleado);

                int res= pst.executeUpdate();

                estado= res > 0;

            }else{
                System.out.println("Conexion Fallida!");
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

    public int log(String nip){
        Connection connect = null;
        int estado = -1;

        try{
            connect = ConnectionPool.getInstance().getConnection();
            if(connect != null){
                //BINARY valida bit por bit si el usuario tiene una mayuscula y si el usuario que ingrese no tiene la
                // mayuscula entonces no se va validar.

                String sql= "select * from empleados where NIP=?";

                pst = connect.prepareStatement(sql);
                pst.setString(1, nip);

                rs= pst.executeQuery();
                if(rs.next()){
                    //SE ENCONTRO EN LA BD
                    estado= 1;
                }else{
                    //NOSE ENCONTRO INFORMACION ENLA BD
                    estado = 0;
                }
            }else{
                System.out.println("Se ah producido un error");
            }
        }catch(HeadlessException | SQLException ex){
            System.out.println("Hubo un error de ejecucion, posibles errores:\n"+ex.getMessage());
        }finally{
            try{
                if(connect != null){
                    ConnectionPool.getInstance().closeConnection(connect);
                }
            }catch(SQLException ex){
                System.err.println(ex.getMessage());
            }
        }
        return estado;
    }

    public int id_Empleado(String nip) {
        Connection connect = null;
        int id=0;

        try{
            connect = ConnectionPool.getInstance().getConnection();
            if(connect != null){

                String sql= "select ID_EMPLEADO from empleados where NIP=?";

                pst = connect.prepareStatement(sql);
                pst.setString(1, nip);

                rs= pst.executeQuery();
                if(rs.next()){
                    //SE ENCONTRO EN LA BD
                    id= rs.getInt("ID_EMPLEADO");
                }else{
                    //NOSE ENCONTRO INFORMACION ENLA BD
                    id = 0;
                }
            }else{
                System.out.println("Se ah producido un error");
            }
        }catch(HeadlessException | SQLException ex){
            System.out.println("Hubo un error de jecucion, posibles errores:\n"+ex.getMessage());
        }finally{
            try{
                if(connect != null){
                    ConnectionPool.getInstance().closeConnection(connect);
                }
            }catch(SQLException ex){
                System.err.println(ex.getMessage());
            }
        }

        return id;
    }

    public String hra_Entrada(String fecha, int empleado){

        String hra_entrada="";
        Connection connect = null;

        try{
            connect = ConnectionPool.getInstance().getConnection();
            if(connect != null){

                String sql= "select HR_ENTRADA from checado where FECHA=? AND EMPLEADO=?";

                pst = connect.prepareStatement(sql);
                pst.setString(1, fecha);
                pst.setInt(2, empleado);

                rs= pst.executeQuery();
                if(rs.next()){
                    //SE ENCONTRO EN LA BD
                    hra_entrada= rs.getString("HR_ENTRADA");
                }else{
                    //NOSE ENCONTRO INFORMACION ENLA BD
                    hra_entrada = "";
                }
            }else{
                System.out.println("Se ah producido un error");
            }
        }catch(HeadlessException | SQLException ex){
            System.out.println("Hubo un error de jecucion, posibles errores:\n"+ex.getMessage());
        }finally{
            try{
                if(connect != null){
                    ConnectionPool.getInstance().closeConnection(connect);
                }
            }catch(SQLException ex){
                System.err.println(ex.getMessage());
            }
        }
        return hra_entrada;
    }

}
