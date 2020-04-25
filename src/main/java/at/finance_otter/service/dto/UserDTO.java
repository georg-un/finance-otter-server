package at.finance_otter.service.dto;

import at.finance_otter.persistence.entity.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO implements Serializable {

    private String userId;
    private String firstName;
    private String lastName;
    private String avatarUrl;
    private Boolean deactivated;

    public static UserDTO fromUser(User user) {
        if (user != null) {
            UserDTO dto = new UserDTO();
            dto.setUserId(user.getUserId());
            dto.setFirstName(user.getFirstName());
            dto.setLastName(user.getLastName());
            dto.setAvatarUrl(user.getAvatarUrl());
            dto.setDeactivated(user.getDeactivated());
            return dto;
        } else {
            return null;
        }
    }

}
