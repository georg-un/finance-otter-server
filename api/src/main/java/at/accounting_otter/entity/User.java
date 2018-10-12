package at.accounting_otter.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table (name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false, nullable = false)
    @Getter
    private int userId;

    @Column(name = "username", unique = true)
    @Getter @Setter
    private String username;

    @Column(name = "firstName", unique = true)
    @Getter @Setter
    private String firstName;

    @Column(name = "lastName", unique = true)
    @Getter @Setter
    private String lastName;

}
