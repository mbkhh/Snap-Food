import java.sql.*;

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
               String  name = rs.getString("name");
               String fullname  = rs.getString("fullname");
               System.out.println( "ID = " + id );
               System.out.println( "NAME = " + name );
               System.out.println( "FULLNAME = " + fullname );
               System.out.println();
            }
            rs.close();
            stm.close();
        } catch (SQLException e) {
            System.out.println("Could not select data from database : Select_test");
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
}
