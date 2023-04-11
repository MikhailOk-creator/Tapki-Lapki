package ru.mirea.tapki_lapki.business_object.employee;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.mirea.tapki_lapki.business_object.user.User;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name="employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @OneToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @Column(name = "hire_date", nullable = false)
    private Date hire_date;

    @Column(name = "salary", nullable = false)
    private int salary;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
