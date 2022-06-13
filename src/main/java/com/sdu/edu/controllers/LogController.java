package com.sdu.edu.controllers;

import com.sdu.edu.pojo.AdminDto;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class LogController {
    @GetMapping("/")
    public ResponseEntity<?> mainPage(){

        return new ResponseEntity<>( HttpStatus.OK);

    }
}
