package ru.mirea.tapki_lapki.business_object.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * The main User entity
 * @see Role
 */
@Entity
@Getter
@Setter
@Table(name = "user_t")
public class User {
    /** User id **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /** User name **/
    @Column(name = "username", nullable = false)
    private String username;
    /** User email **/
    @Column(name = "email", nullable = false)
    private String email;
    /** User password. It is encrypted **/
    @Column(name = "password", nullable = false)
    private String password;
    /** User active status **/
    @Column(name = "active", nullable = false)
    private boolean active;

    /** User role in the system
     * @see Role **/
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role_t", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
}
