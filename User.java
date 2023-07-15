import java.util.ArrayList;

/**
 * User
 */
public class User {
    public int id;
    public static User currentUser = null;
    public String username ;
    public String password;
    public String name;
    public String securityQuestion;
    public String securityAnswer;
    // public Type type;
    public int type;
    public int balance;

    public static ArrayList<User> users = new ArrayList<User>();

    User(int id,String username , String password , String name ,  String securityQuestion, String securityAnswer, int type , int balance)
    {
        this.id=id;
        this.username=username;
        this.password=password;
        this.name=name;
        this.securityQuestion=securityQuestion;
        this.securityAnswer=securityAnswer;
        this.type=type;
        this.balance=balance;
    }


    static boolean addUser(ArrayList<String> registerRequirments)
    {


        if(registerRequirments.size()==1)
        {
            int type=Integer.parseInt(registerRequirments.get(0));
            if(type!=1 && type!=2 && type!=3)
            {
                System.out.println("Invalid type ! There isn't any type related to this number");
                return false;
            }
            else return true;

        }
        else if (registerRequirments.size()==2)
        {
            if(Main.sql.getUser(registerRequirments.get(1))!= null)
            {
                System.out.println("Invalid username! Username is already in use!");
                return false;
            }
            else if(registerRequirments.get(1).length() < 4)
            {
                System.out.println("Invalid username! Username at least must be 4 characters!");
                return false;
            }
            else
                return true;

        }
        else if(registerRequirments.size()==3)
        {
            if(registerRequirments.get(2).length() < 8)
            {
                System.out.println("Invalid password! Password at least must be 8 characters!");
                return false;
            }
            else if(!registerRequirments.get(2).matches("\\W+.*[a-zA-Z]+.*\\d+.*") && !registerRequirments.get(2).matches("\\W+.*\\d+.*[a-zA-Z]+.*") && !registerRequirments.get(2).matches("\\d+.*\\W+.*[a-zA-Z]+.*") && !registerRequirments.get(2).matches("\\d+.*[a-zA-Z]+.*\\W+.*")  && !registerRequirments.get(2).matches("[a-zA-Z]+.*\\W+.*\\d+.*")  && !registerRequirments.get(2).matches("[a-zA-Z]+.*\\d+.*\\W+.*")  )
            {
                System.out.println("Invalid password! Password must contain letters, numbers and symbols at the same time!");
                return false;
            }
            else return true;

        }
        else if(registerRequirments.size()==4)
        {
            if(registerRequirments.get(3).length() < 4)
            {
                System.out.println("Invalid name! Name at least must be 4 characters!");
                return false;
            }
            else if(!registerRequirments.get(3).matches("[a-zA-Z]+\\s*([a-zA-Z]*\\s*)*"))
            {
                System.out.println("Invalid name! Name must only contain letters!");
                return false;
            }
            else return true;
        }
        else if(registerRequirments.size()==8)
        {   if(Integer.parseInt(registerRequirments.get(7)) >=1  && Integer.parseInt(registerRequirments.get(7)) <= 1000 )
        {

            Main.sql.InsertToUser(registerRequirments.get(1), registerRequirments.get(2), registerRequirments.get(3), registerRequirments.get(4), registerRequirments.get(5), Integer.parseInt(registerRequirments.get(0)),Integer.parseInt(registerRequirments.get(6)));
            Main.sql.InsertToAddress(Main.sql.getUser(registerRequirments.get(1)).id,0,Integer.parseInt(registerRequirments.get(7)));
            System.out.println("User added successfully");
            return true;
        }
        else
        {   System.out.println("Invalid address!Address must be a number between 1 to 1000");
            return false;
        }
        }

        else return false;
    }




    static User getUserById(int id)
    {
        User ans = Main.sql.getUser(id);
        return ans;

    }

    static void loginUser (String username , String password)
    {
        String ans;
        boolean get=true;

        if(Main.sql.getUser(username)==null)
        {
            System.out.println("Invalid username! Username doesn't exist!");
        }
        else if(Main.sql.getUser(username, password)== null)
        {

            while(Main.sql.getUser(username, password)== null && get)
            {
                System.out.println("Invalid password! Password is incorrect! Would you like to recover your password? 1-Yes 2-No i try again 3-continue");
                ans=Main.scanner.nextLine();
                if(ans.trim().equals("1"))
                {
                    System.out.println(Main.sql.getUser(username).securityQuestion);
                    ans=Main.scanner.nextLine();
                    if(ans.trim().equals(Main.sql.getUser(username).securityAnswer))
                    {
                        currentUser=Main.sql.getUser(username);
                        System.out.println("User logged in successfully");
                        get=false;
                    }
                    else
                    {
                        System.out.println("Invalid security answer! Your answer is incorrect!");
                        get=false;
                    }


                }

                else if(ans.trim().equals("2"))
                {
                    System.out.println("Enter password:");
                    password=Main.scanner.nextLine();
                    get=true;
                }

                else if(ans.trim().equals("3"))
                {
                    get=false;
                }

            }

            if(Main.sql.getUser(username, password)!= null)
            {
                currentUser=Main.sql.getUser(username, password);
                System.out.println("User logged in successfully");
            }

        }

        else
        {
            currentUser=Main.sql.getUser(username, password);
            System.out.println("User logged in successfully");
        }

    }

    static boolean checkCurrentUser()
    {
        if(currentUser==null)
            return false;
        else
        {
            System.out.println("This operation is not allowed while a user has logged in");
            return true;
        }

    }

    static boolean checkCurrentUser2()
    {
        if(currentUser!=null)
            return false;
        else
        {
            System.out.println("This operation is not allowed while a user has not logged in");
            return true;
        }
    }

    static void logoutUser ()
    {
        currentUser=null;
        System.out.println("User logged out successfully");

    }

    static void deleteAccount (String username , String password)
    {
        User ans = Main.sql.getUser(username,password);
        if(ans==null)
            System.out.println("error");
        else
        {
            Main.sql.deleteFromUser(ans.id);
        }
    }

    static void increaseBalance (int increase)
    {
        Main.sql.updateUserBalance(currentUser.id, increase+Main.sql.getNewBalance(currentUser.id));
        System.out.println("The account has been charged successfully");

    }

    static void reductionBalance(int reduction)
    {
        Main.sql.updateUserBalance(currentUser.id, reduction);
    }

    static int getBalance()
    {
        int balance=Main.sql.getNewBalance(currentUser.id);
        return balance;
    }
}