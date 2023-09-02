package net.vrb.mutesystem;

import net.vrb.Providers.MySQL;
import net.vrb.Utilities.ServerTimeData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MUTEAPI {

    public static boolean isUserExists(String player) {
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT `muted` FROM `mutesystem` WHERE `muted` = ?");
            ps.setString(1, player);
            ResultSet rs = ps.executeQuery();
            return (rs.next() ? true : false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void createTempMutePlayer(String player, String endMute, String reason) {

        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO `mutesystem` (`muted`, `mute_end`, `mute_reason`, `created`) VALUES ('"+player+"', '"+endMute+"', '"+reason+"', '"+ ServerTimeData.getTimeData() +"')");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createMutePlayer(String player, String reason) {

        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO `mutesystem` (`muted`, `mute_reason`, `created`) VALUES ('"+player+"', '"+reason+"', '"+ ServerTimeData.getTimeData() +"')");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteMutePlayer(String player) {
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("DELETE FROM `mutesystem` WHERE `mutesystem`.`muted` = '"+player+"'");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
