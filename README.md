# **Rest Service Game**

A simple ClientServer to execute Request from Blackboard Server

## Getting Started

These instructions will get you an idea of the project and running on your local machine for development and testing purposes.

### Prerequisites
You have to connect with the blackboard Server with OpenVPN or through SSH (Suggestion is OpenVPN)

### Installing

You have to clone this Project in your computer through SSH or HTTP
`git clone https://git.haw-hamburg.de/acm319/vs-clientserver.git`

## Running the program

This is a simple role playing gameController in Java. The User have the change to expore the Zoram World and gain experience by solving quests

### Firt Step

User have three options : 
1.  Register a new user 
2.  Continue with their Adventure by login into their old account
3.  Exit the gameController
```
__          __  _                            _______      ______                         
\ \        / / | |                          |__   __|    |___  /                         
 \ \  /\  / /__| | ___ ___  _ __ ___   ___     | | ___      / / ___  _ __ __ _ _ __ ___  
  \ \/  \/ / _ \ |/ __/ _ \| '_ ` _ \ / _ \    | |/ _ \    / / / _ \| '__/ _` | '_ ` _ \ 
   \  /\  /  __/ | (_| (_) | | | | | |  __/    | | (_) |  / /_| (_) | | | (_| | | | | | |
    \/  \/ \___|_|\___\___/|_| |_| |_|\___|    |_|\___/  /_____\___/|_|  \__,_|_| |_| |_|

 Welcome traveler to this mysterious trial you will be facing!
 This is this magical land , ruled by the tyrannical sorceror Zoram.
 We bid you welcome!
 Since you accept the challenge to defeat Zoram (which main character doesn't?)
 You must choose...
	1) New Game - Register as new User
	2) Continue - Login to your World
	3) Exit

2
============================================================================================= 

__      __   _                    _____     __   __                _      _             _                
\ \    / /__| |__ ___ _ __  ___  |_   _|__  \ \ / /__ _  _ _ _    /_\  __| |_ _____ _ _| |_ _  _ _ _ ___ 
 \ \/\/ / -_) / _/ _ \ '  \/ -_)   | |/ _ \  \ V / _ \ || | '_|  / _ \/ _` \ V / -_) ' \  _| || | '_/ -_)
  \_/\_/\___|_\__\___/_|_|_\___|   |_|\___/   |_|\___/\_,_|_|   /_/ \_\__,_|\_/\___|_||_\__|\_,_|_| \___|
                          
============================================================================================= 


Please enter your adventure name
<name>

Please enter your adventure secret code
<password>

```


### Next Steps

The Server will provide the User the quest description and location to solve the quest. User is doing as structures in the Console

```
=========================================================

 /\_/\___  _   _ _ __    /\/\ (_)___ ___(_) ___  _ __  
\_ _/ _ \| | | | '__|  /    \| / __/ __| |/ _ \| '_ \ 
 / \ (_) | |_| | |    / /\/\ \ \__ \__ \ | (_) | | | |
 \_/\___/ \__,_|_|    \/    \/_|___/___/_|\___/|_| |_|                                                              
===========================================================

 To earn your sporns you have to fulfill quests.
 Those quests require you to accept a quest,
and then visit the location in question to do your deed.
 But start slowly, see which quests are available 
 and list them so you can choose which quest to attend.

 Do you want to fullfil those quests?
Press 'y' to continue or 'q' to exit gameController
y


Brave one! We need help! Our dungeons are overrun by rats.
Please can you get rid of this plague.
Go and kill some rats

 Are you ready to solve this quest?
 Enter 'm' to visit the locations to solve the quest
 Press 'q' to exit gameController 
```

## Deployment

* Class RestHelper will execute all the HTTP Methods for RESTful Services (GET,POST,PUT,DELETE)
* Class Game is the core of this Aventure World. 


## Built With

* [UniRest](http://kong.github.io/unirest-java/) - The RESTApi framework used
* [Maven](https://maven.apache.org/) - Dependency Management
