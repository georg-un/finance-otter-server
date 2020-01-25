package at.finance_otter.persistence.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "purchases")
public class Purchase {

    @Id
    @Column(name = "purchase_id",updatable = false)
    private String purchaseId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "buyer_id", referencedColumnName = "user_id")
    private User buyer;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Debit> debits;

    @Column(name = "date", nullable = false)
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
