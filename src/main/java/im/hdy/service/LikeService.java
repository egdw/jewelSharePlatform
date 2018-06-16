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
    @Autowired
    private PageService pageService;

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

    public Like saveLikes(Like like, String pageId) {
        Like save = likeInterface.save(like);
        int size = save.getUsers().size();
        pageService.updateLikesNum(pageId, save);
        return save;
    }

    /**
     * 修改或者添加点赞
     *
     * @param like
     * @param userId
     */
    public void addOrDelNewLikes(String pageId, Like like, String userId) {
        LinkedList<String> users = like.getUsers();
        int indexOf = users.indexOf(userId);
        if (indexOf != -1) {
            //说明曾经点赞
            users.remove(userId);
        } else {
            users.add(userId);
        }
        //添加或删除点赞
        Like save = likeInterface.save(like);
        pageService.updateLikesNum(pageId, save);

    }
}
