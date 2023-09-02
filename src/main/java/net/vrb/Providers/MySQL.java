package net.vrb.Providers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {
    public static String host;
    public static String port;
    public static String database;
    public static String username;
    public static String password;
    public static Connection con;

    public static void connect() {
        host = "152.70.169.233";
        port = "3306";
        database = "vanillareborn";
        username = "xperikss";
        password = "@Xperikss1233";

        try {
            con = DriverManager.getConnection("jdbc:mysql://"+ host +":"+ port +"/"+ database + "?autoReconnect=true", username, password);
            System.out.println("[VRB] MySQL connected");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect() {
        if(isConnected()) {
            try {
                con.close();
                System.out.println("[VRB] MySQL disconnected");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isConnected() {
        return (con != null);
    }

    public static Connection getConnection() {
        return con;
    }
}
