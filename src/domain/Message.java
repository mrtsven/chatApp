package domain;

public class Message {
    private int id;
    private String msg;

    public Message(int id, String msg) {
        this.id = id;
        this.msg = msg;
    }


    public int getId() {
        return id;
    }

    public String getMsg() {
        return msg;
    }

}
