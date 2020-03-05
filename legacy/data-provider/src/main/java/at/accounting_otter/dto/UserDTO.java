package at.accounting_otter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private int userId;
    private String username;
    private String firstName;
    private String lastName;
    private byte[] userPic;  // TODO: maybe a separate DTO for userpics?
}
