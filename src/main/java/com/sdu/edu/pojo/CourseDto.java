package com.sdu.edu.pojo;

import lombok.Data;
import org.json.JSONObject;

import java.util.List;

@Data
public class CourseDto {
    private Long courseId;
    private String assistant;
    private String pathImage;
    private String courseName;
    private Integer rating;
    private Integer point;
    private Integer studentCount;
    private String assImage;
    private boolean liked;
    private List<TableTimeDto> dates;
}
