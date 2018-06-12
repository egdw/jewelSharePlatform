package im.hdy.impl;

import im.hdy.model.Talk;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TalkInterface extends MongoRepository<Talk, String> {
}
