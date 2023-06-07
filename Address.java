public class Address {
    int id;
    User user;
    Restaurant restaurant;
    int node;
    Address(int id, int userId , int restaurantId, int node) {
        this.id = id;
        if(userId != 0)
            this.user = User.getUserById(userId);
        else
            this.user = null;
        if(restaurantId != 0)
            this.restaurant = Restaurant.getRestaurant(restaurantId);
        else
            this.restaurant =null;
        this.node = node;
    }
    static Address getAddress(int userId ,int restaurantId) {
        Address ans = null;
        ans = Main.sql.getAddress(userId, restaurantId);
        return ans;
    }
    static void addAddress(int userId , int restaurantId , int node) {
        Main.sql.InsertToAddress(userId, restaurantId, node);
    }
}
