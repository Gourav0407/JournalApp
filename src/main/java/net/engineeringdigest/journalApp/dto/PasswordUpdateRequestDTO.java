package net.engineeringdigest.journalApp.dto;


import lombok.Value;

@Value
public class PasswordUpdateRequestDTO {
    String oldPassword;
    String newPassword;
}

