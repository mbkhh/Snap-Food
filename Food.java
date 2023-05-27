import java.util.ArrayList;
/**
 * User
 */
public class Food {
    public int id;
    public String name ;
    public int price;
    public int discountPercent;
    public int discountTime;
    public boolean isActive;
    public static ArrayList<Food> foods = new ArrayList<Food>();

    

    static Food getFoodById(int id)
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