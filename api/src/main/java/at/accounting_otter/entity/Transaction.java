package at.accounting_otter.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity
@Table (name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id", updatable = false, nullable = false)
    private int transactionId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "date")
    private Date date;

    @Column(name = "category")
    private String category;

    @Column(name = "shop")
    private String shop;

    @Column(name = "description")
    private String description;

    @Column(name = "bill_id")
    private String billId;

}
