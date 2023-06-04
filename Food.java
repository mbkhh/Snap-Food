import java.util.ArrayList;
/**
 * User
 */
public class Food {
    public int id;
    public Restaurant restaurant;
    public String name ;
    public int price;
    public FoodType foodType;
    public int discountPercent;
    public int discountTime;
    public boolean isActive;
    public static ArrayList<Food> foods = new ArrayList<Food>();
    public Food (int id, int restaurantId, String name, String price, int discountPercent, int discountTime)
    {
        this.id = id;
        this.name = name;

    }
    public static Food getFoodById(int id)
    {
        // for Test change it when ready
        Food test = new Food();
        test.id = id;
        test.name = "ffsss";
        test.price = 1000;
        test.isActive = true;
        return test;
    }

}