package domain;

public class Message {
    private int id;
    private String msg;
    private boolean receiver;

    public Message(int id, String msg, boolean receiver) {
        this.id = id;
        this.msg = msg;
        this.receiver = receiver;
    }

    public Message(){}


    public int getId() {
        return id;
    }

    public String getMsg() {
        return msg;
    }

    public boolean getReceiver(){return receiver;}

}
