package dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    private static Connection con;

    private DataBaseConnection(){}

    static{
        String dataBaseName = "fiverrpro";
        String username = "root";
        String password = "root";

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            //con = DriverManager.getConnection("jdbc:mysql://localhost:3307/%s,%s, %s".format(dataBaseName, username,password ));

            con = DriverManager.getConnection("jdbc:mysql://localhost:3307/" + dataBaseName,username,password);
            System.out.println("Connection Established!");

        } catch (ClassNotFoundException ex) {
            //Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Class Not Found Connection in Data Base Connection Class");

        }catch (SQLException ex) {
            //Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("SQL Exception in Data Base Connection Class");

        }
        catch(Exception e){
            System.out.println("Other Exception in Data Base Connection Class");
            e.printStackTrace();
        }

    }

    public static Connection getDBConnection(){

        return con;

    }

}

