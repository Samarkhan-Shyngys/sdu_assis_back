package com.sdu.edu.pojo;

import lombok.Data;

@Data
public class ActivateDto {
    private String email;
    private String activateCode;
}
