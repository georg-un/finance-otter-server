package at.accounting_otter.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table (name = "debits")
public class Debit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "debit_id", updatable = false, nullable = false)
    private int debit_id;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "payer_id")
    private User payer;

    @ManyToOne
    @JoinColumn(name = "debtor_id")
    private User debtor;

    @Column(name = "amount")
    private double amount;

    // Getter

    public int getDebitId() {
        return debit_id;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public User getPayer() {
        return payer;
    }

    public User getDebtor() {
        return debtor;
    }

    public double getAmount() {
        return amount;
    }

    // Setter

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public void setPayer(User payer) {
        this.payer = payer;
    }

    public void setDebtor(User debtor) {
        this.debtor = debtor;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
