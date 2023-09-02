package net.vrb.webauth;

import net.vrb.Providers.MySQL;
import net.vrb.Utilities.ServerTimeData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WEBAPI {
    public static boolean isUserExists(String player) {
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT `owner` FROM `web_token` WHERE `owner` = ?");
            ps.setString(1, player);
            ResultSet rs = ps.executeQuery();
            return (rs.next() ? true : false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void createAuthToken(String player, String token) {

        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO `web_token` (`owner`, `token`) VALUES ('"+player+"', '"+ token +"')");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
