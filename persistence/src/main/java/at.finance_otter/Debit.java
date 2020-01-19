package at.finance_otter;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Debit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private int debitId;

    @ManyToOne
    @JoinColumn(name = "transactionId")
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "payerId")
    private User payer;

    @ManyToOne
    @JoinColumn(name = "debtorId")
    private User debtor;

    private double amount;

}
