package im.hdy.constant;

import com.alibaba.fastjson.JSON;
import im.hdy.model.Message;

import java.util.Random;

public class Constants {

    //图片文件保存路径
    public static String fileSaveUrl = null;
    //每页的数量
    public static final int PAGENUM = 10;
    //session当中的用户id
    public static final String CURRENTUSER = "currentUser";
    public static final String errorMessage = JSON.toJSONString(new Message("500", "程序出现错误,请重试!"));

    public static final String successMessage = JSON.toJSONString(new Message("200", "complete"));

    public static final String LOGINURL = "http://test.hongdeyan.cn/jewel/url";

    public static final Random RANDOM = new Random();
}
