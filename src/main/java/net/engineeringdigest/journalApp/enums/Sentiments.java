package net.engineeringdigest.journalApp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Sentiments {
    HAPPY("Happy"),
    SAD("Sad"),
    EMOTIONAL("Emotional"),
    EXCITED("Excited"),
    GRATEFUL("Grateful"),
    PROUD("Proud"),
    HOPEFUL("Hopeful"),
    AMUSED("Amused");

    private String value;

    public String toString(){
        return this.value;
    }

}
