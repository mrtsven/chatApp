package repotest;

import domain.Friend;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repositories.FriendRepo;


public class FriendRepoTest {

    private FriendRepo friendRepo;


    @Before
    public void setup()
    {
        friendRepo = new FriendRepo();
    }

    @Test
    public void getFriends()
    {
        Assert.assertEquals(3,friendRepo.getFriends(4).size());
    }

    @Test
    public void addFriend()
    {
        Assert.assertEquals(true,friendRepo.addFriend(12,"test102"));
    }

    @Test
    public void updateFriendRequest()
    {
        friendRepo.updateFriendRequest(4,true);
    }
}
