/* package kontroler;

	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.sql.Statement;
	import java.util.ArrayList;
	import java.util.Collection;
	
	import model.Tank;

	public class CopyOfWriteToMySql {
	    static Connection kreirajKonekciju(){
	        Connection conn=null;
	        try {
	            Class.forName("com.mysql.jdbc.Driver").newInstance();
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vodosprema","root","");  
        
			}catch(SQLException e){
			    System.out.println("Greska kod konekcije na bazu "+e.toString());
			} catch (ClassNotFoundException e) {
			    System.out.println("Driver nije nadjen "+e.toString());
			} catch (Exception e) {
			    // TODO
			    }
			    return conn;
    	}


		static Collection<Tank> dohvatiRazine() {
		    Collection<Tank> lista = new ArrayList<Tank>();
		    try{
			    Connection conn = kreirajKonekciju();
			    Statement stmt = conn.createStatement();
			    String sql = "SELECT razina FROM razina ORDER BY id ASC LIMIT 1";
			    ResultSet rs = stmt.executeQuery(sql);
			    
				while (rs.next()) {
					int razina;
					razina = rs.getInt("razina");
					
				    Tank dodajMe = new Tank(razina);
				    lista.add(dodajMe);
				}
				conn.close();
		    }catch (Exception ex){
		    System.out.println("Greska kod dohvata razine "+ex.toString());
		        lista = new ArrayList<Tank>();
		    }
		    
		    return lista;
		}
	}  */
/*	
		public  void main(String args[]) {
			
			String url    = "jdbc:mysql://127.0.0.1:3306";			
			
		try	{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection(url);
			
			Statement stt = con.createStatement();		
			
			// Create and select 
			
			stt.execute("CREATE DATABASE IF NOT EXISTS test");
			stt.execute("USE test");
			
			//Create out table
			
			stt.execute("DROP TABLE IF EXISTS razinavode2");
			stt.execute("CREATE TABLE razinavode2 (" 
			+ "id BIGINT NOT NULL AUTO_INCREMENT,"		
			+ "broj INT(4)," 
			+ "PRIMARY KEY(id)"		
			+   ")"					
					); 
		//add entries
			stt.execute("INSERT INTO razinavode2 (broj) VALUES" + 
		
				"('123'),('100'), ('185'), ('280'), ('320')");
					
		
		// Get 
		ResultSet res = stt.executeQuery ("SELECT broj FROM razinavode2 ORDER BY id SC LIMIT 1"
				//+ "ORDER BY id DESC"					
				
				); 
		
		while (res.next()) {
			
			System.out.println(res.getString("broj"));	
			
			Tank spremnik = new Tank();	  //tip modela 
			spremnik.getRazina();  
			spremnik.setRazina(res.getInt("broj"));
			System.out.println(spremnik.getRazina());		
						
			
			//spremnik.setRazina(Integer.parseInt((res.getString("broj")));		
			 
		}
		
		}
		catch(Exception e){
			
		e.printStackTrace();
		
		}
			}
	
}
*/
