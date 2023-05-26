import java.util.ArrayList;

public class Cart {
    int id;
    Food food;
    User user;
    Order order;
    int cost;
    int count;
    static ArrayList<Cart> currentCart = new ArrayList<Cart>();

    Cart(int id, int foodId, int userId, int orderId, int cost, int count)
    {
        this.id = id;
        food = Food.getFoodById(foodId);
        user = User.getUserById(userId);
        if(orderId == 0)
            order = null;
        // TODO : add else for when orderId is not zero
        this.cost = cost;
        this.count = count;
    }
    static Boolean addToCart(Food food, User user)
    {
        ArrayList<Cart> te = Main.sql.getCart(food.id, user.id, 0) ;
        if(te.size() == 0)
        {
            Main.sql.InsertToCart(food.id, user.id, 0, food.price, 1);
        }
        else
        {
            Main.sql.EditCart(te.get(0).id, te.get(0).food.price, te.get(0).count+1);
        }
        return true;
    }
    static boolean removeFromCart(Food food, User user)
    {
        ArrayList<Cart> te = Main.sql.getCart(food.id, user.id, 0) ;
        if(te.size() != 0)
        {
            Main.sql.deleteFromCart(te.get(0).id);
        }
        return true;
    }
    static void removeFromCart(int foodId, User user)
    {
        Food food = Food.getFoodById(foodId);
        if(food == null)
            System.out.println("There is no food with id: "+foodId);
        else
        {
            ArrayList<Cart> te = Main.sql.getCart(food.id, user.id, 0) ;
            if(te.size() != 0)
            {
                Main.sql.deleteFromCart(te.get(0).id);
                System.out.println("Food removed from cart successfully");
            }
            else
                System.out.println("there is some error in deleting food Code:52");                
        }
    }
    static void addToCart(int foodId, User user)
    {
        Food food = Food.getFoodById(foodId);
        if(food == null)
        {
            System.out.println("There is no food with id: "+foodId);
        }
        else
        {
            if(addToCart(food, user) == true)
            {
                System.out.println("Food added to cart successfully");
            }
            else
                System.out.println("there is some error in adding food Code:51");
        }
    }
}
