package repotest;

import domain.Chat;
import interfaces.IChatRepo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repositories.ChatRepo;

public class RepoChatTest {
    private IChatRepo chatRepo;

    @Before
    public void setup()
    {
        this.chatRepo = new ChatRepo();
    }

    @Test
    public void getChats()
    {
        Assert.assertEquals(false,chatRepo.getChats().isEmpty());
    }

    @Test
    public void createChat()
    {
        boolean passed = false;
        chatRepo.createChat("unittestingChat5",9);
        for (Chat c:chatRepo.getChats()
                ) {
            if (c.getUser().getId() == 9)
            {
                passed = true;
            }
        }
        Assert.assertEquals(true,passed);
    }
}
