package im.hdy.controller;

import com.alibaba.fastjson.JSON;
import im.hdy.impl.SignatureInterface;
import im.hdy.model.Signature;
import im.hdy.utils.WeixinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class SignatureController {
    @Autowired
    private SignatureInterface signatureInterface;
    private Logger logger = LoggerFactory.getLogger(SignatureController.class);

    @RequestMapping(value = "sign", method = RequestMethod.GET)
    public String signature(HttpServletRequest request, String requestUrl) {
        Map<String, Object> wxConfig =
                WeixinUtil.getWxConfig(requestUrl);
//        Set<Map.Entry<String, Object>> entries =
//                wxConfig.entrySet();
//        Iterator<Map.Entry<String, Object>> iterator =
//                entries.iterator();
//        while (iterator.hasNext()) {
//            Map.Entry<String, Object> next = iterator.next();
//            logger.info(next.getKey() + " " + next.getValue());
//        }
//        List<Signature> all = signatureInterface.findAll();
//        if (all != null && all.size() > 0) {
//            Signature signOld = all.get(0);
//            return JSON.toJSONString(signOld);
//        }
//        ret.put("appId", appId);
//        ret.put("timestamp", timestamp);
//        ret.put("nonceStr", nonceStr);
//        ret.put("signature", signature);
        Signature signature = new Signature();
        signature.setAppId((String) wxConfig.get("appId"));
        signature.setTimestamp((String) wxConfig.get("timestamp"));
        signature.setNonce((String) wxConfig.get("nonceStr"));
        signature.setSignature((String) wxConfig.get("signature"));
        return JSON.toJSONString(wxConfig);
    }
}
