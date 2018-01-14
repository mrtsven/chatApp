package domaintest;


import domain.Chat;
import domain.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ChatTest {

    private Chat chat;

    @Before
    public void setup()
    {
        this.chat = new Chat(100,"TestChat", new User(4,"Test"));
    }

    @Test
    public void getName()
    {
        Assert.assertEquals("TestChat",chat.getName());
    }

    @Test
    public void getID()
    {
        Assert.assertEquals(100,chat.getId());
    }

    @Test
    public void getUser()
    {
        Assert.assertEquals("Test",chat.getUser().getUsername());
    }

    @Test
    public void getUsername()
    {
        Assert.assertEquals("Test",chat.getUser_Name());
    }
}
