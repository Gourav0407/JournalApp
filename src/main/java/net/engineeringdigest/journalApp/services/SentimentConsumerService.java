package net.engineeringdigest.journalApp.services;

import net.engineeringdigest.journalApp.model.SentimentData;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SentimentConsumerService {

    private final MailService mailService;

    @Autowired
    public SentimentConsumerService(MailService mailService){
        this.mailService=mailService;
    }

    @KafkaListener(
            topics = "weekly-sentiments",
            id = "weekly-sentiment-group-debug-1"
    )
    public void consume(SentimentData sentimentData){
        sendEmail(sentimentData);
    }

    public void sendEmail(@NotNull SentimentData sentimentData){
        mailService.sendMail(sentimentData.getEmail(),"Sentiment Analysis",sentimentData.getSentiments().getValue());
    }

}
