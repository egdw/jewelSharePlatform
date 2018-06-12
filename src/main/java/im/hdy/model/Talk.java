package im.hdy.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document
public class Talk {
    private String _id;
    private ArrayList<Talk> talks;

    public Talk() {
        this.talks = new ArrayList<>();
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

    @Override
    public String toString() {
        return "Talk{" +
                "_id='" + _id + '\'' +
                ", talks=" + talks +
                '}';
    }
}
