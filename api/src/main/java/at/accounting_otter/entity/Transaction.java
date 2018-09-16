package at.accounting_otter.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table (name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id", updatable = false, nullable = false)
    @Getter
    private int transactionId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Getter @Setter
    private User user;

    @Column(name = "datetime")
    @Getter @Setter
    private Date datetime;

    @Column(name = "category")
    @Getter @Setter
    private String category;

    @Column(name = "shop")
    @Getter @Setter
    private String shop;

    @Column(name = "description")
    @Getter @Setter
    private String description;

    @Column(name = "bill_id")
    @Getter @Setter
    private String billId;

}
