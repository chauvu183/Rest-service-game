package hawhamburg.model;

public class Link {
    public String deliveries;
    public String self;
    public String tasks;

    @Override
    public String toString() {
        return "Link{" +
                "deliveries='" + deliveries + '\'' +
                ", self='" + self + '\'' +
                ", tasks='" + tasks + '\'' +
                '}';
    }
}
