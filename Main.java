import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static Sql sql;
    public static Scanner scanner;
    public static void main(String[] args) {
        sql = new Sql();
        scanner = new Scanner(System.in);
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
            command  = scanner.nextLine();
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
            else if(command.matches("(?i)select\\s+order\\s+\\d+\\s*") && !User.checkCurrentUser2())
            {
                if(User.currentUser.type==2)
                    Order.printOrderRestaurant(User.currentUser, Functions.parseInt(command.split(" ")[2]));
                else
                    Order.printOrder(User.currentUser, Functions.parseInt(command.split(" ")[2]));
            }
            else if(command.matches("(?i)edit\\s+order\\s+\\d+\\s*") && !User.checkCurrentUser2())
            {
                if(User.currentUser.type==2)
                {
                    System.out.println("Enter new status:");
                    String status  = scanner.nextLine();
                    System.out.println("Enter new Estiamted time (null for no change):");
                    String time  = scanner.nextLine();
                    int tim = 0;
                    if (!time.isEmpty()) tim = Functions.parseInt(time);
                    Order.editOrder(tim, status, User.currentUser, Functions.parseInt(command.split(" ")[2]));
                }
            }
            else if(command.matches("(?i)show\\s+ESTIMATED\\s+DELIVERY\\s+time\\s*") && !User.checkCurrentUser2())
            {
                Order.showEstimatedTimeOfOrder(User.currentUser);
            }
            else if(command.matches("(?i)access\\s+order\\s+history\\s*") && !User.checkCurrentUser2())
            {
                Order.printOrderHistory(User.currentUser);
            }
            else if(command.matches("(?i)display\\s+cart\\s+status\\s*") && !User.checkCurrentUser2()) {
                Cart.printCart(User.currentUser);
            }
            else if(command.matches("(?i)confirm\\s+order\\s*") && !User.checkCurrentUser2()) {
                Order.confirmOrder(User.currentUser);
            }
            else if(command.matches("(?i)display\\s+open\\s+orders\\s*") && !User.checkCurrentUser2()) {
                if(User.currentUser.type==2)
                {
                    Order.getRestaurantOpenOrder(User.currentUser);
                }
            }
            else if(command.matches("(?i)display\\s+all\\s+orders\\s*") && !User.checkCurrentUser2()) {
                if(User.currentUser.type==2)
                {
                    Order.getRestaurantAllOrder(User.currentUser);
                }
            }
            else if(command.matches("(?i)register\\s+new\\s+user\\s*") && !User.checkCurrentUser()) {

                System.out.println("Enter type of user : 1-normal 2-restaurantOwner 3-Service 4-developer");
                command  = scanner.nextLine();
                info.add(command.trim());

                System.out.println("Enter username:");
                command  = scanner.nextLine();
                info.add(command.trim());

                System.out.println("Enter password:");
                command  = scanner.nextLine();
                info.add(command.trim());

                System.out.println("Enter full name:");
                command  = scanner.nextLine();
                info.add(command.trim());

                System.out.println("Enter security question in case you forget your pass:");
                command  = scanner.nextLine();
                info.add(command.trim());

                System.out.println("Enter security answer:");
                command  = scanner.nextLine();
                info.add(command.trim());

                System.out.println("Your balance is 0 toomans. Do you want to charge it ? Enter 1-Yes or 2-No");
                command  = scanner.nextLine();
                command.trim();
                if(command.matches("1")) {
                    System.out.println("Enter balance:");
                    command  = scanner.nextLine();
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
                command  = scanner.nextLine();
                info.add(command.trim());
                User.deleteAccount(info.get(0), info.get(1));
                info = new ArrayList<String>();
                command  = scanner.nextLine();


            }

            //TODO PARHAM MUST USE THE FUNCTION THAT PRINT ALL RESTAURANTS
            //TODO YOU MUST BUILD A CONFIGURATION THAT REGISTRATION AND LOGIN IS FOR WHEN WE DON'T LOGIN BEFORE AND LOGOUT FOR WHEN WE HAVE LOGGED IN BEFORE
            else if(command.matches("(?i)login\\s+user\\s+with+\\s+username+\\s+\".+\"\\s*") && !User.checkCurrentUser()) {
                info.add(command.split("\"")[1].trim());

                System.out.println("Enter password:");
                command  = scanner.nextLine();
                info.add(command);
                User.loginUser(info.get(0), info.get(1));
                info = new ArrayList<String>();

            }
            else if(command.matches("(?i)logout+\\s*") && !User.checkCurrentUser2()) {
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
                else if (command.matches("change\\s+restaurant\\s+address\\s+to\\s+node\\s+\\d+")) {
                    if (Restaurant.currentRestaurant.id == User.currentUser.id) {
                        if (Restaurant.currentRestaurant.editRestaurantAddress(Integer.parseInt(commands[5])))
                            System.out.println("Address changed successfully");
                    } else
                        System.out.println("You don't have an access to change this");
                }
                else if (command.matches("show\\s+food\\s+types\\s+"))
                    System.out.println(Restaurant.currentRestaurant.typesToString());
                else if (command.matches("change\\s+food\\s+types\\s+to\\s+[\\w,]+")) {
                    if (Restaurant.currentRestaurant.id == User.currentUser.id) {
                        if (Order.openOrders(Restaurant.currentRestaurant.id).size() == 0) {
                            System.out.println("Are you that you want to change your types? all food in your menu will be deleted");
                            command = scanner.nextLine();
                            if (command.equals("yes")) {
                                Restaurant.currentRestaurant.editFoodType(commands[4]);
                                System.out.println("the food types was changed successfully");
                            }
                        } else
                            System.out.println("You have open orders and can't change the types of your restaurant before you finish your jobs");
                    } else
                        System.out.println("You don't have an access to change this");
                }
            }

        }while (!command.equals("end"));

        scanner.close();
        // sql.Select_test();
        //sql.Insert_test("akbar", "akbari");
        //sql.Select_test();
        //sql.delete_test(2);
        //sql.Select_test();
    }
}