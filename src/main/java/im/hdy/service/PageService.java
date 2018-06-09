package im.hdy.service;

import im.hdy.constant.Constants;
import im.hdy.model.Page;
import im.hdy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PageService {

    @Autowired
    private MongoTemplate template;

    public void findAll() {
        long count = template.count(new Query(), Page.class);
        template.updateFirst(new Query(Criteria.where("_id").is("5b1bc8d725ac57e5a615c75a")), Update.update("name", "wyj"), User.class);
        List<Page> all =
                template.findAll(Page.class);
        for (Page p : all) {
            System.out.println(p);
        }
        System.out.println(count);
    }

    /**
     * 添加page
     */
    public void addPage(Page p) {
        template.insert(p);
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
        List<Page> pages = template.find(new Query(Criteria.where("user._id").is(userId)).skip(currentPage * Constants.PAGENUM).limit(Constants.PAGENUM), Page.class);
        return pages;
    }

}
