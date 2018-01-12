package interfaces;

import domain.Message;

import java.util.List;

public interface IMessageRepo {
    void sendMessage(int userId, int chatId, String content);
    List<Message> getMessages(int chatId, int userId);
}
