package hawhamburg.model;

import com.google.gson.JsonObject;

import java.util.Arrays;

public class Quest {
    public JsonObject _links;
    public int[] deliveries;
    public String description;
    public int id;
    public int[] prerequisites;
    public String name;
    public JsonObject requires_tokens;
    public String[] tasks;

    public JsonObject getRequires_tokens() {
        return requires_tokens;
    }

    public String getTasks() {
        return tasks[0];
    }

    public JsonObject get_links() {
        return _links;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Quest{" +
                "_link=" + _links +
                ", deliveries=" + Arrays.toString(deliveries) +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", prerequisites=" + Arrays.toString(prerequisites) +
                ", name='" + name + '\'' +
                ", requires_tokens=" + requires_tokens +
                ", tasks=" + Arrays.toString(tasks) +
                '}';
    }
}
