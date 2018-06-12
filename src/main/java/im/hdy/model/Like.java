package im.hdy.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.LinkedList;

@Document
public class Like {
    private String _id;
    private LinkedList<String> users;

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
        return users;
    }

    public void setUsers(LinkedList<String> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Like{" +
                "_id='" + _id + '\'' +
                ", users=" + users +
                '}';
    }
}
