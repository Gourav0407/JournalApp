package net.engineeringdigest.journalApp.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

@Service
@Slf4j
public class MailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    MailService(JavaMailSender javaMailSender){
        this.javaMailSender=javaMailSender;
    }

    public void sendMail(String address, String subject, String body){

        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            simpleMailMessage.setTo(address);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(body);

            javaMailSender.send(simpleMailMessage);
        }catch (Exception e){
            log.error("A error has occured",e);
        }
    }
}
