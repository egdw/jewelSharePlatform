package im.hdy.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import im.hdy.constant.Constants;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class WeixinUtil {

    /**
     * 方法名：getWxConfig</br>
     * 详述：获取微信的配置信息 </br>
     * 开发人员：souvc  </br>
     * 创建时间：2016-1-5  </br>
     *
     * @return 说明返回值含义
     */
    public static Map<String, Object> getWxConfig(String requestUrl) {
        Map<String, Object> ret = new HashMap<String, Object>();

        String appId = Constants.WX_APPID; // 必填，公众号的唯一标识
        String secret = Constants.WX_APPSECRET;

//        String requestUrl = requestUrl;
        String access_token = "";
        String jsapi_ticket = "";
        String timestamp = Long.toString(System.currentTimeMillis() / 1000); // 必填，生成签名的时间戳
        String nonceStr = UUID.randomUUID().toString(); // 必填，生成签名的随机串
        access_token = RedisUtils.get("access_token");
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + secret;
        JSONObject json = null;
        if (access_token != null && !access_token.isEmpty()) {

        } else {
            HttpsUtils.httpsRequestToString(url, "GET", null);
            json = JSON.parseObject(HttpsUtils.httpsRequestToString(url, "GET", null));
            if (json != null) {
                //要注意，access_token需要缓存
                access_token = json.getString("access_token");
                RedisUtils.setAndExpire("access_token", access_token, 60 * 7);
            }
        }
        url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi";
        json = JSON.parseObject(HttpsUtils.httpsRequestToString(url, "GET", null));
        if (json != null) {
            jsapi_ticket = json.getString("ticket");
        }
        String signature = "";
        // 注意这里参数名必须全部小写，且必须有序
        String sign = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonceStr + "&timestamp=" + timestamp + "&url=" + requestUrl;
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(sign.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ret.put("appId", appId);
        ret.put("timestamp", timestamp);
        ret.put("nonceStr", nonceStr);
        ret.put("signature", signature);
        return ret;
    }


    /**
     * 方法名：byteToHex</br>
     * 详述：字符串加密辅助方法 </br>
     * 开发人员：souvc  </br>
     * 创建时间：2016-1-5  </br>
     *
     * @param hash
     * @return 说明返回值含义
     */
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;

    }
}
