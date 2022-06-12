package com.sdu.edu.pojo;

import lombok.Data;

import javax.sql.rowset.spi.SyncResolver;

@Data
public class AdminContentDto {
    private Long id;
    private String emailID;
    private String name;
    private String profession;
    private String faculty;
    private String phone;
    private String icon;

}
