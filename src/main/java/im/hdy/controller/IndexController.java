package im.hdy.controller;

import im.hdy.constant.Constants;
import im.hdy.model.Banner;
import im.hdy.model.Like;
import im.hdy.model.Page;
import im.hdy.model.User;
import im.hdy.service.BannerService;
import im.hdy.service.PageService;
import im.hdy.service.UserService;
import im.hdy.utils.HttpsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 在这里获取所有用户的信息和数据存储到数据库当中去
 */
@Controller
public class IndexController {
    private Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private PageService pageService;

    @Autowired
    private UserService userService;

    @Autowired
    private BannerService bannerService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    //获取首页
    public String index(HttpSession session, Map<String, Object> map) {
//        return "index";
//        return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9944a4f6e303f87d&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
//        userService.deleteUser("5b1bc8d725ac57e5a615c75a");
//        User one = userService.findOne("5b1f80dc25acdce3869c8c49");
//        session.setAttribute(Constants.CURRENTUSER, one);
//        User u = (User) session.getAttribute(Constants.CURRENTUSER);
//        //用于判断是否是自己点赞的
//        List<Page> pagesRandom = pageService.findPagesRandom();
//        for (int i = 0; i < pagesRandom.size(); i++) {
//            Page page = pagesRandom.get(i);
//            Like likes =
//                    page.getLikes();
//            if (likes != null) {
//                LinkedList<String> users = likes.getUsers();
//                if (likes != null && users != null && users.size() > 0 && u != null && u.get_id() != null) {
//                    boolean contains = users.contains(u.get_id());
//                    page.setUserLiked(contains);
//                }
//            }
//            User user = page.getUser();
//            if (user == null) {
//                pageService.delete(page.get_id());
//                pagesRandom.remove(i);
//            }
//        }
//
        List<Banner> all = bannerService.findAll();
        map.put("banners", all);
//        map.put("pages", pagesRandom);
        return "user/yours";
    }

    @RequestMapping("memorize")
    public String memorize(HttpSession session, Map<String, Object> map) {
//        User u = (User) session.getAttribute(Constants.CURRENTUSER);
//        List<Page> pagesByMemoirs = pageService.findPagesByMemoirs(0);
//        //用于判断是否是自己点赞的
//        for (int i = 0; i < pagesByMemoirs.size(); i++) {
//            Page page = pagesByMemoirs.get(i);
//            Like likes =
//                    page.getLikes();
//            LinkedList<String> users = likes.getUsers();
//            if (likes != null && users != null && users.size() > 0 && u != null && u.get_id() != null) {
//                boolean contains = users.contains(u.get_id());
//                page.setUserLiked(contains);
//            }
//        }
        List<Banner> all = bannerService.findAll();
        map.put("banners", all);
//        map.put("pages", pagesByMemoirs);
        return "user/memorize";
    }

    @RequestMapping("best")
    public String best(HttpSession session, Map<String, Object> map) {
//        User u = (User) session.getAttribute(Constants.CURRENTUSER);
//        //用于判断是否是自己点赞的
//        logger.info("最佳文章获取中..");
//        List<Page> best = pageService.findBest(0);
//        for (int i = 0; i < best.size(); i++) {
//            Page page = best.get(i);
//            Like likes =
//                    page.getLikes();
//            LinkedList<String> users = likes.getUsers();
//            if (likes != null && users != null && users.size() > 0 && u != null && u.get_id() != null) {
//                boolean contains = users.contains(u.get_id());
//                page.setUserLiked(contains);
//            }
//        }
        List<Banner> all = bannerService.findAll();
        map.put("banners", all);
//        logger.info("最佳文章获取" + best);
//        map.put("pages", best);
        return "user/best";
    }

    @RequestMapping(value = "/write", method = RequestMethod.GET)
    public String write() {
        return "user/writing";
    }


    @RequestMapping(value = "me", method = RequestMethod.GET)
    public String me(@RequestParam(required = false, defaultValue = "0") int page, HttpSession session, Map<String, Object> maps) {
//        userService.deleteUser("5b1bc8d725ac57e5a615c75a");
//        临时使用
//        User one = userService.findOne("5b1f80dc25acdce3869c8c49");
//        session.setAttribute(Constants.CURRENTUSER, one);
//        User u = (User) session.getAttribute(Constants.CURRENTUSER);
//        List<Page> pagesByUserId = pageService.findPagesByUserId(u.get_id(), page);
//        return JSON.serialize(pagesByUserId);
//        session.setAttribute("pages", pagesByUserId);
//        maps.put("pages", pagesByUserId);
//        logger.info("获取到的我发布的文章:" + pagesByUserId);
        return "user/me";
    }

    @RequestMapping(value = "me_message", method = RequestMethod.GET)
    public String me_message(HttpSession session) {
//        User one = userService.findOne("5b1f80dc25acdce3869c8c49");
//        session.setAttribute(Constants.CURRENTUSER, one);
//        session.setAttribute(Constants.CURRENTUSER, one);

        return "user/me-message";
    }

    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public String detail(@RequestParam(required = false) String pageId, Map<String, Object> maps, HttpSession session) {
        User u = (User) session.getAttribute(Constants.CURRENTUSER);
        Page one = pageService.findOne(pageId);
        if (one != null) {
            Like like = one.getLikes();
            if (like != null) {
                LinkedList<String> users = like.getUsers();
                if (users != null && users.size() > 0) {
                    boolean contains = users.contains(u.get_id());
                    if (contains) {
                        one.setUserLiked(true);
                    }
                }
            }
            maps.put("page", one);
            if (u.get_id().equals(one.getUser().get_id())) {
                //判断是不是作者自己,如果是的话:
                maps.put(Constants.ISCANDEL, "true");
            }
//            logger.info("获取到的小对话"+one.getTalks().get(0).getTalks());
            return "user/details";
        } else {
            return "error";
        }
    }

}
