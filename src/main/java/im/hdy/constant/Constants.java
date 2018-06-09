package im.hdy.constant;

import com.alibaba.fastjson.JSON;
import im.hdy.model.Message;

public class Constants {

    //图片文件保存路径
    public static String fileSaveUrl = null;
    //每页的数量
    public static int PAGENUM = 10;
    //session当中的用户id
    public static String CURRENTUSER = "currentUser";
    public static String errorMessage = JSON.toJSONString(new Message("500", "程序出现错误,请重试!"));

    public static String successMessage = JSON.toJSONString(new Message("200", "complete"));
}
