package at.finance_otter.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gen_id", updatable = false)
    @Getter
    private Long genId;

    @Column(name = "label", nullable = false)
    @Getter @Setter
    private String label;

    @Column(name = "color", nullable = false)
    @Getter @Setter
    private String color;

}
