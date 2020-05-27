package hawhamburg.model;

public class Message {
	private String senderId;
	private String message;
	private String status;
	private String type;

	public void setMessage(String message) {
		this.message = message;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getSenderId() {
		return senderId;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "Message [message=" + message + ", status=" + status + ", type=" + type + "]";
	}
}
