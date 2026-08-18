package net.engineeringdigest.journalApp.repository;

import lombok.NonNull;
import net.engineeringdigest.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepo extends MongoRepository<User, ObjectId>, UserCustomRepo{
    User findByUserName(String userName);
    void deleteByUserName(@NonNull String userName);
}
