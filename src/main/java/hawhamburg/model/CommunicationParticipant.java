package hawhamburg.model;

import java.util.UUID;

public class CommunicationParticipant {
	public String user;
	public boolean idle;
	public String group;
	public String hirings;
	public String assignments;
	public String messages;
	private String id;

	public CommunicationParticipant() {
		this.id = UUID.randomUUID().toString();
		this.idle = false;
	}

	public String getId() {
		return id;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setIdle(boolean idle) {
		this.idle = idle;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public void setHirings(String hirings) {
		this.hirings = hirings;
	}

	public void setAssignments(String assignments) {
		this.assignments = assignments;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}
}
