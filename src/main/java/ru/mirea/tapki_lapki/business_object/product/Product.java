package ru.mirea.tapki_lapki.business_object.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name_p", nullable = false)
    private String name_p;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "image_url")
    private String image_url;
}
