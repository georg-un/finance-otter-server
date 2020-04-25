package at.finance_otter.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gen_id", updatable = false)
    @Getter
    private Long genId;

    @Column(name = "user_id", unique = true, nullable = false)
    @Getter @Setter
    private String userId;

    @Column(name = "first_name", nullable = false)
    @Getter @Setter
    private String firstName;

    @Column(name = "last_name")
    @Getter @Setter
    private String lastName;

    @Column(name="avatar_url")
    @Getter @Setter
    private String avatarUrl;

    @Column(name = "deactivated")
    @Getter @Setter
    private Boolean deactivated;

}
