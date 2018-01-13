package repositories;

import domain.Chat;
import domain.User;
import interfaces.IChatRepo;
import interfaces.IConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatRepo implements IChatRepo {

    @Override
    public List<Chat> getChats(int userId) {
        List<Chat> chats = new ArrayList<>();
        String getChats = "Select c.ID,c.name,u.ID as userID, u.username from chat c\n" +
                "join user_chat uc on uc.ChatID=c.ID\n" +
                "join user u on u.ID=uc.UserID\n" +
                "Where c.ID in (Select c.ID from chat c\n" +
                "join user_chat uc on uc.ChatID=c.ID\n" +
                "join user u on u.ID=uc.UserID\n" +
                "where u.ID = ?) and u.ID != ?";
        IConnection connection = new ConnectionManager();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStmt = null;
        ResultSet rs = null;
        try {
            preparedStmt = conn.prepareStatement(getChats, Statement.RETURN_GENERATED_KEYS);
            preparedStmt.setInt (1, userId);
            preparedStmt.setInt (2, userId);
            rs = preparedStmt.executeQuery();
            while (rs.next()) {
                chats.add(new Chat(rs.getInt("chat_id"),
                        rs.getString("name"),
                        new User(rs.getInt("id_user"),rs.getString("username"))));
            }

        } catch (SQLException e) {
            Logger.getGlobal().log(Level.SEVERE,"ChatRepo",e);
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
                Logger.getGlobal().log(Level.SEVERE,"ChatRepo",e);
            }
        }
        return chats;
    }

    @Override
    public void createChat(String chatName, int userid) {
        String queryCreateChat = " insert into chat (name) value (?)";
        String queryJoinChat = " insert into user_chat (userID,chatID)"
                + " values (?, ?)";
        IConnection connection = new ConnectionManager();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStmt = null;
        PreparedStatement preparedStmt2 = null;
        ResultSet rs = null;
        try {
            preparedStmt = conn.prepareStatement(queryCreateChat, Statement.RETURN_GENERATED_KEYS);
            preparedStmt.setString(1,chatName);
            preparedStmt.execute();
            rs = preparedStmt.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
            System.out.println(generatedKey);
            preparedStmt2 = conn.prepareStatement(queryJoinChat);
            preparedStmt2.setInt (1, userid);
            preparedStmt2.setInt (2, generatedKey);
            preparedStmt2.execute();
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.SEVERE,"ChatRepo",e);
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (preparedStmt != null) {
                    preparedStmt.close();
                }
                if (preparedStmt2 != null) {
                    preparedStmt2.close();
                }
                conn.close();
            } catch (SQLException e) {
                Logger.getGlobal().log(Level.SEVERE,"ChatRepo",e);
            }
        }
    }
}
