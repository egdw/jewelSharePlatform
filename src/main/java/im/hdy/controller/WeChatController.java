package im.hdy.controller;

import im.hdy.impl.SignatureInterface;
import im.hdy.model.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/wechat")
public class WeChatController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // URL:   http://www.xxxx.com/wechat/
    // Token: 此处TOKEN即为微信接口配置信息的Token

    private String TOKEN = "wechat";
    @Autowired
    private SignatureInterface signatureInterface;

    /**
     * 验证微信后台配置的服务器地址有效性
     * <p>
     * 接收并校验四个请求参数
     *
     * @param signature 微信加密签名
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param echostr   随机字符串
     * @return echostr
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String checkName(@RequestParam(name = "signature") String signature,
                            @RequestParam(name = "timestamp") String timestamp,
                            @RequestParam(name = "nonce") String nonce,
                            @RequestParam(name = "echostr") String echostr) {

        logger.info("微信-开始校验签名");
        logger.info("收到来自微信的 echostr 字符串:{}", echostr);

//        加密/校验流程如下:
//        1. 将token、timestamp、nonce三个参数进行字典序排序
//        2. 将三个参数字符串拼接成一个字符串进行sha1加密
//        3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信

        // 1.排序
        String sortString = sort(TOKEN, timestamp, nonce);
        // 2.sha1加密
        String myString = sha1(sortString);
        // 3.字符串校验
        if (myString != null && myString != "" && myString.equals(signature)) {
            logger.info("微信-签名校验通过");
            //如果检验成功原样返回echostr，微信服务器接收到此输出，才会确认检验完成。
            logger.info("回复给微信的 echostr 字符串:{}", echostr);
            Signature sign = new Signature();
            sign.setEchostr(echostr);
            sign.setNonce(nonce);
            sign.setSignature(signature);
            sign.setTimestamp(timestamp);
            List<Signature> all =
                    signatureInterface.findAll();
            if (all != null && all.size() > 0) {
                Signature signOld = all.get(0);
                String id = signOld.get_id();
                sign.set_id(id);
            }
            signatureInterface.save(sign);
            return echostr;
        } else {
            logger.error("微信-签名校验失败");
            return "";
        }
    }

    /**
     * 排序方法
     *
     * @param token     Token
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @return
     */
    public String sort(String token, String timestamp, String nonce) {
        String[] strArray = {token, timestamp, nonce};
        Arrays.sort(strArray);
        StringBuilder sb = new StringBuilder();
        for (String str : strArray) {
            sb.append(str);
        }

        return sb.toString();
    }

    /**
     * 将字符串进行sha1加密
     *
     * @param str 需要加密的字符串
     * @return 加密后的内容
     */
    public String sha1(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();
            // 创建 16进制字符串
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
