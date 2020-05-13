package hawhamburg;

import hawhamburg.model.User;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

public class RestHelper {
	public String baseUrl;
	public static String token;

	public JsonNode sendPost(String url, String data) {
		JsonNode body = Unirest.post(baseUrl + url).header("Authorization", "Token " + token).body(data).asJson().getBody();
		System.out.println(body);
		return body;
	}

	public void login(User newUser) {
		JsonNode body = Unirest.get(baseUrl + "/login").basicAuth(newUser.name, newUser.password).asJson().getBody();
		String token = body.getObject().getString("token");
		System.out.println("this is parsed token: " + token);
		RestHelper.token = token;
	}

	public JsonNode sendGet(String url) {
		JsonNode node = Unirest.get(baseUrl + url).header("Authorization", "Token " + token).asJson().getBody();
		System.out.println(node);
		return node;
	}

	public JsonNode sendPut(String url,String data) {
		JsonNode node = Unirest.put(baseUrl + url).header("Authorization", "Token " + token).body(data).asJson().getBody();
		System.out.println(node);
		return node;
	}


}
