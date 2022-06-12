package com.sdu.edu.pojo;

import lombok.Data;

@Data
public class CourseDto {
    private Long courseId;
    private String assistant;
    private String pathImage;
    private String courseName;
    private Integer rating;
    private Integer point;
    private Integer studentCount;
}
