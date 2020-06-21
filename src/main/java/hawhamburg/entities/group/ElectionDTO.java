package hawhamburg.entities.group;

public class ElectionDTO {
    private String algorithm;
    private String payload;
    private String user;
    private Job job;
    private String message;

    public String getAlgorithm() {
        return algorithm;
    }

    public String getPayload() {
        return payload;
    }

    public String getUser() {
        return user;
    }

    public Job getJob() {
        return job;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Election{" +
                "algorithm='" + algorithm + '\'' +
                ", payload='" + payload + '\'' +
                ", user='" + user + '\'' +
                ", job=" + job +
                ", message='" + message + '\'' +
                '}';
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
