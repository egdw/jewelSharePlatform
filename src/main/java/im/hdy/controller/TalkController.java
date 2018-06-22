package im.hdy.controller;

import com.mongodb.util.JSON;
import com.sun.tools.internal.jxc.ap.Const;
import im.hdy.constant.Constants;
import im.hdy.impl.SmallTalkService;
import im.hdy.impl.TalkInterface;
import im.hdy.model.Page;
import im.hdy.model.Talk;
import im.hdy.model.User;
import im.hdy.service.PageService;
import im.hdy.service.TalkService;
import im.hdy.utils.HttpsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("talk")
public class TalkController {

    @Autowired
    private TalkService talkService;
    @Autowired
    private PageService pageService;
    @Autowired
    private SmallTalkService smallTalkService;
    private Logger logger = LoggerFactory.getLogger(TalkController.class);
    @Autowired
    private TalkInterface talkInterface;

    @RequestMapping(value = "addSmallPage", method = RequestMethod.POST)
    public String addTalk(String talk_id, String talk_message, @RequestParam(required = false) String destSmallTalkId, HttpSession session) {
        User u = (User) session.getAttribute(Constants.CURRENTUSER);
        Talk one = talkService.findOne(talk_id);
        if (one != null) {
            Talk talk = new Talk(new Date(), talk_message);
            talk.setUser(u);
            talk.setParentPageId(pageId);
            talkService.save(talk);
            boolean add = one.getTalks().add(talk);
            if (add) {
                return Constants.successMessage;
            } else {
                return Constants.errorMessage;
            }
        }
        return Constants.errorMessage;
    }


    @RequestMapping(method = RequestMethod.GET)
    public String getTalk(@RequestParam(required = false) String pageId, @RequestParam(required = false) String talkId) {
        if (pageId != null && !pageId.isEmpty()) {
            Page one = pageService.findOne(pageId);
            if (one == null) {
                return Constants.errorMessage;
            }
            ArrayList<Talk> talks = one.getTalks();
            return com.alibaba.fastjson.JSON.toJSONString(talks);
        }
        if (talkId != null && !talkId.isEmpty()) {
            Talk talk = talkService.findOne(talkId);
            if (talk == null) {
                return Constants.errorMessage;
            }
            return com.alibaba.fastjson.JSON.toJSONString(talk.getTalks());
        }
        return Constants.errorMessage;
    }


    @RequestMapping(value = "addBigPage", method = RequestMethod.POST)
    public String addTalkPage(@RequestParam(required = true) String pageId, @RequestParam(required = true) String talk_message, HttpSession session) {
        if (RedisUtils.isExist(Constants.TALK_SEND_TIME_NAME)) {
            //防止请求频率太快
            return Constants.tooQuickMessage;
        }
        //过滤各种非法字符
        talk_message = HtmlUtils.getNoHTMLString(talk_message, 120);
        Page one = pageService.findOne(pageId);
        User u = (User) session.getAttribute(Constants.CURRENTUSER);
        ArrayList<Talk> talks = one.getTalks();
        if (talks == null) {
            talks = new ArrayList<>();
        }
        Talk talk = new Talk(new Date(), talk_message);
        talk.setUser(u);
        talk.setParentPageId(pageId);
        talk.setPageuser(one.getUser());
        talk.setTalkTime(new Date());
        Talk save = talkService.save(talk);
        talks.add(save);
        one.setTalks(talks);
        pageService.addPage(one);

        RedisUtils.setAndExpire(Constants.TALK_SEND_TIME_NAME, " ", Constants.TALK_SEND_TIME);
        return Constants.successMessage;
    }

    @RequestMapping("test")
    public String test() {
        List<Talk> all =
                talkInterface.findAll();
        for (int i = 0; i < all.size(); i++) {
            Talk talk = all.get(i);
            User pageUser = talk.getPageuser();
//            if (pageUser == null) {
            String parentPageId = talk.getParentPageId();
            Page one = pageService.findOne(parentPageId);
            talk.setPageuser(one.getUser());
            all.set(i, talk);
            talkService.save(talk);
//            }
        }
        return null;
    }
//    @RequestMapping(method = RequestMethod.DELETE)
//    //删除评论
//    public void delComment(String talkId) {
//        talkService.delete(talkId);
//    }

}
