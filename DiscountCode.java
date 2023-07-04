import java.util.ArrayList;

public class DiscountCode {
    public int id;
    public User user;
    public String code;
    public int percent;
    DiscountCode(int id ,int userId ,String code ,int percent) {
        this.id = id;
        this.user = User.getUserById(userId);
        this.code = code;
        this.percent=percent;
    }
    static void getUserDiscountCodes(User user)
    {
        ArrayList<DiscountCode> te = Main.sql.getAllDiscountCodeOfUser(user.id);
        if(te.size() == 0)
            System.out.println("You have no discount code");
        else
            printDiscountCodes(te);
    }
    static void printDiscountCodes(ArrayList<DiscountCode> te)
    {
        String leftAlignFormat = "| %-25s | %-25s |%n";
        String leftAlignHeaderFormat = "| %-25s | %-25s |%n";
        String dashedLine = "-------------------------------------------------------";
        System.out.println(dashedLine);
        System.out.format(leftAlignHeaderFormat,"Code","Amount");
        System.out.println (dashedLine);
        for (int i = 0; i < te.size(); i++)
            System.out.format(leftAlignFormat,te.get(i).code , te.get(i).percent+" %");
        System.out.println(dashedLine);
    }
}
