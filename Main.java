import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Sql sql = new Sql();
        Scanner sc = new Scanner(System.in);
        String command  = sc.nextLine();

        ////////////////////
        // add your variable here
        
        ////////////////////

        while (!command.equals("end")) {



            command  = sc.nextLine();
        }

        sc.close();
        // sql.Select_test();
         //sql.Insert_test("akbar", "akbari");
         //sql.Select_test();
         //sql.delete_test(2);
         //sql.Select_test();
    }
}