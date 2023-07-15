import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static Sql sql;
    public static Scanner scanner;
    public static void main(String[] args) {
        sql = new Sql();
        scanner = new Scanner(System.in);
        String command;
        String[] commands;
        ArrayList<String> info = new ArrayList<>();
        //Map.InsertMapFromFile();
        //Map.findPath(127, 414);
        //test
        //User.currentUser = User.getUserById(1);
        User.currentUser = User.getUserById(3);
//        Restaurant.setCurrentRestaurant(1);
        do {
            command = scanner.nextLine();
            command.trim();
            commands = command.split(" ");
            if (command.matches("return")) {
                if (User.currentUser != null) {
                    if (Restaurant.currentRestaurant != null) {
                        if (Food.currentFood != null) {
                            if (Comment.currentComment != null) {

                            } else {

                            }
                        } else if (Comment.currentComment != null) {

                        } else if (Cart.currentCart != null) {
                            //TODO bagher
                        } else {
                            Restaurant.printRestaurant(Restaurant.currentRestaurant.id);
                            Restaurant.currentRestaurant = null;
                        }
                    } else
                        User.logoutUser();
                }
            }
            else if (command.matches("(?i)add\\s+food\\s+with\\s+id\\s+\\d+\\s+to\\s+cart\\s*")) {
                if (User.currentUser == null)
                    continue;
                Cart.addToCart(Functions.parseInt(command.split(" ")[4]), User.currentUser);
            }
            else if (command.matches("(?i)remove\\s+food\\s+with\\s+id\\s+\\d+\\s+from\\s+cart\\s*")) {
                if (User.currentUser == null)
                    continue;
                Cart.removeFromCart(Functions.parseInt(command.split(" ")[4]), User.currentUser);
            }
            else if (command.matches("(?i)select\\s+order\\s+\\d+\\s*") && !User.checkCurrentUser2()) {
                if (User.currentUser.type == 2)
                    Order.printOrderRestaurant(User.currentUser, Functions.parseInt(command.split(" ")[2]));
                else
                    Order.printOrder(User.currentUser, Functions.parseInt(command.split(" ")[2]));
            }
            else if (command.matches("(?i)edit\\s+order\\s+\\d+\\s*") && !User.checkCurrentUser2()) {
                if (User.currentUser.type == 2) {
                    System.out.println("Enter new status:");
                    String status = scanner.nextLine();
                    System.out.println("Enter new Estiamted time (null for no change):");
                    String time = scanner.nextLine();
                    int tim = 0;
                    if (!time.isEmpty()) tim = Functions.parseInt(time);
                    Order.editOrder(tim, status, User.currentUser, Functions.parseInt(command.split(" ")[2]));
                }
            }
            else if (command.matches("(?i)show\\s+ESTIMATED\\s+DELIVERY\\s+time\\s*") && !User.checkCurrentUser2()) {
                Order.showEstimatedTimeOfOrder(User.currentUser);
            }
            else if (command.matches("(?i)access\\s+order\\s+history\\s*") && !User.checkCurrentUser2()) {
                Order.printOrderHistory(User.currentUser);
            }
            else if (command.matches("(?i)display\\s+cart\\s+status\\s*") && !User.checkCurrentUser2()) {
                Cart.printCart(User.currentUser);
            }
            else if (command.matches("(?i)display\\s+discount\\s+codes\\s*") && !User.checkCurrentUser2()) {
                DiscountCode.getUserDiscountCodes(User.currentUser);
            }
            else if (command.matches("(?i)confirm\\s+order\\s*") && !User.checkCurrentUser2()) {
                Order.confirmOrder(User.currentUser);
            }
            else if (command.matches("(?i)show\\s+map\\s*") && !User.checkCurrentUser2()) {
                Map.showMap();
            }
            else if (command.matches("(?i)show\\s+Path\\s+\\d+\\s*") && !User.checkCurrentUser2()) {
                Order.showBestPath(User.currentUser, Functions.parseInt(command.split(" ")[2]));
            }
            else if (command.matches("(?i)show\\s+free\\s+orders\\s*") && !User.checkCurrentUser2()) {
                if (User.currentUser.type == 3)
                    Order.showFreeOrders();
            }
            else if (command.matches("(?i)find\\s+best\\s+path\\s+\\d+\\s*") && !User.checkCurrentUser2()) {
                if (User.currentUser.type == 3)
                    Order.showBestPathDelivery(User.currentUser, Functions.parseInt(command.split(" ")[3]));
            }
            else if (command.matches("(?i)accept\\s+order\\s+\\d+\\s*") && !User.checkCurrentUser2()) {
                if (User.currentUser.type == 3)
                    Order.acceptOrder(User.currentUser, Functions.parseInt(command.split(" ")[2]));
            }
            else if (command.matches("(?i)receive\\s+order\\s+\\d+\\s*") && !User.checkCurrentUser2()) {
                if (User.currentUser.type == 3)
                    Order.recieveOrder(User.currentUser, Functions.parseInt(command.split(" ")[2]));
            }
            else if (command.matches("(?i)complete\\s+order\\s+\\d+\\s*") && !User.checkCurrentUser2()) {
                if (User.currentUser.type == 3)
                    Order.CompleteOrder(User.currentUser, Functions.parseInt(command.split(" ")[2]));
            }
            else if (command.matches("(?i)accept\\s+order\\s+\\d+\\s*") && !User.checkCurrentUser2()) {
                if (User.currentUser.type == 3)
                    Order.acceptOrder(User.currentUser, Functions.parseInt(command.split(" ")[2]));
            }
            else if (command.matches("(?i)display\\s+open\\s+orders\\s*") && !User.checkCurrentUser2()) {
                if (User.currentUser.type == 2)
                    Order.getRestaurantOpenOrder(User.currentUser);
            }
            else if (command.matches("(?i)display\\s+offers\\s*") && !User.checkCurrentUser2()) {
                HashMap<Integer, ArrayList<Integer>> f =Comment.ratingHistory(User.currentUser.id);
                ArrayList<Restaurant> restaurnat = Main.sql.getRestaurant(0, "0", true, "");
                System.out.println();
                for (int i = 0; i < restaurnat.size(); i++) {
                    for (int j = i; j < restaurnat.size()-1; j++) {
                        double res1rate = (f.containsKey(restaurnat.get(j).id))?Functions.avarage(f.get(restaurnat.get(j).id)):2.5;
                        double res2rate = (f.containsKey(restaurnat.get(j+1).id))?Functions.avarage(f.get(restaurnat.get(j+1).id)):2.5;
                        if(res1rate < res2rate)
                            Collections.swap(restaurnat, j+1, j);
                    }
                }
                System.out.println("Best restaurant for you :)");
                for(Restaurant re : restaurnat){
                    System.out.println(re.id + "   " + re.name + "   " + ((f.containsKey(re.id))?Functions.avarage(f.get(re.id)):2.5));
                }
                System.out.println();
            }
            else if (command.matches("(?i)show\\s+orders\\s+history\\s*") && !User.checkCurrentUser2()) {
                if (User.currentUser.type == 2)
                    Order.getRestaurantAllOrder(User.currentUser);
            }
            else if (command.matches("(?i)register\\s+new\\s+user\\s*") && !User.checkCurrentUser()) {
                do{
                    System.out.println("Enter type of user : 1-normal 2-BusinessOwner 3-Delivery");
                    if(info.size()>=1)
                        info.remove(0);
                    command  = scanner.nextLine();
                    info.add(command.trim());
                }
                while (!User.addUser(info));

                do{
                    System.out.println("Enter username:");
                    if(info.size()>=2)
                        info.remove(1);
                    command  = scanner.nextLine();
                    info.add(command.trim());
                }
                while (!User.addUser(info));

                do{
                    System.out.println("Enter password:");
                    if(info.size()>=3)
                        info.remove(2);
                    command  = scanner.nextLine();
                    info.add(command.trim());
                }
                while (!User.addUser(info));

                do{
                    System.out.println("Enter full name:");
                    if(info.size()>=4)
                        info.remove(3);
                    command  = scanner.nextLine();
                    info.add(command.trim());
                }
                while (!User.addUser(info));


                System.out.println("Enter security question in case you forget your pass:");
                command  = scanner.nextLine();
                info.add(command.trim());

                System.out.println("Enter security answer:");
                command  = scanner.nextLine();
                info.add(command.trim());

                System.out.println("Your balance is 0 toomans. Do you want to charge it ? Enter 1-Yes or 2-No");
                command  = scanner.nextLine();
                command.trim();
                if(command.matches("1"))
                {
                    System.out.println("Enter balance:");
                    command  = scanner.nextLine();
                    info.add(command.trim());
                }
                else if(command.matches("2"))
                {
                    info.add("0");
                }
                do{
                    System.out.println("Enter an address:");
                    if(info.size()>=8)
                        info.remove(7);
                    command  = scanner.nextLine();
                    info.add(command.trim());
                }
                while (!User.addUser(info));


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
            else if (command.matches("(?i)login\\s+user\\s+with+\\s+username+\\s+\".+\"\\s*") && !User.checkCurrentUser()) {
                info.add(command.split("\"")[1].trim());
                System.out.println("Enter password:");
                command = scanner.nextLine();
                info.add(command);
                User.loginUser(info.get(0), info.get(1));
                info = new ArrayList<String>();
            }
            else if (command.matches("(?i)logout+\\s*") && !User.checkCurrentUser2()) {
                User.logoutUser();
            }
            else if (command.matches("(?i)charge\\s+my\\s+account+\\s+\"\\d+\"\\s*") && !User.checkCurrentUser2()) {
                info.add(command.split("\"")[1].trim());
                User.increaseBalance(Integer.parseInt(info.get(0)));
                info = new ArrayList<String>();

            }
            else if (command.matches("(?i)show\\s+my\\s+balance+\\s*") && !User.checkCurrentUser2()) {
                System.out.println("Your account balance is " + User.getBalance());
            }
            else if (command.matches("(?i)login\\s+user\\s+with+\\s+username+\\s+\".+\"\\s*") && !User.checkCurrentUser()) {
                info.add(command.split("\"")[1].trim());
                System.out.println("Enter password:");
                command = scanner.nextLine();
                info.add(command);
                User.loginUser(info.get(0), info.get(1));
                info = new ArrayList<String>();
                Restaurant.printRestaurant(User.currentUser.id);
                if (Restaurant.currentRestaurant != null)
                    System.out.println("You entered your restaurant successfully");
            }
            else if (command.matches("(?i)logout+\\s*") && !User.checkCurrentUser2()) {
                User.logoutUser();
            }
            else if (command.matches("show\\s+restaurant\\s+menu\\s+with\\s+id\\s+\\d+")) {
                Food.printFood(Integer.parseInt(commands[5]));
                Comment.printComment(Integer.parseInt(commands[5]), "restaurantId");
            }
            else if (command.matches("show\\s+restaurants")) {
                if (Restaurant.currentRestaurant != null && Restaurant.currentRestaurant.owner.type == 2) {
                    Restaurant.printRestaurant(Restaurant.currentRestaurant.id);
                }
                else {
                    Restaurant.printAllRestaurants();
                }
            }
            else if (User.currentUser != null) {
                if (Restaurant.currentRestaurant != null) {
                    if (Food.currentFood != null) {
                        if (command.matches("return")) {
                            Comment.printComment(Restaurant.currentRestaurant.id, "restaurantId");
                            Food.printFood(Restaurant.currentRestaurant.id);
                        }
                        else if (User.currentUser.id == Restaurant.currentRestaurant.owner.id) {
                            if (command.matches("change\\s+food\\s+name\\s+with\\s+id\\s+\\d+\\s+to\\s+\\w+")) {
                                if (Food.editFood(Integer.parseInt(commands[5]), "name", commands[7], 0, 0))
                                    System.out.println("successful");
                                else
                                    System.out.println("you have open orders.");
                            }
                            else if (command.matches("change\\s+food\\s+price\\s+with\\s+id\\s+\\d+\\s+to\\s+\\w+")) {
                                if (Food.editFood(Integer.parseInt(commands[5]), "price", "", Integer.parseInt(commands[7]), 0))
                                    System.out.println("successful");
                                else
                                    System.out.println("you have open orders.");
                            }
                            else if (command.matches("activate\\s+food\\s+with\\s+id\\s+\\d+")) {
                                if (Food.editFood(Integer.parseInt(commands[4]), "isActive", "yes", 0, 0))
                                    System.out.println("successful");
                                else
                                    System.out.println("you have open orders.");
                            }
                            else if (command.matches("diactivate\\s+food\\s+with\\s+id\\s+\\d+")) {
                                if (Food.editFood(Integer.parseInt(commands[4]), "isActive", "no", 0, 0))
                                    System.out.println("successful");
                                else
                                    System.out.println("you have open orders.");
                            }
                            else if (command.matches("set\\s+disscount\\s+for\\s+foods\\s+with\\s+id\\s+\\d+")) {
                                int id = Integer.parseInt(commands[6]);
                                if (Order.openOrders(id).size() == 0) {
                                    if (Food.getFood(id).discountPercent == 0) {
                                        System.out.print("enter the discount percent between 0 to 50");
                                        command = scanner.nextLine();
                                        if (command.matches("\\d+") && Integer.parseInt(command) <= 50) {
                                            int discountPercent = Integer.parseInt(command);
                                            System.out.print("How long you want the discount lasts? (something like 4h4m or 0h0m for nothing)");
                                            command = scanner.nextLine();
                                            if (command.matches("\\d+h\\d+m")) {
                                                long discountTime = Integer.parseInt(command.split("\\w")[0]) * 3600000L + Integer.parseInt(command.split("\\w")[1]) * 60000L;
                                                Food.editFood(id, "discount", "", discountPercent, discountTime);
                                            } else
                                                System.out.println("Invalid discount time type");
                                        } else
                                            System.out.println("invalid discount percent type");
                                    } else
                                        System.out.println("You already have an discount offer for this food");
                                } else
                                    System.out.println("you have open orders.");
                            }
                            else if (command.matches("add\\s+a\\s+reply\\s+to\\s+the\\s+comment\\s+with\\s+id\\s+\\d+")) {
                                if (Comment.getComment(Integer.parseInt(commands[8])) != null && Comment.getComment(Integer.parseInt(commands[8])).replyComment == null && Comment.getComment(Integer.parseInt(commands[8])).rate == 0) {
                                    System.out.print("please enter your reply here: ");
                                    command = scanner.nextLine();
                                    if (command.matches("[\\w.,]+")) {
                                        String comment = command;
                                        Comment.addComment(User.currentUser.id, Food.currentFood.id, 0, Integer.parseInt(commands[8]), 0, comment, new Date().getTime());
                                    } else
                                        System.out.println("invalid reply type.");
                                } else
                                    System.out.println("there's no comment with this id.");
                            }
                            else if (command.matches("edit\\s+reply\\s+with\\s+id\\s+\\d+")) {
                                if (Comment.getComment(Integer.parseInt(commands[4])) != null && Comment.getComment(Integer.parseInt(commands[4])).replyComment != null) {
                                    command = editReply(command, commands);
                                } else
                                    System.out.println("there's no reply with this id.");
                            }
                            else if (command.matches("delete\\s+reply\\s+with\\s+id\\s+\\d+")) {
                                deleteReply(commands);
                            }
                        }
                        else {
                            if (command.matches("add\\s+a\\s+rate")) {
                                if (Comment.isUnique(User.currentUser.id, Restaurant.currentRestaurant.id, "restaurantId")) {
                                    System.out.print("please enter your rating between 1 to 5: ");
                                    command = scanner.nextLine();
                                    if (command.matches("[1-5]")) {
                                        int rate = Integer.parseInt(command);
                                        Comment.addComment(User.currentUser.id, Food.currentFood.id, 0, 0, rate, "", new Date().getTime());
                                    } else
                                        System.out.println("invalid rating type");
                                } else
                                    System.out.println("you've rotten for this restaurant before.");
                            }
                            else if (command.matches("add\\s+a\\s+comment")) {
                                System.out.print("please enter your comment here: ");
                                command = scanner.nextLine();
                                if (command.matches("[\\w.,]+")) {
                                    String comment = command;
                                    Comment.addComment(User.currentUser.id, Food.currentFood.id, 0, 0, 0, comment, new Date().getTime());
                                } else
                                    System.out.println("invalid comment type");
                            }
                            else {
                                command = getString(command, commands);
                            }
                        }
                    }
                    else {
                        if (command.matches("search\\s+food")) {
                            System.out.print("what are you searching for (food type or food): ");
                            command = scanner.nextLine();
                            if (command.matches("\\w+")) {
                                Food.printFood(sql.getFood(Restaurant.currentRestaurant.id, "restaurantId", false, command), "your searchText results:");
                            } else
                                System.out.println("invalid type");
                        }
                        else if (command.matches("show\\s+restaurant\\s+location")) {
                            System.out.println(Restaurant.currentRestaurant.getRestaurantAddress().node);
                        }
                        else if (command.matches("show\\s+food\\s+types\\s+")) {
                            System.out.println(Restaurant.foodTypesToString(Restaurant.currentRestaurant.foodTypes, true));
                        }
                        else if (command.matches("select\\s+food\\s+with\\s+id\\s+\\d+")) {
                            if (Food.setCurrentFood(Integer.parseInt(commands[4]))) {
                                System.out.println("You entered restaurant successfully");
                                Comment.printComment(Food.currentFood.id, "foodId");
                            }
                            else
                                System.out.println("There wasn't any food with this id");
                        }
                        else if (User.currentUser.id == Restaurant.currentRestaurant.owner.id) {
                            if (command.matches("return")) {
                                Restaurant.printRestaurant(User.currentUser.id);
                                Restaurant.currentRestaurant = null;
                            }
                            else if (command.matches("change\\s+food\\s+types\\s+to\\s+[\\w,]+")) {
                                if (Order.openOrders(Restaurant.currentRestaurant.id).size() == 0) {
                                    System.out.println("Are you that you want to change your food types? all food in your menu will be deleted");
                                    command = scanner.nextLine();
                                    if (command.equals("yes")) {
                                        Restaurant.editFoodType(Restaurant.currentRestaurant.id, commands[4]);
                                        System.out.println("the food types was changed successfully");
                                    }
                                } else
                                    System.out.println("You have open orders and can't change the food types of your restaurant before finish your jobs");
                            }
                            else if (command.matches("change\\s+restaurant\\s+address\\s+to\\s+node\\s+\\d+")) {
                                if (Restaurant.editRestaurantAddress(Restaurant.currentRestaurant.id, Integer.parseInt(commands[5])))
                                    System.out.println("Address changed successfully");
                                else
                                    System.out.println("you have open orders.");
                            }
                            else if (command.matches("add\\s+food")) {
                                System.out.print("enter the name: ");
                                command = scanner.nextLine();
                                if (command.matches("\\w+")) {
                                    String name = command;
                                    System.out.print("enter the price: ");
                                    command = scanner.nextLine();
                                    if (command.matches("\\w+")) {
                                        int price = Integer.parseInt(command);
                                        System.out.print("enter the food type: ");
                                        command = scanner.nextLine();
                                        if (command.matches("\\w+") && Functions.stringToEnum(command).get(0) != null) {
                                            String foodType = command;
                                            System.out.print("is it active? ");
                                            command = scanner.nextLine();
                                            if (command.matches("yes") || command.matches("no")) {
                                                String isActive = command;
                                                System.out.print("enter the discountPercent it must be between 0 and 50 (if want, you can say 0): ");
                                                command = scanner.nextLine();
                                                if (command.matches("\\d+") && Integer.parseInt(command) <= 50) {
                                                    int discountPercent = Integer.parseInt(command);
                                                    System.out.print("enter the period of time that you want the discount lasts by number of hours: ");
                                                    command = scanner.nextLine();
                                                    if (command.matches("\\d+")) {
                                                        long discountTime = Integer.parseInt(command) * 3600000L;
                                                        Food.addFood(Restaurant.currentRestaurant.id, name, price, foodType, discountPercent, new Date().getTime() + discountTime, isActive);
                                                    } else
                                                        System.out.println("invalid time");
                                                } else
                                                    System.out.println("invalid discount percent");
                                            } else
                                                System.out.println("invalid answer");
                                        } else
                                            System.out.println("invalid type");
                                    } else
                                        System.out.println("invalid price type");
                                } else
                                    System.out.println("invalid name type");
                            }
                            else if (command.matches("add\\s+a\\s+reply\\s+to\\s+the\\s+comment\\s+with\\s+id\\s+\\d+")) {
                                if (Comment.getComment(Integer.parseInt(commands[8])) != null && Comment.getComment(Integer.parseInt(commands[8])).replyComment == null && Comment.getComment(Integer.parseInt(commands[8])).rate == 0) {
                                    System.out.print("please enter your reply here: ");
                                    command = scanner.nextLine();
                                    if (command.matches("[\\w.,]+")) {
                                        String comment = command;
                                        Comment.addComment(User.currentUser.id, 0, Restaurant.currentRestaurant.id, Integer.parseInt(commands[8]), 0, comment, new Date().getTime());
                                    } else
                                        System.out.println("invalid reply type.");
                                } else
                                    System.out.println("there's no comment with this id.");
                            }
                            else if (command.matches("edit\\s+reply\\s+with\\s+id\\s+\\d+")) {
                                if (Comment.getComment(Integer.parseInt(commands[4])) != null && Comment.getComment(Integer.parseInt(commands[4])).replyComment != null && Comment.getComment(Integer.parseInt(commands[8])).rate == 0) {
                                    command = editReply(command, commands);
                                } else
                                    System.out.println("there's no reply with this id.");
                            }
                            else if (command.matches("delete\\s+reply\\s+with\\s+id\\s+\\d+")) {
                                deleteReply(commands);
                            }
                            else if (command.matches("delete\\s+food\\s+with\\s+id\\s+\\d+")) {
                                if (Food.deleteFood(Integer.parseInt(commands[4])))
                                    System.out.println("successful");
                                else
                                    System.out.println("there wasn't any restaurant with this id");
                            }
                        }
                        else {
                            if (command.matches("return")) {
                                Restaurant.printAllRestaurants();
                            }
                            else if (command.matches("add\\s+a\\s+comment")) {
                                System.out.print("please enter your comment here: ");
                                command = scanner.nextLine();
                                if (command.matches("[\\w.,]+")) {
                                    String comment = command;
                                    Comment.addComment(User.currentUser.id, 0, Restaurant.currentRestaurant.id, 0, 0, comment, new Date().getTime());
                                } else
                                    System.out.println("invalid comment type");
                            }
                            else if (command.matches("add\\s+a\\s+rate")) {
                                if (Comment.isUnique(User.currentUser.id, Restaurant.currentRestaurant.id, "restaurantId")) {
                                    System.out.print("please enter your rating between 1 to 5: ");
                                    command = scanner.nextLine();
                                    if (command.matches("[1-5]")) {
                                        int rate = Integer.parseInt(command);
                                        Comment.addComment(User.currentUser.id, 0, Restaurant.currentRestaurant.id, 0, rate, "", new Date().getTime());
                                    } else
                                        System.out.println("invalid rating type");
                                } else
                                    System.out.println("you've rotten for this restaurant before.");
                            }
                            else command = getString(command, commands);
                        }
                    }
                }
                else {
                    if (command.matches("return")) {
                        User.logoutUser();
                    }
                    else if (command.matches("search\\s+food")) {
                        System.out.print("what are you searching for (food type or food): ");
                        command = scanner.nextLine();
                        if (command.matches("\\w+")) {
                            Food.printFood(sql.getFood(0, "", true, command), "your searchText results:");
                        } else
                            System.out.println("invalid type");
                    }
                    else if (command.matches("search\\s+restaurant")) {
                        System.out.print("what are you searching for (food type or restaurant): ");
                        command = scanner.nextLine();
                        if (command.matches("\\w+")) {
                            Restaurant.printRestaurant(sql.getRestaurant(0, "", true, command), "your searchText results:");
                        } else
                            System.out.println("invalid type");
                    }
                    else if (command.matches("select\\s+restaurant\\s+with\\s+id\\s+\\d+")) {
                        if (Restaurant.setCurrentRestaurant(Integer.parseInt(commands[4]))) {
                            System.out.println("You entered restaurant successfully");
                            Comment.printComment(Restaurant.currentRestaurant.id, "restaurantId");
                            Food.printFood(Restaurant.currentRestaurant.id);
                        } else
                            System.out.println("There wasn't any restaurant with this id");
                    }
                    else if (User.currentUser.type == 2) {
                        if (command.matches("add\\s+restaurant")) {
                            System.out.print("enter the owner id: ");
                            command = scanner.nextLine();
                            if (command.matches("\\w+")) {
                                int ownerId = Integer.parseInt(command);
                                if (User.getUserById(ownerId) != null) {
                                    System.out.print("enter the name: ");
                                    command = scanner.nextLine();
                                    if (command.matches("\\w+")) {
                                        String name = command;
                                        System.out.print("enter the post cost: ");
                                        command = scanner.nextLine();
                                        if (command.matches("\\w+")) {
                                            int postCost = Integer.parseInt(command);
                                            System.out.print("enter the food type(s) with a ',' between them if they're two or more without any white space: ");
                                            command = scanner.nextLine();
                                            if (command.matches("[\\w,]+")) {
                                                String foodType = command;
                                                System.out.print("enter restaurant address: ");
                                                command = scanner.nextLine();
                                                if (command.matches("\\d+")) {
                                                    int addressNode = Integer.parseInt(command);
                                                    if (Restaurant.addRestaurant(ownerId, name, postCost, foodType, addressNode))
                                                        System.out.println("The restaurant was successfully added");
                                                }
                                            } else
                                                System.out.println("Invalid food type type");
                                        } else
                                            System.out.println("Invalid post cost type");
                                    } else
                                        System.out.println("Invalid name type");
                                } else
                                    System.out.println("There wasn't a user with this id");
                            } else
                                System.out.println("Invalid owner id type");
                        }
                        else if (command.matches("delete\\s+restaurant\\s+with\\s+id\\s+\\d+")) {
                            if (Restaurant.getRestaurant(Integer.parseInt(commands[4])) != null) {
                                if (User.currentUser.id == Restaurant.getRestaurant(Integer.parseInt(commands[4])).id) {
                                    if (Restaurant.deleteRestaurant(Integer.parseInt(commands[4])))
                                        System.out.println("successful");
                                    else
                                        System.out.println("you have open orders.");
                                } else
                                    System.out.println("You don't have an access to delete this restaurant");
                            } else
                                System.out.println("there wasn't any restaurant with this id");
                        }
                    }
                }
            }
            else
                System.out.println("invalid command");
        } while (!command.equals("end"));
        scanner.close();
    }
    private static String editReply(String command, String[] commands) {
        if (User.currentUser.id == Comment.getComment(Integer.parseInt(commands[4])).user.id) {
            System.out.print("enter a new reply to replace the old one: ");
            command = scanner.nextLine();
            if (command.matches("[\\w.,]+")) {
                Comment.editComment(Integer.parseInt(commands[4]), command, 0);
            } else
                System.out.println("invalid reply type.");
        } else
            System.out.println("you just can edit your replies");
        return command;
    }
    private static void deleteReply(String[] commands) {
        if (Comment.getComment(Integer.parseInt(commands[4])) != null && Comment.getComment(Integer.parseInt(commands[4])).replyComment != null && Comment.getComment(Integer.parseInt(commands[8])).rate == 0) {
            if (User.currentUser.id == Comment.getComment(Integer.parseInt(commands[4])).user.id) {
                Comment.deleteComment(Integer.parseInt(commands[4]));
                System.out.println("the reply was deleted successfully");
            } else
                System.out.println("you just can delete your replies");
        } else
            System.out.println("there's no reply with this id.");
    }
    private static String getString(String command, String[] commands) {
        if (command.matches("edit\\s+comment\\s+with\\s+id\\s+\\d+")) {
            if (Comment.getComment(Integer.parseInt(commands[4])) != null && Comment.getComment(Integer.parseInt(commands[4])).rate == 0) {
                if (User.currentUser.id == Comment.getComment(Integer.parseInt(commands[4])).user.id) {
                    int id = Integer.parseInt(commands[4]);
                    System.out.print("enter a new comment to replace the old one: ");
                    command = scanner.nextLine();
                    if (command.matches("[\\w.,]+")) {
                        Comment.editComment(id, command, 0);
                        System.out.println("Comment was edited successfully");
                    } else
                        System.out.println("invalid comment type.");
                } else
                    System.out.println("you just can edit your comments");
            } else
                System.out.println("there's no comment with this id.");
        }
        else if (command.matches("edit\\s+rate\\s+with\\s+id\\s+\\d+")) {
            if (Comment.getComment(Integer.parseInt(commands[4])) != null && Comment.getComment(Integer.parseInt(commands[4])).Comment.equals("")) {
                if (User.currentUser.id == Comment.getComment(Integer.parseInt(commands[4])).user.id) {
                    int id = Integer.parseInt(commands[4]);
                    System.out.print("enter a new rate to replace the old one: ");
                    command = scanner.nextLine();
                    if (command.matches("[1-5]")) {
                        Comment.editComment(id, "", Integer.parseInt(command));
                        System.out.println("Rating was edited successfully");

                    } else
                        System.out.println("invalid rate type.");
                } else
                    System.out.println("you just can edit your rates.");
            } else
                System.out.println("there's no rate with this id.");
        }
        else if (command.matches("delete\\s+comment\\s+with\\s+id\\s+\\d+")) {
            if (Comment.getComment(Integer.parseInt(commands[4])) != null && Comment.getComment(Integer.parseInt(commands[4])).rate == 0) {
                if (User.currentUser.id == Comment.getComment(Integer.parseInt(commands[4])).user.id) {
                    Comment.deleteComment(Integer.parseInt(commands[4]));
                    System.out.println("the comment was deleted successfully");
                } else
                    System.out.println("you just can delete your comments");
            } else
                System.out.println("there's no comment with this id.");
        }
        else if (command.matches("delete\\s+rate\\s+with\\s+id\\s+\\d+")) {
            if (Comment.getComment(Integer.parseInt(commands[4])) != null && Comment.getComment(Integer.parseInt(commands[4])).Comment.equals("")) {
                if (User.currentUser.id == Comment.getComment(Integer.parseInt(commands[4])).user.id) {
                    Comment.deleteComment(Integer.parseInt(commands[4]));
                    System.out.println("the rate was deleted successfully");
                } else
                    System.out.println("you just can delete your rates");
            } else
                System.out.println("there's no rate with this id.");
        }
        return command;
    }
}