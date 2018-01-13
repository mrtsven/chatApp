package domain;


import java.io.Serializable;

public class Friend implements Serializable {
    private int Id;
    private String username;
    private int status;

    public Friend(int id, String username, int status) {
        Id = id;
        this.username = username;
        this.status = status;
    }

    public int getId() {
        return Id;
    }

    public String getUsername() {
        return username;
    }

    public String getStatus() {
        switch (status){
            case 0:
                return "Declined";
            case 1:
                return "Pending";
            case 2:
                return "Accepted";
                default:
                    return "error";
        }
    }
}
