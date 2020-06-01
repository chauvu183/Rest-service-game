package hawhamburg.controller;

import hawhamburg.entities.adventure.HeroParticipant;
import hawhamburg.entities.adventure.Message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HeroController {

    private static HeroController instance;
    ConcurrentHashMap<Double, HeroParticipant> participantMap = new ConcurrentHashMap<>();
    ConcurrentHashMap<String, HeroParticipant> participantMapWithName = new ConcurrentHashMap<>();
    private Map<String,String> enteredUsers = new ConcurrentHashMap<>();

    // holds the authToken for a specific user
    private ConcurrentHashMap<String, List<Message>> messagesMap = new ConcurrentHashMap<>();
    private HeroController() {

    }

    public synchronized static HeroController getInstance() {
        if (instance == null) {
            instance = new HeroController();
        }
        return instance;
    }

    public void sendMessage(String goalId, Message message) throws Exception{
        List<Message> messages = messagesMap.get(goalId);
        if (message != null && goalId != null && messages == null) {
            messages = new ArrayList<>();
            messagesMap.put(goalId, messages);
        } else {
            throw new Exception("send Message parameter wrong");
        }
        messages.add(message);


        System.out.println(messagesMap);
    }

    public void enter(String userId, String communicationUrl) throws Exception{
        if (userId != null && communicationUrl != null ) {
            enteredUsers.put(userId, communicationUrl);
        } else {
            throw new Exception("User cannot enter without communication url");
        }
    }

    public void addParticipant(HeroParticipant adventurer){
        if(adventurer != null){
            participantMap.put(adventurer.getId(),adventurer);
        }
    }

    public void addParticipantByName(HeroParticipant adventurer){
        if(adventurer != null){
            participantMapWithName.put(adventurer.getUserName(),adventurer);
        }
    }


    public Collection<HeroParticipant> getAllParticipant() throws Exception {
        return participantMap.values();
    }

    public HeroParticipant getParticipant(String id) throws Exception{
        return participantMap.get(id);
    }

    public HeroParticipant getParticipantByName(String name) throws Exception{
        System.out.println(participantMapWithName);
        return participantMapWithName.get(name);
    }

    public void deleteAdventurer(String id){
        participantMap.remove(id);
    }


}
