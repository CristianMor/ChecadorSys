package Modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UsuariosDAO {

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

    //ARRAYLIST POR QUE CUANDO CONSULTAMOS ALA BASE DE DATOS
    // LO QUE VAMOS A OBTENER ES UN ARREGLO, DE TIPO USUARIOS POR QUE ES LA CLASE QUE ADMINISTRA
    // LA INFORMACION DE LA BASE DE DATOS
    //2 PARAMETROS EL PRIMERO POR QUE VAMOS A MANEJAR FILTROS DE BUSQUEDA QUE SERIA POR COLUMNA(*, NOMBRE, ETC)
    //EL SEGUNDO PARAMETRO ES DE ARRAYLIST ES EL QUE CONTIENE LA COINCIDENCIA QUE QUIERO BUSCAR.

    public ArrayList<Usuarios> selectUsuario(String filtro, ArrayList<String> data){

        ArrayList<Usuarios> lista = new ArrayList<>();
        Usuarios usuario;
        Connection connect = null;

        try{
            connect = ConnectionPool.getInstance().getConnection();

            if(connect!=null){
                String sql= "";

                switch(filtro){
                    case "USUARIO":
                        sql= "select USUARIO from usuarios";
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
                    usuario = new Usuarios();

                    //usuario.setIdUsuario(rs.getInt("USUARIO_ID"));
                    usuario.setUsuario(rs.getString("USUARIO"));
                    //usuario.setUltimoInicio(rs.getString("ULTIMO_INICIO"));

                    lista.add(usuario);
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
