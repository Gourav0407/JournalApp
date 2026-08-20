package net.engineeringdigest.journalApp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.engineeringdigest.journalApp.enums.Sentiments;
import net.engineeringdigest.journalApp.services.MailService;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SentimentData {
    private String email;
    private Sentiments sentiments;
}
