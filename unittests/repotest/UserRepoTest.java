package repotest;

import interfaces.IUserRepo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repositories.UserRepo;

public class UserRepoTest {
    private IUserRepo userRepo;

    @Before
    public void setup()
    {
        this.userRepo = new UserRepo();
    }

    @Test
    public void register()
    {
        String username = "bigTest4";
        String password = "testingPassword";
        Assert.assertEquals(true,userRepo.register(username,password));
    }

    @Test
    public void registerNameDoesNotExists()
    {
        Assert.assertEquals(true,userRepo.register("test103","test103"));
    }

    @Test
    public void registerNameExists()
    {
        Assert.assertEquals(false,userRepo.register("a","a"));
    }

    @Test
    public void login()
    {
        Assert.assertEquals(9,userRepo.login("haha","haha").getId());
    }

    @Test
    public void loginWrongPassword()
    {
        Assert.assertEquals(null,userRepo.login("haha","lol"));
    }

}
