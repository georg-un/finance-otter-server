package at.accounting_otter.entity;

import javax.persistence.*;

@Entity
@Table (name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false, nullable = false)
    private int user_id;

    @Column(name = "username", unique = true)
    private String username;

    // Getter

    public int getUserId() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }

    // Setter

    public void setUsername(String username) {
        this.username = username;
    }

}
