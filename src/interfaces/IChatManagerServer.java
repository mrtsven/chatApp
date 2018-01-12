package interfaces;

import domain.Chat;
import domain.User;

import java.rmi.RemoteException;
import java.util.List;

public interface IChatManagerServer {
    User login(String username, String password) throws RemoteException;
    boolean register(String username, String password) throws RemoteException;
    List<Chat>getChats(int id) throws RemoteException;

}
