package hawhamburg.logic;

import com.google.gson.Gson;
import hawhamburg.RestHelper;
import hawhamburg.entities.adventure.Adventurer;
import hawhamburg.entities.group.ElectionGroup;
import hawhamburg.entities.group.ElectionDTO;
import hawhamburg.entities.group.JobDTO;
import hawhamburg.entities.group.Status;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class BullyAlgo {

    RestHelper restHelperBlackboard = new RestHelper();

    Gson gson = new Gson();
    public ElectionGroup memberGroup;
    public Adventurer sourceMember;
    private final AtomicReference<Adventurer> electedLeader = new AtomicReference<>();

    private final AtomicBoolean running = new AtomicBoolean();

    public BullyAlgo(ElectionGroup memberGroup, Adventurer sourceMember) {
        this.memberGroup = memberGroup;
        this.sourceMember = sourceMember;
         running.compareAndSet(false, true);
    }

    public synchronized void electCoordinator() throws Exception {
        Adventurer coordinator = null;
        if (!running.get()) {
            System.out.println("Cannot elect a leader when leader election is shutdown");
            return;
        }
                    
        // find all members with id greater than sourceMember
        final List<Adventurer> greaterIdMembers = memberGroup.largerMembers(sourceMember);

        // sourceMember has the highest id - grab and announce leadership
        if (greaterIdMembers.isEmpty()) {
            if (sourceMember.getStatus() != Status.ALIVE) {
                System.out.println("Failed round of leader election having encountered a non-alive leadership candidate: " +
                        sourceMember);
                return;
            }

            coordinator = sourceMember;
            memberGroup.setLeader(coordinator);
            for (final Adventurer member : memberGroup.otherMembers(sourceMember)) {
                System.out.println("Announcing leader:"+ coordinator.getId() +" to "+ member.getId());
                //declare victory
                sendVictory(member);
            }
            electedLeader.set(coordinator);
            System.out.println("Elected leader:" + coordinator.getId());
        }

        // broadcast election to all greaterIdMembers
        else {
            for (final Adventurer greaterMember : greaterIdMembers) {
                try {
                          System.out.println("Member: "+ sourceMember.getId() +" sending election request to " +
                                  greaterMember.getId());
                           sendElection(greaterMember);
                    
                } catch (Exception problem) {
                    System.out.println("Problem encountered dispatching election rqequest to member: " +
                            greaterMember.getId() + " with "+ problem);
                }
            }
        }
    }

    private void sendElection(Adventurer adventurer){
        JobDTO jobDTO = new JobDTO();
        String message = "Please election";
        ElectionDTO electionDTO = new ElectionDTO("bully","election",adventurer.getUrl(), jobDTO,message);
       restHelperBlackboard.post(adventurer.getIpAdress() +"/election",gson.toJson(electionDTO));
    }

    private  void sendVictory(Adventurer adventurer) {
        JobDTO jobDTO = new JobDTO();
        String message = "I am the Coordinator";
        ElectionDTO electionDTO = new ElectionDTO("bully", "victory", adventurer.getUrl(), jobDTO, message);
        restHelperBlackboard.post(adventurer.getUrl() +"/election",gson.toJson(electionDTO));
    }

}
