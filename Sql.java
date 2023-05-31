import java.sql.*;
import java.util.ArrayList;
public class Sql {
    public Connection connection;
    public Sql() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:Databases\\test.db");
            //connection.setAutoCommit(false);
        } catch (Exception e) {
            System.out.println("Database connection error : " + e.getMessage());
        }
    }
    void Select_test()
    {
        try {
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery( "SELECT * FROM User;" );
            while ( rs.next() ) {
               int id = rs.getInt("id");
               String  username = rs.getString("username");
               String  password = rs.getString("password");
               String  name = rs.getString("name");
               String  securityQuestion = rs.getString("securityQuestion");
               String  securityAnswer = rs.getString("securityAnswer");
               int  type = rs.getInt("type");

               System.out.println( "ID = " + id );
               System.out.println( "USERNAME = " + username );
               System.out.println( "PASSWORD = " + password );
               System.out.println( "NAME = " + name );
               System.out.println( "securityQuestion = " + securityQuestion );
               System.out.println( "securityAnswer = " + securityAnswer );
               System.out.println( "type = " + type );
               
               System.out.println();
            }
            rs.close();
            stm.close();
        } catch (SQLException e) {
            System.out.println("Could not select data from database : Select_test : "+e.getMessage());
        }
    }
    void Insert_test(String name , String fullname)
    {
        try {
            Statement stm = connection.createStatement();
            stm.executeUpdate( "Insert INTO User (name , fullname) VALUES ('"+name+"' , '"+fullname+"');" );
            stm.close();
        } catch (SQLException e) {
            System.out.println("Could not Insert data to database : Insert_test : "+e.getMessage());
        }
    }
    void Update_test(int ID , String name , String fullname)
    {
        try {
            Statement stm = connection.createStatement();
            stm.executeUpdate( "UPDATE User SET `name`='"+name+"' , `fullname`='"+fullname+"' WHERE `ID` = "+ID+";" );
            stm.close();
        } catch (SQLException e) {
            System.out.println("Could not update data to database : update test : "+e.getMessage());
        }
    }
    void delete_test(int ID )
    {
        try {
            Statement stm = connection.createStatement();
            stm.executeUpdate( "DELETE FROM User  WHERE `ID` = "+ID+";" );
            stm.close();
        } catch (SQLException e) {
            System.out.println("Could not delete data to database : delete test : "+e.getMessage());
        }
    }
    public void InsertToCart(int foodId ,int userId ,int orderId ,int cost ,int count)
    {
        try {
            Statement stm = connection.createStatement();
            stm.executeUpdate( "Insert INTO Cart (foodId , userId , orderId , cost , count) VALUES ('"+foodId+"' , '"+userId+"' , '"+orderId+"' , '"+cost+"' , '"+count+"');" );
            stm.close();
        } catch (SQLException e) {
            System.out.println("Could not Insert data to database : InsertToCart : "+e.getMessage());
        }
    }
    public void InsertToMap(int node1 ,int node2 ,int weight)
    {
        try {
            Statement stm = connection.createStatement();
            stm.executeUpdate( "Insert INTO Map (node1 , node2 , weight ) VALUES ('"+node1+"' , '"+node2+"' , '"+weight+"' );" );
            stm.close();
        } catch (SQLException e) {
            System.out.println("Could not Insert data to database : InsertToCart : "+e.getMessage());
        }
    }
    public void deleteMap()
    {
        try {
            Statement stm = connection.createStatement();
            stm.executeUpdate( "DELETE FROM Map;" );
            stm.close();
        } catch (SQLException e) {
            System.out.println("Could not delete data to database : deleteFromCart : "+e.getMessage());
        }
    }
    public ArrayList<Branch> getConnectedBranch(int node)
    {
        ArrayList<Branch> ans = new ArrayList<Branch>();
        try {
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery( "SELECT * FROM Cart Where `node1`="+node+" OR `node2`="+node+";" );
            while ( rs.next() ) {
               int id = rs.getInt("id");
               int node1 = rs.getInt("node1");
               int node2 = rs.getInt("node2");
               int weight = rs.getInt("weight");
                           
               ans.add(new Branch(id, node1, node2, weight));
            }              
            rs.close();    
            stm.close();   
            return ans;
        } catch (SQLException e) {
            System.out.println("Could not select data from database : getConnectedBranch : "+e.getMessage());
            return ans;
        }
    }
    public void EditCart(int id, int cost, int count)
    {
        try {
            Statement stm = connection.createStatement();
            stm.executeUpdate( "UPDATE Cart SET `cost`='"+cost+"' , `count`='"+count+"' WHERE `id` = "+id+";" );
            stm.close();
        } catch (SQLException e) {
            System.out.println("Could not update data to database : EditCart : "+e.getMessage());
        }
    }
    public void deleteFromCart(int ID )
    {
        try {
            Statement stm = connection.createStatement();
            stm.executeUpdate( "DELETE FROM Cart WHERE `id` = "+ID+";" );
            stm.close();
        } catch (SQLException e) {
            System.out.println("Could not delete data to database : deleteFromCart : "+e.getMessage());
        }
    }
    public ArrayList<Cart> getCart(int FoodId, int UserId , int OrderId)
    {
        ArrayList<Cart> ans = new ArrayList<Cart>();
        try {
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery( "SELECT * FROM Cart Where `foodId` = "+FoodId+" AND `userId` = "+UserId+" AND `orderId`="+OrderId+";" );
            while ( rs.next() ) {
               int id = rs.getInt("id");
               int foodId = rs.getInt("foodId");
               int userId = rs.getInt("userId");
               int orderId = rs.getInt("orderId");
               int cost = rs.getInt("cost");
               int count = rs.getInt("count");
                           
               ans.add(new Cart(id, foodId, userId, orderId, cost, count));
            }              
            rs.close();    
            stm.close();   
            return ans;
        } catch (SQLException e) {
            System.out.println("Could not select data from database : getCart : "+e.getMessage());
            return ans;
        }
        
    }
    public ArrayList<Cart> getCart(int UserId , int OrderId)
    {
        ArrayList<Cart> ans = new ArrayList<Cart>();
        try {
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery( "SELECT * FROM Cart Where `userId` = "+UserId+" AND `orderId`="+OrderId+";" );            
            while ( rs.next() ) {
               int id = rs.getInt("id");
               int foodId = rs.getInt("foodId");
               int userId = rs.getInt("userId");
               int orderId = rs.getInt("orderId");
               int cost = rs.getInt("cost");
               int count = rs.getInt("count");

               ans.add(new Cart(id, foodId, userId, orderId, cost, count));
            }
            rs.close();
            stm.close();
            return ans;
        } catch (SQLException e) {
            System.out.println("Could not select data from database : getCart 2 : "+e.getMessage());
            return ans;
        }
    }
}
