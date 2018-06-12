package im.hdy.controller;

import com.mongodb.util.JSON;
import im.hdy.constant.Constants;
import im.hdy.model.Page;
import im.hdy.model.Talk;
import im.hdy.model.User;
import im.hdy.service.PageService;
import im.hdy.service.TalkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(value = "my")
public class MyController {
    @Autowired
    private PageService service;
    @Autowired
    private TalkService talkService;

    @RequestMapping("getPages")
    //获取自己发表的文章 还未测试 (测试通过)
    public String getPages(@RequestParam(required = false, defaultValue = "0") int page, HttpSession session) {
        User u = (User) session.getAttribute(Constants.CURRENTUSER);
//        u = new User("oZhkF01xg0_wIAvE_F_QVf-Fl31M","http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83erkD5FHBTAic3s9dkbbjqpicxLY8akDGJJicI0VkOxh6ASkziaHmoRicYV7NLnLrpUHSv89hGbc2DBrldQ/132', name='洪德衍'}","洪德衍");
//        u.set_id("5b1df4b925ac299226c1057c");
        List<Page> pagesByUserId = service.findPagesByUserId(u.get_id(), page);
        return JSON.serialize(pagesByUserId);
    }

    @RequestMapping("getLikeAndComment")
    //获取别人对我的评论
    public String getLikeAndComment(HttpSession session, @RequestParam(required = true, defaultValue = "0") int currentPage) {
        User u = (User) session.getAttribute(Constants.CURRENTUSER);
//        u = new User("oZhkF01xg0_wIAvE_F_QVf-Fl31M","http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83erkD5FHBTAic3s9dkbbjqpicxLY8akDGJJicI0VkOxh6ASkziaHmoRicYV7NLnLrpUHSv89hGbc2DBrldQ/132', name='洪德衍'}","洪德衍");
//        u.set_id("5b1df4b925ac299226c1057c");
        //暂未实现
        List<Talk> talkList = talkService.findTalkyByUserIdAndTime(u.get_id(), currentPage);
        return com.alibaba.fastjson.JSON.toJSONString(talkList);
    }
}
