package com.sdu.edu.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "language",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id")

        })
@Data
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String lanName;
    private String lanLevel;
    private Long assisId;
}
