package hawhamburg.controller;

import hawhamburg.model.CommunicationParticipant;
import hawhamburg.model.Message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MessageController {

    private static MessageController instance;
    ConcurrentHashMap<String, CommunicationParticipant> participantMap = new ConcurrentHashMap<>();
    private Map<String,String> enteredUsers = new ConcurrentHashMap<>();

    // holds the authToken for a specific user
    private ConcurrentHashMap<String, List<Message>> messagesMap = new ConcurrentHashMap<>();
    private MessageController() {

    }

    public synchronized static MessageController getInstance() {
        if (instance == null) {
            instance = new MessageController();
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

    public void addParticipant(CommunicationParticipant adventurer){
        if(adventurer != null){
            participantMap.put(adventurer.getId(),adventurer);
        }
    }

    public Collection<CommunicationParticipant> getAllParticipant() throws Exception {
        return participantMap.values();
    }

    public CommunicationParticipant getParticipant(String id) throws Exception{
        return participantMap.get(id);
    }

    public void deleteAdventurer(String id){
        participantMap.remove(id);
    }


}
