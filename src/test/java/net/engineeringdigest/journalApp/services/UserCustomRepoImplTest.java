package net.engineeringdigest.journalApp.services;

import net.engineeringdigest.journalApp.repository.UserCustomRepoImpl;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
public class UserCustomRepoImplTest {

    @Autowired
    UserCustomRepoImpl userCustomRepo;

    @Test
    public void testGetListOfUserForSA(){
        assertNotNull(userCustomRepo.getListOfUserForSA());
    }

}
