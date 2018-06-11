package im.hdy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "comment")
public class CommentController {

    @RequestMapping(method = RequestMethod.POST)
    //添加评论
    public void addComment() {

    }

    @RequestMapping(method = RequestMethod.DELETE)
    //删除评论
    public void delComment() {

    }
    
}
