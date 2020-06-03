package hawhamburg;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;

import hawhamburg.controller.GameController;
import hawhamburg.service.HeroService;
import static spark.Spark.*;

public class Start {
	private static final int MIN_PORT_NUMBER = 4567;
	private static final int MAX_PORT_NUMBER = 5567;
	static HeroService heroService;
	static GameController g1;
	static GameController g2;
	public static void main(String[] args) throws IOException {
		int portToUse = 4567;
		while(!available(portToUse)) {
			portToUse++;
		}
		port(portToUse);
		System.out.println("port used = " + portToUse);
		GameController gameController = new GameController(portToUse);
//		Thread t = new Thread(() ->  {try {
//			g1 = new GameController();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}});
//		t.start();
//		
//		Thread t2 = new Thread(() ->  {try {
//			g1 = new GameController();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}});
//		t2.start();
		
		//game.startGame();

		//messageService = new MessageService();
		
		
		// TODO Auto-generated method stub
		//Gson gson = new Gson();
		//RestHelper h1 = new RestHelper();

		//h1.baseUrl = "http://172.27.0.6:5000";
		//String data = "{\"name\":\"userD\",\"password\":\"abc123\"}";

		//h1.sendPost("/users", data);
       // System.out.print("Start from here, login in new user\n");
		//h1.login(new User("userD", "abc123"));

		//JsonNode adventurerList = h1.sendGet("/taverna/adventurers");
		String contactIP = InetAddress.getLocalHost().getHostAddress();
		System.out.println(contactIP);
		//MessageService ms = new MessageService();
		//ms.username = "userD";

		/*h1.sendGet("/whoami");
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
	
	public static boolean available(int port) {
	    if (port < MIN_PORT_NUMBER || port > MAX_PORT_NUMBER) {
	        throw new IllegalArgumentException("Invalid start port: " + port);
	    }

	    ServerSocket ss = null;
	    DatagramSocket ds = null;
	    try {
	        ss = new ServerSocket(port);
	        ss.setReuseAddress(true);
	        ds = new DatagramSocket(port);
	        ds.setReuseAddress(true);
	        return true;
	    } catch (IOException e) {
	    } finally {
	        if (ds != null) {
	            ds.close();
	        }

	        if (ss != null) {
	            try {
	                ss.close();
	            } catch (IOException e) {
	                /* should not be thrown */
	            }
	        }
	    }

	    return false;
	}

}
