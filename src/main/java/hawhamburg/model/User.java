package hawhamburg.model;

import com.google.gson.JsonObject;

import java.util.Arrays;

public class User {

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
    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
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
