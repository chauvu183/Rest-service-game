package hawhamburg.model;

public class User {

    public String name;
    public String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

}
