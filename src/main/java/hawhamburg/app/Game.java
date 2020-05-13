package hawhamburg.app;

import com.google.gson.Gson;
import hawhamburg.RestHelper;
import hawhamburg.model.User;

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
                register(user);
                //userInput.reset();
            }break;
            case'2':{
                //TODO login to account
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
    void register(User user){
        restHelperBlackboard.baseUrl = blackboardServer;
        String userData = "{\"name\":\""+ user.name + "\",\"password\":\""+user.password+"\"}";
        restHelperBlackboard.sendPost("/users",userData);
    }
}
