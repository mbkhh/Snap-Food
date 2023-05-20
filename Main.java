public class Main {
    public static void main(String[] args) {
         Sql s = new Sql();
         s.Select_test();
         s.Insert_test("mohammad", "bagher");
         //s.delete_test(2);
         s.Select_test();
    }
}