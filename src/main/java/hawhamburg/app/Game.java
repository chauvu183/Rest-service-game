package hawhamburg.app;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import hawhamburg.RestHelper;
import hawhamburg.model.Link;
import hawhamburg.model.Quest;
import hawhamburg.model.Task;
import hawhamburg.model.User;
import kong.unirest.JsonNode;
import java.util.Scanner;

public class Game {
    private static final String ANSI_RESET  = "\u001B[0m";
    private static final String GREEN  = "\u001B[92m";
    private static final String	YELLOW = "\u001B[33m";
    private static final String BLUE   = "\033[94m";
    private static final String LIGHT_CYAN   = "\u001B[36m";

    public static Scanner userInput = new Scanner((System.in));
    public static boolean continuing = true;
    private static final String blackboardServer = "http://172.27.0.6:5000";

    Gson gson = new Gson();
    RestHelper restHelperBlackboard = new RestHelper();
    User user = null;
    char playerChoice =' ';
    Quest quest;
    Link link;
    Task firstTask;


    public void startGame(){
        intro();
        restHelperBlackboard.baseUrl = blackboardServer;

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
                System.out.println(registerNewUser);
                while(registerNewUser.equals("Username already taken")){
                    System.out.print("\nChoose another name. \nWhat is your name traveler?\n");
                    String newName = userInput.next();
                    System.out.print("\nSuch an outlandish name dear " + name);
                    System.out.print("\nYou are fully cloaked and we want to know your secret code?\n ");
                    String newPassword = userInput.next();
                    user = new User(newName, newPassword);
                    registerNewUser = register(user);
                    System.out.println(registerNewUser);
                };
                System.out.print("\nNice done! Welcome to Adventure World! \n");
                userInput.reset();
                login(user);
                play();
            }break;
            case'2':{
                //TODO login to account
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
                //TODO Exit
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
        String message = response.getObject().getString("message");
        return message;
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
        System.out.println(" To earn your sporns you have to fulfill quests.\n" +
                " Those quests require you to accept a quest,\nand then visit the location in question to do your deed.\n" +
                " But start slowly, see which quests are available \n" +
                " and list them so you can choose which quest to attend.");
        System.out.println("\n Do you want to fullfil those quests?\nPress 'y' to continue or 'q' to exit game");
        getUserInput();
        //See the quests
        if(continuing) getQuest();
        else endGame();

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
        };
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
            };
        }else{
            endGame();
        }
        userInput.reset();

    }
    void solveFirstTask(JsonNode postVisits){
        String token = postVisits.getObject().getString("token");
        System.out.println("This is your Token : "+ token);
        System.out.println("\n Use this Token to confirm you have resolve the quests");
        System.out.println("\n Continue?");
        getUserInput();
        String taskUrl = firstTask.getlink().get("self").toString();
        String inputData = "{\"tokens\":{"+ taskUrl +":\""+token+"\"}}";
        //get the correct url /blackboard/tasks/1
        JsonNode result = restHelperBlackboard.sendPost(link.deliveries,inputData);
        String status = result.getObject().getString("status");
        System.out.println(status);
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


    void getUserInput(){
        char answer = userInput.next().charAt(0);
        while(answer != 'y' && answer != 'q'){
            System.out.println("\n Please enter again");
            answer = userInput.next().charAt(0);
        };
        if (answer == 'q') continuing = false;
    }

    void getMap(){
        JsonNode request = restHelperBlackboard.sendGet("/map");
    }

    void userInformation(User user){
        JsonNode request = restHelperBlackboard.sendGet("/users/"+ user.name);
        //String tasksUrl = firstQuest.getObject().getJSONObject("object").getJSONObject("_links").getString("tasks");
        //Task task = gson.fromJson(taskString, Task.class);
        //				System.out.println(task);
         String userObject = request.getObject().getJSONObject("object").toString();

         User userData = gson.fromJson(userObject, User.class);
        System.out.println(userData);
    }

    void endGame(){
        System.out.println("Until next time!");
        System.exit(0);
    }
}
