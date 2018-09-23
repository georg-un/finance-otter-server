package at.accounting_otter;

import at.accounting_otter.entity.User;
import javassist.NotFoundException;

public interface UserService {

    User createUser(User user);

    User getUser(int userId);

    User changeUsername(int userId, String newUsername) throws RuntimeException;

    void removeUser(int userId) throws NotFoundException;

}
