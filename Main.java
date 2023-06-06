import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static Sql sql;
    static Scanner sc;
    public static void main(String[] args) {
        sql = new Sql();
        sc = new Scanner(System.in);
        String command;
        ////////////////////
        // add your variable here
        
        ////////////////////
        //Map.InsertMapFromFile();
        Map.findPath(127, 414);

        ArrayList<String> info = new ArrayList<String>();

        //test
        //User.currentUser = User.getUserById(1);

        do{
            command  = sc.nextLine();
            
            if(command.matches("(?i)ADD\\s+food\\s+with\\s+id\\s+\\d+\\s+to\\s+cart\\s*"))
            {
                if(User.currentUser == null)
                    continue;
                Cart.addToCart(functions.parseInt(command.split(" ")[4]), User.currentUser);
            }
            else if(command.matches("(?i)remove\\s+food\\s+with\\s+id\\s+\\d+\\s+from\\s+cart\\s*"))
            {
                if(User.currentUser == null)
                    continue;
                Cart.removeFromCart(functions.parseInt(command.split(" ")[4]),User.currentUser);
            }
            else if(command.matches("(?i)display\\s+cart\\s+status\\s*") && !User.checkCurrentUser2())
            {
                Cart.printCart(User.currentUser);
            }
            else if(command.matches("(?i)confirm\\s+order\\s*") && !User.checkCurrentUser2())
            {
                Order.confirmOrder(User.currentUser);
            }
            else if(command.matches("(?i)register\\s+new\\s+user\\s*") && !User.checkCurrentUser())
            {   
                
                System.out.println("Enter type of user : 1-normal 2-restaurantOwner 3-Service 4-developer"); 
                command  = sc.nextLine();
                info.add(command.trim());
                
                System.out.println("Enter username:");   
                command  = sc.nextLine();
                info.add(command.trim());

                System.out.println("Enter password:");    
                command  = sc.nextLine();
                info.add(command.trim());

                System.out.println("Enter full name:");  
                command  = sc.nextLine();
                info.add(command.trim());

                System.out.println("Enter security question in case you forget your pass:");
                command  = sc.nextLine();
                info.add(command.trim());

                System.out.println("Enter security answer:");
                command  = sc.nextLine();
                info.add(command.trim());

                System.out.println("Your balance is 0 toomans. Do you want to charge it ? Enter 1-Yes or 2-No");
                command  = sc.nextLine();
                command.trim();
                if(command.matches("1"))
                {
                    System.out.println("Enter balance:");
                    command  = sc.nextLine();
                    info.add(command.trim());
                }
                else if(command.matches("2"))
                {
                    info.add("0");
                }


                
                User.addUser(info.get(1),info.get(2),info.get(3),info.get(4),info.get(5),Integer.parseInt(info.get(0)) ,Integer.parseInt(info.get(6)) );
                info = new ArrayList<String>(); 

            }

            else if(command.matches("(?i)remove\\s+user\\s+with+\\s+username+\\s+\".+\"\\s*") && !User.checkCurrentUser())
            {   
                info.add(command.split("\"")[1].trim());

                System.out.println("Enter password:");
                command  = sc.nextLine();
                info.add(command.trim());
                User.deleteAccount(info.get(0), info.get(1));
                info = new ArrayList<String>(); 
                command  = sc.nextLine();


            }

            else if(command.matches("(?i)login\\s+user\\s+with+\\s+username+\\s+\".+\"\\s*") && !User.checkCurrentUser())
            {
                info.add(command.split("\"")[1].trim());

                System.out.println("Enter password:");
                command  = sc.nextLine();
                info.add(command);
                User.loginUser(info.get(0), info.get(1));
                info = new ArrayList<String>();
               
            }

            else if(command.matches("(?i)logout+\\s*") && !User.checkCurrentUser2()) 
            {
                User.logoutUser();
            }
            
        }while (!command.equals("end"));

        sc.close();
        // sql.Select_test();
         //sql.Insert_test("akbar", "akbari");
         //sql.Select_test();
         //sql.delete_test(2);
         //sql.Select_test();
    }
}