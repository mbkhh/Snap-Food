import java.sql.*;
import java.util.ArrayList;

public class Sql {
    Connection con;
    Sql()
    {
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:Databases\\test.db");
            //con.setAutoCommit(false);
        } catch (Exception e) {
            System.out.println("Database connection error : " + e.getMessage());
        }
    }
    void Select_test()
    {
        try {
            Statement stm = con.createStatement();
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
            Statement stm = con.createStatement();
            stm.executeUpdate( "Insert INTO User (name , fullname) VALUES ('"+name+"' , '"+fullname+"');" );
            stm.close();
        } catch (SQLException e) {
            System.out.println("Could not Insert data to database : Insert_test : "+e.getMessage());
        }
    }
    void Update_test(int ID , String name , String fullname)
    {
        try {
            Statement stm = con.createStatement();
            stm.executeUpdate( "UPDATE User SET `name`='"+name+"' , `fullname`='"+fullname+"' WHERE `ID` = "+ID+";" );
            stm.close();
        } catch (SQLException e) {
            System.out.println("Could not update data to database : update test : "+e.getMessage());
        }
    }
    void delete_test(int ID )
    {
        try {
            Statement stm = con.createStatement();
            stm.executeUpdate( "DELETE FROM User  WHERE `ID` = "+ID+";" );
            stm.close();
        } catch (SQLException e) {
            System.out.println("Could not delete data to database : delete test : "+e.getMessage());
        }
    }
    void InsertToCart(int foodId ,int userId ,int orderId ,int cost ,int count)
    {
        try {
            Statement stm = con.createStatement();
            stm.executeUpdate( "Insert INTO Cart (foodId , userId , orderId , cost , count) VALUES ('"+foodId+"' , '"+userId+"' , '"+orderId+"' , '"+cost+"' , '"+count+"');" );
            stm.close();
        } catch (SQLException e) {
            System.out.println("Could not Insert data to database : InsertToCart : "+e.getMessage());
        }
    }
    void EditCart(int id, int cost, int count)
    {
        try {
            Statement stm = con.createStatement();
            stm.executeUpdate( "UPDATE Cart SET `cost`='"+cost+"' , `count`='"+count+"' WHERE `id` = "+id+";" );
            stm.close();
        } catch (SQLException e) {
            System.out.println("Could not update data to database : EditCart : "+e.getMessage());
        }
    }
    void deleteFromCart(int ID )
    {
        try {
            Statement stm = con.createStatement();
            stm.executeUpdate( "DELETE FROM Cart WHERE `id` = "+ID+";" );
            stm.close();
        } catch (SQLException e) {
            System.out.println("Could not delete data to database : deleteFromCart : "+e.getMessage());
        }
    }
    ArrayList<Cart> getCart(int FoodId, int UserId , int OrderId)
    {
        ArrayList<Cart> ans = new ArrayList<Cart>();
        try {
            Statement stm = con.createStatement();
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
            System.out.println("Could not select data from database : Select_test : "+e.getMessage());
            return ans;
        }
        
    }
    ArrayList<Cart> getCart(int UserId , int OrderId)
    {
        ArrayList<Cart> ans = new ArrayList<Cart>();
        try {
            Statement stm = con.createStatement();
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
            System.out.println("Could not select data from database : Select_test : "+e.getMessage());
            return ans;
        }
        
    }
        

    void InsertToUser(String username , String password , String name ,  String securityQuestion, String securityAnswer, int type , int balance)
    {
        try {
            Statement stm = con.createStatement();
            stm.executeUpdate( "Insert INTO User (username , password , name , securityQuestion , securityAnswer , type , balance) VALUES ('"+username+"' , '"+password+"' , '"+name+"' , '"+securityQuestion+"' , '"+securityAnswer+"' , '"+type+"' , '"+balance+"');" );
            stm.close();
        } catch (SQLException e) {
            System.out.println("Could not Insert data to database : InsertToUser : "+e.getMessage());
        }
    }

    User getUser (int id)
    {  
        User ans = null;
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery( "SELECT * FROM User Where `id` = "+id+";");            
            while ( rs.next() ) {
               String username = rs.getString("username");
               String password = rs.getString("password");
               String name = rs.getString("name");
               String securityQuestion = rs.getString("securityQuestion");
               String securityAnswer = rs.getString("securityAnswer");
               int type = rs.getInt("type");
               int balance = rs.getInt("balance");

                ans = new User(id,username, password, name, securityQuestion, securityAnswer, type, balance);
              
            }
            rs.close();
            stm.close();
            return ans;
        } catch (SQLException e) {
            System.out.println("Could not select data from database : getUser : "+e.getMessage());
            return ans;
        }
        
    }
    User getUser (String username , String password)
    {  
        User ans = null;
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery( "SELECT * FROM User Where `username` = '"+username+"' AND `password`='"+password+"';" );              
            while ( rs.next() ) {
               int id=rs.getInt("id");
               String name = rs.getString("name");
               String securityQuestion = rs.getString("securityQuestion");
               String securityAnswer = rs.getString("securityAnswer");
               int type = rs.getInt("type");
               int balance = rs.getInt("balance");
                ans = new User(id,username, password, name, securityQuestion, securityAnswer, type, balance);
              
            }
            rs.close();
            stm.close();
            return ans;
        } catch (SQLException e) {
            System.out.println("Could not select data from database : getUser : "+e.getMessage());
            return ans;
        }
        
    }

    User getUser (String username)
    {  
        User ans = null;
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery( "SELECT * FROM User Where `username` = '"+username+"';" );              
            while ( rs.next() ) {
               int id=rs.getInt("id");
               String password = rs.getString("password");
               String name = rs.getString("name");
               String securityQuestion = rs.getString("securityQuestion");
               String securityAnswer = rs.getString("securityAnswer");
               int type = rs.getInt("type");
               int balance = rs.getInt("balance");
                ans = new User(id,username, password, name, securityQuestion, securityAnswer, type, balance);
              
            }
            rs.close();
            stm.close();
            return ans;
        } catch (SQLException e) {
            System.out.println("Could not select data from database : getUser : "+e.getMessage());
            return ans;
        }
        
    }



    void deleteFromUser(int id )
    {
        try {
            Statement stm = con.createStatement();
            stm.executeUpdate( "DELETE FROM User WHERE `id` = "+id+";" );
            stm.close();
        } catch (SQLException e) {
            System.out.println("Could not delete data to database : deleteFromUser : "+e.getMessage());
        }
    }
}
