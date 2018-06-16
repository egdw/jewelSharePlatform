package im.hdy.impl;

import im.hdy.model.SmallTalk;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface SmallTalkService extends MongoRepository<SmallTalk, String> {
}
