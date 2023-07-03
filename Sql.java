import java.sql.*;
import java.util.ArrayList;
public class Sql {
    public Connection connection;
    public Sql() {
        try {
            Class.forName("org.sqlite.JDBC");
            //connection = DriverManager.getConnection("jdbc:sqlite:D:\\Desktop\\Programing\\Snap-Food\\Databases\\test.db");
            connection = DriverManager.getConnection("jdbc:sqlite:Databases\\test.db");
//            connection.setAutoCommit(false);
        } catch (Exception e) {
            System.out.println("Database connection error : " + e.getMessage());
        }
    }
    void Select_test() {
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
    void Insert_test(String name , String fullname) {
        try {
            Statement stm = connection.createStatement();
            stm.executeUpdate( "Insert INTO User (name , fullname) VALUES ('"+name+"' , '"+fullname+"');" );
            stm.close();
        } catch (SQLException e) {
            System.out.println("Could not Insert data to database : Insert_test : "+e.getMessage());
        }
    }
    void Update_test(int ID , String name , String fullname) {
        try {
            Statement stm = connection.createStatement();
            stm.executeUpdate( "UPDATE User SET `name`='"+name+"' , `fullname`='"+fullname+"' WHERE `ID` = "+ID+";" );
            stm.close();
        } catch (SQLException e) {
            System.out.println("Could not update data to database : update test : "+e.getMessage());
        }
    }
    void delete_test(int ID ) {
        try {
            Statement stm = connection.createStatement();
            stm.executeUpdate( "DELETE FROM User  WHERE `ID` = "+ID+";" );
            stm.close();
        } catch (SQLException e) {
            System.out.println("Could not delete data to database : delete test : "+e.getMessage());
        }
    }
    /**
     * *
     * *
     * *
     * M.Bagher's functions
     * *
     * *
     * *
     */
    public void InsertToCart(int foodId ,int userId ,int orderId ,int cost ,int count) {
        try {
            Statement stm = connection.createStatement();
            stm.executeUpdate( "Insert INTO Cart (foodId , userId , orderId , cost , count) VALUES ('"+foodId+"' , '"+userId+"' , '"+orderId+"' , '"+cost+"' , '"+count+"');" );
            stm.close();
        } catch (SQLException e) {
            System.out.println("Could not Insert data to database : InsertToCart : "+e.getMessage());
        }
    }
    public void InsertToAddress(int userId ,int restaurantId ,int node ) {
        try {
            Statement stm = connection.createStatement();
            stm.executeUpdate( "Insert INTO Address (userId , restaurantId , node) VALUES ('"+userId+"' , '"+restaurantId+"' , '"+node+"' );" );
            stm.close();
        } catch (SQLException e) {
            System.out.println("Could not Insert data to database : InsertToAddress : "+e.getMessage());
        }
    }
    public void InsertToMap(int node1 ,int node2 ,int weight) {
        try {
            Statement stm = connection.createStatement();
            stm.executeUpdate( "Insert INTO Map (node1 , node2 , weight ) VALUES ('"+node1+"' , '"+node2+"' , '"+weight+"' );" );
            stm.close();
        } catch (SQLException e) {
            System.out.println("Could not Insert data to database : InsertToCart : "+e.getMessage());
        }
    }
    public void deleteMap() {
        try {
            Statement stm = connection.createStatement();
            stm.executeUpdate( "DELETE FROM Map;" );
            stm.close();
        } catch (SQLException e) {
            System.out.println("Could not delete data to database : deleteFromCart : "+e.getMessage());
        }
    }
    public ArrayList<Branch> getConnectedBranch(int node) {
        ArrayList<Branch> ans = new ArrayList<Branch>();
        try {
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery( "SELECT * FROM Map Where `node1`="+node+" OR `node2`="+node+";" );
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
    public ArrayList<Branch> getAllBranch() {
        ArrayList<Branch> ans = new ArrayList<Branch>();
        try {
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery( "SELECT * FROM Map;" );
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
            System.out.println("Could not select data from database : getAllBranch : "+e.getMessage());
            return ans;
        }
    }
    public void EditCart(int id, int cost, int count) {
        try {
            Statement stm = connection.createStatement();
            stm.executeUpdate( "UPDATE Cart SET `cost`='"+cost+"' , `count`='"+count+"' WHERE `id` = "+id+";" );
            stm.close();
        } catch (SQLException e) {
            System.out.println("Could not update data to database : EditCart : "+e.getMessage());
        }
    }
    public void finalizeCart(int userId, int orderId) {
        try {
            Statement stm = connection.createStatement();
            stm.executeUpdate( "UPDATE Cart SET `orderId`="+orderId+" WHERE `userId` = "+userId+" AND `orderId` = 0;" );
            stm.close();
        } catch (SQLException e) {
            System.out.println("Could not update data to database : EditCart : "+e.getMessage());
        }
    }
    public void deleteFromCart(int ID ) {
        try {
            Statement stm = connection.createStatement();
            stm.executeUpdate( "DELETE FROM Cart WHERE `id` = "+ID+";" );
            stm.close();
        } catch (SQLException e) {
            System.out.println("Could not delete data to database : deleteFromCart : "+e.getMessage());
        }
    }
    public ArrayList<Cart> getCart(int FoodId, int UserId , int OrderId) {
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
    public ArrayList<Cart> getCart(int UserId , int OrderId) {
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
    public void InsertToOrder(int userId, int restaurantId, int deliveryId, String path, int pathLength,int estimatedTime, Long addTime, int totalprice, int totalDiscount, OrderStatus status, String discription) {
        try {
            Statement stm =  connection.createStatement();
            stm.executeUpdate( "Insert INTO `Order` (userId ,restaurantId ,deliveryId,path , pathLength ,estimatedTotalTime ,addTime ,totalPrice ,totalDiscount ,status ,discription) VALUES ('"+userId+"' ,'"+restaurantId+"' ,'"+deliveryId+"','"+path+"' , '"+pathLength+"' ,'"+estimatedTime+"' ,'"+addTime+"' ,'"+totalprice+"' ,'"+totalDiscount+"' ,'"+status+"' ,'"+discription+"');" );
            stm.close();
        } catch (SQLException e) {
            System.out.println("Could not Insert data to database : InsertToOrder : "+e.getMessage());
        }
    }
    public ArrayList<Order> getAllOrderOfUser(int userId) {
        ArrayList<Order> ans = new ArrayList<Order>();
        try {
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery( "SELECT * FROM `Order` WHERE `userId` ="+userId+" ;" );
            while ( rs.next() ) {
                int id = rs.getInt("id");
                int restaurantId = rs.getInt("restaurantId");
                int deliveryId = rs.getInt("deliveryId");
                String path = rs.getString("path");
                int pathLength = rs.getInt("pathLength");
                int estimatedTime = rs.getInt("estimatedTotalTime");
                long addTime = rs.getLong("addTime");
                int totalprice = rs.getInt("totalprice");
                int totalDiscount = rs.getInt("totalDiscount");
                String status = rs.getString("status");
                String discription= rs.getString("discription");

                ans.add(new Order(id, userId, restaurantId, deliveryId, path, pathLength, estimatedTime, addTime, totalprice, totalDiscount, status, discription));
            }
            rs.close();
            stm.close();
            return ans;
        } catch (SQLException e) {
            System.out.println("Could not select data from database : getAllOrderOfUser : "+e.getMessage());
            return ans;
        }
    }
    public ArrayList<Order> getAllOrderById(int orderId, int userId) {
        ArrayList<Order> ans = new ArrayList<Order>();
        try {
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery( "SELECT * FROM `Order` WHERE `userId` ="+userId+" AND `id`="+orderId+" ;" );
            while ( rs.next() ) {
                int id = rs.getInt("id");
                int restaurantId = rs.getInt("restaurantId");
                int deliveryId = rs.getInt("deliveryId");
                String path = rs.getString("path");
                int pathLength = rs.getInt("pathLength");
                int estimatedTime = rs.getInt("estimatedTotalTime");
                long addTime = rs.getLong("addTime");
                int totalprice = rs.getInt("totalprice");
                int totalDiscount = rs.getInt("totalDiscount");
                String status = rs.getString("status");
                String discription= rs.getString("discription");

                ans.add(new Order(id, userId, restaurantId, deliveryId, path, pathLength, estimatedTime, addTime, totalprice, totalDiscount, status, discription));
            }
            rs.close();
            stm.close();
            return ans;
        } catch (SQLException e) {
            System.out.println("Could not select data from database : getAllOrderOfUser : "+e.getMessage());
            return ans;
        }
    }
    public ArrayList<Order> getFreeOrder() {
        ArrayList<Order> ans = new ArrayList<Order>();
        try {
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery( "SELECT * FROM `Order` WHERE `deliveryId`=0 AND `status`!='Canceled' AND `status`!='Completed' ;" );
            while ( rs.next() ) {
                int id = rs.getInt("id");
                int userId = rs.getInt("userId");
                int restaurantId = rs.getInt("restaurantId");
                int deliveryId = rs.getInt("deliveryId");
                String path = rs.getString("path");
                int pathLength = rs.getInt("pathLength");
                int estimatedTime = rs.getInt("estimatedTotalTime");
                long addTime = rs.getLong("addTime");
                int totalprice = rs.getInt("totalprice");
                int totalDiscount = rs.getInt("totalDiscount");
                String status = rs.getString("status");
                String discription= rs.getString("discription");

                ans.add(new Order(id, userId, restaurantId, deliveryId, path, pathLength, estimatedTime, addTime, totalprice, totalDiscount, status, discription));
            }
            rs.close();
            stm.close();
            return ans;
        } catch (SQLException e) {
            System.out.println("Could not select data from database : getAllOrderOfUser : "+e.getMessage());
            return ans;
        }
    }
    public ArrayList<Order> getFreeOrderById(int orderId) {
        ArrayList<Order> ans = new ArrayList<Order>();
        try {
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery( "SELECT * FROM `Order` WHERE `deliveryId`=0 AND `status`!='Canceled' AND `status`!='Completed' AND id="+orderId+";" );
            while ( rs.next() ) {
                int id = rs.getInt("id");
                int userId = rs.getInt("userId");
                int restaurantId = rs.getInt("restaurantId");
                int deliveryId = rs.getInt("deliveryId");
                String path = rs.getString("path");
                int pathLength = rs.getInt("pathLength");
                int estimatedTime = rs.getInt("estimatedTotalTime");
                long addTime = rs.getLong("addTime");
                int totalprice = rs.getInt("totalprice");
                int totalDiscount = rs.getInt("totalDiscount");
                String status = rs.getString("status");
                String discription= rs.getString("discription");

                ans.add(new Order(id, userId, restaurantId, deliveryId, path, pathLength, estimatedTime, addTime, totalprice, totalDiscount, status, discription));
            }
            rs.close();
            stm.close();
            return ans;
        } catch (SQLException e) {
            System.out.println("Could not select data from database : getAllOrderOfUser : "+e.getMessage());
            return ans;
        }
    }
    public ArrayList<Order> getOrderByIdAndDelivery(int orderId,int deliveryId) {
        ArrayList<Order> ans = new ArrayList<Order>();
        try {
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery( "SELECT * FROM `Order` WHERE `deliveryId`="+deliveryId+" AND id="+orderId+";" );
            while ( rs.next() ) {
                int id = rs.getInt("id");
                int userId = rs.getInt("userId");
                int restaurantId = rs.getInt("restaurantId");
                String path = rs.getString("path");
                int pathLength = rs.getInt("pathLength");
                int estimatedTime = rs.getInt("estimatedTotalTime");
                long addTime = rs.getLong("addTime");
                int totalprice = rs.getInt("totalprice");
                int totalDiscount = rs.getInt("totalDiscount");
                String status = rs.getString("status");
                String discription= rs.getString("discription");

                ans.add(new Order(id, userId, restaurantId, deliveryId, path, pathLength, estimatedTime, addTime, totalprice, totalDiscount, status, discription));
            }
            rs.close();
            stm.close();
            return ans;
        } catch (SQLException e) {
            System.out.println("Could not select data from database : getAllOrderOfUser : "+e.getMessage());
            return ans;
        }
    }
    public ArrayList<Order> getAllOrderById2(int orderId, int restaurantId) {
        ArrayList<Order> ans = new ArrayList<Order>();
        try {
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery( "SELECT * FROM `Order` WHERE `restaurantId` ="+restaurantId+" AND `id`="+orderId+" ;" );
            while ( rs.next() ) {
                int id = rs.getInt("id");
                int userId = rs.getInt("userId");
                int deliveryId = rs.getInt("deliveryId");
                String path = rs.getString("path");
                int pathLength = rs.getInt("pathLength");
                int estimatedTime = rs.getInt("estimatedTotalTime");
                long addTime = rs.getLong("addTime");
                int totalprice = rs.getInt("totalprice");
                int totalDiscount = rs.getInt("totalDiscount");
                String status = rs.getString("status");
                String discription= rs.getString("discription");

                ans.add(new Order(id, userId, restaurantId, deliveryId, path, pathLength, estimatedTime, addTime, totalprice, totalDiscount, status, discription));
            }
            rs.close();
            stm.close();
            return ans;
        } catch (SQLException e) {
            System.out.println("Could not select data from database : getAllOrderOfUser : "+e.getMessage());
            return ans;
        }
    }
    public ArrayList<Order> getRestaurantOpenOrder(int restaurantId) {
        ArrayList<Order> ans = new ArrayList<Order>();
        try {
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery( "SELECT * FROM `Order` WHERE `restaurantId` ="+restaurantId+" AND `status`!='Canceled' AND `status`!='Completed' ;" );
            while ( rs.next() ) {
                int id = rs.getInt("id");
                int userId = rs.getInt("userId");
                int deliveryId = rs.getInt("deliveryId");
                String path = rs.getString("path");
                int pathLength = rs.getInt("pathLength");
                int estimatedTime = rs.getInt("estimatedTotalTime");
                long addTime = rs.getLong("addTime");
                int totalprice = rs.getInt("totalprice");
                int totalDiscount = rs.getInt("totalDiscount");
                String status = rs.getString("status");
                String discription= rs.getString("discription");

                ans.add(new Order(id, userId, restaurantId, deliveryId, path, pathLength, estimatedTime, addTime, totalprice, totalDiscount, status, discription));
            }
            rs.close();
            stm.close();
            return ans;
        } catch (SQLException e) {
            System.out.println("Could not select data from database : getAllOrderOfUser : "+e.getMessage());
            return ans;
        }
    }
    public ArrayList<Order> getRestaurantAllOrder(int restaurantId) {
        ArrayList<Order> ans = new ArrayList<Order>();
        try {
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery( "SELECT * FROM `Order` WHERE `restaurantId` ="+restaurantId+" ;" );
            while ( rs.next() ) {
                int id = rs.getInt("id");
                int userId = rs.getInt("userId");
                int deliveryId = rs.getInt("deliveryId");
                String path = rs.getString("path");
                int pathLength = rs.getInt("pathLength");
                int estimatedTime = rs.getInt("estimatedTotalTime");
                long addTime = rs.getLong("addTime");
                int totalprice = rs.getInt("totalprice");
                int totalDiscount = rs.getInt("totalDiscount");
                String status = rs.getString("status");
                String discription= rs.getString("discription");

                ans.add(new Order(id, userId, restaurantId, deliveryId, path, pathLength, estimatedTime, addTime, totalprice, totalDiscount, status, discription));
            }
            rs.close();
            stm.close();
            return ans;
        } catch (SQLException e) {
            System.out.println("Could not select data from database : getAllOrderOfUser : "+e.getMessage());
            return ans;
        }
    }
    public void editOrder(int id, int time, String status) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate( "UPDATE `Order` SET status = '" + status + "',estimatedTotalTime = " + time + "  WHERE id = " + id + ";" );
            statement.close();
        } catch (SQLException e) {
            System.out.println("Could not update data to database : editOrder : " + e.getMessage());
        }
    }
    public void editOrder2(int id,int deliveryId, String status) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate( "UPDATE `Order` SET status = '" + status + "',deliveryId = " + deliveryId + "  WHERE id = " + id + ";" );
            statement.close();
        } catch (SQLException e) {
            System.out.println("Could not update data to database : editOrder : " + e.getMessage());
        }
    }
    public int getOrderLastId() {
        try {
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery( "SELECT MAX(id) AS max_id FROM `Order`;" );
            int id = rs.getInt("max_id");
            //while ( rs.next() ) {
            //    int id = rs.getInt("max_id");
            //}
            rs.close();
            stm.close();
            return id;
        } catch (SQLException e) {
            System.out.println("Could not select data from database : getOrderLastId : "+e.getMessage());
            return 0;
        }
    }
    public Address getAddress (int userId , int restaurantId) {
        Address ans = null;
        try {
            Statement stm =  connection.createStatement();
            ResultSet rs = stm.executeQuery( "SELECT * FROM Address Where `userId` = "+userId+" AND `restaurantId` = "+restaurantId+";");
            while ( rs.next() ) {
                int id = rs.getInt("id");
                int node = rs.getInt("node");
                ans = new Address(id, userId , restaurantId , node);
            }
            rs.close();
            stm.close();
            return ans;
        } catch (SQLException e) {
            System.out.println("Could not select data from database : getAddress : "+e.getMessage());
            return ans;
        }
    }
    public boolean editAddress(int id, int userId, int restaurantId, int node) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate( "UPDATE User SET userId = " + userId + ", restaurantId = " + restaurantId + ", node = " + node + " WHERE id = " + id + ";" );
            statement.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Could not update data to database : editAddress : " + e.getMessage());
            return false;
        }
    }

    /****
     * Parham's functions **
     * *********************/

    public void InsertToUser(String username , String password , String name ,  String securityQuestion, String securityAnswer, int type , int balance) {
        try {
            Statement stm =  connection.createStatement();
            stm.executeUpdate( "Insert INTO User (username , password , name , securityQuestion , securityAnswer , type , balance) VALUES ('"+username+"' , '"+password+"' , '"+name+"' , '"+securityQuestion+"' , '"+securityAnswer+"' , '"+type+"' , '"+balance+"');" );
            stm.close();
        } catch (SQLException e) {
            System.out.println("Could not Insert data to database : InsertToUser : "+e.getMessage());
        }
    }

    public User getUser (int id) {
        User ans = null;
        try {
            Statement stm =  connection.createStatement();
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
    public User getUser (String username , String password) {
        User ans = null;
        try {
            Statement stm =  connection.createStatement();
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
    public User getUser (String username) {
        User ans = null;
        try {
            Statement stm =  connection.createStatement();
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
    public void deleteFromUser(int id ) {
        try {
            Statement stm =  connection.createStatement();
            stm.executeUpdate( "DELETE FROM User WHERE `id` = "+id+";" );
            stm.close();
        } catch (SQLException e) {
            System.out.println("Could not delete data to database : deleteFromUser : "+e.getMessage());
        }
    }
    void updateUserBalance(int ID , int balance) {
        try {
            Statement stm = connection.createStatement();
            stm.executeUpdate( "UPDATE User SET `balance`='"+balance+"' WHERE `ID` = "+ID+";" );
            stm.close();
        } catch (SQLException e) {
            System.out.println("Could not update data to database : update test : "+e.getMessage());
        }
    }

    int getNewBalance (int id)
    {  
        int balance = -1;
        try {
            Statement stm =  connection.createStatement();
            ResultSet rs = stm.executeQuery( "SELECT * FROM User Where `id` = "+id+";");            
            while ( rs.next() ) {
             balance = rs.getInt("balance");  
            }
            rs.close();
            stm.close();
            return balance;
        } catch (SQLException e) {
            System.out.println("Could not select data from database : getUser : "+e.getMessage());
            return balance;
        }
        
    }
    /**
     * *
     * *
     * *
     * Taha's functions
     * *
     * *
     * *
     **/

    public void insertToRestaurant(int ownerId, String name, String type, int postCost) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("Insert INTO Restaurant (ownerId, name, type, postCost) VALUES ('" + ownerId + "', '" + name + "', '" + type + "', '" + postCost + "');");
            statement.close();
        } catch (SQLException e) {
            System.out.println("Could not Insert data to database : insertToRestaurant : " + e.getMessage());
        }
    }
    public void insertToFood(int restaurantId, String name, int price, String type, int discountPercent, long discountTime, String isActive) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("Insert INTO Food (restaurantId, name, price, type, discountPercent, discountTime, isActive) VALUES (" + restaurantId + ", '" + name + "', " + price + ", '" + type + "', " + discountPercent + ", " + discountTime + ", '" + isActive + "');");
            statement.close();
        } catch (SQLException e) {
            System.out.println("Could not Insert data to database : insertToRestaurant : " + e.getMessage());
        }
    }
    public void editRestaurant(int id, int ownerId, String name,  String type, int postCost) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate( "UPDATE Reastaurant SET ownerId = " + ownerId + ", name = '" + name + "', type = '" + type + "', postCost = " + postCost + " WHERE id = " + id + ";" );
            statement.close();
        } catch (SQLException e) {
            System.out.println("Could not update data to database : editRestaurant : " + e.getMessage());
        }
    }
    public void editFood(int id, int restaurantId, String name, int price, String type, int discountPercent, long discountTime, String isActive) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate( "UPDATE Food SET restaurantId = " + restaurantId + ", name = '" + name + "', price = " + price + " type = '" + type + "', discountPercent = " + discountPercent + ", discountTime = " + discountTime + ", isActive = '" + isActive + "' WHERE id = " + id + ";" );
            statement.close();
        } catch (SQLException e) {
            System.out.println("Could not update data to database : editRestaurant : " + e.getMessage());
        }
    }
    public void deleteFromRestaurant(int id) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate( "DELETE FROM Restaurant WHERE id = " + id + ";" );
            statement.close();
        } catch (SQLException e) {
            System.out.println("Could not delete data to database : deleteFromRestaurant : " + e.getMessage());
        }
    }
    public void deleteFromFood(int idKey, String witchId) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate( "DELETE FROM Food WHERE " + witchId + " = " + idKey + ";" );
            statement.close();
        } catch (SQLException e) {
            System.out.println("Could not delete data to database : deleteFromRestaurant : " + e.getMessage());
        }
    }
    public ArrayList<Restaurant> getRestaurant(int idKey, String whichId, boolean isForAll) {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet;
            if (!isForAll)
                resultSet = statement.executeQuery("SELECT * FROM Restaurant Where " + whichId + " = " + idKey + " ORDER BY name, id;");
            else
                resultSet = statement.executeQuery("SELECT * FROM Restaurant ORDER BY name, id;");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                User owner = User.getUserById(resultSet.getInt("ownerId"));
                ArrayList<FoodType> types = Functions.stringToEnum(resultSet.getString("type"));
                int postCost = resultSet.getInt("postCost");
                restaurants.add(new Restaurant(id, owner, name, types, postCost));
            }
            resultSet.close();
            statement.close();
            return restaurants;
        } catch (SQLException e) {
            System.out.println("Could not select data from database : getRestaurant : " + e.getMessage());
            return restaurants;
        }
    }
    public ArrayList<Food> getFood(int idKey, String whichId, boolean isForAll) {
        ArrayList<Food> foods = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet;
            if (!isForAll)
                resultSet = statement.executeQuery("SELECT * FROM Food Where " + whichId + " = " + idKey + " ORDER BY type, name, id;");
            else
                resultSet = statement.executeQuery("SELECT * FROM Food ORDER BY type, name, id;");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int restaurantId = resultSet.getInt("restaurantId");
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                String type = resultSet.getString("type");
                int discountPercent = resultSet.getInt("discountPercent");
                long discountTime = resultSet.getLong("discountTime");
                String isActive = resultSet.getString("isActive");
                foods.add(new Food(id, restaurantId, name, price, type, discountPercent, discountTime, isActive));
            }
            resultSet.close();
            statement.close();
            return foods;
        } catch (SQLException e) {
            System.out.println("Could not select data from database : getRestaurant : " + e.getMessage());
            return foods;
        }
    }
}
