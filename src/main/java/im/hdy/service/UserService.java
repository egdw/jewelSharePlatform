package im.hdy.service;

import im.hdy.constant.Constants;
import im.hdy.impl.UserInterface;
import im.hdy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserInterface userIntrface;

    @Autowired
    private MongoTemplate template;

    public User findOne(String id) {
        return userIntrface.findOne(id);
    }

    public User findByOpenId(String openId) {
        return template.findOne(new Query(Criteria.where("openid").is(openId)), User.class);
    }

    public List<User> findAll(int currentPage) {
        return template.find(new Query().skip(currentPage * Constants.PAGENUM).limit(Constants.PAGENUM), User.class);
    }

    public User saveUser(User user){
        User save = userIntrface.save(user);
        return save;
    }

    public void deleteUser(String _id){
        userIntrface.delete(_id);
    }
}
