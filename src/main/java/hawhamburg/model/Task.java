package hawhamburg.model;

public class Task {
	public String self;
	public int[] deliverables;
	public String description;
	public int id;
	public String location;
	public String name;
	public int quest;
	public int required_players;
	public String resource;
	@Override
	public String toString() {
		return "Task [description=" + description + ", location=" + location + ", name=" + name + ", resource="
				+ resource + "]";
	}
	
	
}
