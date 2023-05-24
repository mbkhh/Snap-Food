/**
 * User
 */
public class User {
    public int id;
    static User currentUser = null; 
    public String username ;
    public String password;
    public String name;
    public String securityQuestion;
    public String securityAnswer;
    public int type;
    public int balance;
    public static ArrayList<User> users = new ArrayList<User>();
}