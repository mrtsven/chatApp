package interfaces;

import domain.Chat;
import domain.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IChatManagerServer extends  IRemotePublisher, Remote {
    User login(String username, String password) throws RemoteException;
    boolean register(String username, String password) throws RemoteException;
    List<Chat>getChats() throws RemoteException;
    void createChat(String Chatname, int userid)throws RemoteException;

}
