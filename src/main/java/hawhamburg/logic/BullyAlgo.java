package hawhamburg.logic;

import hawhamburg.RestHelper;
import hawhamburg.entities.adventure.Adventurer;
import hawhamburg.entities.group.AdventurerGroup;
import hawhamburg.entities.group.Status;
import kong.unirest.JsonNode;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class BullyAlgo {
    RestHelper restHelperBlackboard = new RestHelper();
    private AdventurerGroup memberGroup;
    private Adventurer sourceMember;
    private final AtomicReference<Adventurer> electedLeader = new AtomicReference<>();

    private final AtomicBoolean running = new AtomicBoolean();

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

            //TODO send message to other members and take jobs
            //final CoordinatorRequest victoryMessage = new CoordinatorRequest(sourceMember.getId(), epoch);

            for (final Adventurer member : memberGroup.otherMembers(sourceMember)) {
                System.out.println("Announcing leader:"+ coordinator.getId() +" to "+ member.getId());
                boolean successfulDispatch = false;
                //TODO compare with other member
                if( sourceMember.compareTo(member) < 0){
                    coordinator = member;
                }
                if (!successfulDispatch) {
                    System.out.println("Failed to successfully dispatch victory message to member: " +
                            member.getId());
                }
            }
            electedLeader.set(coordinator);
            System.out.println("Elected leader:" + coordinator.getId());
        }

        // broadcast election to all greaterIdMembers
        else {
            //final ElectionRequest electionRequest = new ElectionRequest(sourceMember.getId(), epoch);

            for (final Adventurer greaterMember : greaterIdMembers) {
                System.out.println("Member: "+ sourceMember.getId() +" sending election request to " +
                        greaterMember.getId());
                try {
                    //Response response = transport.dispatchTo(greaterMember, electionRequest);
                } catch (Exception problem) {
                    System.out.println("Problem encountered dispatching election rqequest to member: " +
                            greaterMember.getId() + " with "+ problem);
                }
            }
        }
    }

    private void sendElection(){
        JsonNode electionNode = restHelperBlackboard.sendGet("/election");
    }


}
