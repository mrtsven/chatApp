package server;

import domain.Chat;
import domain.Friend;
import domain.Message;
import domain.User;
import interfaces.*;
import repositories.ChatRepo;
import repositories.FriendRepo;
import repositories.MessageRepo;
import repositories.UserRepo;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatManager extends UnicastRemoteObject implements IChatManagerServer {

    private IUserRepo userRepo;
    private IMessageRepo msgRepo;
    private IChatRepo chatRepo;
    private IMessageRepo messageRepo;
    private IFriendRepo friendRepo;
    private List<IListener> listeners = new ArrayList<>();
    private boolean timerPause = true;

    public ChatManager() throws RemoteException {
        System.setProperty("java.rmi.server.hostname", "127.0.0.1");
        userRepo = new UserRepo();
        msgRepo = new MessageRepo();
        chatRepo = new ChatRepo();
        messageRepo = new MessageRepo();
        friendRepo = new FriendRepo();
    }

    @Override
    public User login(String username, String password) {
        return userRepo.login(username, password);
    }

    @Override
    public boolean register(String username, String password) throws RemoteException {
        return userRepo.register(username, password);
    }

    @Override
    public List<Chat> getChats() throws RemoteException {
        return chatRepo.getChats();
    }

    @Override
    public void createChat(String chatname,int userid) throws RemoteException{
        chatRepo.createChat(chatname, userid);
    }

    @Override
    public void sendMessage(int userId,String username, int chatId, String msg) throws RemoteException {
        if (!timerPause)
        {
            for (IListener l:listeners
                    ) {
                if (l.getChatId() == chatId)
                {
                    if (l.getUserId() == userId)
                    {
                        l.addMessage(new Message(0,username,msg,false));
                    }
                    else
                    {
                        l.addMessage(new Message(0,username,msg,true));
                    }
                }
            }
        }
        messageRepo.sendMessage(userId,chatId,msg);
    }

    @Override
    public synchronized List<String> getChatUsers(int chatid) throws RemoteException {
        List<String> users = new ArrayList<>();
        if (!timerPause) {
            for (IListener user : listeners
                    ) {
                if (user.getChatId() == chatid) {
                    users.add(user.getUserName());
                }
            }
        }
        return users;
    }

    @Override
    public List<Friend> getFriends(int userid) throws RemoteException {
        return friendRepo.getFriends(userid);
    }

    @Override
    public boolean addFriend(int userSender, String username) throws RemoteException {
        return friendRepo.addFriend(userSender, username);
    }

    @Override
    public void updateFriendRequest(int FriendRequestID, boolean accept) throws RemoteException {
        friendRepo.updateFriendRequest(FriendRequestID, accept);
    }

    @Override
    public synchronized void addListener(IListener listener) {
        if (timerPause) {
            timerPause = false;
        }
        listeners.add(listener);
        try {
            listener.setChatMessages(msgRepo.getMessages(listener.getChatId(), listener.getUserId()));
            updateListeners(listener.getChatId());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private synchronized void updateListeners(int chatId) {
        if (!timerPause) {
            for (IListener user : listeners
                    ) {
                try {
                    if (user.getChatId() == chatId) {
                        user.setChatUsers(getChatUsers(chatId));
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public synchronized void removeListener(IListener listener) {
        int chatID = 0;
        try {
            chatID = listener.getChatId();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        listeners.remove(listener);
        updateListeners(chatID);
    }
}
