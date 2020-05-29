package at.finance_otter.persistence.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gen_id", updatable = false)
    private Long genId;

    @Column(name = "purchase_id", unique = true, nullable = false, updatable = false)
    private String purchaseId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "buyer_id", referencedColumnName = "gen_id")
    private User buyer;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Debit> debits = new ArrayList<>();

    @Column(name = "date", nullable = false)
    private Date date;

    @ManyToOne()
    @JoinColumn(name = "category_id", referencedColumnName = "gen_id")
    private Category category;

    @Column(name = "shop")
    private String shop;

    @Column(name = "description")
    private String description;

    @Column(name= "is_compensation")
    private Boolean isCompensation;

    public void addDebit(Debit debit) {
        debits.add(debit);
        debit.setPurchase(this);
    }

    public void removeDebit(Debit debit) {
        debits.remove(debit);
        debit.setPurchase(null);
    }

    public void removeAllDebits() {
        debits.forEach(d -> d.setPurchase(null));
        debits.clear();
    }

}
