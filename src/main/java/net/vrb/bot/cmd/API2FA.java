package net.vrb.bot.cmd;

import net.md_5.bungee.api.connection.Server;
import net.vrb.Providers.MySQL;
import net.vrb.Utilities.ServerTimeData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class API2FA {
    public static boolean isUserExists(String player) {
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT `account_name` FROM `vr_2fa` WHERE `account_name` = ?");
            ps.setString(1, player);
            ResultSet rs = ps.executeQuery();
            return (rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isUserExists2Fa(Long userId, String player) {
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT `*` FROM `vr_2fa` WHERE `userId` = ? AND `account_name` = ?");
            ps.setLong(1, userId);
            ps.setString(2, player);
            ResultSet rs = ps.executeQuery();
            return (rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean getList2FA(Long userId) {
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT COUNT(*) as count FROM `vr_2fa` WHERE `userId` = ?");
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt("userId");
                if (count >= 2) {
                    return false;
                } else {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void setPlayerIn(String player, Long userId) {
        String date = ServerTimeData.getTimeData();
        if(!isUserExists(player)) {
            try {
                PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO `vr_2fa` (userId, account_name, created) " +
                        "VALUES ('"+userId+"', '"+player+"', '"+date+"')");
                ps.executeUpdate();


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getLastIp(String player) {
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT `last_ip` FROM `vr_2fa` WHERE `account_name` = ?");
            ps.setString(1, player);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                return rs.getString("last_ip");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getStatusIp(String player) {
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT `status_ip` FROM `vr_2fa` WHERE `account_name` = ?");
            ps.setString(1, player);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                return rs.getString("status_ip");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long getUserId(String player) {
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT `userId` FROM `vr_2fa` WHERE `account_name` = ?");
            ps.setString(1, player);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                return rs.getLong("userId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void updateStatusIp(String player, int status) {
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE `vr_2fa` SET `status_ip`="+status+" WHERE `account_name` = '"+player+"'");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateLastIp(String player, String ip) {
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE `vr_2fa` SET `last_ip`='"+ip+"' WHERE `account_name` = '"+player+"'");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateLastJoin(String player) {
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE `vr_2fa` SET `last_join`='"+ ServerTimeData.getTimeData() +"' WHERE `account_name` = '"+player+"'");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void remove2FA(String player) {
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("DELETE FROM `vr_2fa` WHERE `account_name` = "+player+" ");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
