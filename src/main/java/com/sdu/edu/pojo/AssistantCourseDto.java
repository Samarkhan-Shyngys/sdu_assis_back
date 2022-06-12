package com.sdu.edu.pojo;

import lombok.Data;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

@Data
public class AssistantCourseDto {
    private String courseName;
    private String about;
    private String format;
    private List<JSONObject> dates;
    private String photoPath;

}
