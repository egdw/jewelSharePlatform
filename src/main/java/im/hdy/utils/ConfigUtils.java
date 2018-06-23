package im.hdy.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@PropertySource("classpath:my.properties")
@Component
public class ConfigUtils {
    @Value("${domian.url}")
    private String URL = "http://test.hongdeyan.cn";
    @Value("${wx.appid}")
    private String WX_APPID = "";
    @Value("${wx.appsec}")
    private String WX_APPSECRET = "";
    @Value("${wx.redirectUrl}")
    private String REDIRECT_URL = "";

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getWX_APPID() {
        return WX_APPID;
    }

    public void setWX_APPID(String WX_APPID) {
        this.WX_APPID = WX_APPID;
    }

    public String getWX_APPSECRET() {
        return WX_APPSECRET;
    }

    public void setWX_APPSECRET(String WX_APPSECRET) {
        this.WX_APPSECRET = WX_APPSECRET;
    }

    public String getREDIRECT_URL() {
        return REDIRECT_URL;
    }

    public void setREDIRECT_URL(String REDIRECT_URL) {
        this.REDIRECT_URL = REDIRECT_URL;
    }
}
