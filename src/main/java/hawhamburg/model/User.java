package hawhamburg.model;

import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.UUID;

public class User {
    private String userId;
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

    @Override
    public String toString() {
        return "User {" +
                "name='" + name + '\'' +
                ", deliverables done=" + Arrays.toString(deliverables_done) +
                ", delivered=" + Arrays.toString(delivered) +
                ", ip='" + ip + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
