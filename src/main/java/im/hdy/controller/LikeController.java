package im.hdy.controller;

import im.hdy.model.Like;
import im.hdy.model.Page;
import im.hdy.service.LikeService;
import im.hdy.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
@RequestMapping(value = "like")
public class LikeController {

    @Autowired
    private PageService pageService;
    @Autowired
    private LikeService likeService;

    @RequestMapping(method = RequestMethod.POST)
    public void addOrCancle(@RequestParam(value = "pageId") String id) {
        //根据Pageid进行修改增加或取消
        Page one = pageService.getOne(id);
        ArrayList<Like> likes = one.getLikes();
        Like like = new Like("", id);
        likes.add(like);
        pageService.update(one);
    }

}
