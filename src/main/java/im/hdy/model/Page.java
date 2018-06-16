package im.hdy.model;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

@Document
public class Page {
    private String _id;
    private String text;
    private ArrayList<String> imgUrl;

    private ArrayList<Talk> talks;

    @DBRef(lazy = true)
    private Like likes;
    private Date date;
    private Date talkDate;
    @DBRef
    private User user;

    //是否进入回忆录?
    private boolean isInRecall;
    private Date enterInRecallDate;
    public Page() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<String> getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(ArrayList<String> imgUrl) {
        this.imgUrl = imgUrl;
    }

    public ArrayList<Talk> getTalks() {
        return talks;
    }

    public void setTalks(ArrayList<Talk> talks) {
        this.talks = talks;
    }

    public Like getLikes() {
        return likes;
    }

    public void setLikes(Like likes) {
        this.likes = likes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean getIsInRecall() {
        return isInRecall;
    }

    public void setIsInRecall(boolean isInRecall) {
        this.isInRecall = isInRecall;
    }

    public Date getTalkDate() {
        return talkDate;
    }

    public void setTalkDate(Date talkDate) {
        this.talkDate = talkDate;
    }

    @Override
    public String toString() {
        return "Page{" +
                "_id='" + _id + '\'' +
                ", text='" + text + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", talks=" + talks +
                ", likes=" + likes +
                ", date=" + date +
                ", talkDate=" + talkDate +
                ", user=" + user +
                ", isInRecall='" + isInRecall + '\'' +
                '}';
    }

    public Date getEnterInRecallDate() {
        return enterInRecallDate;
    }

    public void setEnterInRecallDate(Date enterInRecallDate) {
        this.enterInRecallDate = enterInRecallDate;
    }
}
