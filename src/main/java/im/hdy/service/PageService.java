package im.hdy.service;

import com.mongodb.WriteResult;
import im.hdy.constant.Constants;
import im.hdy.impl.PageInterface;
import im.hdy.model.Like;
import im.hdy.model.Page;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PageService {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(PageService.class);
    @Autowired
    private MongoTemplate template;
    @Autowired
    private PageInterface pageInterface;

    public Page findOne(String id) {
        Page page = template.findOne(new Query(Criteria.where("_id").is(id)), Page.class);
        return page;
    }

    public List<Page> findAll() {
//        long count = template.count(new Query(), Page.class);
//        template.updateFirst(new Query(Criteria.where("_id").is("5b1bc8d725ac57e5a615c75a")), Update.update("name", "wyj"), User.class);
//        List<Page> all =
//                template.findAll(Page.class);
//        for (Page p : all) {
//            System.out.println(p);
//        }
//        System.out.println(count);
        return pageInterface.findAll();
    }

    /**
     * 添加page
     */
    public Page addPage(Page p) {
        Page save = pageInterface.save(p);
        return save;
    }

    /**
     * 根据文章id获取文章
     *
     * @param id
     * @return
     */
    public Page getOne(String id) {
        List<Page> pages = template.find(new Query(Criteria.where("_id").is(id)), Page.class);
        if (pages != null && pages.size() > 0) {
            return pages.get(0);
        } else {
            return null;
        }
    }

    /**
     * 更新数据
     *
     * @param p
     */
    public void update(Page p) {
        template.insert(p);
    }


    /**
     * 根据用户id获取自己的文章
     *
     * @param userId
     * @return
     */
    public List<Page> findPagesByUserId(String userId, int currentPage) {
        //按先后顺序排序
        List<Page> pages = template.find(new Query(Criteria.where("user._id").is(userId)).with(new Sort(Sort.Direction.DESC, "date")).skip(currentPage * Constants.PAGENUM).limit(Constants.PAGENUM), Page.class);
        return pages;
    }


    /**
     * 根据上传时间获取文章
     *
     * @param currentPage
     * @return
     */
    public List<Page> findPagesByUploadTime(int currentPage) {
        return template.find(new Query(Criteria.where("isinrecall").is(false)).with(new Sort(Sort.Direction.DESC, "date")).skip(currentPage * Constants.PAGENUM).limit(Constants.PAGENUM), Page.class);
    }


    public List<Page> findPagesByMemoirs(int currentPage) {
        List<Page> pages = template.find(new Query(Criteria.where("isinrecall").is(true)).with(new Sort(Sort.Direction.DESC, "enterInRecallDate")), Page.class);
        return pages;
    }

    /**
     * 随机获取文章
     *
     * @return
     */
    public List<Page> findPagesRandom() {
        long count = template.count(new Query(), Page.class);
        logger.info("随机获取文章的中的所有文章数量" + count);
        if (count < 10) {
            return template.findAll(Page.class);
        }
        int nextInt = Constants.RANDOM.nextInt((int) count);
        logger.info("随机获取文章的中的随机值" + nextInt);
        //通过随机数获取一段时间的文章
        Criteria criteria = new Criteria();
        return template.find(new Query(criteria.orOperator(Criteria.where("isinrecall").is(false), Criteria.where("isinrecall").exists(false))).with(new Sort(Sort.Direction.DESC, "date")).skip(nextInt).limit(Constants.PAGENUM), Page.class);
    }


    public void delete(String pageId) {
        pageInterface.delete(pageId);
    }

    public List<Page> findBest(int currentPage) {
        Criteria criteria = new Criteria();
//        System.out.println(template.find(new Query(criteria.orOperator(Criteria.where("isinrecall").exists(false), Criteria.where("isinrecall").is(false))), Page.class));
//        System.out.println(template.find(new Query(Criteria.where("isInRecall").is(false)).with(new Sort(Sort.Direction.DESC, "liked")), Page.class));
        return template.find(new Query(criteria.orOperator(Criteria.where("isinrecall").exists(false), Criteria.where("isinrecall").is(false))).with(new Sort(Sort.Direction.DESC, "liked")).skip(currentPage * Constants.PAGENUM).limit(Constants.PAGENUM), Page.class);
//        Aggregation aggregation = Aggregation.newAggregation(
//                Aggregation.match(Criteria.where("isInRecall").is(false)),
//                Aggregation.project().and("likes.users").project("size").as("count"));


//        ArrayList<AggregationOperation> aggregations = new ArrayList<>();
//        aggregations.add(Aggregation.match(Criteria.where("isInRecall").is(false)));
//        aggregations.add(Aggregation.group("likes.users").count().as("total"));
//        aggregations.add( Aggregation.project("total").and("_id").as("likes.users"));
//        aggregations.add(Aggregation.sort(Sort.Direction.DESC, "total"));
//        Aggregation newAggregation = Aggregation.newAggregation(aggregations);
////        AggregationResults<Page> results = template.aggregate(newAggregation, Page.class);
//        AggregationResults<Page> results = template.aggregate(newAggregation, Page.class, Page.class);
//        System.out.println(results.getMappedResults());
    }


    public int updateLikesNum(String pageId, Like like) {
        Page one = pageInterface.findOne(pageId);
        Like likes = one.getLikes();
        if (likes == null) {
            one.setLikes(like);
            pageInterface.save(one);
        }

        String id = pageInterface.findOne(pageId).getLikes().get_id();
        if (id == like.get_id()) {
            WriteResult writeResult = template.updateFirst(new Query(Criteria.where("_id").is(pageId)), Update.update("liked", like.getUsers().size()), Page.class);
            return writeResult.getN();
        } else {
            return 0;
        }
    }
}
