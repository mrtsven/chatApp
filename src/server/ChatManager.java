package server;

import domain.Chat;
import domain.Message;
import domain.User;
import interfaces.*;
import repositories.ChatRepo;
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
    private List<IListener> listeners = new ArrayList<>();
    private Timer messageTimer;
    private boolean timerPause = true;

    public ChatManager() throws RemoteException {
        System.setProperty("java.rmi.server.hostname", "127.0.0.1");
        userRepo = new UserRepo();
        msgRepo = new MessageRepo();
        chatRepo = new ChatRepo();
        messageRepo = new MessageRepo();
        messageTimer = new Timer();
        messageTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                UpdateListeners();

            }
        }, 1000 , 50);
    }

    private void UpdateListeners() {
        if (!timerPause) {
            if (listeners.isEmpty()) {
                timerPause = true;
            }
            for (IListener l : listeners) {
                try {
                    if (l.getchatMessages().isEmpty()) {
                        l.setChatMessages(msgRepo.getMessages(l.getChatId(), l.getUserId()));
                    }
                } catch (RemoteException e) {
                    Logger.getGlobal().log(Level.SEVERE,"ChatManager",e);
                }
            }
        }
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
    public void sendMessage(int userId, int chatId, String msg) throws RemoteException {
        if (!timerPause)
        {
            for (IListener l:listeners
                    ) {
                if (l.getChatId() == chatId)
                {
                    if (l.getUserId() == userId)
                    {
                        l.addMessage(new Message(0,msg,false));
                    }
                    else
                    {
                        l.addMessage(new Message(0,msg,true));
                    }
                }
            }
        }
        messageRepo.sendMessage(userId,chatId,msg);
    }

    @Override
    public void addListener(IListener listener) {
        if (timerPause) {
            timerPause = false;
        }
        listeners.add(listener);
    }

    @Override
    public void removeListener(IListener listener) {
        listeners.remove(listener);
    }
}
