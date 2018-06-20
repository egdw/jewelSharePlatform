package im.hdy.controller;

import com.alibaba.fastjson.JSON;
import com.oracle.tools.packager.Log;
import im.hdy.constant.Constants;
import im.hdy.impl.UserInterface;
import im.hdy.model.Like;
import im.hdy.model.Page;
import im.hdy.model.User;
import im.hdy.service.LikeService;
import im.hdy.service.PageService;
import im.hdy.utils.HtmlUtils;
import im.hdy.utils.RedisUtils;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping(value = "page")
public class PageController {

    private Logger log = LoggerFactory.getLogger(PageController.class);
    @Autowired
    private PageService pageService;
    @Autowired
    private UserInterface userInterface;
    @Autowired
    private LikeService likeService;

    /**
     * 单个pageId的数据
     *
     * @param pageId
     * @return
     */
    @RequestMapping(value = "get", method = RequestMethod.GET)
    @ResponseBody
    public String get(String pageId, HttpSession session) {
        Page one = pageService.findOne(pageId);
        User u = (User) session.getAttribute(Constants.CURRENTUSER);
        boolean contains = one.getLikes().getUsers().contains(u.get_id());
        one.setLiked(contains);
        return JSON.toJSONString(one);
    }


    /**
     * @return
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    @ResponseBody
    public String get(HttpSession session) {
        User one = userInterface.findOne("5b1f80dc25acdce3869c8c49");
        session.setAttribute(Constants.CURRENTUSER, one);


        User u = (User) session.getAttribute(Constants.CURRENTUSER);
        //用于判断是否是自己点赞的
        List<Page> pagesRandom = pageService.findPagesRandom();
        for (int i = 0; i < pagesRandom.size(); i++) {
            Page page = pagesRandom.get(i);
            Like likes =
                    page.getLikes();
            LinkedList<String> users = likes.getUsers();
            if (likes != null && users != null && users.size() > 0 && u != null && u.get_id() != null) {
                boolean contains = users.contains(u.get_id());
                page.setLiked(contains);
            }
            User user = page.getUser();
            if (user == null) {
                pageService.delete(page.get_id());
                pagesRandom.remove(i);
            }
        }
        return JSON.toJSONString(pagesRandom);
    }


    @RequestMapping(value = "best", method = RequestMethod.GET)
    @ResponseBody
    public String getVeryBest(@RequestParam(required = false, defaultValue = "0") int currentPage, HttpSession session) {
        User u = (User) session.getAttribute(Constants.CURRENTUSER);
        //用于判断是否是自己点赞的
        List<Page> best = pageService.findBest(0);
        for (int i = 0; i < best.size(); i++) {
            Page page = best.get(i);
            Like likes =
                    page.getLikes();
            LinkedList<String> users = likes.getUsers();
            if (likes != null && users != null && users.size() > 0 && u != null && u.get_id() != null) {
                boolean contains = users.contains(u.get_id());
                page.setLiked(contains);
            }
        }
        return JSON.toJSONString(best);
    }

    @RequestMapping(value = "memoirs", method = RequestMethod.GET)
    @ResponseBody
    public String getMemoirs(@RequestParam(required = false, defaultValue = "0") int currentPage, HttpSession session) {
        User u = (User) session.getAttribute(Constants.CURRENTUSER);
        List<Page> pagesByMemoirs = pageService.findPagesByMemoirs(0);
        //用于判断是否是自己点赞的
        for (int i = 0; i < pagesByMemoirs.size(); i++) {
            Page page = pagesByMemoirs.get(i);
            Like likes =
                    page.getLikes();
            LinkedList<String> users = likes.getUsers();
            if (likes != null && users != null && users.size() > 0 && u != null && u.get_id() != null) {
                boolean contains = users.contains(u.get_id());
                page.setLiked(contains);
            }
        }
        return JSON.toJSONString(pagesByMemoirs);
    }

//    @RequestMapping(method = RequestMethod.POST)
//    public String add2(@RequestParam(required = true) String text, @RequestParam(required = false) MultipartFile[] file, HttpServletRequest request, HttpSession session, String isHidden) {
//        System.out.println(isHidden);
//        return "";
//    }

    @RequestMapping(method = RequestMethod.POST)
//    @ResponseBody
    //!!! 这里需要Xss防御(完成)
    public String add(@RequestParam(required = true) String text, @RequestParam(required = false) MultipartFile[] file, HttpServletRequest request, HttpSession session, String isHidden) {
        if (RedisUtils.isExist(Constants.PAGE_SEND_TIME_NAME)) {
            //防止请求频率太快
            return Constants.tooQuickMessage;
        }

        //过滤所有的html代码
        text = HtmlUtils.getNoHTMLString(text, 300);
        log.info("过滤掉所有的html代码", text);
        //进行文件的上传
//        pageService.addPage();
        //图片保存
        File saveFile = SaveFile(request, file);
        Like like = likeService.saveLikes(new Like());
        //获取到当前用户的id
        User u = (User) session.getAttribute(Constants.CURRENTUSER);
//        String userId = u.get_id();
        Page page = new Page();
        page.setIsInRecall(false);
        page.setInRecall(false);
//        User user = userInterface.findOne(userId);
        page.setUser(u);
        page.setText(text);
        page.setImgUrl(saveFile.getName());
        page.setDate(new Date());
//        page.setLikes(like);
        Page addPage = pageService.addPage(page);
        Like like = likeService.saveLikes(new Like(), addPage.get_id());
        addPage.setLikes(like);
        Page addPage1 = pageService.addPage(page);
        RedisUtils.setAndExpire(Constants.PAGE_SEND_TIME_NAME, " ", Constants.PAGE_SEND_TIME);
        log.info("添加的内容" + addPage1);
        return "redirect:/";
//        return Constants.successMessage;
    }


    private File SaveFile(HttpServletRequest request, MultipartFile file) {
        //获取到文件上传的地址
        StringBuilder sb = null;
        if (Constants.fileSaveUrl == null) {
            sb = new StringBuilder()
                    .append(request.getSession().getServletContext()
                            .getRealPath("/")).append("upload");
            Constants.fileSaveUrl = sb.toString();
        }
        //通过uuid生成图片名称
        String uuid_fileName = UUID.randomUUID().toString();

        File saveFile = new File(Constants.fileSaveUrl, uuid_fileName + ".jpg");
        try {
            //保存压缩图
            Thumbnails.of(file.getInputStream()).scale(1f)
                    .outputQuality(0.5f)
                    .toFile(saveFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return saveFile;
    }

}
