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

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "zip_code", nullable = false)
    private int zip_code;

    @Column(name = "area_code", nullable = false)
    private int area_code;

    @Column(name = "phone_number", nullable = false)
    private String phone_number;

    @Column(name = "comments", nullable = false)
    private String comments;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
