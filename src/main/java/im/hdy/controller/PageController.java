package im.hdy.controller;

import com.alibaba.fastjson.JSON;
import im.hdy.constant.Constants;
import im.hdy.impl.UserInterface;
import im.hdy.model.Like;
import im.hdy.model.Page;
import im.hdy.model.User;
import im.hdy.service.LikeService;
import im.hdy.service.PageService;
import net.coobird.thumbnailator.Thumbnails;
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
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(value = "page")
public class PageController {


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
    public String get(String pageId) {
        Page one = pageService.findOne(pageId);
        return JSON.toJSONString(one);
    }


    /**
     * @return
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    @ResponseBody
    public String get() {
        List<Page> pagesRandom = pageService.findPagesRandom();
        return JSON.toJSONString(pagesRandom);
    }

    @RequestMapping(value = "best", method = RequestMethod.GET)
    @ResponseBody
    public String getVeryBest(@RequestParam(required = false, defaultValue = "0") int currentPage) {
        List<Page> pagesByUploadTime = pageService.findPagesByUploadTime(currentPage);
        return JSON.toJSONString(pagesByUploadTime);
    }

    @RequestMapping(value = "memoirs", method = RequestMethod.GET)
    @ResponseBody
    public String getMemoirs(@RequestParam(required = false, defaultValue = "0") int currentPage) {
        List<Page> pagesByMemoirs = pageService.findPagesByMemoirs(currentPage);
        return JSON.toJSONString(pagesByMemoirs);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    //!!! 这里需要Xss防御
    public String add(@RequestParam(required = true) String text, @RequestParam(required = true) MultipartFile file, HttpServletRequest request, HttpSession session) {
        //进行文件的上传
//        pageService.addPage();
        //图片保存
        File saveFile = SaveFile(request, file);
        Like like = likeService.saveLikes(new Like());
        //获取到当前用户的id
        User u = (User) session.getAttribute(Constants.CURRENTUSER);
//        String userId = u.get_id();
        Page page = new Page();
//        User user = userInterface.findOne(userId);
        page.setUser(u);
        page.setText(text);
        page.setImgUrl(saveFile.getName());
        page.setDate(new Date());
        page.setLikes(like);
        pageService.addPage(page);
        return Constants.successMessage;
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
