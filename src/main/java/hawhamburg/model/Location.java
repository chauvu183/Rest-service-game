package hawhamburg.model;

import java.util.Arrays;

public class Location {
    private String host;
    private String name;
    private int[] tasks;
    private int[] visitors;

    @Override
    public String toString() {
        return "Location{" +
                "host='" + host + '\'' +
                ", name='" + name + '\'' +
                ", tasks=" + Arrays.toString(tasks) +
                '}';
    }

    public String getHost() {
        return host;
    }

    public String getName() {
        return name;
    }

    public int[] getTasks() {
        return tasks;
    }
}
