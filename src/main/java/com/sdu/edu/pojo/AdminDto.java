package com.sdu.edu.pojo;

import lombok.Data;

import java.util.List;

@Data
public class AdminDto {
    private List<?> requests;
    private List<AdminContentDto> assistents;
    private List<AdminContentDto> applied;
    private List<AdminContentDto> students;
}
