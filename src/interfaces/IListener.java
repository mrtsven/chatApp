package interfaces;

import domain.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IListener extends Remote {
    void setChatMessages(List<Message> messages) throws RemoteException;
    int getChatId() throws RemoteException;
    int getUserId() throws RemoteException;
    List<Message> getchatMessages() throws RemoteException;
    void addMessage(Message message) throws RemoteException;
    String getUserName() throws RemoteException;
    void setChatUsers(List<String> users) throws RemoteException;
}
