package hawhamburg;

import java.io.IOException;

import com.google.gson.Gson;

import hawhamburg.model.Task;
import kong.unirest.JsonNode;


public class Start {

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub 
		Gson gson = new Gson();
		RestHelper h1 = new RestHelper();
		
		h1.baseUrl = "http://172.27.0.6:5000";
		String data = "{\"name\":\"userD\",\"password\":\"abc123\"}";
		
		h1.sendPost("/users", data);
		
		h1.login("userD", "abc123");
		
		h1.sendGet("/whoami");
		
		h1.sendGet("/blackboard/quests");
		
		JsonNode firstQuest = h1.sendGet("/blackboard/quests/1");
		String tasksUrl = firstQuest.getObject().getJSONObject("object").getJSONObject("_links").getString("tasks");
		JsonNode tasks = h1.sendGet(tasksUrl);
		
		String taskString = tasks.getObject().getJSONArray("objects").getJSONObject(0).toString();
		
		Task task = gson.fromJson(taskString, Task.class);
				System.out.println(task);
		JsonNode location = h1.sendGet(task.location);
		
		String host = location. getObject().getJSONObject("object").getString("host");
		
		RestHelper h2 = new RestHelper();
		h2.baseUrl = "http://"+host;
		JsonNode visits = h2.sendGet("/visits");
		JsonNode postVisits = h2.sendPost("/visits", "\n");
		
		String token = postVisits.getObject().getString("token");
		// TODO send to delivery {"taskUrl":"", Token:token}
	}

}
