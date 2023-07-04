import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.ArrayList;

public class Order {
    public int id;
    public User user;
    public Restaurant restaurant;
    public User delivery;
    public String path;
    public int pathLength;
    public int estimatedTime;
    public long addTime;
    public int totalprice;
    public int totalDiscount;
    public OrderStatus status;
    public String discription;

    Order(int id ,int userId ,int restaurantId ,int deliveryId ,String path , int pathLength , int estimatedTime ,long addTime ,int totalprice ,int totalDiscount ,String status ,String discription) {
        this.id = id;
        this.user = User.getUserById(userId);
        this.restaurant = Restaurant.getRestaurant(restaurantId);
        if(deliveryId == 0)
            this.delivery = null;
        else
            this.delivery = User.getUserById(deliveryId);
        this.path = path;
        this.pathLength = pathLength;
        this.estimatedTime = estimatedTime;
        this.addTime = addTime;
        this.totalprice = totalprice;
        this.totalDiscount = totalDiscount;
        this.status = OrderStatus.valueOf(status);
        this.discription = discription;
    }
    static void printOrders(ArrayList<Order> orders)
    {
        String leftAlignFormat = "| %-5d | %-25s | %-11d | %-10d | %-10s | %-15s |%n";
        String leftAlignHeaderFormat = "| %-5s | %-25s | %-11s | %-10s | %-10s | %-15s |%n";
        System.out.println("-----------------------------------------------------------------------------------------------");
        System.out.format(leftAlignHeaderFormat,"Id","Add time","total Price","Discount","Status","Estimated Time");
        System.out.println("-----------------------------------------------------------------------------------------------");
        for (int i = 0; i < orders.size(); i++) {
            // System.out.println(cart.get(i).food.name + "\t" +  cart.get(i).cost + "\t" +  cart.get(i).count + "\t" +  "0" + "\t" +  cart.get(i).cost*cart.get(i).count);
            //double[] prices = cart.get(i).food.getPrice(1);
            DateFormat f = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date(orders.get(i).addTime);
            System.out.format(leftAlignFormat,orders.get(i).id , f.format(date) , orders.get(i).totalprice , orders.get(i).totalDiscount , orders.get(i).status,orders.get(i).estimatedTime);
        }
        System.out.println("-----------------------------------------------------------------------------------------------");
    }
    static void printOrders2(ArrayList<Order> te)
    {
        String leftAlignFormat = "| %-5d | %-25s | %-10s | %-25s | %-11d | %-10d | %-10s | %-15s |%n";
        String leftAlignHeaderFormat = "| %-5s | %-25s | %-10s | %-25s | %-11s | %-10s | %-10s | %-15s |%n";
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
        System.out.format(leftAlignHeaderFormat,"Id","User Name" , "Address","Add time","total Price","Discount","Status","Estimated Time");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < te.size(); i++) {
            // System.out.println(cart.get(i).food.name + "\t" +  cart.get(i).cost + "\t" +  cart.get(i).count + "\t" +  "0" + "\t" +  cart.get(i).cost*cart.get(i).count);
            //double[] prices = cart.get(i).food.getPrice(1);
            DateFormat f = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date(te.get(i).addTime);
            Address t = Main.sql.getAddress(te.get(i).user.id, 0);
            System.out.format(leftAlignFormat,te.get(i).id,te.get(i).user.name ,t.node, f.format(date) , te.get(i).totalprice , te.get(i).totalDiscount , te.get(i).status, te.get(i).estimatedTime);
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
    }
    static void editOrder(int time, String status, User user, int orderId)
    {
        ArrayList<Order> te = Main.sql.getAllOrderById2(orderId ,Restaurant.getRestaurantByOwnerId(user.id).id);
        if(te.size() == 0)
            System.out.println("There is no order registered with this ID for you");
        else
        {
            if(time == 0 )
                Main.sql.editOrder(orderId, te.get(0).estimatedTime, status);
            else
                Main.sql.editOrder(orderId, time, status);
            System.out.println("Order edited successfully");
        }
    }
    static void printOrder(User user, int orderId)
    {
        ArrayList<Order> te = Main.sql.getAllOrderById(orderId ,user.id);
        if(te.size() == 0)
            System.out.println("There is no order registered with this ID for you");
        else
        {
            System.out.println("\nOrder info:");
            printOrders(te);
            System.out.println();
            if(!te.get(0).discription.isEmpty()) System.out.println("discription: "+te.get(0).discription);
            
            System.out.println("Restaurant name: "+te.get(0).restaurant.name);
            System.out.println("\nFoods info:");
            ArrayList<Cart> cart = Main.sql.getCart(user.id, orderId);
            Cart.printCart(cart);
            System.out.println();

        }
    }
    static void showBestPath(User user, int orderId)
    {
        ArrayList<Order> te = Main.sql.getAllOrderById(orderId ,user.id);
        if(te.size() == 0)
            System.out.println("There is no order registered with this ID for you");
        else
        {
            
            System.out.println("Path lenght: "+te.get(0).pathLength);
            System.out.println("Estiamted total time: "+te.get(0).estimatedTime);
            System.out.println("Path: "+te.get(0).path);
        }
    }
    static void showBestPathDelivery(User user, int orderId)
    {
        ArrayList<Order> te = Main.sql.getOrderByIdAndDelivery(orderId ,user.id);
        if(te.size() == 0)
            System.out.println("There is no order registered with this ID for you");
        else
        {
            Address resturantAddress = Address.getAddress(0, te.get(0).restaurant.id);
            Address userAddress = Address.getAddress(user.id, 0);
            System.out.println("Path to restaurant: " + Map.findPath(userAddress.node, resturantAddress.node).getPath());
            //System.out.println("Path lenght: "+te.get(0).pathLength);
            //System.out.println("Estiamted total time: "+te.get(0).estimatedTime);
            System.out.println("Path to costumer: "+te.get(0).path);
        }
    }
    static void acceptOrder(User user, int orderId)
    {
        ArrayList<Order> te = Main.sql.getFreeOrderById(orderId);
        if(te.size() == 0)
            System.out.println("You cant get this order!");
        else
        {
            Main.sql.editOrder2(orderId,user.id, te.get(0).status.toString());
            System.out.println("Order Accepted successfully");
        }
    }
    static void recieveOrder(User user, int orderId)
    {
        ArrayList<Order> te = Main.sql.getOrderByIdAndDelivery(orderId,user.id);
        if(te.size() == 0)
            System.out.println("Bad id number!");
        else
        {
            Main.sql.editOrder(orderId, te.get(0).estimatedTime, "Sent");
            System.out.println("Order updated successfully");
        }
    }
    static void CompleteOrder(User user, int orderId)
    {
        ArrayList<Order> te = Main.sql.getOrderByIdAndDelivery(orderId,user.id);
        if(te.size() == 0)
            System.out.println("Bad id number!");
        else
        {
            Main.sql.editOrder(orderId, te.get(0).estimatedTime, "Completed");
            System.out.println("Order updated successfully");
        }
    }
    static void showFreeOrders()
    {
        ArrayList<Order> te = Main.sql.getFreeOrder();
        if(te.size() == 0)
            System.out.println("There is no order FREE ORDER!");
        else
        {
            String leftAlignFormat = "| %-5d | %-25s | %-15s | %-25s | %-20s | %-20d | %-10d |%n";
            String leftAlignHeaderFormat = "| %-5s | %-25s | %-15s | %-25s | %-20s | %-20s | %-10s |%n";
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.format(leftAlignHeaderFormat,"Id","User Name" , "User Address","Add time","Restaurant Name","Restaurant Address","Post Cost");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------");
            for (int i = 0; i < te.size(); i++) {
                // System.out.println(cart.get(i).food.name + "\t" +  cart.get(i).cost + "\t" +  cart.get(i).count + "\t" +  "0" + "\t" +  cart.get(i).cost*cart.get(i).count);
                //double[] prices = cart.get(i).food.getPrice(1);
                DateFormat f = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date(te.get(i).addTime);
                Address t = Main.sql.getAddress(te.get(i).user.id, 0);
                Address resaurantAddress = Main.sql.getAddress(0, te.get(i).restaurant.id);
                System.out.format(leftAlignFormat,te.get(i).id,te.get(i).user.name ,t.node, f.format(date) ,te.get(i).restaurant.name  , resaurantAddress.node,  te.get(i).restaurant.postCost);
            }
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------");
        }
    }
    static void showEstimatedTimeOfOrder(User user)
    {
        ArrayList<Order> te = Main.sql.getAllOrderOfUser(user.id);
        if(te.size() == 0)
            System.out.println("There is no active Order");
        else
        {
            if(te.get(te.size()-1).status != OrderStatus.Canceled ||te.get(te.size()-1).status != OrderStatus.Completed )
            {
                System.out.println("Estimated total time: " + te.get(te.size()-1).estimatedTime);
            }
            else
                System.out.println("There is no active Order");
        }

    }
    static void printOrderHistory(User user)
    {
        ArrayList<Order> te = Main.sql.getAllOrderOfUser(user.id);
        if(te.size() == 0)
            System.out.println("You don't have any registered ORDER!");
        else
            printOrders(te);
    }
    static void getRestaurantOpenOrder(User user)
    {
        ArrayList<Order> te = openOrders(Restaurant.getRestaurantByOwnerId(user.id).id);
        if(te.size() == 0)
            System.out.println("No order yet");
        else
        {
            printOrders2(te);
        }
    }
    static void getRestaurantAllOrder(User user)
    {
        ArrayList<Order> te = AllOrders(Restaurant.getRestaurantByOwnerId(user.id).id);
        if(te.size() == 0)
            System.out.println("No order yet");
        else
        {
            printOrders2(te);
        }
    }
    static void printOrderRestaurant(User user, int orderId)
    {
        ArrayList<Order> te = Main.sql.getAllOrderById2(orderId ,Restaurant.getRestaurantByOwnerId(user.id).id);
        if(te.size() == 0)
            System.out.println("There is no order registered with this ID for you");
        else
        {
            System.out.println("\nOrder info:");
            printOrders2(te);
            System.out.println();
            if(!te.get(0).discription.isEmpty()) System.out.println("discription: "+te.get(0).discription);
            System.out.println("\nFoods info:");
            ArrayList<Cart> cart = Main.sql.getCart(user.id, orderId);
            Cart.printCart(cart);
            System.out.println();

        }
    }
    static void confirmOrder(User user) {
        ArrayList<Cart> te = Main.sql.getCart(user.id, 0) ;
        if(te.size() == 0)
            System.out.println("there is nothing in your cart!");
        else {
            int totalPrice = 0;
            int totalDiscount = 0;
            for (int i = 0; i < te.size(); i++) {
                totalPrice += te.get(i).food.getPrice(i)[0] * te.get(i).count;
                totalDiscount += te.get(i).food.getPrice(i)[1] * te.get(i).count;
            }
            totalPrice += te.get(0).food.restaurant.postCost;
            if(totalPrice > user.balance)
                System.out.println("You do not have enough credit");
            else {
                System.out.println("Discription: (if nothing just press enter)");
                String discription = Main.scanner.nextLine();
                System.out.println("Discount Code: (if nothing just press enter)");
                String code = Main.scanner.nextLine();
                Address resturantAddress = Address.getAddress(0, te.get(0).food.restaurant.id);
                Address userAddress = Address.getAddress(user.id, 0);
                if(userAddress == null) {
                    do {
                        System.out.println("You have no registered Address please enter your address:");
                        String node = Main.scanner.nextLine();
                        node = node.trim();
                        if(node.matches("\\d+"))
                        {
                            int n = Functions.parseInt(node);
                            Address.addAddress(user.id, 0, n);
                        }
                    } while(Address.getAddress(user.id, 0) == null);
                    userAddress = Address.getAddress(user.id, 0);
                }
                int CodePrice = 0;
                if (!code.isEmpty())
                {
                    ArrayList<DiscountCode>  discountCode = Main.sql.getDiscountCodeOfUser(user.id,code);
                    if(discountCode.size() != 0)
                    {
                        CodePrice = totalPrice*discountCode.get(0).percent/100;
                        Main.sql.deleteFromDiscountCode(discountCode.get(0).id);
                    }
                    else
                    {
                        System.out.println("invalid discount code");
                        return;
                    }
                }
                //System.out.println(System.currentTimeMillis());
                Vertex x = Map.findPath(resturantAddress.node, userAddress.node);
                Main.sql.InsertToOrder(user.id, te.get(0).food.restaurant.id, 0, x.getPath(), x.pathLength, x.pathLength*100, System.currentTimeMillis(), totalPrice-CodePrice, totalDiscount+CodePrice, OrderStatus.Registered, discription);
                int lastId = Main.sql.getOrderLastId();
                Main.sql.finalizeCart(user.id, lastId);
                User.reductionBalance(totalPrice-CodePrice);
                System.out.println("Order with id "+lastId + " added successfully.");
                ArrayList<Order> tes =  Main.sql.getAllOrderOfUser(user.id);
                if(tes.size() == 3)
                    Main.sql.InsertToDiscountCode(user.id, "3ORDER", 10  );
            }
        }
    }
    public static ArrayList<Order> openOrders(int restaurantId) {
        ArrayList<Order> orders = Main.sql.getRestaurantOpenOrder(restaurantId);
        return orders;
    }
    public static ArrayList<Order> AllOrders(int restaurantId) {
        ArrayList<Order> orders = Main.sql.getRestaurantAllOrder(restaurantId);
        return orders;
    }
}
