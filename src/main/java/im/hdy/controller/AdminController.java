package im.hdy.controller;

import im.hdy.service.LikeService;
import im.hdy.service.PageService;
import im.hdy.service.TalkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@RequestMapping(value = "admin")
@Controller
public class AdminController {

    @Autowired
    private LikeService likeService;
    @Autowired
    private TalkService talkService;
    @Autowired
    private PageService pageService;

    @RequestMapping(value = "deletecomment", method = RequestMethod.DELETE)
    //删除评论
    public void deleteComment(String talkId) {
        talkService.delete(talkId);
    }

    @RequestMapping(value = "login")
    //后台登录
    public void login(@RequestParam(required = true) String username, @RequestParam(required = true) String password) {
        System.out.println(username);
        System.out.println(password);
        if ("hzkj".equals(username) && "hzkjadmin".equals(password)) {
            //说明密码正确
        }
    }

    @RequestMapping(value = "deletePage", method = RequestMethod.DELETE)
    //删除文章
    public void deletePage(String pageId, HttpSession session) {
        pageService.delete(pageId);
    }

    @RequestMapping(method = RequestMethod.GET)
    //管理首页
    public String admin() {
        System.out.println("admin/admin");
        return "admin/admin";
    }


}
