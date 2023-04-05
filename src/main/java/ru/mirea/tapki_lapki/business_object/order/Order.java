package ru.mirea.tapki_lapki.business_object.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "sales_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "costumer_id", nullable = false)
    private Long customer_id;

    @Column(name = "order_date", nullable = false)
    private Date date;

    @Column(name = "ship_date")
    private Date ship_date;

    @Column(name = "total")
    private Double totalPrice;

    @Column(name = "status", nullable = false)
    private Status statusOfOrder;
}
