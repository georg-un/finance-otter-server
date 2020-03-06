package at.finance_otter.service;

import at.finance_otter.persistence.DatabaseAdapter;
import at.finance_otter.persistence.entity.User;
import at.finance_otter.service.dto.UserDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserService {

    @Inject
    DatabaseAdapter databaseAdapter;

    public UserDTO createUser(UserDTO userDTO, String userId) throws ExposableException {
        if (userDTO.getFirstName() == null) {
            throw new ExposableException("First name must not be null.");
        } else if (userId == null) {
            throw new ExposableException("User ID must not be null.");
        } else if (getUser(userId) != null) {
            throw new ExposableException("This user ID already has a user associated.");
        } else {
            User user = new User();
            user.setUserId(userId);
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            return UserDTO.fromUser(this.databaseAdapter.createUser(user));
        }
    }

    public UserDTO getUser(String userId) {
        return UserDTO.fromUser(this.databaseAdapter.getUser(userId));
    }

    public List<UserDTO> getActiveUsers() {
        return this.databaseAdapter.getActiveUsers()
                .stream()
                .map(UserDTO::fromUser)
                .collect(Collectors.toList());
    }

    public UserDTO updateUser(UserDTO userDTO) throws ExposableException {
        // TODO: Prevent that one user can update another
        if (userDTO.getUserId() == null) {
            throw new ExposableException("User ID must not be null.");
        } else if (userDTO.getFirstName() == null) {
            throw new ExposableException("First name must not be null.");
        } else {
            User user = databaseAdapter.getUser(userDTO.getUserId());
            if (user == null) {
                throw new ExposableException("Could not find any user this ID.");
            }
            user.setUserId(userDTO.getUserId());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setDeactivated(userDTO.getDeactivated());
            return UserDTO.fromUser(databaseAdapter.updateUser(user));
        }
    }

    public Boolean isCurrentUserActive(String userId) {
        return this.getUser(userId) != null;
    }

    // TODO: Add a possibility to deactivate users

}
