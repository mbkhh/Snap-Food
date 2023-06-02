public class functions
{
    public static int parseInt(String i, String part)
    {
        int ans = 0;
        try {
            ans = Integer.parseInt(i);
        } catch (Exception e) {
            System.out.println("Bad Input. error in parsing in: "+part);
        }
        return ans;
    }
    public static int parseInt(String i)
    {
        int ans = 0;
        try {
            ans = Integer.parseInt(i);
        } catch (Exception e) {
            System.out.println("Bad Input. in parsing in: ");
        }
        return ans;
    }
}
