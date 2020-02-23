package at.finance_otter.persistence.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "debits")
public class Debit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "debit_id", updatable = false)
    private Long debitId;

    @Column(name = "sec_debit_id", unique = true, nullable = false, updatable = false)
    private String secDebitId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "debtor_id", referencedColumnName = "user_id")
    private User debtor;

    @Column(name = "amount", nullable = false)
    private double amount;

}
