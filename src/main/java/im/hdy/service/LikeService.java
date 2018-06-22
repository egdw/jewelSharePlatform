package im.hdy.service;

import com.mongodb.WriteResult;
import im.hdy.impl.LikeInterface;
import im.hdy.model.Like;
import im.hdy.model.Page;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.logging.Logger;

@Service
public class LikeService {

    @Autowired
    private MongoTemplate template;

    @Autowired
    private LikeInterface likeInterface;
    @Autowired
    private PageService pageService;
    private org.slf4j.Logger logger = LoggerFactory.getLogger(LikeService.class);

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
//        int size = save.getUsers().size();
        pageService.updateLikesNum(pageId, save);
        return save;
    }

    /**
     * 修改或者添加点赞
     *
     * @param like
     * @param userId
     */
    public boolean addOrDelNewLikes(String pageId, Like like, String userId) {
        LinkedList<String> users = like.getUsers();
        int indexOf = users.indexOf(userId);
        if (indexOf != -1) {
            //说明曾经点赞
            users.remove(userId);
            like.setUsers(users);
            Like save = likeInterface.save(like);

            WriteResult writeResult = template.updateFirst(new Query(Criteria.where("_id").is(pageId)), Update.update("liked", users.size()), Page.class);
            int n = writeResult.getN();
            logger.info("修改点赞的数量:" + n);
            long liked = pageService.findOne(pageId).getLiked();
            logger.info("获取到的点赞数量:" + liked);

//            pageService.updateLikesNum(pageId, save);
            return false;
        } else {
            users.add(userId);
            like.setUsers(users);
            Like save = likeInterface.save(like);

            WriteResult writeResult = template.updateFirst(new Query(Criteria.where("_id").is(pageId)), Update.update("liked", users.size()), Page.class);
            int n = writeResult.getN();
            logger.info("修改点赞的数量:" + n);
//            pageService.updateLikesNum(pageId, save);
            long liked = pageService.findOne(pageId).getLiked();
            logger.info("获取到的点赞数量:" + liked);
            return true;
        }
        //添加或删除点赞

    }
}
