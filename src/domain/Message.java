package domain;

import java.io.Serializable;

public class Message implements Serializable{
    private int id;
    private String username;
    private String msg;
    private boolean receiver;

    public Message(int id,String username, String msg, boolean receiver) {
        this.id = id;
        this.username = username;
        this.msg = msg;
        this.receiver = receiver;
    }

    public int getId() {
        return id;
    }

    public String getMsg() {
        return msg;
    }

    public boolean getReceiver(){return receiver;}

    public String getUsername() {
        return username;
    }
}
