package Modelos;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeptosDAO {

    private PreparedStatement pst;
    private ResultSet rs;

    public boolean addUsuario(Usuarios users){

        boolean estado= false;
        Connection connect = null;

        try{
            connect= ConnectionPool.getInstance().getConnection();
            if(connect!= null){
                String sql = "INSERT INTO usuarios(USUARIO, ULTIMO_INICIO) values(?, ?)";

                pst= connect.prepareStatement(sql);
                pst.setString(1, users.getUsuario());
                pst.setString(2, users.getUltimoInicio());

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

    public boolean updateUsuario(Usuarios usuario){
        boolean estado = false;
        Connection connect = null;

        try{
            connect = ConnectionPool.getInstance().getConnection();

            if(connect != null){
                String sql = "update usuarios set ULTIMO_INICIO=? where USUARIO_ID=?";

                pst = connect.prepareStatement(sql);
                pst.setString(1, usuario.getUltimoInicio());
                pst.setInt(2, usuario.getIdUsuario());

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

    //ARRAYLIST POR QUE CUANDO CONSULTAMOS ALA BASE DE DATOS
    // LO QUE VAMOS A OBTENER ES UN ARREGLO, DE TIPO USUARIOS POR QUE ES LA CLASE QUE ADMINISTRA
    // LA INFORMACION DE LA BASE DE DATOS
    //2 PARAMETROS EL PRIMERO POR QUE VAMOS A MANEJAR FILTROS DE BUSQUEDA QUE SERIA POR COLUMNA(*, NOMBRE, ETC)
    //EL SEGUNDO PARAMETRO ES DE ARRAYLIST ES EL QUE CONTIENE LA COINCIDENCIA QUE QUIERO BUSCAR.

    public ArrayList<Deptos> selectDeptos(String filtro, ArrayList<String> data){

        ArrayList<Deptos> lista = new ArrayList<>();
        Deptos deptos;
        Connection connect = null;

        try{
            connect = ConnectionPool.getInstance().getConnection();

            if(connect!=null){
                String sql= "";

                switch(filtro){
                    case "NOMBRE_DEPTO":
                        sql= "select * from departamentos";
                        pst= connect.prepareStatement(sql);
                        //pst.setString(1, data.get(0));
                        break;
                    default:
                        sql= "select * from usuarios where 1";
                        pst = connect.prepareStatement(sql);
                        break;
                }
                rs= pst.executeQuery();

                while(rs.next()){
                    deptos = new Deptos();

                    //usuario.setIdUsuario(rs.getInt("USUARIO_ID"));
                    deptos.setNombre_Depto(rs.getString("NOMBRE_DEPTO"));
                    System.out.println("Nombre: "+deptos.getNombre_Depto());
                    //usuario.setUltimoInicio(rs.getString("ULTIMO_INICIO"));

                    lista.add(deptos);
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



    //VERDADERO SI SE EJECUTO DICHO PROCESO Y FALSO SI SE GENERO ALGUN PROBLEMA
    public boolean deleteUsuario(int id){
        boolean estado= false;
        Connection connect = null;

        try{
            connect = ConnectionPool.getInstance().getConnection();

            if(connect != null){
                String sql ="delete from usuarios where USUARIO_ID=?";

                //Se carga la sentencia
                pst = connect.prepareStatement(sql);
                pst.setInt(1, id);

                //se ejecuta la sentencia
                int res = pst.executeUpdate();

                estado = res > 0;
            }else{
                System.out.println("Conexion Fallida");
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }finally {
            try{
                ConnectionPool.getInstance().closeConnection(connect);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }

        return estado;
    }

    public int log(String user, String password){
        Connection connect = null;
        int estado = -1;

        try{
            connect = ConnectionPool.getInstance().getConnection();
            if(connect != null){
                //BINARY valida bit por bit si el usuario tiene una mayuscula y si el usuario que ingrese no tiene la
                // mayuscula entonces no se va validar.

                String sql= "select * from usuarios where BINARY USUARIO=? AND PASSWORD=AES_ENCRYPT(?, 'key')";

                pst = connect.prepareStatement(sql);
                pst.setString(1, user);
                pst.setString(2, password);

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
        return estado;
    }
}
