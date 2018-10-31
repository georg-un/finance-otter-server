package at.accounting_otter;

import at.accounting_otter.entity.User;

import java.util.List;

public interface UserService {

    User createUser(User user);

    User getUser(int userId);

    User getUser(String username);

    List<User> getAllUser();

    User changeUsername(int userId, String newUsername) throws RuntimeException, ObjectNotFoundException;

    void setUserPic(int userId, byte[] userPic);

    byte[] getUserPic(int userId);

    void removeUser(int userId) throws ObjectNotFoundException;

}
