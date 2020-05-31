package hawhamburg.model;

import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.UUID;

public class User {
    private String userId;
    private Group ownerOfGroup;
    public String name;
    public String password;
    public JsonObject _links;
    public int[] deliverables_done;
    public int[] delivered;
    public String ip;
    public String location;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public void setOwnerOfGroup(Group ownerOfGroup) {
        this.ownerOfGroup = ownerOfGroup;
    }

    public static User generateUser(String username, String password) {
        return new User(username, password);
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getUserId() {
        return userId;
    }

    public Group getOwnerOfGroup() {
        return ownerOfGroup;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", ownerOfGroup=" + ownerOfGroup +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", ip='" + ip + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
