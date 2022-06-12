package com.sdu.edu.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "jobs",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id")

        })
@Data
public class JobMdl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String organisation;
    private String position;
    private Integer startWorkYear;
    private String startWorkMonth;
    private boolean endDate;
    private String endWorkMonth;
    private Integer endWorkYear;
    private Long assId;
}
