package net.vrb.bansystem;

import net.vrb.Providers.MySQL;
import net.vrb.Utilities.ServerTimeData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BANAPI {
    public static boolean isUserExists(String player) {
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT `banned` FROM `bansystem` WHERE `banned` = ?");
            ps.setString(1, player);
            ResultSet rs = ps.executeQuery();
            return (rs.next() ? true : false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void createTempBanPlayer(String player, String endBan, String reason) {

        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO `bansystem` (`banned`, `end_ban`, `ban_reason`, `created`) VALUES ('"+player+"', '"+endBan+"', '"+reason+"', '"+ ServerTimeData.getTimeData() +"')");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createBanPlayer(String player, String reason) {

        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO `bansystem` (`banned`, `ban_reason`, `created`) VALUES ('"+player+"', '"+reason+"', '"+ ServerTimeData.getTimeData() +"')");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteBanPlayer(String player) {
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("DELETE FROM `bansystem` WHERE `bansystem`.`banned` = '"+player+"'");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
