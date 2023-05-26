import java.util.Scanner;

public class Main {
    static Sql sql;
    public static void main(String[] args) {
        sql = new Sql();
        Scanner sc = new Scanner(System.in);
        String command;

        ////////////////////
        // add your variable here
        
        ////////////////////


        //test
        User.currentUser = User.getUserById(1);

        do{
            command  = sc.nextLine();
            
            if(command.matches("(?i)ADD\\s+food\\s+with\\s+id\\s+\\d+\\s+to\\s+cart\\s*"))
            {
                if(User.currentUser == null)
                    continue;
                Cart.addToCart(functions.parseInt(command.split(" ")[4]), User.currentUser);
            }
            if(command.matches("(?i)remove\\s+food\\s+with\\s+id\\s+\\d+\\s+from\\s+cart\\s*"))
            {
                if(User.currentUser == null)
                    continue;
                Cart.removeFromCart(functions.parseInt(command.split(" ")[4]),User.currentUser);
            }

            
        }while (!command.equals("end"));

        sc.close();
        // sql.Select_test();
         //sql.Insert_test("akbar", "akbari");
         //sql.Select_test();
         //sql.delete_test(2);
         //sql.Select_test();
    }
}