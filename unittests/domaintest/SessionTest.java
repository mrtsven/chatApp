package domaintest;

import domain.Session;
import domain.User;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;
import server.ChatManager;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SessionTest {

    private Session session;

    @Before
    public void setup()
    {
        try {
            this.session = new Session(new ChatManager());
        } catch (RemoteException e) {
            Logger.getGlobal().log(Level.SEVERE,"domaintest/session",e);
        }
    }

    @Test
    public void getServer()
    {
        Assert.assertEquals(false,session.getServer().equals(null));
    }

    @Test
    public void setUser()
    {
        session.setUser(new User(1,"Henkie"));
        Assert.assertEquals("Henkie",session.getUser().getUsername());
    }
}
