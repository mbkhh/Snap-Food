import java.util.ArrayList;
import java.util.Date;

public class Food {
    public static Food currentFood = null;
    public int id;
    public Restaurant restaurant;
    public String name ;
    public int price;
    public FoodType foodType;
    public int discountPercent;
    public Date discountTime;
    public boolean isActive;
    public static ArrayList<Food> foods = new ArrayList<Food>();
    public Food (int id, int restaurantId, String name, int price, String type, int discountPercent, long discountTime, String isActive) {
        this.id = id;
        this.restaurant = Restaurant.getRestaurant(restaurantId);
        this.name = name;
        this.price = price;
        this.foodType = Functions.stringToEnum(type).get(0);
        this.discountPercent = discountPercent;
        this.discountTime = new Date(discountTime);
        this.isActive = isActive.equals("YES");
    }
    public static Food getFood(int id) {
        return Main.sql.getFood(id, "id", false).get(0);
    }
    public double[] getPrice(int i) {
        double[] prices = new double[2];
        prices[0] = 1100;
        prices[1] = 10;
        return prices;
    }
}