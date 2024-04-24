package example.model;

import jdk.jshell.Snippet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private Connection conn;
    static boolean ConnectionStat = false;

    public DatabaseManager() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/pharmacie", "root", "");
        }catch (Exception e){
                ConnectionStat = false;
                e.printStackTrace();
        }
    }
    public Connection getConnection() {
        return conn;
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
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}
