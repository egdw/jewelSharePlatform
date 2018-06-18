package im.hdy.controller;

import im.hdy.constant.Constants;
import im.hdy.model.Like;
import im.hdy.model.Page;
import im.hdy.model.User;
import im.hdy.service.PageService;
import im.hdy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 在这里获取所有用户的信息和数据存储到数据库当中去
 */
@Controller
public class IndexController {

    @Autowired
    private PageService pageService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    //获取首页
    public String index(HttpSession session, Map<String, Object> map) {
//        return "index";
//        return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9944a4f6e303f87d&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
//        userService.deleteUser("5b1bc8d725ac57e5a615c75a");
        //临时使用
        User one = userService.findOne("5b1f80dc25acdce3869c8c49");
        session.setAttribute(Constants.CURRENTUSER, one);
        User u = (User) session.getAttribute(Constants.CURRENTUSER);
        //用于判断是否是自己点赞的
        List<Page> pagesRandom = pageService.findPagesRandom();
        for (int i = 0; i < pagesRandom.size(); i++) {
            Page page = pagesRandom.get(i);
            Like likes =
                    page.getLikes();
            if (likes != null) {
                boolean contains = page.getLikes().getUsers().contains(u.get_id());
                page.setLiked(contains);
            }
            User user = page.getUser();
            if (user == null) {
                pageService.delete(page.get_id());
                pagesRandom.remove(i);
            }
        }

        map.put("pages", pagesRandom);
        return "user/yours";
    }

    @RequestMapping("memorize")
    public String memorize(HttpSession session, Map<String, Object> map) {
        User u = (User) session.getAttribute(Constants.CURRENTUSER);
        List<Page> pagesByMemoirs = pageService.findPagesByMemoirs(0);
        //用于判断是否是自己点赞的
        for (int i = 0; i < pagesByMemoirs.size(); i++) {
            Page page = pagesByMemoirs.get(i);
            boolean contains = page.getLikes().getUsers().contains(u.get_id());
            page.setLiked(contains);
        }
        map.put("pages", pagesByMemoirs);
        return "user/memorize";
    }

    public String best(HttpSession session, Map<String, Object> map) {
        User u = (User) session.getAttribute(Constants.CURRENTUSER);
        //用于判断是否是自己点赞的
//        List<Page> pagesByUploadTime = pageService.findPagesByUploadTime(currentPage);
//        for (int i = 0; i < pagesByUploadTime.size(); i++) {
//            Page page = pagesByUploadTime.get(i);
//            boolean contains = page.getLikes().getUsers().contains(u.get_id());
//            page.setLiked(contains);
//        }

        List<Page> best = pageService.findBest(0);
        for (int i = 0; i < best.size(); i++) {
            Page page = best.get(i);
            boolean contains = page.getLikes().getUsers().contains(u.get_id());
            page.setLiked(contains);
        }
        map.put("pages", best);
        return "user/best";
    }

    @RequestMapping(value = "/write", method = RequestMethod.GET)
    public String write() {
        return "user/writing";
    }
}
