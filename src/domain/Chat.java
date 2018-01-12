package domain;


public class Chat {
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
