import java.awt.print.Printable;
import java.util.ArrayList;

public class Restaurant {
    public static Restaurant currentRestaurant = null;
    public int id;
    public User owner;
    public String name;
    public ArrayList<FoodType> types;
    public int postCost;

    public Restaurant(int id, User owner, String name, ArrayList<FoodType> types, int postCost) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.types = types;
        this.postCost = postCost;
    }
    public static boolean addRestaurant(int ownerId, String name, int postCost, String type) {
        if (User.getUserById(ownerId) != null) {
            Main.sql.insertToRestaurant(ownerId, name, type, postCost);
            return true;
        }
        return false;
    }
    public String typesToString() {
        String foodType = types.get(0).getType();
        for (int i = 1; i < types.size(); i++) {
            foodType += ", " + types.get(i).getType();
        }
        return foodType;
    }
    public static void printRestaurant(ArrayList<Restaurant> restaurants, String topic) {
        String leftAlignFormat = "| %-5d | %-25s | %-25s | %-10d |%n";
        String leftAlignHeaderFormat = "| %-5s | %-25s | %-25s | %-10s |%n";
        String dashedLine = "--------------------------------------------------------------------------";
        System.out.println(topic);
        System.out.println(dashedLine);
        System.out.format(leftAlignHeaderFormat,"Id","Name","Types","PostCost");
        System.out.println (dashedLine);
        for (int i = 0; i < restaurants.size(); i++)
            System.out.format(leftAlignFormat,restaurants.get(i).id,restaurants.get(i).name,restaurants.get(i).typesToString(),restaurants.get(i).postCost);
        System.out.println(dashedLine);
    }
    public Address getRestaurantAddress() {
        return Address.getAddress(-1, id);
    }
    public boolean editRestaurantAddress(int node) {
        return Main.sql.editAddress(getRestaurantAddress().id, -1, id, node);
    }
    public static Restaurant getRestaurant(int id) {
        return Main.sql.getRestaurant(id, "id").get(0);
    }
    public static void printRestaurant(int ownerId) {
        ArrayList<Restaurant> restaurants = new ArrayList<>(Main.sql.getRestaurant(ownerId, "ownerId"));
        if (restaurants.size() == 0)
            System.out.println("You don't have any restaurant");
        else if (restaurants.size() == 1) {
            printRestaurant(restaurants, "Yours restaurant:");
            currentRestaurant = restaurants.get(0);
            System.out.println("You entered your restaurant successfully");
        }
        else
            printRestaurant(restaurants, "Yours restaurants:");
    }
    public static boolean deleteRestaurant(int id) {
        if (getRestaurant(id) != null) {
            Main.sql.deleteFromRestaurant(id);
            return true;
        }
        return false;
    }
    public static boolean setCurrentRestaurant(int id) {
        Restaurant restaurant = getRestaurant(id);
        if (restaurant != null) {
            currentRestaurant = new Restaurant(id, restaurant.owner, restaurant.name, restaurant.types, restaurant.postCost);
            return true;
        }
        return false;
    }
}