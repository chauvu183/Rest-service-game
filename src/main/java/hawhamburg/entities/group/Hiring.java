package hawhamburg.entities.group;

public class Hiring {
    private String group;
    private int quest;
    private String message;

    public String getGroup() {
        return group;
    }

    public int getQuest() {
        return quest;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Hiring{" +
                "group='" + group + '\'' +
                ", quest=" + quest +
                ", message='" + message + '\'' +
                '}';
    }
}
