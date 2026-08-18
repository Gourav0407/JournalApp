package net.engineeringdigest.journalApp.services;

import net.engineeringdigest.journalApp.dto.JouranalEntryDTO;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepo;
import net.engineeringdigest.journalApp.repository.UserRepo;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepo journalEntryRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    private final Logger logger= LoggerFactory.getLogger(JournalEntryService.class);

    @Transactional
    public void saveEntry(JournalEntry journalEntry){
        try {
            User user= userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
            if(user!=null) {
                JournalEntry save = journalEntryRepo.save(journalEntry);
                user.getJournalEntries().add(save);
                userRepo.save(user);
            }
        }catch (Exception e) {
            logger.info("Hey");
            throw new RuntimeException("An error had occurred while saving the entry");
        }

    }

    public Boolean updateEntry(JouranalEntryDTO journalEntry, ObjectId myId){
        User user= userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user != null &&
                user.getJournalEntries().stream()
                        .anyMatch(entry -> entry
                                .getId().equals(myId))) {

            JournalEntry old = journalEntryRepo.findById(myId).orElse(null);
            if (old != null) {
                old.setTitle(journalEntry.getTitle()!=null && !journalEntry.getTitle().isEmpty() ? journalEntry.getTitle() : old.getTitle());
                old.setContent(journalEntry.getContent() != null && !journalEntry.getContent().isEmpty() ? journalEntry.getContent() : old.getContent());
                old.setSentiments(journalEntry.getSentiments() != null ? journalEntry.getSentiments() : old.getSentiments());
                journalEntryRepo.save(old);
                return true;
            }
        }
        return false;
    }

    public List<JournalEntry> getAll(){
        return userService.findByUserName(SecurityContextHolder.
                        getContext().getAuthentication().getName())
                .getJournalEntries();
    }
    public Optional<JournalEntry> findById(ObjectId myId){
        User user= userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        boolean userOwnsEntry = user.getJournalEntries()
                .stream()
                .anyMatch(entry -> entry.getId().equals(myId));


        if(userOwnsEntry){
            return journalEntryRepo.findById(myId);
        }
        return Optional.empty();

    }

    public void deleteById(ObjectId myId){
        User user= userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        if(user != null) {
            if(user.getJournalEntries().removeIf(x -> x.getId().equals(myId))) {
                journalEntryRepo.deleteById(myId);
                userService.saveEntry(user);
            }

        }
    }
}
