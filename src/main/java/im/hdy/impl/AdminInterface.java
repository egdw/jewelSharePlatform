package im.hdy.impl;

import im.hdy.model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface AdminInterface extends MongoRepository<Admin, String> {
}
