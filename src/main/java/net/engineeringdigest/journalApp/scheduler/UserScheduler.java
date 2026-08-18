package net.engineeringdigest.journalApp.scheduler;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.enums.Sentiments;
import net.engineeringdigest.journalApp.repository.UserRepo;
import net.engineeringdigest.journalApp.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    private final UserRepo userRepo;

    private final MailService mailService;

    @Autowired
    public UserScheduler(UserRepo userRepo, MailService mailService){
        this.userRepo=userRepo;
        this.mailService=mailService;
    }

    @Scheduled(cron = "0 * * * * *")
    public void scheduleEmail(){
        List<User> users= userRepo.getListOfUserForSA();
        for(User user : users){
            List<JournalEntry> journalEntries= user.getJournalEntries();
            List<Sentiments> sentiments= journalEntries.stream().filter(entry-> ChronoUnit.DAYS.between(entry.getDate(),LocalDateTime.now())<7).map(entry-> entry.getSentiments()).collect(Collectors.toList());
            Map<Sentiments,Integer> sentimentsCountMap= new HashMap<>();
            for (Sentiments sentiment : sentiments){
                if(sentimentsCountMap.containsKey(sentiment)){
                    sentimentsCountMap.put(sentiment,sentimentsCountMap.get(sentiment)+1);
                }else{
                    sentimentsCountMap.put(sentiment,1);
                }
            }
            Sentiments thisWeekSentiment=null;
            int maxCount=0;
            for(Map.Entry<Sentiments,Integer> entry : sentimentsCountMap.entrySet()){
                if(maxCount<=entry.getValue()){
                    maxCount=entry.getValue();
                    thisWeekSentiment=entry.getKey();
                }
            }

            if(thisWeekSentiment!=null){
                mailService.sendMail(user.getEmail(),"Sentiments",thisWeekSentiment.getValue());
            }
        }
    }
}
