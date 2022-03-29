package com.sdu.edu.service;

import com.sdu.edu.models.User;
import com.sdu.edu.pojo.ActivateDto;
import com.sdu.edu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.UUID;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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

    public String activateUser(ActivateDto activateCodeDto) {
        if(!userRepository.existsByEmail(activateCodeDto.getEmail())){
            return null;
        }
        else {
            User user = userRepository.findByEmail(activateCodeDto.getEmail());
            System.out.println(activateCodeDto.getActivateCode());
            if(user.getActivateCode().compareTo(activateCodeDto.getActivateCode())!=0){
                return null;
            }
            user.setActivate(1);
            userRepository.save(user);
            return user.getUsername();
        }
    }
}
