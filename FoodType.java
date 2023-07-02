public enum FoodType {
    IRANIAN("IRANIAN"),
    KEBAB("KEBAB"),
    FAST_FOOD("FAST_FOOD"),
    TYPE1("TYPE1"),
    TYPE2("TYPE2"),
    TYPE3("TYPE3"),
    /////////////////////////////////
    // TODO INSERT YOUR TYPES OF FOOD
    /////////////////////////////////
    OTHER("OTHER");
    private final String type;
    FoodType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
    public static FoodType stringToFoodType(String type) {
        for (FoodType foodType : FoodType.values()) {
            if (foodType.name().equals(type))
                return foodType;
        }
        return null;
    }
}