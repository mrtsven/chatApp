package domain;


import java.io.Serializable;

public class Chat implements Serializable{
    private int id;
    private String name;
    private User user;

    public Chat(int id, String name, User user) {
        this.id = id;
        this.name = name;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public User getUser(){ return user; }

}
