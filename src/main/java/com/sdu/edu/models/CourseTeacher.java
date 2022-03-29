package com.sdu.edu.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "course_teacher",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id")
        })
@Data
public class CourseTeacher {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_name", columnDefinition="TEXT")
    private String courseName;

    @Column(name = "course_time")
    private String courseTime;

    @Column(name = "course_info", columnDefinition="TEXT")
    private String courseInfo;

    @Column(name = "assistent_id")
    private Integer assistentId;



}
