package repotest;

import domain.Message;
import interfaces.IMessageRepo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repositories.MessageRepo;


public class MessageRepoTest {
    private IMessageRepo messageRepo;

    @Before
    public void setup()
    {
        this.messageRepo = new MessageRepo();
    }

    @Test
    public void message() {
        this.messageRepo.sendMessage(7, 9,"sup");
        boolean passed = false;
        for (Message m : messageRepo.getMessages(9, 7)
                ) {
            if (m.getMsg().equals("sup"))
            {
                passed = true;
            }
        }
        Assert.assertEquals(true,passed);
    }
}
