package hawhamburg.service;

import com.google.gson.Gson;
import hawhamburg.controller.HiringController;
import hawhamburg.controller.HeroController;
import hawhamburg.model.Assignment;
import hawhamburg.model.HeroParticipant;
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

public class HeroService {
    private Gson gson = new Gson();
    private HeroController heroController;
    private HiringController hiringController;

    private User user;
    private String baseURL = "http://172.27.0.6:5000";
    private String localURL = InetAddress.getLocalHost().getHostAddress();

    public HeroService() throws UnknownHostException {
        this.heroController = HeroController.getInstance();
        this.hiringController = HiringController.getInstance();
        registerServices();
    }

    public void registerServices() {
        post("/adventure/send/:userId", (req, res) -> {
            //TODO request
            String body = req.body();
            String goalUserId = req.params(":userId");

            SendMessageRequest request = gson.fromJson(body, SendMessageRequest.class);
            //request.getMessage().setSenderId(myUserId);
            heroController.sendMessage(goalUserId, request.getMessage());

            return "message sent";
        });

        post("/adventures", (request, response) -> {
            try{
                response.status(201);
                response.type("application/json");
                String name=(new JSONObject(request.body())).getString("name");
                // CommunicationParticipant adventurer = new Gson().fromJson(request.body(), CommunicationParticipant.class);
                HeroParticipant adventurer = new HeroParticipant(name);
                heroController.addParticipant(adventurer);
                heroController.addParticipantByName(adventurer);
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));

            }catch (Exception e){
                response.status(400);
                e.printStackTrace();
                return "invalid username";
            }
            });

        get("/adventures",(req,res)->{
            res.type("application/json");
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(heroController.getAllParticipant())));
        });

      /*  get("/adventures/:id", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(messageController.getParticipant(request.params(":id")))));
        });
        */

        get("/adventures/:userName", (request, response) -> {
            try{
                response.status(202);
                response.type("application/json");
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(heroController.getParticipantByName(request.params(":userName")))));

            }catch(Exception e){
                response.status(400);
                e.printStackTrace();
                return "invalid username";
            }
            });




        get("/adventures/contact/:name",(req, res) ->{
            try{
                String userName = req.params(":name");
                HeroParticipant csr = new HeroParticipant(userName);
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
            System.out.println(hiring);
            if(hiring.getGroup()!= null){
                response.status(201);
                hiringController.handleHiring(hiring);
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
            }else{
                response.status(400);
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, new Gson().toJson(hiring.getMessage())));
            }
        }));

        post("/adventures/assignments",(req,res)->{
            res.type("application/json");
            Assignment assignment = new Gson().fromJson(req.body(),Assignment.class);
            //TODO the owner assign tasks for each member
           return null;
        });

        post("/adventures/assignments/delivered",(req,res)->{
            res.type("application/json");
            Assignment assignment = new Gson().fromJson(req.body(),Assignment.class);
            //TODO member fullfill assigned tasks and post back the result
           return null;
        });

    }
}
