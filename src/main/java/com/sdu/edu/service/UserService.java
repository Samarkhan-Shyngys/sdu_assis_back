package com.sdu.edu.service;

import com.sdu.edu.models.Student;
import com.sdu.edu.models.User;
import com.sdu.edu.pojo.ActivateDto;
import com.sdu.edu.pojo.StudentProfileDto;
import com.sdu.edu.repository.StudentRepository;
import com.sdu.edu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Service
public class UserService {
    @Value("${file.upload-path}")
    private String uploadPath;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MailSender mailSender;

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    public void senderMail(String code, String email) throws Exception {
        mailSender.send(email, "Activation code", code);
    }

    public User activateUser(ActivateDto activateCodeDto) {
        if(!userRepository.existsByEmail(activateCodeDto.getEmail())){
            return null;
        }
        else {
            User user = userRepository.findByEmail(activateCodeDto.getEmail());
            if(user.getActivate()==1){
                return null;
            }
            if(user.getActivateCode().compareTo(activateCodeDto.getActivateCode())!=0){
                return null;
            }
            user.setActivate(1);
            userRepository.save(user);
            return user;
        }
    }


}
