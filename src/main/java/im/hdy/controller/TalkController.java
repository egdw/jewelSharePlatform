package im.hdy.controller;

import com.mongodb.util.JSON;
import com.sun.tools.internal.jxc.ap.Const;
import im.hdy.constant.Constants;
import im.hdy.model.Page;
import im.hdy.model.SmallTalk;
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

@RestController
@RequestMapping("talk")
public class TalkController {

    @Autowired
    private TalkService talkService;
    @Autowired
    private PageService pageService;

    @RequestMapping(method = RequestMethod.POST)
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


    @RequestMapping(method = RequestMethod.PUT)
    public String addTalkPage(@RequestParam(required = true) String pageId, @RequestParam(required = true) String talk_message, HttpSession session) {
        Page one = pageService.findOne(pageId);
        User u = (User) session.getAttribute(Constants.CURRENTUSER);
        ArrayList<Talk> talks = one.getTalks();
        if (talks == null) {
            talks = new ArrayList<>();
        }
        Talk talk = new Talk(new Date(), talk_message);
        talk.setUser(u);
        talk.setParentPageId(pageId);
        talk.setTalkTime(new Date());
        Talk save = talkService.save(talk);
        talks.add(save);
        pageService.addPage(one);
        return Constants.successMessage;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    //删除评论
    public void delComment(String talkId) {
        talkService.delete(talkId);
    }

}
