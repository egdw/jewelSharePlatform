package im.hdy.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Like {
    private String _id;
    private String userId;

    private String pageId;

    public Like(String userId, String pageId) {
        this.userId = userId;
        this.pageId = pageId;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    @Override
    public String toString() {
        return "Like{" +
                "_id='" + _id + '\'' +
                ", userId='" + userId + '\'' +
                ", pageId='" + pageId + '\'' +
                '}';
    }
}
