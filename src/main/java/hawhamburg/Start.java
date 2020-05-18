package hawhamburg;

import java.io.IOException;

import hawhamburg.app.Game;
import hawhamburg.app.MessageService;


public class Start {

	public static void main(String[] args) throws IOException, InterruptedException {
		Game game = new Game();
		game.startGame();
		
		
/*		// TODO Auto-generated method stub
		Gson gson = new Gson();
		RestHelper h1 = new RestHelper();

		h1.baseUrl = "http://172.27.0.6:5000";
		String data = "{\"name\":\"userD\",\"password\":\"abc123\"}";

		//h1.sendPost("/users", data);
        System.out.print("Start from here, login in new user\n");
		h1.login(new User("userD", "abc123"));

		h1.sendGet("/whoami");
        System.out.print("get all the quests\n");
		h1.sendGet("/blackboard/quests");

        System.out.print("Get Quest 1\n");
		JsonNode firstQuest = h1.sendGet("/blackboard/quests/1");
        System.out.println("Get TaskUrl\n");
		String tasksUrl = firstQuest.getObject().getJSONObject("object").getJSONObject("_links").getString("tasks");

		JsonNode tasks = h1.sendGet(tasksUrl);

		String taskString = tasks.getObject().getJSONArray("objects").getJSONObject(0).toString();
		String selfTask = tasks.getObject().getJSONArray("objects").getJSONObject(0).getJSONObject("_links").getString("self");

		System.out.println(selfTask);
		System.out.println("Get location\n");
		Task task = gson.fromJson(taskString, Task.class);
				System.out.println(task);
		JsonNode location = h1.sendGet(task.location);

		String host = location. getObject().getJSONObject("object").getString("host");

		RestHelper h2 = new RestHelper();
		h2.baseUrl = "http://"+host;
		JsonNode visits = h2.sendGet(task.resource);
		JsonNode postVisits = h2.sendPost(task.resource, "\n");
        System.out.print("Get token to resolve the first quest\n");
		String token = postVisits.getObject().getString("token");
		// TODO send to delivery {"taskUrl":"", Token:token}
		System.out.println(token);
        // get all the necessary links in the blackboard to execute the delivery
        JSONObject allUrlObject = firstQuest.getObject().getJSONObject("object").getJSONObject("_links");

        Link link = gson.fromJson(String.valueOf(allUrlObject),Link.class);

        System.out.println("link");
        // {"tokens":{""+link.self+"":"token"}}

        String inputData = "{\"tokens\":{\""+selfTask+"\":\""+token+"\"}}";

		//get the correct url /blackboard/tasks/1
        h1.sendPost(link.deliveries,inputData);*/


	}

}
