package com.sdu.edu.pojo;

import lombok.Data;

@Data
public class JobDto {
    private String organisation;
    private String position;
    private String startWorkYear;
    private String startWorkMonth;
    private String endDate;
    private String endWorkMonth;
    private String endWorkYear;
}
