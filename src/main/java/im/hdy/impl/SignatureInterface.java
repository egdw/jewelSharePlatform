package im.hdy.impl;

import im.hdy.model.Signature;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface SignatureInterface extends MongoRepository<Signature, String> {
}
