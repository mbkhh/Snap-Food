import java.awt.print.Printable;
import java.util.ArrayList;

public class Restaurant {
    public static Restaurant currentRestaurant = null;
    public int id;
    public User owner;
    public String name;
    public ArrayList<FoodType> types;
    public int postCost; //we have a unique post cost of a restaurant for any address??

    public Restaurant(int id, User owner, String name, ArrayList<FoodType> types, int postCost) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.types = types;
        this.postCost = postCost;
    }
    public String typesToString() {
        String foodType = types.get(0).getType();
        for (int i = 1; i < types.size(); i++) {
            foodType += ", " + types.get(i).getType();
        }
        return foodType;
    }
    public static void printRestaurant(ArrayList<Restaurant> restaurants) {
        String leftAlignFormat = "| %-5d | %-25s | %-25s | %-10d |%n";
        String leftAlignHeaderFormat = "| %-5s | %-25s | %-25s | %-10s |%n";
        String dashedLine = "--------------------------------------------------------------------------";
        //TODO what happen if we not have any restaurant?
        System.out.println("Yours restaurant(s):");
        System.out.println(dashedLine);
        System.out.format(leftAlignHeaderFormat,"Id","Name","Types","PostCost");
        System.out.println (dashedLine);
        for (int i = 0; i < restaurants.size(); i++)
            System.out.format(leftAlignFormat,restaurants.get(i).id,restaurants.get(i).name,restaurants.get(i).typesToString(),restaurants.get(i).postCost);
        System.out.println(dashedLine);
    }
    public static Restaurant getRestaurantBy(int id) {
        return Main.sql.getRestaurant(id, "id").get(0);
    }
    public static void printRestaurant(int ownerId) {
        ArrayList<Restaurant> restaurants = new ArrayList<>(Main.sql.getRestaurant(ownerId, "ownerId"));
        if (restaurants.size() == 0)
            System.out.println("You don't have any restaurant");
        else if (restaurants.size() == 1)
            //TODO must go in that restaurant
            ;
        else
            printRestaurant(restaurants);
    }
}
