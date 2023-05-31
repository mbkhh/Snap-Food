import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Map {
    static void InsertMapFromFile()
    {
        try {
            Main.sql.deleteMap();
            File myObj = new File("graph.txt");
            Scanner myReader = new Scanner(myObj);
            String data = myReader.nextLine();
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
                int node1 = functions.parseInt(data.split(" ")[0] , "Map insert");
                int node2 = functions.parseInt(data.split(" ")[1] , "Map insert");
                int weight = functions.parseInt(data.split(" ")[2] , "Map insert");
                Main.sql.InsertToMap(node1, node2, weight);
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }
    static void findPath(int node1, int node2)
    {
        ArrayList<Integer> indexed = new ArrayList<Integer>();
        ArrayList<Vertex> list = new ArrayList<Vertex>();
        list.add(new Vertex(node1));
        while (list.get(0).nodeName == node2) {
            
        }
    }
}
