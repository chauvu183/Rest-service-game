package hawhamburg;

import hawhamburg.model.User;
import kong.unirest.*;

import java.util.concurrent.atomic.AtomicBoolean;

public class RestHelper {
	public String baseUrl;
	public static String token;

	public JsonNode sendPost(String url, String data) {
		System.out.println("sending post request to: " + baseUrl + url);
		HttpResponse<JsonNode> request = Unirest.post(baseUrl + url).header("Authorization", "Token " + token).body(data).asJson();
		JsonNode body = request.getBody();
		System.out.println(body);
		request.ifSuccess(response -> {
			//String token = body.getObject().getString("token");
		//	System.out.println(body);
		}).ifFailure(response ->{
			System.out.println("Oh No! Status" + response.getStatus());
			if (body != null) {
				String message = body.getObject().getString("message");
				System.out.println(message);
				response.getParsingError().ifPresent(e -> {
					System.out.println("Parsing Exception: " + e);
					System.out.println("Original body: " + e.getOriginalBody());
				});
			}
		});
		return body;
	}

	public boolean login(User newUser) {
		final boolean[] isValid = {true};
		HttpResponse<JsonNode> request = Unirest.get(baseUrl + "/login").basicAuth(newUser.name, newUser.password).asJson();
		JsonNode body = request.getBody();
		request.ifSuccess(response -> {
			String token = body.getObject().getString("token");
			RestHelper.token = token;
		}).ifFailure(response ->{
			isValid[0] = false;
			System.out.println("Oh No! Status" + response.getStatus());
			String message = body.getObject().getString("message");
			System.out.println(message);
			response.getParsingError().ifPresent(e -> {
				System.out.println("Parsing Exception: " + e);
				System.out.println("Original body: " + e.getOriginalBody());
			});
		});
		return isValid[0];
	}

	public JsonNode sendGet(String url) {
		System.out.println("sending get request to: " + baseUrl + url);
		HttpResponse<JsonNode> request = Unirest.get(baseUrl + url).header("Authorization", "Token " + token).asJson();
		JsonNode body = request.getBody();
		System.out.println(body);
		request.ifSuccess(response -> {
			//String token = body.getObject().getString("token");
			//System.out.println(body);
		}).ifFailure(response ->{
			System.out.println("Oh No! Status" + response.getStatus());
			String message = body.getObject().getString("message");
			System.out.println(message);
			response.getParsingError().ifPresent(e -> {
				System.out.println("Parsing Exception: " + e);
				System.out.println("Original body: " + e.getOriginalBody());
			});
		});
		return body;
	}

	public JsonNode sendPut(String url,String data) {
		JsonNode node = Unirest.put(baseUrl + url).header("Authorization", "Token " + token).body(data).asJson().getBody();
		System.out.println(node);
		return node;
	}


}
