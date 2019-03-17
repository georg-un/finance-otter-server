package at.accounting_otter;

import at.accounting_otter.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO user);

    UserDTO getUser(int userId);

    UserDTO getUser(String username);

    List<UserDTO> getAllUser();

    UserDTO changeUsername(int userId, String newUsername) throws RuntimeException, ObjectNotFoundException;

    void setUserPic(int userId, byte[] userPic) throws ObjectNotFoundException;

    byte[] getUserPic(int userId);

    void removeUser(int userId) throws ObjectNotFoundException;

}
