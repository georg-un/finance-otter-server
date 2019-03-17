package at.accounting_otter.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table (name = "debits")
public class Debit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "debit_id", updatable = false, nullable = false)
    @Getter @Setter
    private int debitId;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    @Getter @Setter
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "payer_id")
    @Getter @Setter
    private User payer;

    @ManyToOne
    @JoinColumn(name = "debtor_id")
    @Getter @Setter
    private User debtor;

    @Column(name = "amount")
    @Getter @Setter
    private double amount;

}
