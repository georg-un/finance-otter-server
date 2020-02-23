package at.finance_otter.service;

import at.finance_otter.persistence.DatabaseAdapter;
import at.finance_otter.persistence.entity.User;
import at.finance_otter.service.dto.UserDTO;

import javax.inject.Inject;

public class UserService {

    @Inject
    DatabaseAdapter databaseAdapter;

    public UserDTO createUser(UserDTO userDTO) throws ExposableException {
        if (userDTO.getUsername() == null) {
            throw new ExposableException("Username must not be null");
        } else if (userDTO.getFirstName() == null) {
            throw new ExposableException("First name must not be null");
        } else {
            User user = new User();
            user.setUsername(userDTO.getUsername());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            return UserDTO.fromUser(this.databaseAdapter.createUser(user));
        }
    }

    public UserDTO getUser(Long userId) {
        return UserDTO.fromUser(this.databaseAdapter.getUser(userId));
    }

    public UserDTO updateUser(UserDTO userDTO) throws ExposableException {
        // TODO: Prevent that one user can update another
        if (userDTO.getUserId() == null) {
            throw new ExposableException("UserId must not be null");
        } else if (userDTO.getUsername() == null) {
            throw new ExposableException("Username must not be null");
        } else if (userDTO.getFirstName() == null) {
            throw new ExposableException("First name must not be null");
        } else {
            User user = databaseAdapter.getUser(userDTO.getUserId());
            if (user == null) {
                throw new ExposableException("User with id " + userDTO.getUserId().toString() + " not found.");
            }
            user.setUsername(userDTO.getUsername());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setDeactivated(userDTO.getDeactivated());
            return UserDTO.fromUser(databaseAdapter.updateUser(user));
        }
    }

    // TODO: Add a possibility to deactivate users

}
