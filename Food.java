import java.util.ArrayList;
/**
 * User
 */
public class Food {
    public int id;
    public int restaurantId;
    public String name ;
    public int price;
    public int discountPercent;
    public int discountTime;
    public boolean isActive;
    public static ArrayList<Food> foods = new ArrayList<Food>();

    

    public static Food getFoodById(int id)
    {
        // for Test change it when ready
        Food test = new Food();
        test.id = id;
        test.name = "ffsss";
        test.restaurantId = 2;
        test.price = 1000;
        test.isActive = true;
        return test;
    }
    public int[] getPrice(int id)
    {
        // TODO : connect this part to database and calculate discount {final price , discount amount in rial}
        int[] a= {1000 , 100};
        return a;
    }
}