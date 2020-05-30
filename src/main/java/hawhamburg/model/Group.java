package hawhamburg.model;

import com.google.gson.JsonObject;

import java.util.Arrays;

public class Group {
    public JsonObject _links;
    private int id;
    private String[] members;
    private String owner;

    public JsonObject get_links() {
        return _links;
    }

    public int getId() {
        return id;
    }

    public String[] getMembers() {
        return members;
    }

    public String getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return "Group{" +
                "_links=" + _links +
                ", id=" + id +
                ", members=" + Arrays.toString(members) +
                ", owner='" + owner + '\'' +
                '}';
    }
}
