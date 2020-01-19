package at.finance_otter;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private int transactionId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private Date date;

    private String category;

    private String shop;

    private String description;

    private String billId;

}
