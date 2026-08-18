package net.engineeringdigest.journalApp.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.engineeringdigest.journalApp.entity.JournalConfig;
import net.engineeringdigest.journalApp.repository.JournalConfigRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AppCache {

    @Getter
    @AllArgsConstructor
    public enum Keys {
        WEATHER_API("weather_api");

        private final String value;

        @Override
        public String toString(){
            return this.value;
        }
    }

    private final JournalConfigRepo journalConfigRepo;

    @Autowired
    public AppCache(JournalConfigRepo journalConfigRepo){
        this.journalConfigRepo=journalConfigRepo;
    }

    private Map<String,String> appCache;

    @PostConstruct
    public void init(){
        List<JournalConfig> journalConfigList= journalConfigRepo.findAll();

        //Can use For Each
        appCache=(journalConfigList.stream()
                .collect(Collectors
                        .toConcurrentMap(JournalConfig::getKey,JournalConfig::getValue)));

    }

    @Scheduled(fixedRate = 1000*60)
    public void cacheReset(){
        init();
    }

    public String getConfig(Keys keys){
        return appCache.get(keys.getValue());
    }



}
