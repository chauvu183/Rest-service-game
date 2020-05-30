package hawhamburg.controller;
import hawhamburg.RestHelper;
import hawhamburg.model.Hiring;

import java.util.concurrent.ConcurrentHashMap;

public class HiringController {
    private static HiringController instance;
    private static final String blackboardURL = "http://172.27.0.6:5000";;
    private RestHelper restHelper = new RestHelper();

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
