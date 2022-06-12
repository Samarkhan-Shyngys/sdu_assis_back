package com.sdu.edu.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "certificates",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id")

        })
@Data
public class CertificateMdl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String photoPath;
    private String name;
    private String cerName;
    private String cerDec;
    private Long assId;


}
