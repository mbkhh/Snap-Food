import java.util.ArrayList;

public class Restaurant {
    public static Restaurant currentRestaurant = null;
    public int id;
    public User owner;
    public String name;
    public ArrayList<FoodType> foodTypes;
    public int postCost;

    public Restaurant(int id, User owner, String name, ArrayList<FoodType> foodTypes, int postCost) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.foodTypes = foodTypes;
        this.postCost = postCost;
    }
    public static boolean addRestaurant(int ownerId, String name, int postCost, String foodType, int addressNode) {
        if (User.getUserById(ownerId) != null) {
            Main.sql.insertToRestaurant(ownerId, name, foodType, postCost);
            ArrayList<Restaurant> restaurants = Main.sql.getRestaurant(ownerId, "ownerId", false, "");
            Main.sql.InsertToAddress(0, restaurants.get(restaurants.size() - 1).id, addressNode);
            return true;
        }
        return false;
    }
    public static String foodTypesToString(ArrayList<FoodType> foodTypes, boolean space) {
        String foodType = foodTypes.get(0).getFoodType();
        for (int i = 1; i < foodTypes.size(); i++) {
            if (space)
                foodType += ", " + foodTypes.get(i).getFoodType();
            else
                foodType += "," + foodTypes.get(i).getFoodType();
        }
        return foodType;
    }
    public static void printRestaurant(ArrayList<Restaurant> restaurants, String topic) {
        String leftAlignFormat = "| %-5d | %-15s | %-50s | %-8d |%n";
        String leftAlignHeaderFormat = "| %-5s | %-15s | %-50s | %-8s |%n";
        String dashedLine = "-------------------------------------------------------------------------------------------";
        System.out.println(topic);
        System.out.println(dashedLine);
        System.out.format(leftAlignHeaderFormat," Id","     Name","                   foodTypes","PostCost");
        System.out.println (dashedLine);
        for (int i = 0; i < restaurants.size(); i++)
            System.out.format(leftAlignFormat,restaurants.get(i).id,restaurants.get(i).name,foodTypesToString(restaurants.get(i).foodTypes, true),restaurants.get(i).postCost);
        System.out.println(dashedLine);
    }
    public Address getRestaurantAddress() {
        return Address.getAddress(0, id);
    }
    public static boolean editRestaurantAddress(int id, int node) {
        if (Order.openOrders(id).size() == 0) {
            if (Main.sql.editAddress(getRestaurant(id).getRestaurantAddress().id, -1, id, node)) {
                currentRestaurant = getRestaurant(id);
                return true;
            }
        }
        return false;
    }
    public static Restaurant getRestaurantByOwnerId(int id) {
        return Main.sql.getRestaurant(id, "ownerId", false, "").get(0);
    }
    public static Restaurant getRestaurant(int id) {
        return Main.sql.getRestaurant(id, "id", false, "").get(0);
    }
    public static void printRestaurant(int ownerId) {
        ArrayList<Restaurant> restaurants = new ArrayList<>(Main.sql.getRestaurant(ownerId, "ownerId", false, ""));
        if (restaurants.size() == 0)
            System.out.println("You don't have any restaurant");
        else if (restaurants.size() == 1) {
            printRestaurant(restaurants, "Your restaurant:");
            currentRestaurant = restaurants.get(0);
        } else
            printRestaurant(restaurants, "Your restaurants:");
    }
    public static void printAllRestaurants() {
        printRestaurant(Main.sql.getRestaurant(0, "", true, ""), "These are all of the restaurants:");
    }
    public static boolean deleteRestaurant(int id) {
        if (Order.openOrders(id).size() == 0) {
            if (getRestaurant(id) != null) {
                ArrayList<Food> foods = Main.sql.getFood(id, "restaurantId", false, "");
                Main.sql.deleteFromRestaurant(id);
                Main.sql.deleteFromComment(id, "restaurantId");
                for (Food food : foods) {
                    Main.sql.deleteFromComment(food.id, "foodId");
                    Main.sql.deleteFromCartByFoodId(food.id);
                }
                Main.sql.deleteFromOrderByRestaurantId(id);
                Main.sql.deleteFromFood(id, "restaurantId");
                return true;
            }
        }
        return false;
    }
    public static boolean setCurrentRestaurant(int id) {
        Restaurant restaurant = getRestaurant(id);
        if (restaurant != null) {
            currentRestaurant = new Restaurant(id, restaurant.owner, restaurant.name, restaurant.foodTypes, restaurant.postCost);
            return true;
        }
        return false;
    }
    public static void editFoodType(int id, String foodType) {
        if (Order.openOrders(id).size() == 0) {
            Restaurant restaurant = getRestaurant(id);
            ArrayList<Food> foods = Main.sql.getFood(id, "restaurantId", false, "");
            Main.sql.editRestaurant(id, restaurant.owner.id, restaurant.name, foodType, restaurant.postCost);
            Main.sql.deleteFromComment(id, "restaurantId");
            for (Food food : foods) {
                Main.sql.deleteFromComment(food.id, "foodId");
                Main.sql.deleteFromCartByFoodId(food.id);
            }
            Main.sql.deleteFromFood(id, "restaurantId");
            currentRestaurant = getRestaurant(id);
        }
    }

}