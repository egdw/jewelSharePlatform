package im.hdy.controller;

import im.hdy.model.Like;
import im.hdy.model.Page;
import im.hdy.model.User;
import im.hdy.service.LikeService;
import im.hdy.service.PageService;
import im.hdy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

@RequestMapping("test")
@RestController
public class TestController {
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private LikeService likeService;
    @Autowired
    private PageService pageService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "add")
    public void addPages() {
        logger.info("add");
        String[] users = new String[]{"5b1df4b925ac299226c1057c", "5b1f7adc25ac4d97f54948de", "5b1f80dc25acdce3869c8c49"};

        for (int i = 0; i < 20; i++) {
            Page page = new Page();
            boolean isinrecall = back();
            page.setInRecall(isinrecall);
            page.setHidden(back());
            page.setDate(new Date());
            page.setText("这是测试内容这是测试内容这是测试内容这是测试内容这是测试内容这是测试内容这是测试内容这是测试内容");
            ArrayList<String> images = new ArrayList<>();
            images.add("test.png");
            images.add("test2.png");
            images.add("test3.png");
            page.setImgUrl(images);
            if(isinrecall){
                page.setEnterInRecallDate(new Date());
                String user = users[i % 3];
                logger.info("获取到user"+user);
                User one = userService.findOne(user);
                logger.info("查找到的用户",one);
                if(one == null){
                    continue;
                }
                page.setUser(one);
            }
            Page addPage = pageService.addPage(page);
//            page.setLikes(like);
            Like like = new Like();
            LinkedList<String> likeUsers = like.getUsers();
            for (int j = 0; j < users.length; j++) {
                likeUsers.add(users[j]);
            }
            like.setUsers(likeUsers);
            like.setTotal(likeUsers.size());
            Like likes = likeService.saveLikes(like, addPage.get_id());
            addPage.setLikes(likes);

//            likeService.saveLikes()
        }



    }


    public static boolean back(){
        Random random = new Random();
        int i = random.nextInt(2);
        if(i == 0){
            return true;
        }else{
            return false;
        }
    }

}


