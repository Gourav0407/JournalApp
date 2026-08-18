package net.engineeringdigest.journalApp.services;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.dto.DeleteUserDTO;
import net.engineeringdigest.journalApp.dto.PasswordUpdateRequestDTO;
import net.engineeringdigest.journalApp.dto.UserUpdateDTO;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepo;
import net.engineeringdigest.journalApp.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    JournalEntryRepo journalEntryRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean saveEntry(User user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(new ArrayList<>(Collections.singletonList("USER")));
            userRepo.save(user);
            return true;
        }catch (Exception e){
//            log.error("An error has occurred for {}", user.getUserName(),e);
            log.error("sdfdd");
            log.info("ddddd");
            log.warn("dsdfdf");
            log.trace("sdfdfc");
            log.debug("dsdfdf");
            return false;
        }

    }

    public void createAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(new ArrayList<>(Collections.singletonList("ADMIN")));
        userRepo.save(user);
    }

    public boolean updateUser(UserUpdateDTO userUpdateDTO){
        if(userUpdateDTO == null || userUpdateDTO.getUserName() == null || userUpdateDTO.getUserName().trim().isEmpty()){
            return false;
        }
        User existingUser= userRepo.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        if(existingUser == null) {
            return false;
        }
        existingUser.setUserName(userUpdateDTO.getUserName());
        userRepo.save(existingUser);
        return true;
    }

    public boolean changePassword(PasswordUpdateRequestDTO passwordUpdateRequestDTO){
        if (passwordUpdateRequestDTO == null ||
                passwordUpdateRequestDTO.getOldPassword() == null ||
                passwordUpdateRequestDTO.getNewPassword() == null ||
                passwordUpdateRequestDTO.getNewPassword().trim().isEmpty()) {
            return false;
        }
        User user= userRepo.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        if(user != null) {
            if (passwordEncoder.matches(passwordUpdateRequestDTO.getOldPassword(), user.getPassword())) {
                String password = passwordEncoder.encode(passwordUpdateRequestDTO.getNewPassword());
                user.setPassword(password);
                userRepo.save(user);
                return true;
            }
        }
        return false;
    }

    public List<User> getAll(){
        return userRepo.findAll();
    }
    public Optional<User> findById(ObjectId myId){
        return userRepo.findById(myId);

    }

    public void deleteById(ObjectId myId){
        userRepo.deleteById(myId);
    }

    public User findByUserName(String userName){
        return userRepo.findByUserName(userName);
    }

    @Transactional
    public String deleteByUserName(DeleteUserDTO deleteUserDTO) {
        try {
            User user=userRepo.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
            if(user!=null){
                if(passwordEncoder.matches(deleteUserDTO.getPassword(),user.getPassword())){
                    if(user.getJournalEntries()!=null && !user.getJournalEntries().isEmpty() ){
                        List<ObjectId> entries=user.getJournalEntries().stream().map(JournalEntry :: getId).collect(Collectors.toList());
                        journalEntryRepo.deleteAllById(entries);
                    }
                    userRepo.deleteByUserName(user.getUserName());
                    return "Deleted";
                }else {
                    return "Wrong Password";
                }
            }
            return "An Error Occurred";
        }catch (Exception e){
            return "An Error Occurred";
        }


    }
}
