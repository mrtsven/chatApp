package interfaces;

import domain.Friend;

import java.util.List;

public interface IFriendRepo {
    List<Friend> getFriends(int userid);
    boolean addFriend(int userSender, String username);
    void updateFriendRequest(int FriendRequestID, boolean accept);
}
