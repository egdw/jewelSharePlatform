package im.hdy.model;

import java.io.Serializable;

public class Signature {

    private String signature = null;
    private String timestamp = null;
    private String nonce = null;
    private String echostr = null;
    private String _id;
    private String appId;

    public Signature() {
    }

    public Signature(String signature, String timestamp, String nonce, String echostr) {
        this.signature = signature;
        this.timestamp = timestamp;
        this.nonce = nonce;
        this.echostr = echostr;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getEchostr() {
        return echostr;
    }

    public void setEchostr(String echostr) {
        this.echostr = echostr;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "Signature{" +
                "signature='" + signature + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", nonce='" + nonce + '\'' +
                ", echostr='" + echostr + '\'' +
                ", _id='" + _id + '\'' +
                ", appId='" + appId + '\'' +
                '}';
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
