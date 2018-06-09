package im.hdy.impl;

import im.hdy.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserInterface extends MongoRepository<User, String> {
}
