package im.hdy.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class SmallTalk {
    private String _id;
    @DBRef
    //当前发言的用户
    private User user;
    //向某个发言的用户回复
    @DBRef
    private User destUser;
    private Date talkTime;
    private String message;

    public SmallTalk(User user, User destUser, Date talkTime, String message) {
        this.user = user;
        this.destUser = destUser;
        this.talkTime = talkTime;
        this.message = message;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getDestUser() {
        return destUser;
    }

    public void setDestUser(User destUser) {
        this.destUser = destUser;
    }

    public Date getTalkTime() {
        return talkTime;
    }

    public void setTalkTime(Date talkTime) {
        this.talkTime = talkTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SmallTalk{" +
                "_id='" + _id + '\'' +
                ", user=" + user +
                ", destUser=" + destUser +
                ", talkTime=" + talkTime +
                ", message='" + message + '\'' +
                '}';
    }
}
