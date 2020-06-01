package hawhamburg.controller;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;


import com.google.gson.Gson;

import hawhamburg.RestHelper;
import hawhamburg.entities.adventure.Adventurer;
import hawhamburg.entities.basic.Link;
import hawhamburg.entities.basic.Quest;
import hawhamburg.entities.basic.Task;
import hawhamburg.entities.group.Group;
import hawhamburg.entities.basic.User;
import hawhamburg.service.HeroService;
import kong.unirest.JsonNode;

public class GameController {
    public static Scanner userInput = new Scanner((System.in));
    public static boolean continuing = true;
    private HeroService heroService;

    private static final String PROTOCOL = "http";
    private static final String blackboardServerURL = "http://172.27.0.6:5000";
    private Integer localHost = 4567;
    private String localURL = InetAddress.getLocalHost().getHostAddress() ;
    private String heroServerURL;

    Gson gson = new Gson();
    RestHelper restHelperBlackboard = new RestHelper();
    User user = null;
    char playerChoice =' ';
    Quest quest;
    Link link;
    Task firstTask;
    //MessageService ms;


    public GameController() throws UnknownHostException {
        heroServerURL = String.format("%s://%s:%d", PROTOCOL, localURL, localHost);
        heroService = new HeroService();
        startGame();
    }

    public void startGame(){
    	//ms = new MessageService();
        intro();
        restHelperBlackboard.baseUrl = blackboardServerURL;

        while(playerChoice != '1' && playerChoice != '2' && playerChoice != '3') {
            playerChoice = userInput.next().charAt(0);
            if(playerChoice != '1' && playerChoice != '2' && playerChoice != '3')
                System.out.println("\nPlease enter '1', '2' or '3'");
        }

        switch (playerChoice){
            case '1':{
                System.out.print("\nTell us about yourself...\nWhat is your name traveler?\n");
                String name = userInput.next();
                System.out.print("\nSuch an outlandish name dear " + name);
                System.out.print("\nYou are fully cloaked and we want to know your secret code?\n ");
                String password = userInput.next();
                user = new User(name, password);
                //register as new User
                String registerNewUser = register(user);
                while(registerNewUser.equals("Username already taken")){
                    System.out.print("\nChoose another name. \nWhat is your name traveler?\n");
                    String newName = userInput.next();
                    System.out.print("\nSuch an outlandish name dear " + newName);
                    System.out.print("\nYou are fully cloaked and we want to know your secret code?\n ");
                    String newPassword = userInput.next();
                    user = new User(newName, newPassword);
                    registerNewUser = register(user);
                }
                System.out.print("\nNice done! Welcome to Adventure World! \n");
                userInput.reset();
                login(user);
                play();
            }break;
            case'2':{
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
                //ms.username = loginName;
                boolean isValid = login(user);
                int i = 0;
                while (i < 3 && !isValid) {
                    userInput.reset();
                    System.out.print("\nPlease enter your adventure name again\n");
                    String loginNameAgain = userInput.next();
                    System.out.print("\nPlease enter your adventure secret code\n");
                    String loginPasswordAgain = userInput.next();
                    user = new User(loginNameAgain,loginPasswordAgain);
                    isValid = login(user);
                    //break if the login detail is correct
                    if(isValid){
                        break;
                    }
                    i++;
                }
                play();

            }break;
            case 3:{
               endGame();
            }break;
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

    private String register(User user){
        String userData = "{\"name\":\""+ user.name + "\",\"password\":\""+user.password+"\"}";
        JsonNode response = restHelperBlackboard.sendPost("/users",userData);
        return response.getObject().getString("message");
    }

    boolean login(User user){
        return restHelperBlackboard.login(user);
    }

    void play(){
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
        System.out.println("\t3) Exit");
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
                //Register at Tavern
                System.out.println("Welcome to Tavern, where you can communicate with other heros\n");
                System.out.println("Firstly please subscribe yourself with a Hero class");
                System.out.println("Which hero class do you want to register?\n");
                String heroclass = userInput.next();
                System.out.println("Nice done!");
                enterTavern(heroclass);
            }break;
            case '3':{
                endGame();
            }break;
        }

    }

    void getQuest(){
        JsonNode node = restHelperBlackboard.sendGet("/blackboard/quests");
        String nodeString = node.getObject().getJSONArray("objects").getJSONObject(0).toString();
        quest = gson.fromJson(nodeString, Quest.class);
        System.out.println("\n"+quest.getDescription());
        getTask(quest);
    }

    void getTask(Quest quest){
        link = gson.fromJson(quest.get_links().toString(),Link.class);
        String linkString = link.tasks;
        JsonNode tasks = restHelperBlackboard.sendGet(linkString);
        String taskString = tasks.getObject().getJSONArray("objects").getJSONObject(0).toString();
        firstTask = gson.fromJson(taskString, Task.class);
        System.out.println(firstTask.getDescription());
        System.out.println("\n Are you ready to solve this quest?"
                +"\n Enter 'm' to visit the locations to solve the quest"
                +"\n Press 'q' to exit game ");
        char answer2 = userInput.next().charAt(0);
        while(answer2 != 'm' && answer2 != 'q'){
            System.out.println("\n Please enter again");
            answer2 = userInput.next().charAt(0);
        }
        if (answer2 == 'q') continuing = false;
        if(continuing){
            //see the location
            JsonNode location = restHelperBlackboard.sendGet(firstTask.location);
            // solve the first quest
            String host = location. getObject().getJSONObject("object").getString("host");
            RestHelper h2 = new RestHelper();
            h2.baseUrl = "http://"+host;
            JsonNode visits = h2.sendGet(firstTask.resource);
            System.out.println("\n "+visits.getObject().getString("message"));
            
            System.out.println("Do you want to do this?"
                    +"\n Enter 'y' to solve it"
                    +"\n Press 'q' to exit game ");
            getUserInput();
            if(continuing){
                JsonNode postVisits = h2.sendPost(firstTask.resource, "\n");
                System.out.println(firstTask.resource);
                System.out.print("Get token to resolve the first quest\n");
                System.out.println(" Write 't' to get the Token to solve quest");
                char answer3 = userInput.next().charAt(0);
                while (answer3 !='t'){
                    System.out.println("Please enter again");
                    answer3 = userInput.next().charAt(0);
                    if(answer3 == 't'){
                        break;
                    }
                }
                solveFirstTask(postVisits);
            }
        }else{
            endGame();
        }
        userInput.reset();

    }
    void solveFirstTask(JsonNode postVisits){
        String token = postVisits.getObject().getString("token");
        System.out.println(" This is your Token : "+ token);
        System.out.println("\n Use this Token to confirm you have resolved the quests");
        System.out.println("\n Confirmed?");
        getUserInput();
        String taskUrl = firstTask.getlink().get("self").toString();
        String inputData = "{\"tokens\":{"+ taskUrl +":\""+token+"\"}}";
        //get the correct url /blackboard/tasks/1
        JsonNode result = restHelperBlackboard.sendPost(link.deliveries,inputData);
        String status = result.getObject().getString("status");
        if(status.equals("success")){
            System.out.println(" _____                             _          __   __            _                     \n" +
                    "/  __ \\                           | |         \\ \\ / /           | |                    \n" +
                    "| /  \\/ ___  _ __   __ _ _ __ __ _| |_ ___     \\ V /___  _   _  | |__   __ ___   _____ \n" +
                    "| |    / _ \\| '_ \\ / _` | '__/ _` | __/ __|     \\ // _ \\| | | | | '_ \\ / _` \\ \\ / / _ \\\n" +
                    "| \\__/\\ (_) | | | | (_| | | | (_| | |_\\__ \\_    | | (_) | |_| | | | | | (_| |\\ V /  __/\n" +
                    " \\____/\\___/|_| |_|\\__, |_|  \\__,_|\\__|___( )   \\_/\\___/ \\__,_| |_| |_|\\__,_| \\_/ \\___|\n" +
                    "                    __/ |                 |/                                           \n" +
                    "                   |___/           ");

            System.out.println("           _               _   _   _             __      _     _    ___  ____         _             \n" +
                    "          | |             | | | | | |           / _|    (_)   | |   |  \\/  (_)       (_)            \n" +
                    " ___  ___ | |_   _____  __| | | |_| |__   ___  | |_ _ __ _ ___| |_  | .  . |_ ___ ___ _  ___  _ __  \n" +
                    "/ __|/ _ \\| \\ \\ / / _ \\/ _` | | __| '_ \\ / _ \\ |  _| '__| / __| __| | |\\/| | / __/ __| |/ _ \\| '_ \\ \n" +
                    "\\__ \\ (_) | |\\ V /  __/ (_| | | |_| | | |  __/ | | | |  | \\__ \\ |_  | |  | | \\__ \\__ \\ | (_) | | | |\n" +
                    "|___/\\___/|_| \\_/ \\___|\\__,_|  \\__|_| |_|\\___| |_| |_|  |_|___/\\__| \\_|  |_/_|___/___/_|\\___/|_| |_|");

        }
       }

    void solveOtherTask(){

    }

    void enterTavern(String heroclass){
        String userURL = heroServerURL + "/adventures/"+ user.getName();
        String data =  "{\"heroclass\":\""+ heroclass + "\",\"url\":\""+userURL+"\"}";

        JsonNode request = restHelperBlackboard.sendPost("/taverna/adventurers",data);

        sendToHeroService();

        String nodeString = request.getObject().getJSONArray("object").getJSONObject(0).toString();
        Adventurer adventurer = gson.fromJson(nodeString, Adventurer.class);
        System.out.println(adventurer);

        System.out.println("Do you want to create a new group or access to you old group?");
        System.out.println(" You must choose...");
        System.out.println("\t1) Create a new group");
        System.out.println("\t2) Access to your old group");
        System.out.println("\t3) Exit");
        playerChoice = ' ';
        while(playerChoice != '1' && playerChoice != '2' && playerChoice != '3') {
            playerChoice = userInput.next().charAt(0);
            if(playerChoice != '1' && playerChoice != '2' && playerChoice != '3')
                System.out.println("\nPlease enter '1', '2' or '3'");
        }
        switch (playerChoice){
            case 1:{
                createGroupInTavern();
            }break;
            case 2:{

            }break;
            case 3:{
                endGame();
            }break;
        }
        //createGroupInTavern();
    }

    void sendToHeroService(){
        RestHelper restHelperToHeroService = new RestHelper();
        //URI uri = new URI("http","heroServerURL",null);
        restHelperToHeroService.baseUrl = heroServerURL;
        System.out.println(heroServerURL);
        String data = "{\"name\":\""+ user.name + "\"}";
        restHelperToHeroService.sendPost("/adventures",data);
    }

    void createGroupInTavern(){
        //TODO check if the user have been register in tavern
        //HAFT DONE create a Group
        JsonNode groupRequest = restHelperBlackboard.sendPost("/taverna/groups","\n");
        String groupString = groupRequest.getObject().getJSONArray("object").getJSONObject(0).toString();
        Group group = gson.fromJson(groupString, Group.class);
        user.setOwnerOfGroup(group);
        Link linkToGroup = gson.fromJson(group.get_links().toString(),Link.class);
        String[] members = linkToGroup.members;
        System.out.println(group);
        System.out.println(linkToGroup);
        //assign that this user is the owner of
    }

    void accessToOldGroup(){
        System.out.println("Please enter your group id");
        String  userID = userInput.next();

    }


    void createHirings(User user){
        System.out.println("Which ");
    }

    void getUserInput(){
        char answer = userInput.next().charAt(0);
        while(answer != 'y' && answer != 'q'){
            System.out.println("\n Please enter again");
            answer = userInput.next().charAt(0);
        }
        if (answer == 'q') continuing = false;
    }

    void getMap(){

        JsonNode request = restHelperBlackboard.sendGet("/map");
    }

    void userInformation(User user){
        JsonNode request = restHelperBlackboard.sendGet("/users/"+ user.name);
         String userObject = request.getObject().getJSONObject("object").toString();

         User userData = gson.fromJson(userObject, User.class);
        System.out.println(userData);
    }




    void endGame(){
        System.out.println("Until next time!");
        System.exit(0);
    }
}
