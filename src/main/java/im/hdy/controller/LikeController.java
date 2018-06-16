package im.hdy.controller;

import com.alibaba.fastjson.JSON;
import im.hdy.constant.Constants;
import im.hdy.model.Like;
import im.hdy.model.Page;
import im.hdy.model.User;
import im.hdy.service.LikeService;
import im.hdy.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "like")
public class LikeController {

    @Autowired
    private PageService pageService;
    @Autowired
    private LikeService likeService;

    @RequestMapping(method = RequestMethod.POST)
    public String addOrCancle(@RequestParam(value = "pageId") String id, HttpSession session) {
        //根据Pageid进行修改增加或取消
        Page one = pageService.getOne(id);
        User u = (User) session.getAttribute(Constants.CURRENTUSER);
        Like likes = one.getLikes();
        likeService.addOrDelNewLikes(id, likes, u.get_id());
        return Constants.successMessage;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String isLiked(String pageId, HttpSession session) {
        User u = (User) session.getAttribute(Constants.CURRENTUSER);
        Page page = pageService.getOne(pageId);
        boolean contains = page.getLikes().getUsers().contains(u.get_id());
        return JSON.toJSONString(contains);
    }
}
