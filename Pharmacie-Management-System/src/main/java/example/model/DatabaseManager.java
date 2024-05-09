package example.model;

import jdk.jshell.Snippet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private Connection conexion;
    static boolean ConnectionStat = false;

    public DatabaseManager() {
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3307/pharmacie", "root", "");
        }catch (Exception e){
                ConnectionStat = false;
                e.printStackTrace();
        }
    }
    public Connection getConnection() {
        return conexion;
    }
    public boolean setConnectionStat(boolean Stat){
        ConnectionStat = Stat;
        return Stat;
    }
    public boolean ConnectionStat(){
        return ConnectionStat;
    }
    //Usage : DatabaseManager dbManager = new DatabaseManager();
    //Connection conn = dbManager.getConnection();
    public void closeConnection() throws SQLException {
        if (conexion != null && !conexion.isClosed()) {
            conexion.close();
        }
    }
}
