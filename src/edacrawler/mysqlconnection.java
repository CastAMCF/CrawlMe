package edacrawler;

import java.sql.*;

public class mysqlconnection {
	
	public static Connection conexao = null;
	
	public static Connection bdconnector() {
		
		try {
			//Class.forName("com.mysql.jdbc.Driver");
			Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost/teste", "root", "rootroot");
			return conexao;
		}catch(Exception e1) {return null;}
	}

}
