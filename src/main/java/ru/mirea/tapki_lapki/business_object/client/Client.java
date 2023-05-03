package ru.mirea.tapki_lapki.business_object.client;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.mirea.tapki_lapki.business_object.user.User;

/**
 * Client entity
 * @see User
 */
@Entity
@Getter
@Setter
@Table(name="customer")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip_code")
    private int zip_code;

    @Column(name = "area_code")
    private int area_code;

    @Column(name = "phone_number")
    private String phone_number;

    @Column(name = "comments")
    private String comments;

    @Column(name = "cart")
    private Boolean cart;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
