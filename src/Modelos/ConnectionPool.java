package Modelos;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {

    //PRIVADO Y FINAL POR QUE SON DATOS UNICOS QUE NO VAN A CAMBIAR EN TODA LA EJECUCION DEL PROGRAMA.
    private final String DB="bdchecador";
    private final String URL="jdbc:mysql://localhost/"+DB;
    private final String USER="root";
    private final String PASS=".Lokote234";

    //OBJETO PRIVADO Y ESTATICO POR QUE ES LA UNICA INSTANCIA QUE SE CREARA DE ESTA CLASE
    private static ConnectionPool dataSource;

    //NOS PERMITE CREAR UN POOL DE CONECCIONES.
    private BasicDataSource basicDataSource= null;

    //CONSTRUCTOR PRIVADO ES PARA QUE DESDE OTRAS CLASES NO SE PUEDA INICILIZAR EL OBJETO.
    private ConnectionPool(){

        //CONFIGURACION BASICA DE NUESTRO POOL DE CONECCIONES.
        basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        basicDataSource.setUsername(USER);
        basicDataSource.setPassword(PASS);
        basicDataSource.setUrl(URL);

        //CONFIGURACION PARA ADMINISTRAR LA BASE DE DATOS
        basicDataSource.setMinIdle(5);
        basicDataSource.setMaxIdle(20);
        basicDataSource.setMaxTotal(50);
        basicDataSource.setMaxWaitMillis(10000);
    }

    //METODO PARA INCIALIZAR Y CREAR UNA INSTANCIA DE ESTA CLASE
    public static ConnectionPool getInstance(){
        if(dataSource == null){
            dataSource= new ConnectionPool();
            return dataSource;
        }else{
            return dataSource;
        }
    }

    //METODO PARA ESTABLECER LA CONEXION ALA BASE DE DATOS
    public Connection getConnection() throws SQLException {
        return this.basicDataSource.getConnection();
    }

    //METODO PARA DESCONECTAR LA BASE DE DATOS
    public void closeConnection(Connection connection) throws SQLException{
        connection.close();
    }

}
