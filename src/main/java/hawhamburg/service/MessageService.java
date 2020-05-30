package hawhamburg.service;

import com.google.gson.Gson;
import hawhamburg.controller.HiringController;
import hawhamburg.controller.MessageController;
import hawhamburg.model.CommunicationParticipant;
import hawhamburg.model.Hiring;
import hawhamburg.model.User;
import hawhamburg.requests.SendMessageRequest;
import hawhamburg.response.StandardResponse;
import hawhamburg.response.StatusResponse;
import kong.unirest.json.JSONObject;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static spark.Spark.get;
import static spark.Spark.post;

public class MessageService {
    private Gson gson = new Gson();
    private MessageController messageController;
    private HiringController hiringController;

    private User user;
    private String baseURL = "http://172.27.0.6:5000";
    private String localURL = InetAddress.getLocalHost().getHostAddress();

    public MessageService() throws UnknownHostException {
        this.messageController = MessageController.getInstance();
        registerServices();
    }

    public void registerServices() {
        post("/adventure/send/:userId", (req, res) -> {
            //TODO request
            String body = req.body();
            String goalUserId = req.params(":userId");

            SendMessageRequest request = gson.fromJson(body, SendMessageRequest.class);
            //request.getMessage().setSenderId(myUserId);
            messageController.sendMessage(goalUserId, request.getMessage());

            return "message sent";
        });

        post("/adventures", (request, response) -> {
            response.type("application/json");
            String name=(new JSONObject(request.body())).getString("name");
           // CommunicationParticipant adventurer = new Gson().fromJson(request.body(), CommunicationParticipant.class);
            CommunicationParticipant adventurer = new CommunicationParticipant(name);
            messageController.addParticipant(adventurer);
            messageController.addParticipantByName(adventurer);
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
        });

        get("/adventures",(req,res)->{
            res.type("application/json");
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(messageController.getAllParticipant())));
        });

      /*  get("/adventures/:id", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(messageController.getParticipant(request.params(":id")))));
        });
        */

        get("/adventures/:userName", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(messageController.getParticipantByName(request.params(":userName")))));
        });




        get("/adventures/contact/:name",(req, res) ->{
            try{
                String userName = req.params(":name");
                CommunicationParticipant csr = new CommunicationParticipant(userName);
                res.type("application/json");
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.SUCCESS,new Gson()
                                .toJsonTree(csr))
                );
            }catch (Exception e){
                e.printStackTrace();
                return "invalid username";
            }
        } );

        post("/adventures/hirings",((request, response) -> {
            response.type("application/json");
            Hiring hiring = new Gson().fromJson(request.body(), Hiring.class);

            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));

        }));

    }
}
