package im.hdy.controller;

import com.mongodb.util.JSON;
import im.hdy.constant.Constants;
import im.hdy.model.Page;
import im.hdy.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "my")
public class MyController {
    @Autowired
    private PageService service;

    @RequestMapping("getPages")
    @ResponseBody
    public String getPages(int page, HttpSession session) {
        List<Page> pagesByUserId = service.findPagesByUserId(Constants.CURRENTUSER, page);
        return JSON.serialize(pagesByUserId);
    }

    @RequestMapping("getLikeAndComment")
    public void getLikeAndComment() {
        //暂未实现
    }
}
