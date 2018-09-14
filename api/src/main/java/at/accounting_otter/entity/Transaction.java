package at.accounting_otter.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;


@Entity
@Table (name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id", updatable = false, nullable = false)
    private int transactionId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "datetime")
    private Date datetime;

    @Column(name = "shop")
    private String shop;

    @Column(name = "description")
    private String description;

    @Column(name = "bill_id")
    private String billId;

    // Getter

    public int getTransactionId() {
        return transactionId;
    }

    public User getUser() {
        return user;
    }

    public Date getDatetime() {
        return datetime;
    }

    public String getShop() {
        return shop;
    }

    public String getDescription() {
        return description;
    }

    public String getBillId() {
        return billId;
    }

    // Setter

    public void setUser(User user) {
        this.user = user;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

}
