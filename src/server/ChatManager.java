package server;

import domain.User;
import interfaces.IChatManagerServer;
import interfaces.IUserRepo;
import repositories.UserRepo;

import java.io.IOException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

public class ChatManager extends UnicastRemoteObject implements IChatManagerServer {



    private IUserRepo userRepo = new UserRepo();

    public ChatManager() throws IOException, SQLException, ClassNotFoundException{}

    @Override
    public User login(String username, String password) {
        return userRepo.login(username, password);
    }
}
