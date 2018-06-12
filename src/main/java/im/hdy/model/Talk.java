package im.hdy.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;

@Document
public class Talk {
    private String _id;
    private ArrayList<Talk> talks;
    private Date talkTime;
    private String message;
    private String parentPageId;
    @DBRef
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

    public ArrayList<Talk> getTalks() {
        return talks;
    }

    public void setTalks(ArrayList<Talk> talks) {
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


    @Override
    public String toString() {
        return "Talk{" +
                "_id='" + _id + '\'' +
                ", talks=" + talks +
                ", TalkTime=" + talkTime +
                ", message='" + message + '\'' +
                ", parentPageId='" + parentPageId + '\'' +
                ", user=" + user +
                '}';
    }
}
