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
    public List<Chat> getChats() {
        List<Chat> chats = new ArrayList<>();
        String getChats = "Select c.chat_ID,c.name,u.id_user as userID, u.username from chat c\n" +
                "join chat_user uc on uc.chat_id=c.chat_ID\n" +
                "join user u on u.id_user=uc.user_id\n" +
                "Where c.chat_Id in (Select c.chat_ID from chat c\n" +
                "join chat_user uc on uc.chat_id=c.chat_ID\n" +
                "join user u on u.id_user=uc.user_id\n" +
                ")";
        IConnection connection = new ConnectionManager();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStmt = null;
        ResultSet rs = null;
        try {
            preparedStmt = conn.prepareStatement(getChats, Statement.RETURN_GENERATED_KEYS);
            rs = preparedStmt.executeQuery();
            while (rs.next()) {
                chats.add(new Chat(rs.getInt("chat_ID"),
                        rs.getString("name"),
                        new User(rs.getInt("userID"),rs.getString("username"))));
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
        String queryJoinChat = " insert into chat_user (user_id,chat_id)"
                + " values (?, ?)";
        IConnection connection = new ConnectionManager();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStmt = null;
        PreparedStatement preparedStmt2 = null;
        PreparedStatement preparedStmt3 = null;
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
