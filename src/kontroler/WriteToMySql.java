package kontroler;

import java.sql.*;

import model.Tank;
public class WriteToMySql {	
	
	
		public void main(String args[]) {
			
			String url    = "jdbc:mysql://localhost:3306";			
			
		try	{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection(url);
			
			Statement stt = con.createStatement();		
			
			// Create and select 
			
			stt.execute("CREATE DATABASE IF NOT EXISTS razina");
			stt.execute("USE razina");
			
			//Create out table
			
			stt.execute("DROP TABLE IF EXISTS razina");
			stt.execute("CREATE TABLE razina (" 
			+ "id INT NOT NULL AUTO_INCREMENT,"		
			+ "razina INT(4)," 
			+ "PRIMARY KEY(id)"		
			+   ")"					
					); 
		//add entries
			stt.execute("INSERT INTO razina (razina) VALUES" +		
				"(350)");
					
		
		// Get 
		ResultSet res = stt.executeQuery ("SELECT broj FROM razina ORDER BY id DESC LIMIT 1"
				//+ "ORDER BY id DESC"					
				
				);
		
		while (res.next()) {
			
			System.out.println(res.getString("razina"));	
			
			Tank spremnik = new Tank();	  //tip modela 
			spremnik.getRazina();  
			System.out.println(spremnik.getRazina());	
			spremnik.setRazina(res.getInt("razina"));				
												 
		}
		
		}
		catch(Exception e){
			
		e.printStackTrace();
		
		}
			}
	
}
