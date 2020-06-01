package hawhamburg.entities.adventure;

public class HeroParticipant {
	public String user;
	public boolean idle;
	public String group;
	public String hirings;
	public String assignments;
	public String messages;
	private double id;
	private String userName;
	private String localURL;

	public HeroParticipant(String userName,String localURL) {
		this.user = "http://172.27.0.6:5000/users/" + userName;
		this.id = Math.random();
		this.idle = false;
		this.hirings = localURL + "/hirings/" + userName;
		this.assignments = localURL + "/assignments/" + userName;
		this.messages = localURL+ "/messages/" + userName;
		this.userName = userName;
	}

	public double getId() {
		return id;
	}
	public String getUserName(){
		return userName;
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
