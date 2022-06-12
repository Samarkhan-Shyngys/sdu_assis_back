package com.sdu.edu.pojo.admin;

import lombok.Data;

import java.util.List;

@Data
public class PersonalDto {
    private String fio;
    private String imagePath;
    private String phone;
    private String emailId;
    private String about;
    private String courseId;
    private String faculty;
    private String prof;
    private List<?> langue;
    private String video;
}
