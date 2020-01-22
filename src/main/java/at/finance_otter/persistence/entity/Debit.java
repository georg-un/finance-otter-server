package at.finance_otter.persistence.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Debits")
public class Debit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "debit_id", updatable = false, nullable = false)
    private int debitId;

    @ManyToOne
    @JoinColumn(name = "transaction_id", referencedColumnName = "transaction_id")
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "payer_id", referencedColumnName = "user_id")
    private User payer;

    @ManyToOne
    @JoinColumn(name = "debtor_id", referencedColumnName = "user_id")
    private User debtor;

    @Column(name = "amount")
    private double amount;

}
