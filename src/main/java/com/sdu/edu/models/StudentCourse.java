package com.sdu.edu.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "student_course",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id")

        })
@Data
public class StudentCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long courseId;
    private Long studentId;
    private boolean liked;
}
