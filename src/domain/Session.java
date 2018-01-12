package domain;

import interfaces.IChatManagerServer;

public class Session {
    private IChatManagerServer server;
    private User user;

    public Session(IChatManagerServer server) {
        this.server = server;
    }

    public IChatManagerServer getServer() {
        return server;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
