package interfaces;

import domain.Chat;

import java.util.List;

public interface IChatRepo {
    List<Chat> getChats();
    void createChat(String chatName, int userid);

}
