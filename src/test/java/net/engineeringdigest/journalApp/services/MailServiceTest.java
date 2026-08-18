package net.engineeringdigest.journalApp.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MailServiceTest {

    @Autowired
    MailService mailService;

    @Test
    public void testSendMail(){
        mailService.sendMail("gouravsyal2@gmail.com","Java Mail Testing", "Hello how are you");
    }
}
