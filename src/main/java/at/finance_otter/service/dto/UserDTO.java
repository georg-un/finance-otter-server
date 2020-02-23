package at.finance_otter.service.dto;

import at.finance_otter.persistence.entity.User;
import lombok.Data;

@Data
public class UserDTO {

    private Long userId;
    private String username;
    private String firstName;
    private String lastName;

    public static UserDTO fromUser(User user) {
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        return dto;
    }

}
