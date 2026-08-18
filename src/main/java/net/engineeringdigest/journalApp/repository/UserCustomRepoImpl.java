package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserCustomRepoImpl implements UserCustomRepo {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public UserCustomRepoImpl(MongoTemplate mongoTemplate){
        this.mongoTemplate=mongoTemplate;
    }

    public List<User> getListOfUserForSA(){
        Query query=new Query();

        query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))
                .addCriteria(Criteria.where("sentimentAnalysis").is(true));


        List<User> filteredUsers= mongoTemplate.find(query,User.class);
        return filteredUsers;
    }

}
