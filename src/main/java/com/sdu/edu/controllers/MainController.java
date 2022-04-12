package com.sdu.edu.controllers;

import com.sdu.edu.pojo.MainDto;
import com.sdu.edu.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/main")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MainController {
    @Autowired
    LibraryService libraryService;

    @GetMapping("/")
    public ResponseEntity<?> mainPage(){
        MainDto main = new MainDto();
        main.setLibrary(libraryService.getAllBooks());
        return new ResponseEntity<>(main, HttpStatus.OK);
    }
}
