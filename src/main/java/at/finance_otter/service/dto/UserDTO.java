package at.finance_otter.service.dto;

import at.finance_otter.persistence.entity.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO implements Serializable {

    private Long genId;
    private String userId;
    private String username;
    private String firstName;
    private String lastName;
    private Boolean deactivated;

    public static UserDTO fromUser(User user) {
        if (user != null) {
            UserDTO dto = new UserDTO();
            dto.setGenId(user.getGenId());
            dto.setUserId(user.getUserId());
            dto.setUsername(user.getUsername());
            dto.setFirstName(user.getFirstName());
            dto.setLastName(user.getLastName());
            dto.setDeactivated(user.getDeactivated());
            return dto;
        } else {
            return null;
        }
    }

}
