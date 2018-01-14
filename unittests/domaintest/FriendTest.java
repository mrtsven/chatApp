package domaintest;

import domain.Friend;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FriendTest {
    private Friend friend;
    private Friend friend2;
    private Friend friend3;
    private Friend friend4;

    @Before
    public void setup(){
        this.friend = new Friend(100,"Sender",1);
        this.friend2 = new Friend(100,"Sender",0);
        this.friend3 = new Friend(100,"Sender",2);
        this.friend4 = new Friend(100,"Sender",-1);
    }

    @Test
    public void getId() {
        Assert.assertEquals(100, friend.getId());
    }

    @Test
    public void getUsername() {
        Assert.assertEquals("Sender", friend.getUsername());
    }

    @Test
    public void getStatusPending() {
        Assert.assertEquals("Pending", friend.getStatus());
    }

    @Test
    public void getStatusAccepted() {
        Assert.assertEquals("Accepted", friend3.getStatus());
    }

    @Test
    public void getStatusDeclined() {
        Assert.assertEquals("Declined", friend2.getStatus());
    }

    @Test
    public void getStatusError() {
        Assert.assertEquals("Error", friend4.getStatus());
    }
}
