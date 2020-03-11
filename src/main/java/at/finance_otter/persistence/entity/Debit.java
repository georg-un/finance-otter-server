package at.finance_otter.persistence.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "debits")
public class Debit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gen_id", updatable = false)
    private Long genId;

    @Column(name = "debit_id", unique = true, nullable = false, updatable = false)
    private String debitId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "debtor_id", referencedColumnName = "gen_id")
    private User debtor;

    @ManyToOne(fetch = FetchType.LAZY)
    private Purchase purchase;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Debit )) return false;
        return genId != null && genId.equals(((Debit) o).getGenId());
    }

    @Override
    public int hashCode() {
        return 42;
    }

}
