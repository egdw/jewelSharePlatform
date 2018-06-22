package im.hdy.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;

@Document
public class Talk {
    private String _id;
    @DBRef(db = "smallTalk")
    private ArrayList<SmallTalk> talks;
    private Date talkTime;
    private String message;
    private String parentPageId;
    @DBRef
    //当前发言的用户
    private User user;

    public Talk(Date date, String message) {
        this.talks = new ArrayList<>();
        this.talkTime = date;
        this.message = message;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public ArrayList<SmallTalk> getTalks() {
        return talks;
    }

    public void setTalks(ArrayList<SmallTalk> talks) {
        this.talks = talks;
    }

    public Date getTalkTime() {
        return talkTime;
    }

    public void setTalkTime(Date talkTime) {
        talkTime = talkTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getParentPageId() {
        return parentPageId;
    }

    public void setParentPageId(String parentPageId) {
        this.parentPageId = parentPageId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getPageuser() {
        return pageuser;
    }

    public void setPageuser(User pageuser) {
        this.pageuser = pageuser;
    }

    @Override
    public String toString() {
        return "Talk{" +
                "_id='" + _id + '\'' +
                ", talks=" + talks +
                ", talkTime=" + talkTime +
                ", message='" + message + '\'' +
                ", parentPageId='" + parentPageId + '\'' +
                ", user=" + user +
                ", pageuser=" + pageuser +
                '}';
    }
}
