package interfaces;

import domain.User;

public interface IUserRepo {
    User login(String username, String password);
    boolean register(String username, String password);
}
