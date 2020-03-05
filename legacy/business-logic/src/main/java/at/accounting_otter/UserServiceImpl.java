package at.accounting_otter;

import at.accounting_otter.dto.UserDTO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;


@RequestScoped
public class UserServiceImpl implements UserService {

    @Inject
    private DataProvider dataProvider;

    @Override
    public UserDTO createUser(UserDTO user) {
        if (doesUsernameAlreadyExist(user.getUsername())) {
            throw new RuntimeException("Username does already exist.");
        } else {
            return dataProvider.createUser(user);
        }
    }

    @Override
    public UserDTO getUser(int userId) {
        return dataProvider.getUser(userId);
    }

    @Override
    public UserDTO getUser(String username) {
        return dataProvider.getUser(username);
    }

    @Override
    public List<UserDTO> getAllUser() {
        return dataProvider.getAllUsers();
    }

    @Override
    public UserDTO changeUsername(int userId, String newUsername) throws RuntimeException, ObjectNotFoundException {
        if (dataProvider.getUser(userId) == null) {
            throw new ObjectNotFoundException("User with id " + userId + " not found.");
        }

        if (doesUsernameAlreadyExist(newUsername)) {
            throw new RuntimeException("Username does already exist.");
        } else {
            UserDTO user = dataProvider.getUser(userId);
            user.setUsername(newUsername);
            user = dataProvider.updateUser(user);
            return user;
        }
    }

    private boolean doesUsernameAlreadyExist(String username) {
        UserDTO user = dataProvider.getUser(username);
        return user != null;
    }

    @Override
    public void setUserPic(int userId, byte[] userPic) throws ObjectNotFoundException {
        UserDTO user = dataProvider.getUser(userId);
        user.setUserPic(userPic);
        dataProvider.updateUser(user);
    }

    @Override
    public byte[] getUserPic(int userId) {
        return dataProvider.getUser(userId).getUserPic();
    }

    @Override
    public void removeUser(int userId) throws ObjectNotFoundException {
        if (dataProvider.getUser(userId) == null) {
            throw new ObjectNotFoundException("User with id " + userId + " not found.");
        } else {
            // TODO
        }
    }

}
