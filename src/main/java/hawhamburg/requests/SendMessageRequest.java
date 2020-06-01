package hawhamburg.requests;

import hawhamburg.entities.adventure.Message;

public class SendMessageRequest {
    private Message message;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
