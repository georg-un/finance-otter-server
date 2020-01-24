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
    @Column(name = "purchase_id",updatable = false, nullable = false)
    private String purchaseId;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "user_id")
    private User user;

    @OneToMany
    private Set<Debit> debits;

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
