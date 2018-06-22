package im.hdy.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;

//@Document
public class Like {
    private String _id;
    private LinkedList<String> users;
    private long total;

    public Like() {
        users = new LinkedList<String>();
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public LinkedList<String> getUsers() {
        this.total = this.users.size();
        return users;
    }

    public void setUsers(LinkedList<String> users) {
        this.users = users;
        this.total = this.users.size();
    }


    public int getLength() {
        return users.size();
    }

    public long getTotal() {
        if (users != null) {
            this.total = users.size();
        }
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Like{" +
                "_id='" + _id + '\'' +
                ", users=" + users +
                ", total=" + total +
                '}';
    }
}
