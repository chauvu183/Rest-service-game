package hawhamburg.entities.adventure;

import hawhamburg.entities.group.Status;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Adventurer  implements Comparable<Adventurer>{
	private String heroclass;
	private String capabilities;
	private String url;
	private String user;
	private String group;
	private String id;
	private Status status;

	String IPADDRESS_PATTERN = "^https?://\\d{1,3}(?:\\.\\d{1,3}){3}(?::\\d{1,5})?";

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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getIpAdress(){
		Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
		Matcher matcher = pattern.matcher(getUrl());
		if(matcher.find()){
			return matcher.group(0);
		}
		return null;
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

	@Override
	public int compareTo(Adventurer other) {
		return getId().compareTo(other.getId());
	}
}

