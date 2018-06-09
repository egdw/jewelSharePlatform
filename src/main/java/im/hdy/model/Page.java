package im.hdy.model;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document
public class Page {
    private String _id;
    private String text;
    private String imgUrl;

    private ArrayList<Talk> talks;

    private ArrayList<Like> likes;

    @DBRef
    private User user;

    //是否进入回忆录?
    private String isInRecall;

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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public ArrayList<Talk> getTalks() {
        return talks;
    }

    public void setTalks(ArrayList<Talk> talks) {
        this.talks = talks;
    }

    public ArrayList<Like> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<Like> likes) {
        this.likes = likes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getIsInRecall() {
        return isInRecall;
    }

    public void setIsInRecall(String isInRecall) {
        this.isInRecall = isInRecall;
    }

    @Override
    public String toString() {
        return "Page{" +
                "_id='" + _id + '\'' +
                ", text='" + text + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", talks=" + talks +
                ", likes=" + likes +
                ", user=" + user +
                ", isInRecall='" + isInRecall + '\'' +
                '}';
    }
}
