package hawhamburg.entities.group;

public class ElectionDTO {
    private String algorithm;
    private String payload;
    private String user;
    private JobDTO jobDTO;
    private String message;

    public ElectionDTO(String algorithm, String payload, String user, JobDTO jobDTO, String message) {
        this.algorithm = algorithm;
        this.payload = payload;
        this.user = user;
        this.jobDTO = jobDTO;
        this.message = message;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getPayload() {
        return payload;
    }

    public String getUser() {
        return user;
    }

    public JobDTO getJobDTO() {
        return jobDTO;
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
                ", job=" + jobDTO +
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

    public void setJobDTO(JobDTO jobDTO) {
        this.jobDTO = jobDTO;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
