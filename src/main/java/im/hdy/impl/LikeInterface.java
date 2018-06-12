package im.hdy.impl;

import im.hdy.model.Like;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface LikeInterface extends MongoRepository<Like, String> {
}
