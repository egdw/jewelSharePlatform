package im.hdy.service;

import im.hdy.constant.Constants;
import im.hdy.impl.SmallTalkService;
import im.hdy.impl.TalkInterface;
import im.hdy.model.SmallTalk;
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
    @Autowired
    private SmallTalkService smallTalkService;

    public Talk findOne(String _id) {
        return talkInterface.findOne(_id);
    }

    public Talk save(Talk talk) {
        return talkInterface.save(talk);
    }


    public List<Talk> findTalkyByUserIdAndTime(String userId, int currentPage) {
        return template.find(new Query(new Criteria().andOperator(Criteria.where("pageuser._id").is(userId), Criteria.where("user._id").ne(userId))).with(new Sort(Sort.Direction.DESC, "TalkTime")).skip(currentPage * Constants.PAGENUM).limit(Constants.PAGENUM), Talk.class);
//        return template.find(new Query(Criteria.where("pageuser._id").is(userId)).with(new Sort(Sort.Direction.DESC, "TalkTime")).skip(currentPage * Constants.PAGENUM).limit(Constants.PAGENUM), Talk.class);
//        return template.find(new Query(Criteria.where("pageuser._id").is(userId)).with(new Sort(Sort.Direction.DESC, "TalkTime")).skip(currentPage * Constants.PAGENUM).limit(Constants.PAGENUM), Talk.class);

    }

    public void delete(String talkId) {
        talkInterface.delete(talkId);
    }


    public SmallTalk saveSmallTalk(SmallTalk talk) {
        SmallTalk save = smallTalkService.save(talk);
        return save;
    }

    public SmallTalk findSmallTalkOne(String _id) {
        return smallTalkService.findOne(_id);
    }
}
