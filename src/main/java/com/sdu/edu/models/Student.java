package com.sdu.edu.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "student",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id")

        })
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;
    private String faculty;
    private String profession;
    private String phone;
    private String photoPath;
    private Long userId;

}
