package repositories;

import domain.Friend;
import interfaces.IConnection;
import interfaces.IFriendRepo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FriendRepo implements IFriendRepo {
    @Override
    public List<Friend> getFriends(int userid) {
        List<Friend> friends = new ArrayList<>();
        //userID_sender
        String getFriends = "Select us.username,f.ID,f.`Status` from friend f\n" +
                "join user us on us.id_user = f.UserId_Sender\n" +
                "join user u on u.id_user = f.UserId_Receiver\n" +
                "where u.id_user = ?;";
        IConnection connection = new ConnectionManager();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStmt = null;
        ResultSet rs = null;
        try {
            preparedStmt = conn.prepareStatement(getFriends, Statement.RETURN_GENERATED_KEYS);
            preparedStmt.setInt (1, userid);
            rs = preparedStmt.executeQuery();
            while (rs.next()) {
                friends.add(new Friend(rs.getInt("ID"), rs.getString("username"),rs.getInt("Status")));
            }

        } catch (SQLException e) {
            Logger.getGlobal().log(Level.SEVERE,"FriendRepo",e);
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
                Logger.getGlobal().log(Level.SEVERE,"FriendRepo",e);
            }
        }
        //userId_Receiver
        getFriends = "Select us.username,f.ID,f.`Status` from friend f\n" +
                "join user u on u.id_user = f.UserId_Sender\n" +
                "join user us on us.id_user = f.UserId_Receiver\n" +
                "where u.id_user = ?;";
        connection = new ConnectionManager();
        conn = connection.getConnection();
        preparedStmt = null;
        rs = null;
        try {
            preparedStmt = conn.prepareStatement(getFriends, Statement.RETURN_GENERATED_KEYS);
            preparedStmt.setInt (1, userid);
            rs = preparedStmt.executeQuery();
            while (rs.next()) {
                friends.add(new Friend(rs.getInt("ID"), rs.getString("username"),rs.getInt("Status")));
            }

        } catch (SQLException e) {
            Logger.getGlobal().log(Level.SEVERE,"friendRepo",e);
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
                Logger.getGlobal().log(Level.SEVERE,"friendRepo",e);
            }
        }
        return friends;
    }

    @Override
    public boolean addFriend(int userSender, String username) {
        int userID = checkIfNameExists(username);
        if(userID != 0){
            try {
                String query = "INSERT into friend(userId_Sender,userId_Receiver) VALUES(?, ?);";
                IConnection connection = new ConnectionManager();
                Connection conn = connection.getConnection();
                PreparedStatement preparedStmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                preparedStmt.setInt(1, userSender);
                preparedStmt.setInt(2,userID);
                preparedStmt.execute();
                conn.close();
            } catch (SQLException e) {
                Logger.getGlobal().log(Level.SEVERE,"friendRepo",e);
                return false;
            }
            return true;
        }else{
            return false;
        }

    }

    @Override
    public void updateFriendRequest(int friendRequestID, boolean accept) {
        String query = "UPDATE friend set status = ? WHERE id = ?;";
        IConnection connection = new ConnectionManager();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            if (accept)
            {
                preparedStmt.setInt(1,2);
            }
            else {
                preparedStmt.setInt(1,0);
            }
            preparedStmt.setInt(2,friendRequestID);
            preparedStmt.execute();
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.SEVERE,"friendRepo",e);
        }
        finally {
            try {
                if (preparedStmt != null) {
                    preparedStmt.close();
                }
                conn.close();
            } catch (SQLException e) {
                Logger.getGlobal().log(Level.SEVERE,"friendRepo",e);
            }
        }
    }

    public int checkIfNameExists(String username) {
        int id = 0;
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
                id = rs.getInt("id_user");
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
        return id;
    }


}
