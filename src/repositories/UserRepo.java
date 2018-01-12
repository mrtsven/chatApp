package repositories;

import domain.User;
import interfaces.IConnection;
import interfaces.IUserRepo;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserRepo implements IUserRepo {

    @Override
    public User login(String username, String password) {
        User user = null;
        String query = "SELECT * FROM user WHERE username = ? AND password= ?;";
        IConnection connection = new ConnectionManager();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStmt = null;
        ResultSet rs = null;
        try {
            preparedStmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStmt.setString(1,username.toLowerCase());
            preparedStmt.setString(2,password);
            rs = preparedStmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getInt("id"),rs.getString("username"));
            }
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.SEVERE,"UserRepo",e);
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (preparedStmt != null) {
                    preparedStmt.close();
                }
                conn.close();
            } catch (SQLException e) {
                Logger.getGlobal().log(Level.SEVERE,"UserRepo",e);
            }
        }
        return user;
    }

    public Boolean checkIfNameExists(String username) {
        boolean exists = false;
        String query = "SELECT * FROM user WHERE username = ?;";
        IConnection connection = new ConnectionManager();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStmt = null;
        ResultSet rs = null;
        try {
            preparedStmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStmt.setString(1,username.toLowerCase());
            rs = preparedStmt.executeQuery();
            if (rs.next()) {
                exists = true;
            }
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.SEVERE,"UserRepo",e);
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (preparedStmt != null) {
                    preparedStmt.close();
                }
                conn.close();
            } catch (SQLException e) {
                Logger.getGlobal().log(Level.SEVERE,"UserRepo",e);
            }
        }
        return exists;
    }

    @Override
    public boolean register(String username, String password) {
        if (!checkIfNameExists(username))
        {
            try {
                String query = "INSERT into user(username, password) VALUES(?, ?);";
                IConnection connection = new ConnectionManager();
                Connection conn = connection.getConnection();
                PreparedStatement preparedStmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                preparedStmt.setString(1, username.toLowerCase());
                preparedStmt.setString(2,password);
                preparedStmt.execute();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        else {
            return false;
        }
    }
}
