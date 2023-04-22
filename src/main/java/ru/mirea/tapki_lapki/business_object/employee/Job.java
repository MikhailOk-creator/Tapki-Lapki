package ru.mirea.tapki_lapki.business_object.employee;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Job entity
 * @see Employee
 */
@Entity
@Getter
@Setter
@Table(name="job")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="function", nullable = false)
    private String function;
}
