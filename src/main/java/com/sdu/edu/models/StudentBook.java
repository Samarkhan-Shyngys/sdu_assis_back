package com.sdu.edu.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "student_book",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id")

        })
@Data
public class StudentBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long bookId;
    private Long studentId;
    private boolean liked;
}
