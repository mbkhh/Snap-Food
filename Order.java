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

    Order(int id ,int userId ,int restaurantId ,int deliveryId ,String path , int pathLength , int estimatedTime ,long addTime ,int totalprice ,int totalDiscount ,String status ,String discription)
    {
        this.id = id;
        this.user = User.getUserById(userId);
        this.restaurant = Restaurant.getRestuarantById(restaurantId);
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

    static void confirmOrder(User user)
    {
        ArrayList<Cart> te = Main.sql.getCart(user.id, 0) ;
        if(te.size() == 0)
            System.out.println("there is nothing in your cart!");
        else{
            int totalPrice = 0;
            int totalDiscount = 0;
            for (int i = 0; i < te.size(); i++) {
                totalPrice += te.get(i).food.getPrice(i)[0] * te.get(i).count;
                totalDiscount += te.get(i).food.getPrice(i)[1] * te.get(i).count;
            }
            if(totalPrice > user.balance)
                System.out.println("You do not have enough credit");
            else
            {
                System.out.println("Discription: (if nothing just press enter)");
                String discription = Main.sc.nextLine();
                Address resturantAddress = Address.getAddress(0, te.get(0).food.restaurant.id);
                Address userAddress = Address.getAddress(user.id, 0);
                if(userAddress == null)
                {
                    do
                    {
                        System.out.println("You have no registered Address please enter your address:");
                        String node = Main.sc.nextLine();
                        node = node.trim();
                        if(node.matches("\\d+"))
                        {
                            int n = Functions.parseInt(node);
                            Address.addAddress(user.id, 0, n);
                        }
                    }while(Address.getAddress(user.id, 0) == null);
                    userAddress = Address.getAddress(user.id, 0);
                }
                System.out.println(System.currentTimeMillis());

                Vertex x = Map.findPath(resturantAddress.node, userAddress.node);
                
                Main.sql.InsertToOrder(user.id, te.get(0).food.restaurant.id, 0, x.getPath(), x.pathLength, x.pathLength*100, System.currentTimeMillis(), totalPrice, totalDiscount, OrderStatus.Registered, discription);
            }
        }
    }
}
