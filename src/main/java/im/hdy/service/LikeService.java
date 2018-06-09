package im.hdy.service;

import im.hdy.controller.LikeController;
import im.hdy.model.Like;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.sound.midi.Track;

@Service
public class LikeService {

    @Autowired
    private MongoTemplate template;

    public void update(Like like) {
        Like one = template.findOne(new Query(Criteria.where("_id").is(like.getUserId())), Like.class);
        if (one != null) {
            //说明曾经点赞过.
            //那么进行删除
            template.remove(one);
            return;
        }
        template.insert(like);
    }
}
