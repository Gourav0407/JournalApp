package net.engineeringdigest.journalApp.controllers;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("users")
    public ResponseEntity<?> getAll(){
        List<User> users = userService.getAll();
        if(users != null && !users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Not Found",HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("create-admin")
    public ResponseEntity<?> addAdmin(@RequestBody User user){
        try {
            userService.createAdmin(user);
            return new ResponseEntity<>("Successfully Created", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Bad Request",HttpStatus.BAD_REQUEST);
        }
    }
}
