package hawhamburg.controller;


import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


import com.google.gson.Gson;

import hawhamburg.RestHelper;
import hawhamburg.entities.adventure.Adventurer;
import hawhamburg.entities.basic.*;
import hawhamburg.entities.group.Assignment;
import hawhamburg.entities.group.Group;
import hawhamburg.entities.group.Hiring;
import hawhamburg.logic.BullyAlgo;
import hawhamburg.service.HeroService;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class GameController {
    public static Scanner userInput = new Scanner((System.in));
    public static boolean continuing = true;
    public static boolean online = true;
    private HeroService heroService;

    private static final String PROTOCOL = "http";
    private static final String blackboardServerURL = "http://172.27.0.6:5000"; // TODO nicht hard code
    //private ServerSocket socket = new ServerSocket(0);
    private String localURL = InetAddress.getLocalHost().getHostAddress() ;
    private String heroServerURL;

    Gson gson = new Gson();
    RestHelper restHelperBlackboard = new RestHelper();
    RestHelper restHelperHero = new RestHelper();
    User user = null;
    char playerChoice =' ';
    Quest quest;
    Link link;
    Task firstTask;

    public GameController(int port) throws IOException {
        //Hero Server is always on port 4567
        heroServerURL = String.format("%s://%s:%d", PROTOCOL, localURL, port);
        heroService = new HeroService(port);
        restHelperHero.baseUrl = heroServerURL;
        restHelperBlackboard.baseUrl = blackboardServerURL;
        startGame();
    }


    public void startGame() throws IOException{
    	//ms = new MessageService();
        intro();
        try{
            while(playerChoice != '1' && playerChoice != '2' && playerChoice != '3') {
                playerChoice = userInput.next().charAt(0);
                if(playerChoice != '1' && playerChoice != '2' && playerChoice != '3')
                    System.out.println("\nPlease enter '1', '2' or '3'");
            }
            switch (playerChoice){
                case '1':{
                    registerUser();
                    userInput.reset();
                    loginInBlackboard(user);
                    play();
                }break;
                case'2':{
                    login();
                    play();
                }break;
                case 3:{
                    endGame();
                }break;
            }
        }catch (Exception e){
            System.out.println("Something went wrong");
        }
    }

    void intro() {
        System.out.println("__          __  _                            _______      ______                         \n" +
                "\\ \\        / / | |                          |__   __|    |___  /                         \n" +
                " \\ \\  /\\  / /__| | ___ ___  _ __ ___   ___     | | ___      / / ___  _ __ __ _ _ __ ___  \n" +
                "  \\ \\/  \\/ / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\    | |/ _ \\    / / / _ \\| '__/ _` | '_ ` _ \\ \n" +
                "   \\  /\\  /  __/ | (_| (_) | | | | | |  __/    | | (_) |  / /_| (_) | | | (_| | | | | | |\n" +
                "    \\/  \\/ \\___|_|\\___\\___/|_| |_| |_|\\___|    |_|\\___/  /_____\\___/|_|  \\__,_|_| |_| |_|");
        System.out.println("\n Welcome traveler to this mysterious trial you will be facing!");
        System.out.println(" This is this magical land , ruled by the tyrannical sorceror Zoram.");
        System.out.println(" We bid you welcome!");
        System.out.println(" Since you accept the challenge to defeat Zoram (which main character doesn't?)");
        System.out.println(" You must choose...");
        System.out.println("\t1) New Game - Register as new User");
        System.out.println("\t2) Continue - Login to your World");
        System.out.println("\t3) Exit");
    }

    private void registerUser(){
        System.out.print("\nTell us about yourself...\nWhat is your name traveler?\n");
        String name = userInput.next();
        System.out.print("\nSuch an outlandish name dear " + name);
        System.out.print("\nYou are fully cloaked and we want to know your secret code?\n ");
        String password = userInput.next();
        user = new User(name, password);
        //register as new User
        String registerNewUser = registerInBlackboard(user);
        while(registerNewUser.equals("Username already taken")){
            System.out.print("\nChoose another name. \nWhat is your name traveler?\n");
            String newName = userInput.next();
            System.out.print("\nSuch an outlandish name dear " + newName);
            System.out.print("\nYou are fully cloaked and we want to know your secret code?\n ");
            String newPassword = userInput.next();
            user = new User(newName, newPassword);
            registerNewUser = registerInBlackboard(user);
        }
        System.out.print("\nNice done! Welcome to Adventure World! \n");
    }

    private String registerInBlackboard(User user){
        String userData = "{\"name\":\""+ user.name + "\",\"password\":\""+user.password+"\"}";
        JsonNode response = restHelperBlackboard.sendPost("/users",userData);
        return response.getObject().getString("message");
    }

    private void login(){
        System.out.println( "============================================================================================= \n" );
        System.out.println("__      __   _                    _____     __   __                _      _             _                \n" +
                "\\ \\    / /__| |__ ___ _ __  ___  |_   _|__  \\ \\ / /__ _  _ _ _    /_\\  __| |_ _____ _ _| |_ _  _ _ _ ___ \n" +
                " \\ \\/\\/ / -_) / _/ _ \\ '  \\/ -_)   | |/ _ \\  \\ V / _ \\ || | '_|  / _ \\/ _` \\ V / -_) ' \\  _| || | '_/ -_)\n" +
                "  \\_/\\_/\\___|_\\__\\___/_|_|_\\___|   |_|\\___/   |_|\\___/\\_,_|_|   /_/ \\_\\__,_|\\_/\\___|_||_\\__|\\_,_|_| \\___|\n" +
                "                          ");
        System.out.println( "============================================================================================= \n" );
        System.out.print("\nPlease enter your adventure name\n");
        String loginName = userInput.next();
        System.out.print("\nPlease enter your adventure secret code\n");
        String loginPassword = userInput.next();
        user = new User(loginName,loginPassword);
        boolean isValid = loginInBlackboard(user);
        int i = 0;
        while (i < 3 && !isValid) {
            userInput.reset();
            System.out.print("\nPlease enter your adventure name again\n");
            String loginNameAgain = userInput.next();
            System.out.print("\nPlease enter your adventure secret code\n");
            String loginPasswordAgain = userInput.next();
            user = new User(loginNameAgain,loginPasswordAgain);
            isValid = loginInBlackboard(user);
            //break if the login detail is correct
            if(isValid){
                break;
            }
            i++;
        }

    }

    boolean loginInBlackboard(User user){
        return restHelperBlackboard.login(user);
    }

    void play() throws Exception {
        while(online){
            System.out.println("\n=========================================================\n");
            System.out.println(" /\\_/\\___  _   _ _ __    /\\/\\ (_)___ ___(_) ___  _ __  \n" +
                    "\\_ _/ _ \\| | | | '__|  /    \\| / __/ __| |/ _ \\| '_ \\ \n" +
                    " / \\ (_) | |_| | |    / /\\/\\ \\ \\__ \\__ \\ | (_) | | | |\n" +
                    " \\_/\\___/ \\__,_|_|    \\/    \\/_|___/___/_|\\___/|_| |_|" +
                    "                                                              ");
            System.out.println("===========================================================\n");
            //choose which task to do (register at tavern or solve the quest or quit)

            System.out.println("Welcome, which tasks do you want to perform ?\n");
            System.out.println(" You must choose...");
            System.out.println("\t1) Solve the Quests");
            System.out.println("\t2) Enter Tavern, where all the heros gather");
            System.out.println("\t3) Logout");
            playerChoice = ' ';
            while(playerChoice != '1' && playerChoice != '2' && playerChoice != '3') {
                playerChoice = userInput.next().charAt(0);
                if(playerChoice != '1' && playerChoice != '2' && playerChoice != '3')
                    System.out.println("\nPlease enter '1', '2' or '3'");
            }

            switch(playerChoice){
                case '1':{
                    //Solve Quest
                    System.out.println(" To earn your sporns you have to fulfill quests.\n" +
                            " Those quests require you to accept a quest,\nand then visit the location in question to do your deed.\n" +
                            " But start slowly, see which quests are available \n" +
                            " and list them so you can choose which quest to attend.");
                    System.out.println("\n Do you want to fullfil those quests?\nPress 'y' to continue or 'q' to exit game");
                    getUserInput();
                    //See the quests
                    if(continuing) getQuest();
                    else endGame();
                } break;
                case '2':{
                    userInput.reset();
                    System.out.println(" _______                                       \n" +
                            " |__   __|                                      \n" +
                            "    | |      __ _  __   __   ___   _ __   _ __  \n" +
                            "    | |     / _` | \\ \\ / /  / _ \\ | '__| | '_ \\ \n" +
                            "    | |    | (_| |  \\ V /  |  __/ | |    | | | |\n" +
                            "    |_|     \\__,_|   \\_/    \\___| |_|    |_| |_|\n" +
                            "                                                \n" +
                            "                                                ");
                    //Register at Tavern
                    System.out.println("Welcome to TAVERN, where you can communicate with other heros\n");
                    enterTavern();
                }break;
                case '3':{
                    login();
                    play();
                }break;
            }
        }
    }

    void getQuest() {
		JsonNode node = restHelperBlackboard.sendGet("/blackboard/quests");
		String nodeString = node.getObject().getJSONArray("objects").getJSONObject(0).toString();
		quest = gson.fromJson(nodeString, Quest.class);
		System.out.println("\n" + quest.getDescription());
		getTask(quest);
	}

	void getTask(Quest quest) {
		link = gson.fromJson(quest.get_links().toString(), Link.class);
		String linkString = link.tasks;
		JsonNode tasks = restHelperBlackboard.sendGet(linkString);
		String taskString = tasks.getObject().getJSONArray("objects").getJSONObject(0).toString();
		firstTask = gson.fromJson(taskString, Task.class);
		System.out.println(firstTask.getDescription());
		System.out.println("\n Are you ready to solve this quest?"
				+ "\n Enter 'm' to visit the locations to solve the quest" + "\n Press 'q' to exit game ");
		char answer2 = userInput.next().charAt(0);
		while (answer2 != 'm' && answer2 != 'q') {
			System.out.println("\n Please enter again");
			answer2 = userInput.next().charAt(0);
		}
		if (answer2 == 'q')
			continuing = false;
		if (continuing) {
			// see the location
			JsonNode location = restHelperBlackboard.sendGet(firstTask.location);
			// String tasksCount = location.
			// getObject().getJSONObject("object").getString("tasks");
			// solve the first quest
			String host = location.getObject().getJSONObject("object").getString("host");
			RestHelper h2 = new RestHelper();
			h2.baseUrl = "http://" + host;
			JsonNode visits = h2.sendGet(firstTask.resource);
			System.out.println("\n " + visits.getObject().getString("message"));

			// trying to make the quest solving as generic as possible...
			// we check if the result contains a field named next which could contain additional steps
			// if not, handling for the first quest is the same in the else block
			String preDelivery = "";
			if (visits.getObject().has("next")) {
				String nextVisit = visits.getObject().getString("next");
				preDelivery = nextVisit;
				JsonNode visitRats = h2.sendGet(nextVisit);
				// Read all steps to todos
				JSONArray stepsTodo = visitRats.getObject().getJSONArray("steps_todo");
				Iterator<Object> ir = stepsTodo.iterator();
				List<String> gainedTokens = new ArrayList<>();
//				List<String> taskUris = new ArrayList<>();
				// With iterator through all steps
				while (ir.hasNext()) {
					Object next = ir.next();
					if (next instanceof String) {
						String nextRat = (String) next;
//						taskUris.add(nextRat);
//						getUserInput();
						// Send get to next step
						h2.sendGet(nextRat);
						// in the response it is mentioned that we need to post something to kill the rat
						JsonNode postResponse = h2.sendPost(nextRat, "");

						String token = postResponse.getObject().getString("token");
						gainedTokens.add(token);
						System.err.println("We got our token: " + token);
					}
				}
				System.out.println("gainedTokens size " + gainedTokens.size());
				solveTask(firstTask.getlink().get("self").toString(), preDelivery,host, gainedTokens);
			} else {
				System.out
						.println("Do you want to do this?" + "\n Enter 'y' to solve it" + "\n Press 'q' to exit game ");
				getUserInput();
				if (continuing) {
					JsonNode postVisits = h2.sendPost(firstTask.resource, "\n");
					System.out.println(firstTask.resource);
					System.out.print("Get token to resolve the first quest\n");
					System.out.println(" Write 't' to get the Token to solve quest");
					char answer3 = userInput.next().charAt(0);
					while (answer3 != 't') {
						System.out.println("Please enter again");
						answer3 = userInput.next().charAt(0);
						if (answer3 == 't') {
							break;
						}
					}
					solveTask(firstTask.getlink().get("self").toString(), null, null,Arrays.asList(postVisits.getObject().getString("token")));

				}
			}
		} else {
			endGame();
		}
		userInput.reset();
	}

	/**
	 * solve task is an generic method to solve a task...
	 * it takes a task and a tokens list, since some task require several tokens
	 *
	 *
	 * @param tokens
	 */
	void solveTask(String taskLink,String preDelivery, String preDeliveryHost, List<String> tokens) {
		// String token = postVisits.getObject().getString("token");
		JsonNode finalResult = null;
		// get the correct url /blackboard/tasks/1
		if (preDelivery != null) {
			RestHelper h2 = new RestHelper();
			h2.baseUrl = "http://" + preDeliveryHost;
			System.out.println("sending prelivery to " + preDelivery);
			JSONObject inputData = new JSONObject();
			inputData.put("tokens", tokens);
			JsonNode preResult = h2.sendPost(preDelivery, inputData.toString());
			String finalToken = preResult.getObject().getString("token");

			JSONObject inputData2 = new JSONObject();
			System.out.println("final token is" + finalToken);
			JSONObject taskLinkWithToken = new JSONObject();
			taskLinkWithToken.put(taskLink, finalToken);
			inputData2.put("tokens", taskLinkWithToken);
			System.out.println("link deliveries are " + link.deliveries);
			finalResult = restHelperBlackboard.sendPost(link.deliveries, "{\"tokens\":{"+ taskLink +":\""+finalToken+"\"}}");
		} else {
			JSONObject inputData2 = new JSONObject();
			System.out.println("final token is" + tokens.get(0));
			JSONObject taskLinkWithToken = new JSONObject();
			taskLinkWithToken.put(taskLink, tokens.get(0));
			inputData2.put("tokens", taskLinkWithToken);
		    System.out.println("link deliveries are " + link.deliveries);
			finalResult = restHelperBlackboard.sendPost(link.deliveries, "{\"tokens\":{"+ taskLink +":\""+tokens.get(0)+"\"}}");
		}

		String status = finalResult.getObject().getString("status");
		if (status.equals("success")) {
            System.out.println(" ________      ________      ________       ________      ________      ________      _________    ________      \n" +
                    "|\\   ____\\    |\\   __  \\    |\\   ___  \\    |\\   ____\\    |\\   __  \\    |\\   __  \\    |\\___   ___\\ |\\   ____\\     \n" +
                    "\\ \\  \\___|    \\ \\  \\|\\  \\   \\ \\  \\\\ \\  \\   \\ \\  \\___|    \\ \\  \\|\\  \\   \\ \\  \\|\\  \\   \\|___ \\  \\_| \\ \\  \\___|_    \n" +
                    " \\ \\  \\        \\ \\  \\\\\\  \\   \\ \\  \\\\ \\  \\   \\ \\  \\  ___   \\ \\   _  _\\   \\ \\   __  \\       \\ \\  \\   \\ \\_____  \\   \n" +
                    "  \\ \\  \\____    \\ \\  \\\\\\  \\   \\ \\  \\\\ \\  \\   \\ \\  \\|\\  \\   \\ \\  \\\\  \\|   \\ \\  \\ \\  \\       \\ \\  \\   \\|____|\\  \\  \n" +
                    "   \\ \\_______\\   \\ \\_______\\   \\ \\__\\\\ \\__\\   \\ \\_______\\   \\ \\__\\\\ _\\    \\ \\__\\ \\__\\       \\ \\__\\    ____\\_\\  \\ \n" +
                    "    \\|_______|    \\|_______|    \\|__| \\|__|    \\|_______|    \\|__|\\|__|    \\|__|\\|__|        \\|__|   |\\_________\\\n" +
                    "                                                                                                     \\|_________|");;
		}

	}

    void enterTavern() throws Exception {
        String heroclass = null;
        Adventurer adventurer = null;
        JsonNode adventurerRequest = restHelperBlackboard.sendGet("/taverna/adventurers/"+ user.name);
        if(adventurerRequest.getObject().getString("status").equals( "error")){
                System.out.println("\nYou haven't registered as an adventurer.");
                System.out.println("Firstly please subscribe yourself with a Hero class");
                System.out.println("Which hero class do you want to register?");
                heroclass = userInput.next();
                System.out.println("Nice done!");
                //register to Hero Service;
                String userURL = heroServerURL + "/adventures/"+ user.getName();
                String data =  "{\"heroclass\":\""+ heroclass + "\",\"url\":\""+userURL+"\"}";
                JsonNode request = restHelperBlackboard.sendPost("/taverna/adventurers",data);
                sendToHeroService();
                JsonNode newAdventurerRequest = restHelperBlackboard.sendGet("/taverna/adventurers/"+ user.name);
                String adventurerString = newAdventurerRequest.getObject().getJSONObject("object").toString();
                adventurer = gson.fromJson(adventurerString, Adventurer.class);
        }else{
            String adventurerString = adventurerRequest.getObject().getJSONObject("object").toString();
            adventurer = gson.fromJson(adventurerString, Adventurer.class);
            if(adventurer.getUrl().contains(localURL)){
                sendToHeroService();
            }
        }

        System.out.println("Your Rolle: " + adventurer.getHeroclass());
        System.out.println("Everyone can contact you through this URL: " + adventurer.getUrl());
        System.out.println("\nDo you want to create a new group or access to you old group?");
        System.out.println(" You must choose...");
        System.out.println("\t1) Create a new group");
        System.out.println("\t2) Access to your old group");
        System.out.println("\t3) Join another group");
        System.out.println("\t4) Election");
        System.out.println("\t5) Logout");

        playerChoice = ' ';
        while(playerChoice != '1' && playerChoice != '2' && playerChoice != '3' && playerChoice != '4' && playerChoice != '5') {
            playerChoice = userInput.next().charAt(0);
            if(playerChoice != '1' && playerChoice != '2' && playerChoice != '3' && playerChoice != '4'&& playerChoice != '5')
                System.out.println("\nPlease enter '1', '2' ,'3' ,'4' or '5'");
        }
        switch (playerChoice){
            case '1':{
                createGroupInTavern();
                sleep();
                accessToOldGroup(adventurer);
            }break;
            case '2':{
                accessToOldGroup(adventurer);
            }break;
            case '3':{
                joinAnotherGroup();
                sleep();
                accessToOldGroup(adventurer);
            }break;
            case '4':{
                election(adventurer);
            }break;
            case '5':{
                login();
                play();
            }break;
        }
    }

    private void election(Adventurer adventurer) throws Exception {
        BullyAlgo bullyAlgo = new BullyAlgo();
        bullyAlgo.electCoordinator();
    }

    private void sendElection(){
        JsonNode electionNode = restHelperHero.sendGet("/election");
    }


    private void declareVictory(){

    }

    void sendToHeroService(){
        String data = "{\"name\":\""+ user.name +"\"}";
        restHelperHero.sendPost("/adventures",data);
    }

    void createGroupInTavern(){
        JsonNode groupRequest = restHelperBlackboard.sendPost("/taverna/groups","\n");
        String groupString = groupRequest.getObject().getJSONArray("object").getJSONObject(0).toString();
        Group group = gson.fromJson(groupString, Group.class);
        user.setOwnerOfGroup(group);
        System.out.println("Great! You have a group in Tavern! \n");
        System.out.println("Your Group Id is " + group.getId() +"\n");
        System.out.println("Which Quest do you together with your teammate want to solve? \n");
        char quest = userInput.next().charAt(0);
        int questId = Integer.parseInt(Character.toString(quest));
        String data = "{\"group\":\""+ group.getId() + "\",\"quest\":\""+questId+"\"}";
        restHelperHero.sendPost("/adventures/hirings/" + user.name,data);
    }

    void accessToOldGroup(Adventurer adventurer){
        System.out.println("WELCOME BACK TO YOUR GROUP IN TAVERNA \n");
        String groupID = adventurer.getGroup();
        // Access to old Group and see the assignments
        JsonNode groupRequest = restHelperBlackboard.sendGet("/taverna/groups/" + groupID);
        String groupString = groupRequest.getObject().getJSONObject("object").toString();
        Group group = gson.fromJson(groupString, Group.class);
        System.out.println("Your Group ID is " + groupID);
        System.out.println("There are " + group.getMembers().length +" members in your group.\n");
        System.out.println("Here are member names: ");
        for(int x = 0; x < group.getMembers().length; x++){
            System.out.println(group.getMembers()[x] );
        }
        boolean giveTasksDone = true;
        if(group.getOwner().equals(user.name)){
            user.setOwnerOfGroup(group);
            System.out.println("You are the owner of this group.");
            System.out.println("As a group owner you can assign tasks for your member or hiring other member\n");
            if(group.getMembers().length > 1){
                System.out.println(" You must choose...");
                System.out.println("\t1) Hiring");
                System.out.println("\t2) Give assignment");
                playerChoice = ' ';
                while(playerChoice != '1' && playerChoice != '2' ) {
                    playerChoice = userInput.next().charAt(0);
                    if(playerChoice != '1' && playerChoice != '2' )
                        System.out.println("\nPlease enter '1', '2'");
                }
                switch (playerChoice){
                    case '1':{
                       createHirings();
                    }break;
                    case '2':{
                        giveAssignment(giveTasksDone);
                    }break;
                }

            }else{
                System.out.println("You are the only member in this group.\n"
                        +"Wait for other to join or create Hiring\n");
                createHirings();

            }
        }else{
            receiveAssignment(adventurer);
        }

    }

    void giveAssignment(boolean giveTasksDone){

        while(giveTasksDone){
            Assignment assignment = new Assignment();
            System.out.println("Which member do you want to choose ?");
            String memberToAssign = userInput.next();
            System.out.println("What is the ID of this task?");
            assignment.setId(userInput.next());
            System.out.println("Which Task ?");
            assignment.setTask(userInput.next()) ;
            System.out.println("What is resources for this task?");
            assignment.setResource( userInput.next());
            System.out.println("Which method ?");
            assignment.setMethod(userInput.next());
            System.out.println("Data about tasks?");
            assignment.setData(userInput.next());

            JsonNode assignmentsRequestNode = restHelperBlackboard.get(getAdventure(memberToAssign).getUrl());
            String assignmentsURL = assignmentsRequestNode.getObject().getString("assignments");

            System.out.println("Please send some messages to your member?");
            assignment.setMessage(userInput.next());
            assignment.setCallback( assignmentsURL +"/delivered");

            System.out.println("Sending Hiring request to "+ getAdventure(memberToAssign).getUrl());
            System.out.println(assignmentsURL);
            restHelperHero.post(assignmentsURL,gson.toJson(assignment));
            System.out.println("Do you want to assign Tasks to other member?");
            getUserInput();
            if(!continuing){
                break;
            }
        }
    }

    void receiveAssignment(Adventurer adventurer){
        sleep();
        JsonNode assignmentUrlNode = restHelperBlackboard.get(adventurer.getUrl());
        String assignmentUrlString = assignmentUrlNode.getObject().getString("assignments");
        JsonNode receivedAssignmentNode = restHelperBlackboard.get(assignmentUrlString);
        String receivedAssignmentString = receivedAssignmentNode.getObject().toString();
        Assignment receivedAssignment = gson.fromJson(receivedAssignmentString, Assignment.class);
        System.out.println("Do you want to see your assignment?");
        getUserInput();
        if(continuing){
            System.out.println("*************Assignments**************");
            System.out.println(receivedAssignment);
            System.out.println("**************************************");
        }
        System.out.println("Do you want to deliver Assignment?");
        getUserInput();
        if(continuing){
          deliverAssignment(receivedAssignment);
        }
	}

	void deliverAssignment(Assignment assignment){
        System.out.println("Which is the uri to the user send the tasks?");
        String user = userInput.next();
        System.out.println("Send some message");
        String message = userInput.next();

        //set new deliver assignment
	    Assignment assignment1 = new Assignment();
	    assignment1.setId(assignment.getId());
	    assignment1.setTask(assignment.getTask());
	    assignment1.setResource(assignment.getResource());
	    assignment1.setMethod(assignment.getMethod());
	    assignment1.setData(assignment.getData());
        assignment1.setUser(user);
        assignment1.setMessage(message);

        //send to heroService
        JsonNode assignmentsRequestNode = restHelperBlackboard.post(assignment.getCallback(),gson.toJson(assignment));
        JsonNode deliveredassignmentsRequestNode = restHelperBlackboard.get(assignment.getCallback());
        String deliveredassignment = deliveredassignmentsRequestNode.getObject().getJSONObject("data").toString();
        System.out.println("deliver the assignments");
        System.out.println(deliveredassignment);
    }


    void joinAnotherGroup(){
        System.out.println("Which adventurer group do you want to join ? Enter group id.\n");
        String groupID = userInput.next();
        System.out.println("Which quest do you want to solve ? Enter quest\n");
        String quest = userInput.next();
        String data = "{\"group\":\""+ groupID + "\",\"quest\":\""+quest+"\"}";
        restHelperHero.sendPost("/adventures/hirings/" + user.name,data);
        userInput.reset();
    }


    void createHirings(){
        System.out.println("Which adventurer do you want to hire ? Enter an user name.");
        String hiringUserName = userInput.next();
        System.out.println("Enter the Group id.");
        String groupId = userInput.next();
        System.out.println("Which quest should this adventurer solve?");
        String questId = userInput.next();
        System.out.println("Enter a message?");
        String message = userInput.next();
        Hiring hiring = new Hiring("/taverna/groups" + "/"+ groupId,questId, message);
        System.out.println("Sending Hiring request to "+ getAdventure(hiringUserName).getUrl());
        JsonNode hiringRequestNode = restHelperBlackboard.get(getAdventure(hiringUserName).getUrl());
        String hiringURL = hiringRequestNode.getObject().getString("hirings");
        restHelperBlackboard.post(hiringURL,gson.toJson(hiring));
	}

     private Adventurer getAdventure(String username){
        JsonNode adventurerRequest = restHelperBlackboard.sendGet("/taverna/adventurers/"+ username);
        String adventurerString = adventurerRequest.getObject().getJSONObject("object").toString();
        Adventurer adventurer = gson.fromJson(adventurerString, Adventurer.class);
        return adventurer;
	}

    void getUserInput(){
        char answer = userInput.next().charAt(0);
        while(answer != 'y' && answer != 'q'){
            System.out.println("\n Please enter again");
            answer = userInput.next().charAt(0);
        }
        if (answer == 'q'){
            continuing = false;
        }else{
            continuing = true;
        };
    }

    void getMap(){
        JsonNode request = restHelperBlackboard.sendGet("/map");
        JSONArray mapArray = request.getObject().getJSONArray("objects");
        for (int x =0; x < mapArray.length();x++){
            Location location = gson.fromJson(mapArray.getJSONObject(x).toString(),Location.class);
            System.out.println(location);
        }

    }

    void userInformation(User user){
        JsonNode request = restHelperBlackboard.sendGet("/users/"+ user.name);
         String userObject = request.getObject().getJSONObject("object").toString();

         User userData = gson.fromJson(userObject, User.class);
        System.out.println(userData);
    }

    void whoami(User user){
	    JsonNode request = restHelperBlackboard.sendGet("/whoami");
        String userObject = request.getObject().getJSONObject("user").toString();
        User userData = gson.fromJson(userObject, User.class);
        System.out.println(userData);
    }


    void logOut() throws Exception {
        login();
        play();
    }

    void endGame(){
        System.out.println("Until next time!");
        System.exit(0);
    }

    private static void sleep() {
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            System.out.println("Sleep interrupted: " + e);
        }
    }
}
