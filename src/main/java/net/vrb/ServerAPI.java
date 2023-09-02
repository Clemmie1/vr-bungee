package net.vrb;

import net.vrb.Providers.MySQL;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ServerAPI {

    public static void setServerStatus(boolean isServerOnline) {

        if (isServerOnline){
            try {
                PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE `server_bungee` SET `server_status` = 1 ");
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE `server_bungee` SET `online` = 0 ");
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE `server_bungee` SET `server_status` = 0 ");
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE `server_bungee` SET `online` = 0 ");
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


    public static void addOnlinePlayer(boolean isAdd){

        if (isAdd){
            try {
                PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE `server_bungee` SET online=online+'1'");
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE `server_bungee` SET online=online-'1'");
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
