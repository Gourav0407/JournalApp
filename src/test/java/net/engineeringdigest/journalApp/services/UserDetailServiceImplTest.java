package net.engineeringdigest.journalApp.services;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Disabled
public class UserDetailServiceImplTest {

    @InjectMocks
    private UserDetailServiceImpl userDetailService;

    @Mock
    private UserRepo userRepo;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadUserByUserName(){
        when(userRepo.findByUserName(ArgumentMatchers.anyString()))
                .thenReturn(User.builder().userName("Ram").password("Ram").roles(Arrays.asList("USER")).build());
        UserDetails userDetails= userDetailService.loadUserByUsername("ram");
        assertNotNull(userDetails);
    }

}
