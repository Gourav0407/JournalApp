package net.engineeringdigest.journalApp.services;

import net.engineeringdigest.journalApp.entity.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserService userService;


    @Test
    public void testGetAll(){
        assertNotNull(userService.getAll());
    }

//  @EnumSource
//  @ArgumentsSource()
//  @ValueSource(Strings={"","",""})
    @ParameterizedTest
    @CsvSource({
            "3,1,2",
            "4,3,1",
            "7,5,2"
    })
    public void testTest(int expected, int a, int b){
        assertEquals(expected,a+b);

    }

    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    public void testSaveNewUser(User user, boolean expected){
        assertEquals(expected, userService.saveEntry(user));
    }
}
