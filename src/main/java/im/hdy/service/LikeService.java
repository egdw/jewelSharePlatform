package im.hdy.service;

import com.mongodb.BasicDBObject;
import im.hdy.impl.LikeInterface;
import im.hdy.model.Like;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class LikeService {

    @Autowired
    private MongoTemplate template;

    @Autowired
    private LikeInterface likeInterface;

    public void update(Like like) {
//        Like one = template.findOne(new Query(Criteria.where("_id").is(like.getUserId())), Like.class);
//        if (one != null) {
//            //说明曾经点赞过.
//            //那么进行删除
//            template.remove(one);
//            return;
//        }
//        template.insert(like);
    }

    public Like saveLikes(Like like) {
        Like save = likeInterface.save(like);
        return save;
    }

    /**
     * 修改或者添加点赞
     * @param likeId
     * @param userId
     */
    public void addOrDelNewLikes(String likeId, String userId) {
        Like like = likeInterface.findOne(likeId);
        LinkedList<String> users = like.getUsers();
        int indexOf = users.indexOf(userId);
        if (indexOf != -1) {
            //说明曾经点赞
            users.remove(userId);
        } else {
            users.add(userId);
        }
        //添加或删除点赞
        likeInterface.save(like);
    }
}
