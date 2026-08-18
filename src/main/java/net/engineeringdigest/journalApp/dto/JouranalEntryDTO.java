package net.engineeringdigest.journalApp.dto;

import lombok.*;
import net.engineeringdigest.journalApp.enums.Sentiments;

@Value
public class JouranalEntryDTO {

    private String title;
    private String content;
    private Sentiments sentiments;
}
