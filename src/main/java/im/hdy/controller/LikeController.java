package im.hdy.controller;

import im.hdy.constant.Constants;
import im.hdy.model.Like;
import im.hdy.model.Page;
import im.hdy.model.User;
import im.hdy.service.LikeService;
import im.hdy.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "like")
public class LikeController {

    @Autowired
    private PageService pageService;
    @Autowired
    private LikeService likeService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String addOrCancle(@RequestParam(value = "pageId") String id, HttpSession session) {
        //根据Pageid进行修改增加或取消
        Page one = pageService.getOne(id);
        User u = (User) session.getAttribute(Constants.CURRENTUSER);
        Like likes = one.getLikes();
        likeService.addOrDelNewLikes(likes.get_id(), u.get_id());
        return Constants.successMessage;
    }

}
