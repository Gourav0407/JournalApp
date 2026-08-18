package net.engineeringdigest.journalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document("config_journal_app")
public class JournalConfig {

    @Id
    private Object id;

    @NonNull
    private String key;
    private String value;
}
