package hawhamburg.model;

import java.util.UUID;

public class Adventurer {
	private String heroclass;
	private String capabilities;
	private String url;
	private String user;
	private String group;
	private String id;

	public Adventurer() {
		this.id = UUID.randomUUID().toString();
	}

	public String getId() {
		return id;
	}

	public String getHeroclass() {
		return heroclass;
	}

	public String getCapabilities() {
		return capabilities;
	}

	public String getUrl() {
		return url;
	}

	public String getUser() {
		return user;
	}

	public String getGroup() {
		return group;
	}

	@Override
	public String toString() {
		return "Adventurer{" +
				"heroclass='" + heroclass + '\'' +
				", capabilities='" + capabilities + '\'' +
				", url='" + url + '\'' +
				", user='" + user + '\'' +
				", group='" + group + '\'' +
				'}';
	}
}

