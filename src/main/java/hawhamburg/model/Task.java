package hawhamburg.model;

import com.google.gson.JsonObject;

public class Task {
	public JsonObject _link;
	public int[] deliverables;
	public String description;
	public int id;
	public String location;
	public String name;
	public int quest;
	public int required_players;
	public String resource;

	public String getlink() {

		return null;
	}

	@Override
	public String toString() {
		return "Task{" +
				"self='" + _link + '\'' +
				", location='" + location + '\'' +
				", name='" + name + '\'' +
				", quest=" + quest +
				", resource='" + resource + '\'' +
				'}';
	}
}
