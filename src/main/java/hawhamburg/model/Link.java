package hawhamburg.model;

public class Link {
    public String deliveries;
    public String self;
    public String tasks;
    public String members;

    @Override
    public String toString() {
        return "Link{" +
                "deliveries='" + deliveries + '\'' +
                ", self='" + self + '\'' +
                ", tasks='" + tasks + '\'' +
                ", members='" + members + '\'' +
                '}';
    }
}
