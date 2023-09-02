package net.vrb.wlsystem;

import net.vrb.Providers.MySQL;
import net.vrb.Utilities.ServerTimeData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WLAPI {

    public static boolean isUserExists(String player) {
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT `account_name` FROM `whitelist` WHERE `account_name` = ?");
            ps.setString(1, player);
            ResultSet rs = ps.executeQuery();
            return (rs.next() ? true : false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void addToWhiteList(String player) {

        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO `whitelist` (`account_name`, `created`) VALUES ('"+player+"', '"+ ServerTimeData.getTimeData() +"')");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeFormWhiteList(String player) {
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("DELETE FROM `whitelist` WHERE `whitelist`.`account_name` = '"+player+"'");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
