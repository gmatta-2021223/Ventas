package org.gersonmatta.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    private Connection conexion;
    private static Conexion instancia;

    private Conexion() {

        try {

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/DBJavaEEVentas2022?useSSL=false", "root", "admin");

        } catch (Exception e) {

            System.out.println("Error en la conexión");
            e.printStackTrace();

        }

    }

    public static Conexion getInstance() {

        if (instancia == null) {

            instancia = new Conexion();
            
        }
        
        return instancia;

    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

}
