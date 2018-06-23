package im.hdy.constant;

import com.alibaba.fastjson.JSON;
import im.hdy.model.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.Resources;
import java.util.Random;

@Component
public class Constants {
    public static String URL = "http://test.hongdeyan.cn";

//    @Value("${domian.url}")
//    public void setURL(String URL) {
//        System.err.println("注入数据" + URL);
//        Constants.URL = URL;
//    }

    //临时保存用户之前访问的url地址
    public static String PRE_URL = "PRE_URL";

    //图片文件保存路径
    public static String fileSaveUrl = null;
    //每页的数量
    public static final int PAGENUM = 10;
    //session当中的用户id
    public static final String CURRENTUSER = "currentUser";
    public static final String errorMessage = JSON.toJSONString(new Message("500", "程序出现错误,请重试!"));

    public static final String tooQuickMessage = JSON.toJSONString(new Message("501", "请求速度太快,请等待一分钟后重试"));

    public static final String formatErrorMessage = JSON.toJSONString(new Message("502", "文本格式有误,请重试"));

    public static final String successMessage = JSON.toJSONString(new Message("200", "complete"));

    public static final String LOGINURL = "http://test.hongdeyan.cn/jewel/url";

    public static final Random RANDOM = new Random();

    //文章发送间隔
    public static final String PAGE_SEND_TIME_NAME = "PAGE_SEND_TIME_NAME";
    public static final int PAGE_SEND_TIME = 60;

    //评论发送间隔
    public static final String TALK_SEND_TIME_NAME = "TALK_SEND_TIME_NAME";
    public static final int TALK_SEND_TIME = 60;


    private static final String signature = null;
    private static final String timestamp = null;
    private static final String nonce = null;
    private static final String echostr = null;
    public static String WX_APPID = "wx04d5544324fa686e";
    public static String WX_APPSECRET = "ac7197726c8715aec46729aa0b5b64a6";

//    @Value("${wx.appid}")
//    public void setWxAppid(String wxAppid) {
//        WX_APPID = wxAppid;
//    }
//
//    @Value("${wx.appsec}")
//    public void setWxAppsecret(String wxAppsecret) {
//        WX_APPSECRET = wxAppsecret;
//        Constants.redirectUrL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + Constants.WX_APPID + "&redirect_uri=" + Constants.URL + "/jewel/url&response_type=code&scope=snsapi_userinfo&state=STATE&connect_redirect=1#wechat_redirect";
//    }


    public static String redirectUrL = "";

    //判断现在是否是管理员身份
    public static final String ISADMIN = "ISADMIN";
    //判断现在是否能够删除
    public static final String ISCANDEL = "ISCANDEL";
}
