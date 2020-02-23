package at.finance_otter.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false)
    @Getter
    private Long userId;

    @Column(name = "username", unique = true, nullable = false)
    @Getter @Setter
    private String username;

    @Column(name = "first_name", nullable = false)
    @Getter @Setter
    private String firstName;

    @Column(name = "last_name")
    @Getter @Setter
    private String lastName;

    @Column(name = "deactivated")
    @Getter @Setter
    private Boolean deactivated;

    @Column(name = "user_pic")
    @Getter @Setter
    private byte[] userPic;

}
