package at.finance_otter.persistence.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "receipts")
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gen_id", updatable = false)
    private Long genId;

    @OneToOne()
    private Purchase purchase;

    @Column(name="image", nullable = false)
    private byte[] image;

}
