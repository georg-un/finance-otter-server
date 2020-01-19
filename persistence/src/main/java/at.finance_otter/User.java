package at.finance_otter;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long userId;

    @Column(unique = true)
    private String username;

    private String firstName;

    private String lastName;

    private byte[] userPic;

}
