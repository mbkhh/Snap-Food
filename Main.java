import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static Sql sql;
    static Scanner sc;
    public static void main(String[] args) {
        sql = new Sql();
        sc = new Scanner(System.in);
        String command;
        String[] commands;
        ////////////////////
        // add your variable here

        ////////////////////
        //Map.InsertMapFromFile();
        //Map.findPath(127, 414);

        ArrayList<String> info = new ArrayList<String>();

        //test
        //User.currentUser = User.getUserById(1);
        do {
            command  = sc.nextLine();
            command.trim();
            commands = command.split(" ");
            if(command.matches("(?i)ADD\\s+food\\s+with\\s+id\\s+\\d+\\s+to\\s+cart\\s*")) {
                if(User.currentUser == null)
                    continue;
                Cart.addToCart(Functions.parseInt(command.split(" ")[4]), User.currentUser);
            }
            else if(command.matches("(?i)remove\\s+food\\s+with\\s+id\\s+\\d+\\s+from\\s+cart\\s*")) {
                if(User.currentUser == null)
                    continue;
                Cart.removeFromCart(Functions.parseInt(command.split(" ")[4]),User.currentUser);
            }
            else if(command.matches("(?i)display\\s+cart\\s+status\\s*") && !User.checkCurrentUser2()) {
                Cart.printCart(User.currentUser);
            }
            else if(command.matches("(?i)confirm\\s+order\\s*") && !User.checkCurrentUser2()) {
                Order.confirmOrder(User.currentUser);
            }
            else if(command.matches("(?i)register\\s+new\\s+user\\s*") && !User.checkCurrentUser()) {

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
                if(command.matches("1")) {
                    System.out.println("Enter balance:");
                    command  = sc.nextLine();
                    info.add(command.trim());
                }
                else if(command.matches("2")) {
                    info.add("0");
                }



                User.addUser(info.get(1),info.get(2),info.get(3),info.get(4),info.get(5),Integer.parseInt(info.get(0)) ,Integer.parseInt(info.get(6)) );
                info = new ArrayList<String>();

            }

            else if(command.matches("(?i)remove\\s+user\\s+with+\\s+username+\\s+\".+\"\\s*") && !User.checkCurrentUser()) {
                info.add(command.split("\"")[1].trim());

                System.out.println("Enter password:");
                command  = sc.nextLine();
                info.add(command.trim());
                User.deleteAccount(info.get(0), info.get(1));
                info = new ArrayList<String>();
                command  = sc.nextLine();


            }

            else if(command.matches("(?i)login\\s+user\\s+with+\\s+username+\\s+\".+\"\\s*") && !User.checkCurrentUser()) {
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

            /////////////////
            // taha's main //
            /////////////////

            if (command.matches("add\\s+restaurant\\s+with\\s+owner\\s+id\\s+\\d+\\s+name\\s+\\w+\\s+post\\s+cost\\s+\\d+\\s+typ(e|es)\\s+[\\w,]+")) {
                if (!Restaurant.addRestaurant(Integer.parseInt(commands[5]), commands[7], Integer.parseInt(commands[10]), commands[12])){
                    System.out.println("There wasn't a user with this id");
                } else
                    System.out.println("The restaurant was successfully added");
            }
            else if (command.matches("delete\\s+restaurant\\s+with\\s+id\\s+\\d+")) {
                if (User.currentUser.id == Restaurant.getRestaurant(Integer.parseInt(commands[4])).id){
                    if (Restaurant.deleteRestaurant(Integer.parseInt(commands[4])))
                        System.out.println("successful");
                    else
                        System.out.println("there wasn't any restaurant with this id");
                } else
                    System.out.println("You don't have an access to delete this restaurant");
            }
            else if (command.matches("select\\s+restaurant\\s+with\\s+id\\s+\\d+")) {
                if (Restaurant.setCurrentRestaurant(Integer.parseInt(commands[4])))
                    System.out.println("You entered restaurant successfully");
                else
                    System.out.println("There wasn't any restaurant with this id");
            }
            if (Restaurant.currentRestaurant != null) {
                if (command.matches("show\\s+restaurant\\s+location"))
                    System.out.println(Restaurant.currentRestaurant.getRestaurantAddress().node);
                else if (command.matches("change\\s+restaurant\\s+address\\s+to\\s+node\\s+\\d+"))
                    if (Restaurant.currentRestaurant.editRestaurantAddress(Integer.parseInt(commands[5])))
                        System.out.println("Address changed successfully");
                else if (command.matches("show\\s+food\\s+types\\s+"))
                    System.out.println(Restaurant.currentRestaurant.typesToString());
                else if (command.matches("edit\\s+food\\s+types"))


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