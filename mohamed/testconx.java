package mohamed;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class testconx {

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
