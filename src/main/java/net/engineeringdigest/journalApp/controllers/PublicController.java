package net.engineeringdigest.journalApp.controllers;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String healthCheck(){
        return "Active";
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> addUser(@RequestBody User user){
        if(userService.saveEntry(user)) {
            return new ResponseEntity<>("Successfully Created", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Bad Request",HttpStatus.BAD_REQUEST);
        }
    }
}
