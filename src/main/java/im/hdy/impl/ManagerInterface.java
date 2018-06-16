package im.hdy.impl;

import im.hdy.model.Manager;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface ManagerInterface extends MongoRepository<Manager, String> {
}
