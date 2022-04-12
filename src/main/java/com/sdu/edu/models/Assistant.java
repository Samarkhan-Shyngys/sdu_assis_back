package com.sdu.edu.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "assistant",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id")

        })
@Data
public class Assistant {
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
    private String language;
    private String aboutYou;
    private String job;
    private String certificate;
}
