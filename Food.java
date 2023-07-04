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
    public static void addFood(int restaurantId, String name, int price, String type, int discountPercent, long discountTime, String isActive) {
        Main.sql.insertToFood(restaurantId, name, price, type, discountPercent, discountTime, isActive);
    }
    public static boolean deleteFood(int id) {
        if (getFood(id) != null) {
            Main.sql.deleteFromFood(id, "id");
            return true;
        }
        return false;
    }
    public static void printFood(ArrayList<Food> foods, String topic) {
        String leftAlignFormat = "| %-5d | %-25s | %-25s | %-10d |%n";
        String leftAlignHeaderFormat = "| %-5s | %-25s | %-25s | %-10s |%n";
        String dashedLine = "--------------------------------------------------------------------------";
        System.out.println(topic);
        System.out.println(dashedLine);
        System.out.format(leftAlignHeaderFormat,"Id","RestaurantId" ,"Name","Type","DiscountPercent", "");
        System.out.println (dashedLine);
        for (int i = 0; i < restaurants.size(); i++)
            System.out.format(leftAlignFormat,restaurants.get(i).id,restaurants.get(i).name,restaurants.get(i).typesToString(),restaurants.get(i).postCost);
        System.out.println(dashedLine);
    }

    public double[] getPrice(int id) {
        double[] prices = new double[2];
        prices[0] = price;
        prices[1] = ;
        return prices;
    }
}