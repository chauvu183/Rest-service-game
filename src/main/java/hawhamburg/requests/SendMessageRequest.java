package hawhamburg.requests;

import hawhamburg.model.Message;

public class SendMessageRequest {
    private Message message;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
