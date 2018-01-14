package domaintest;

import domain.Message;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MessageTest {

    private Message message;

    @Before
    public void setup()
    {
        this.message = new Message(4, "test","Hello, this is a test", false);
    }

    @Test
    public void getID()
    {
        Assert.assertEquals(4,message.getId());
    }

    @Test
    public void getContent()
    {
        Assert.assertEquals("Hello, this is a test",message.getMsg());
    }

    @Test
    public void getReceiver()
    {
        Assert.assertEquals(false,message.getReceiver());
    }

    @Test
    public void getUsername() {
        Assert.assertEquals("test", message.getUsername());
    }
}
