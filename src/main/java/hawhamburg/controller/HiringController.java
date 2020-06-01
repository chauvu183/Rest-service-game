package hawhamburg.controller;
import com.google.gson.Gson;
import hawhamburg.RestHelper;
import hawhamburg.entities.adventure.HeroParticipant;
import hawhamburg.entities.basic.Link;
import hawhamburg.entities.basic.User;
import hawhamburg.entities.group.Group;
import hawhamburg.entities.group.Hiring;
import kong.unirest.JsonNode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class HiringController {
    private static HiringController instance;
    private static final String blackboardURL = "http://172.27.0.6:5000";;

    private RestHelper restHelper = new RestHelper();
    private final Gson gson = new Gson();

    private ConcurrentHashMap<Integer, HeroParticipant> assignmentDistributes = new ConcurrentHashMap<>();
    private List<Integer> unsolvedQuest = new ArrayList<>();

     private ConcurrentHashMap<String, Hiring> enteredUsers = new ConcurrentHashMap<>();

     private HiringController() {
         restHelper.baseUrl = blackboardURL;
    }

    public synchronized static HiringController getInstance() {
        if (instance == null) {
            instance = new HiringController();
        };
        return instance;
    }


    public void handleHiring(Hiring hiring){
         // TODO if the player give the group URL -> register member in this group
            if(hiring.getGroup()!= null){
                //register als a new member in
                restHelper.sendPost(hiring.getGroup(),"\n");

            }else{
                //TODO Else if he didn't give anything -> response with a message?
            }
    }

}
