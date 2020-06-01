package hawhamburg.entities.basic;

import com.google.gson.JsonObject;

import java.util.Arrays;

public class Task {
	public JsonObject _links;
	public int[] deliverables;
	public String description;
	public int id;
	public String location;
	public String name;
	public int quest;
	public int required_players;
	public String resource;

	public JsonObject getlink() {
		return _links;
	}

	public String getDescription() {
		return description;
	}

	public String getResource() {
		return resource;
	}

	public String getLocation() {
		return location;
	}

	@Override
	public String toString() {
		return "Task{" +
				"_link=" + _links +
				", deliverables=" + Arrays.toString(deliverables) +
				", description='" + description + '\'' +
				", id=" + id +
				", location='" + location + '\'' +
				", name='" + name + '\'' +
				", quest=" + quest +
				", required_players=" + required_players +
				", resource='" + resource + '\'' +
				'}';
	}
}
