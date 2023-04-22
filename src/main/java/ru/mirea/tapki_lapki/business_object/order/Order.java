package ru.mirea.tapki_lapki.business_object.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.mirea.tapki_lapki.business_object.client.Client;

import java.util.Date;

/**
 * Order entity
 * @see Client
 */
@Entity
@Getter
@Setter
@Table(name = "sales_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "costumer_id")
    private Client client;

    @Column(name = "order_date", nullable = false)
    private Date date;

    @Column(name = "ship_date")
    private Date ship_date;

    @Column(name = "total")
    private Double totalPrice;

    @Column(name = "status", nullable = false)
    private Status statusOfOrder;
}
