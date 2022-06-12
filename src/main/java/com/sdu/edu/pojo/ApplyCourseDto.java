package com.sdu.edu.pojo;

import com.sdu.edu.models.CertificateMdl;
import com.sdu.edu.models.JobMdl;
import lombok.Data;
import org.json.JSONObject;

import java.util.List;

@Data
public class ApplyCourseDto {
    private String imagePath;
    private String courseName;
    private String assistantName;
    private Integer rating;
    private Integer point;
    private Integer studentCount;
    private Integer courseCount;
    private String aboutCourse;
    private String courseFormat;
    private String aboutAssistant;
    private List<JobMdl> workList;
    private List<CertificateMdl> certificateList;





}
