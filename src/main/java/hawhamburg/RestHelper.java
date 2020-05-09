package hawhamburg;

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

	public void login(String name, String password) {
		JsonNode body = Unirest.get(baseUrl + "/login").basicAuth(name, password).asJson().getBody();
		System.out.println(body);
		String token = body.getObject().getString("token");
		System.out.println("this is parsed token: " + token);
		RestHelper.token = token;
	}

	public JsonNode sendGet(String url) {
		JsonNode node = Unirest.get(baseUrl + url).header("Authorization", "Token " + token).asJson().getBody();
		System.out.println(node);
		return node;
	}
}
