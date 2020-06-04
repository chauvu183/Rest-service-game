package hawhamburg.service;

import com.google.gson.Gson;
import hawhamburg.controller.HiringAndAssignmentController;
import hawhamburg.controller.HeroController;
import hawhamburg.entities.group.Assignment;
import hawhamburg.entities.adventure.HeroParticipant;
import hawhamburg.entities.group.Hiring;
import hawhamburg.entities.basic.User;
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
    private HiringAndAssignmentController hiringAndAssignmentController;

    private User user;
    private static final String PROTOCOL = "http";
    private String baseURL = "http://172.27.0.6:5000";
    private String localURL;
    private int port;

    public HeroService(int port) throws UnknownHostException {
        this.port = port;
        this.heroController = HeroController.getInstance();
        this.hiringAndAssignmentController = HiringAndAssignmentController.getInstance();
        this.localURL = String.format("%s://%s:%d",PROTOCOL,InetAddress.getLocalHost().getHostAddress(),4567);
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
                response.status(200);
                response.type("application/json");
                String name=(new JSONObject(request.body())).getString("name");
                // CommunicationParticipant adventurer = new Gson().fromJson(request.body(), CommunicationParticipant.class);
                HeroParticipant adventurer = new HeroParticipant(name,localURL);
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
                HeroParticipant csr = new HeroParticipant(userName,localURL);
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

        post("/adventures/hirings/:name",((request, response) -> {
            response.type("application/json");
            String userName = request.params(":name");
            Hiring hiring = new Gson().fromJson(request.body(), Hiring.class);
            System.out.println(hiring);
            if(hiring.getGroup()!= null){
                response.status(201);
                hiringAndAssignmentController.handleHiring(hiring);
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
            }else{
                response.status(400);
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, new Gson().toJson(hiring.getMessage())));
            }
        }));

        post("/adventures/assignments/:name",(req,res)->{
            res.type("application/json");
            String userName = req.params(":name");
            Assignment assignment = new Gson().fromJson(req.body(),Assignment.class);
            hiringAndAssignmentController.handleAssignment(assignment,userName);
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
        });

        get("/adventures/assignments/:name",(req,res)->{
            res.status(202);
            res.type("application/json");
            String userName = req.params(":name");
            Assignment assignment = hiringAndAssignmentController.getAssignmentByName(userName);
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(assignment)));

        });

        get("/adventures/assignments",(req,res)->{
            res.status(200);
            res.type("application/json");
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, new Gson().toJsonTree(hiringAndAssignmentController.getAllAssignment())));
        });


        post("/adventures/assignments/:id/delivered",(req,res)->{
            res.type("application/json");
            String id = req.params(":id");
            Assignment assignment = new Gson().fromJson(req.body(),Assignment.class);
            hiringAndAssignmentController.handleReceivedAssigment(assignment,id);
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
        });


        get("/adventures/assignments/:id/delivered",(req,res)->{
            res.type("application/json");
            String id = req.params(":id");
            if(hiringAndAssignmentController.getResolvedAssignmentById(id) != null){
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(hiringAndAssignmentController.getResolvedAssignmentById(id))));
            }else{
                return new Gson().toJson(null);
            }
        });

    }
}
