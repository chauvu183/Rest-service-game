package hawhamburg.entities.group;

public class Assignment {
    private String id;
    private String task;
    private String resource;
    private String method;
    private String data;
    private String callback;
    private String message;

    public String getId() {
        return id;
    }

    public String getTask() {
        return task;
    }

    public String getResource() {
        return resource;
    }

    public String getMethod() {
        return method;
    }

    public String getData() {
        return data;
    }

    public String getCallback() {
        return callback;
    }

    public String getMessage() {
        return message;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "id='" + id + '\'' +
                ", task='" + task + '\'' +
                ", resource='" + resource + '\'' +
                ", method='" + method + '\'' +
                ", data='" + data + '\'' +
                ", callback='" + callback + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
