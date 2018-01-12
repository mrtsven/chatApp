package interfaces;

import domain.Chat;

import java.util.List;

public interface IChatRepo {
    List<Chat> getChats(int userid);
    void createChat(int userid, int newChatUserID);

}
