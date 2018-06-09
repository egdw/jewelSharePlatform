package im.hdy.impl;

import im.hdy.model.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface PageInterface extends MongoRepository<Page, String> {

}
