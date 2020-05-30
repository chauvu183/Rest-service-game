package hawhamburg.controller;
import hawhamburg.model.CommunicationParticipant;
import hawhamburg.model.Hiring;
import hawhamburg.model.Message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HiringController {
    private static HiringController instance;

     private ConcurrentHashMap<String, Hiring> enteredUsers = new ConcurrentHashMap<>();
     private HiringController() {
    }

    public synchronized static HiringController getInstance() {
        if (instance == null) {
            instance = new HiringController();
        };
        return instance;
    }


    public void handleHiring(){
         // TODO if the player give the group URL -> register member in this group

        //TODO Else if he didn't give anything -> response with a message?


    }
}
