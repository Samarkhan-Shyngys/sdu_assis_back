package com.sdu.edu.models;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "library",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id")
        })
@Data
public class Library {
        @Id
        @Column(name = "id", nullable = false)
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "book_name")
        private String bookName;

        @Column(name = "author")
        private String author;

        @Column(name = "book_path")
        private String bookPath;

        @Column(name = "image_path")
        private String imagePath;

        @Column(name = "upload_date")
        private Timestamp uploadDate = new Timestamp(System.currentTimeMillis());
}
