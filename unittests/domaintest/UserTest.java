package domaintest;

import domain.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserTest {
    private User user;

    @Before
    public void setup(){
        this.user = new User(1, "Svenovski");
    }

    @Test
    public void getId(){
        Assert.assertEquals(1,user.getId());
    }
    @Test
    public void getUsername(){
        Assert.assertEquals("Svenovski", user.getUsername());
    }
}
