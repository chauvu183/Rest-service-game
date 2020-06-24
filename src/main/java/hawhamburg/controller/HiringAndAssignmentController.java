package hawhamburg.controller;
import com.google.gson.Gson;
import hawhamburg.RestHelper;
import hawhamburg.entities.group.Assignment;
import hawhamburg.entities.adventure.HeroParticipant;
import hawhamburg.entities.group.ElectionDTO;
import hawhamburg.entities.group.Hiring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class HiringAndAssignmentController {
    private static HiringAndAssignmentController instance;
    private static final String blackboardURL = "http://172.27.0.6:5000";;
    ConcurrentHashMap<String,Assignment> assignmentDivision = new ConcurrentHashMap<>();
    ConcurrentHashMap<String,Assignment> resolvedAssigments = new ConcurrentHashMap<>();
    ArrayList<ElectionDTO> electionsList = new ArrayList<>();

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
            if(hiring.getGroup()!= null){
                String hiringGroup = "/taverna/groups/" + hiring.getGroup() + "/members";
                //register als a new member in
                restHelper.sendPost(hiringGroup,"\n");

            }else{
                hiring.setMessage("There are no recuiting right now");
            }
    }

    public void handleAssignment(Assignment assignment, String user){
        if(assignment.getId()!= null){
            assignmentDivision.put(user,assignment);
        }else{
            assignment.setMessage("There are no Assignment right now");
        }
    }

    public void handleReceivedAssigment(Assignment assignment,String id){
        if(assignment.getId()!= null){
            resolvedAssigments.put(id,assignment);
        }else{
            assignment.setMessage("There are no Assignment right now");
        }
    }

    public Collection<Assignment> getAllAssignment() throws Exception{
         return assignmentDivision.values();
    }

    public Collection<Assignment> getAllSolvedAssignment() throws Exception{
        return resolvedAssigments.values();
    }

    public Assignment getResolvedAssignmentById(String id) throws Exception{
         return resolvedAssigments.get(id);
    }

    public Assignment getAssignmentByName (String name) throws Exception{
         return assignmentDivision.get(name);
    }

    public void handleReceivedElection(ElectionDTO electionDTO){
        if(electionDTO.getMessage()!= null){
            electionsList.add(electionDTO);
        }else{
            electionDTO.setMessage("There are no Assignment right now");
        }
    }


}
