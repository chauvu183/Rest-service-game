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

    public void setGroup(String group) {
        this.group = group;
    }

    public void setQuest(int quest) {
        this.quest = quest;
    }

    public void setMessage(String message) {
        this.message = message;
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
