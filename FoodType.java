public enum FoodType {
    IRANIAN("IRANIAN"),
    KEBAB("KEBAB"),
    FAST_FOOD("FAST FOOD"),
    /////////////////////////////////
    // TODO INSERT YOUR TYPES OF FOOD
    /////////////////////////////////
    OTHER("OTHER");
    private final String type;
    FoodType(String type)
    {
        this.type = type;
    }
    public String getType(FoodType foodType)
    {
        return foodType.type;
    }
    public static FoodType stringToFoodType(String type)
    {
        for (FoodType foodType : FoodType.values()) {
            if (foodType.name().equals(type))
                return foodType;
        }
        return null;
    }
}