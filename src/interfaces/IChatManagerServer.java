package interfaces;

import domain.Chat;
import domain.Friend;
import domain.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IChatManagerServer extends  IRemotePublisher, Remote {
    User login(String username, String password) throws RemoteException;
    boolean register(String username, String password) throws RemoteException;
    List<Chat>getChats() throws RemoteException;
    void createChat(String Chatname, int userid)throws RemoteException;
    void sendMessage(int userId,String username, int chatId, String msg) throws RemoteException;

    List<String>getChatUsers(int chatid) throws RemoteException;
    List<Friend> getFriends(int userid)throws RemoteException;
    boolean addFriend(int userSender, String username)throws RemoteException;
    void updateFriendRequest(int FriendRequestID, boolean accept)throws RemoteException;
}
