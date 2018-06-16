package im.hdy.service;

import im.hdy.impl.ManagerInterface;
import im.hdy.model.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerService {
    @Autowired
    private ManagerInterface managerInterface;
    @Autowired
    private MongoTemplate template;


    public List<Manager> findByUserName(String username) {
        return template.find(new Query(Criteria.where("username").is(username)), Manager.class);
    }

    public void updatePassword(String newPassword, String oldPassword, Manager manager) {
        if (manager.getPassword().equals(oldPassword)) {
            manager.setPassword(newPassword);
        }
        managerInterface.save(manager);
    }

}
