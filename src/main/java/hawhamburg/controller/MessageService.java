/*
package hawhamburg.controller;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import java.util.ArrayList;

import com.google.gson.Gson;

import hawhamburg.response.CommunicationServiceResponse;
import hawhamburg.model.Message;
import hawhamburg.response.TravernInfoResponse;

public class MessageService {
    static String baseUrl = "http://172.27.0.50:5000";
    public String username;
    private Gson gson = new Gson();
    ArrayList<Message> messages = new ArrayList<>();
    public MessageService() {
        registerService();
    }

    public void registerService() {
        port(5000);
        get("/", (req,res) -> {
            TravernInfoResponse tir = new TravernInfoResponse();
            tir.heroClass = "WORRIOR";// TODO Check real hero class of the user
            tir.capabilites = "";
            tir.url = baseUrl + "/communication";
            return gson.toJson(tir);
        });

        get("/communication", (req,res) -> {
            CommunicationServiceResponse csr = new CommunicationServiceResponse();
            csr.user = "/taverna/adventurers/"+username;
            csr.idle = false;
            csr.group = "";
            csr.hirings = "";
            csr.messages = baseUrl + "/message";
            return gson.toJson(csr);
        });

        post("/message", (req, res) -> {
            Message m = gson.fromJson(req.body(), Message.class);
            System.out.println("received a message; " + m);
            messages.add(m);
            return "message sent";
        });
    }
}
*/
