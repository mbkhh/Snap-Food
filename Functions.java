import java.util.ArrayList;
import java.util.Arrays;

public class Functions {
    public static int parseInt(String i, String part) {
        int ans = 0;
        try {
            ans = Integer.parseInt(i);
        } catch (Exception e) {
            System.out.println("Bad Input. error in parsing in: "+part);
        }
        return ans;
    }
    public static int parseInt(String i) {
        int ans = 0;
        try {
            ans = Integer.parseInt(i);
        } catch (Exception e) {
            System.out.println("Bad Input. in parsing in: ");
        }
        return ans;
    }
    public static ArrayList<FoodType> stringToEnum(String foodTypes) {
        String[] foodType =  foodTypes.split(", *");
        ArrayList<FoodType> types = new ArrayList<>();
        for (String type : foodType) {
            types.add(FoodType.stringToFoodType(type));
        }
        return types;
    }
}
