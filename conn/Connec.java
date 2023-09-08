package conn;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connec {
	public static Connection connection() {
		try{  
			   Class.forName("com.mysql.cj.jdbc.Driver");
			    
			   Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mini-sas","root","");
			   
			   return con;
			   
			   }catch(Exception e){
			    System.out.println(e);
			    return null;
			   }
	}
}