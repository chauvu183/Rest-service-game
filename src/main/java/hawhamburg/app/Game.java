package hawhamburg.app;

import com.google.gson.Gson;
import hawhamburg.RestHelper;
import hawhamburg.model.User;
import kong.unirest.JsonNode;


import java.util.Scanner;

public class Game {
    public static Scanner userInput = new Scanner((System.in));
    public static boolean continuing = true;
    private static final String blackboardServer = "http://172.27.0.6:5000";

    Gson gson = new Gson();
    RestHelper restHelperBlackboard = new RestHelper();


    public void startGame(){
        User user = null;
        char playerChoice =' ';
        intro();

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
                System.out.print("\nNice done! Welcome to Adventure World \n");
                userInput.reset();
                login(user);
            }break;
            case'2':{
                //TODO login to account
                System.out.print("\nWelcome \nPlease login\n");
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
                System.out.println("\nWelcome back "+user.name+"\n");
            }break;
            case 3:{
                //TODO Exit
                System.out.println("Until next time!");
                System.exit(0);
            }break;
        }
    }

    void intro() {
        System.out.println("Welcome traveler to this mysterious trial you will be facing!");
        System.out.println("This is this magical land , ruled by the tyrannical sorceror Zoram.");
        System.out.println("We bid you welcome!");
        System.out.println("Since you accept the challenge to defeat Zoram (which main character doesn't?)");
        System.out.println("You must choose...");
        System.out.println("\t1) New Game - Register as new User");
        System.out.println("\t2) Continue - Login to your World");
        System.out.println("\t3) Exit");
    }
    private String register(User user){
        restHelperBlackboard.baseUrl = blackboardServer;
        String userData = "{\"name\":\""+ user.name + "\",\"password\":\""+user.password+"\"}";
        JsonNode response = restHelperBlackboard.sendPost("/users",userData);
        String message = response.getObject().getString("message");
        return message;
    }
    boolean login(User user){
        restHelperBlackboard.baseUrl = blackboardServer;
        return restHelperBlackboard.login(user);
    }

    void checkValidation(User user){

    }
    void userInformation(User user){
        restHelperBlackboard.baseUrl = blackboardServer;
        JsonNode quest = restHelperBlackboard.sendGet("/users/"+ user.name);
        //String tasksUrl = firstQuest.getObject().getJSONObject("object").getJSONObject("_links").getString("tasks");
        //Task task = gson.fromJson(taskString, Task.class);
        //				System.out.println(task);
         String userObject = quest.getObject().getJSONObject("object").toString();

         User userData = gson.fromJson(userObject, User.class);
        System.out.println(userData);
    }
}
