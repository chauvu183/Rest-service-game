package hawhamburg.controller;

import hawhamburg.model.Message;
import hawhamburg.response.TravernInfoResponse;
import hawhamburg.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MessageController {

    private static MessageController instance;
    ConcurrentHashMap<String,User> users = new ConcurrentHashMap<>();
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

    public List<TravernInfoResponse> getAllUserContact() throws Exception {
        // TODO give all the User contact
        return null;
    }

    public TravernInfoResponse getUsesrContact() throws Exception{
        return null;
    }


}
