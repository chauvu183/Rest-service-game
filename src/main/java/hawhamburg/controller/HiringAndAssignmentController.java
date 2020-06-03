package hawhamburg.controller;
import com.google.gson.Gson;
import hawhamburg.RestHelper;
import hawhamburg.entities.group.Assignment;
import hawhamburg.entities.adventure.HeroParticipant;
import hawhamburg.entities.group.Hiring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class HiringAndAssignmentController {
    private static HiringAndAssignmentController instance;
    private static final String blackboardURL = "http://172.27.0.6:5000";;
    ConcurrentHashMap<String,Assignment> assignmentDivision = new ConcurrentHashMap<>();

    private RestHelper restHelper = new RestHelper();
    private final Gson gson = new Gson();

    private List<Integer> unsolvedQuest = new ArrayList<>();

     private ConcurrentHashMap<String, Hiring> enteredUsers = new ConcurrentHashMap<>();

     private HiringAndAssignmentController() {
         restHelper.baseUrl = blackboardURL;
    }

    public synchronized static HiringAndAssignmentController getInstance() {
        if (instance == null) {
            instance = new HiringAndAssignmentController();
        };
        return instance;
    }


    public void handleHiring(Hiring hiring){
         // TODO if the player give the group URL -> register member in this group
            if(hiring.getGroup()!= null){
                String hiringGroup = "/taverna/groups/" + hiring.getGroup() + "/members";
                //register als a new member in
                restHelper.sendPost(hiringGroup,"\n");

            }else{
                hiring.setMessage("There are no recuiting right now");
                //TODO Else if he didn't give anything -> response with a message?
            }
    }

    public void handleAssignment(Assignment assignment, String user){
        // TODO group Owner give assignments for other
        if(assignment.getId()!= null){
            assignmentDivision.put(user,assignment);
        }else{
            assignment.setMessage("There are no Assignment right now");
        }
    }

    public Collection<Assignment> getAllAssignment() throws Exception{
         return assignmentDivision.values();
    }

    public Assignment getAssignmentByName (String name) throws Exception{
         return assignmentDivision.get(name);
    }

}
