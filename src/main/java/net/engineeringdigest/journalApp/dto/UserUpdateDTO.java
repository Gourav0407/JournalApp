package net.engineeringdigest.journalApp.dto;

import lombok.Data;
import net.engineeringdigest.journalApp.entity.JournalEntry;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserUpdateDTO {
    private String userName;
}
