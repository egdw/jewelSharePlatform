package im.hdy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 在这里获取所有用户的信息和数据存储到数据库当中去
 */
@Controller
public class IndexController {


    @RequestMapping(value = "/",method = RequestMethod.GET)
    //获取首页
    public String index(){
//        return "index";
//        return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9944a4f6e303f87d&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
        return "user/yours";
    }
}
