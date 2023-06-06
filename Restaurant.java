public class Restaurant {
    public static Restaurant currentRestaurant = null;
    public int id;
    public int ownerId;
    public String name;
    public String type;
    public int postCost; //we have a unique post cost of a restaurant for any address??

    public static Restaurant getRestuarantById(int id)
    {
        // for Test change it when ready
        Restaurant test = new Restaurant();
        test.id = id;
        test.name = "ffsss";
        return test;
    }
}
