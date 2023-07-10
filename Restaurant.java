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
    public static boolean addRestaurant(int ownerId, String name, int postCost, String foodType) {
        if (User.getUserById(ownerId) != null) {
            Main.sql.insertToRestaurant(ownerId, name, foodType, postCost);
            return true;
        }
        return false;
    }
    public String foodTypesToString() {
        String foodType = foodTypes.get(0).getFoodType();
        for (int i = 1; i < foodTypes.size(); i++) {
            foodType += ", " + foodTypes.get(i).getFoodType();
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
            System.out.format(leftAlignFormat,restaurants.get(i).id,restaurants.get(i).name,restaurants.get(i).foodTypesToString(),restaurants.get(i).postCost);
        System.out.println(dashedLine);
    }
    public Address getRestaurantAddress() {
        return Address.getAddress(0, id);
    }
    public boolean editRestaurantAddress(int node) {
        return Main.sql.editAddress(getRestaurantAddress().id, -1, id, node);
    }
    public static Restaurant getRestaurantByOwnerId(int id) {
        return Main.sql.getRestaurant(id, "ownerId", false, "").get(0);
    }
    public static Restaurant getRestaurant(int id) {
        return Main.sql.getRestaurant(id, "id", false, "").get(0);
    }
    public static void printRestaurant(int ownerId) {
        if (currentRestaurant!=null && currentRestaurant.owner.name.equals(User.currentUser.name)) {
            ArrayList<Restaurant> restaurants = new ArrayList<>(Main.sql.getRestaurant(ownerId, "ownerId", false, ""));
            if (restaurants.size() == 0)
                System.out.println("You don't have any restaurant");
            else if (restaurants.size() == 1) {
                printRestaurant(restaurants, "Yours restaurant:");
                currentRestaurant = restaurants.get(0);
            } else
                printRestaurant(restaurants, "Yours restaurants:");
        }
        else {
            printRestaurant(Main.sql.getRestaurant(0, "", true, ""), "These are all of the restaurants:");
        }
    }
    public static boolean deleteRestaurant(int id) {
        if (getRestaurant(id) != null) {
            ArrayList<Food> foods = Main.sql.getFood(id, "restaurantId", false, "");
            Main.sql.deleteFromRestaurant(id);
            Main.sql.deleteFromComment(id, "restaurantId");
            for (Food food : foods)
                Main.sql.deleteFromComment(food.id, "foodId");
            Main.sql.deleteFromFood(id, "restaurantId");
            return true;
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
    public void editFoodType(String foodType) {
        ArrayList<Food> foods = Main.sql.getFood(id, "restaurantId", false, "");
        Main.sql.editRestaurant(id, owner.id, name, foodType, postCost);
        Main.sql.deleteFromComment(id, "restaurantId");
        for (Food food : foods)
            Main.sql.deleteFromComment(food.id, "foodId");
        Main.sql.deleteFromFood(id, "restaurantId");
    }
}