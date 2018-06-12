package im.hdy.service;

import im.hdy.constant.Constants;
import im.hdy.impl.TalkInterface;
import im.hdy.model.Talk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TalkService {

    @Autowired
    private TalkInterface talkInterface;
    @Autowired
    private MongoTemplate template;

    public Talk findOne(String _id) {
        return talkInterface.findOne(_id);
    }

    public Talk save(Talk talk) {
        return talkInterface.save(talk);
    }


    public List<Talk> findTalkyByUserIdAndTime(String userId, int currentPage) {
        return template.find(new Query(Criteria.where("user._id").is(userId)).with(new Sort(Sort.Direction.ASC, "TalkTime")).skip(currentPage * Constants.PAGENUM).limit(Constants.PAGENUM), Talk.class);
    }

    public void delete(String talkId) {
        talkInterface.delete(talkId);
    }
}
