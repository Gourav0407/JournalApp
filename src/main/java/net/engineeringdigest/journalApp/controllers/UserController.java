package net.engineeringdigest.journalApp.controllers;

import net.engineeringdigest.journalApp.dto.DeleteUserDTO;
import net.engineeringdigest.journalApp.dto.PasswordUpdateRequestDTO;
import net.engineeringdigest.journalApp.dto.UserUpdateDTO;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateDTO userUpdateDTO){
        if(userService.updateUser(userUpdateDTO)){
            return new ResponseEntity<>("Updated Successfully",HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>("Bad Request",HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("password")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordUpdateRequestDTO passwordUpdateRequestDTO){

        if(userService.changePassword(passwordUpdateRequestDTO)){
            return new ResponseEntity<>("Password Updated Successfully",HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Old password is incorrect",HttpStatus.UNPROCESSABLE_ENTITY);

    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestBody DeleteUserDTO deleteUserDTO){
        String result= userService.deleteByUserName(deleteUserDTO);
        if(result.equals("Deleted")){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else if(result.equals("Wrong Password")) {
            return new ResponseEntity<>("Wrong Password",HttpStatus.FORBIDDEN);
        }else {
            return new ResponseEntity<>("Something went wrong",HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping
    public ResponseEntity<?> greetings(){
        String user=SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>("Hi"+user,HttpStatus.OK);
    }
}
