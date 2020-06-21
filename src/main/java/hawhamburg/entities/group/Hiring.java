package hawhamburg.entities.group;

public class Hiring {
    private String group;
    private String quest;
    private String message;

    public Hiring(String group, String quest, String message) {
        this.group = group;
        this.quest = quest;
        this.message = message;
    }

    public String getGroup() {
        return group;
    }

    public String getQuest() {
        return quest;
    }

    public String getMessage() {
        return message;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setQuest(String quest) {
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
