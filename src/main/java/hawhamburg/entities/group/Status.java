package hawhamburg.entities.group;

public enum Status {
    UNKNOWN(-9), ALIVE(0), SUSPECTED_DEAD(9), DEAD(99);

    private final int status;

    private Status(final int status) {
        this.status = status;
    }

    public int statusNumber() {
        return status;
    }
}
