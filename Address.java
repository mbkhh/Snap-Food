public class Address {
    int id;
    User user;
    Restaurant restaurant;
    int node;
    Address(int id, int userId , int restaurantId, int node)
    {
        this.id = id;
        this.user = User.getUserById(userId); 
        this.restaurant = Restaurant.getRestuarantById(restaurantId);
        this.node = node;
    }
    static Address getAddress(int userId ,int restaurantId)
    {
        Address ans = null;
        ans = Main.sql.getAddress(userId, restaurantId);
        return ans;
    }
    static void addAddress(int userId , int restaurantId , int node)
    {
        Main.sql.InsertToAddress(userId, restaurantId, node);
    }
}
