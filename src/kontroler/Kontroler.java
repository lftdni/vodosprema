package kontroler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Alarm;
import model.Motor;
import model.Senzor;
import model.Tank;

public class Kontroler {	

//-------------------------------------------
	Tank spremnik = new Tank();	
	Alarm pun = new Alarm();		
	Alarm prazan = new Alarm();		
	Senzor vrloNisko = new Senzor();		
	Senzor nisko = new Senzor();		
	Senzor visoko = new Senzor();		//Stvaranje klasa odredenog tipa
	Senzor vrloVisoko = new Senzor();		
	Motor pumpa = new Motor();		
	Motor ventil= new Motor();
	
//----------------------------------------------------																	
	public void pokreni()
	{	
		spremnik.setRazina(250);
		 System.out.println("Upaljena aplikacija");	
	//funkcija pokreni koja po svome pozivanju postavlja vrijednost razine objekta spremnik na 250
	}
//----------------------------------------------------
	
	
	public void upaliPumpu(boolean stanjePumpe)
	{	
		if(stanjePumpe==true)
		{
			
		int razinaTek=spremnik.getRazina();
		razinaTek=razinaTek-10;
		spremnik.setRazina(razinaTek);
		pumpa.setOtvoren_upaljena(true);
	/*Funkcija upaliPumpu argument je stanjePumpe on se provjerava 	 ako je njegova vrijednost true(ako je pumpa aktivirana) uzima se vrijednost razine objekta 
	 spremnik umanji se za 10 i umanjena vrijednost se ponovno sprema objektu spremnik i njegovom atributu razina
	atribut Otvoren_upaljena postavlja se na true kao dojava stanja da pumpa radi razina-10*/
		
		}
	}

	public void otvoriVentil(boolean stanjeVentila)
	{
		if(stanjeVentila==true)
		{
		int razinaTek=spremnik.getRazina();
		razinaTek=razinaTek+10;
		spremnik.setRazina(razinaTek);
		ventil.setOtvoren_upaljena(true);
	/*Funkcija otvoriVentil argument je stanjeVentila on se provjerava
	 ako je njegova vrijednost true(ako je ventil aktiviran) uzima se vrijednost razine objekta spremnik, poveca se
	 za 10 i povecana vrijednost sprema se natrag atributu razina objektu spremnik i postavlja se atributu 
	 otvoren_upaljena objekta ventil vrijednost na true u smislu da je ventil otvoren 
	  */
		}
	}

	public void upaljenoOtvoreno()
	{		
		int razinaTek=spremnik.getRazina();
		razinaTek=razinaTek+6;
		spremnik.setRazina(razinaTek);
		pumpa.setOtvoren_upaljena(true);
		/*Funkcija upaljenoOtvoreno je funkcija kada je ventil otvoren a pumpa upaljena
		 * uzima se vrijednost razine objekta spremnik poveca se za 6 i vraca natrag,stanje se postavlja na true*/		
	}
	
	public void kruzniProces()
	{
		
		int razinaTek=spremnik.getRazina();
		String stanja=" ";
		String stanjeSpremnika=" ";
	
	if(razinaTek==500)
		{
			pun.setAktiviran(true);
			vrloVisoko.setDetektira(false); // 
			stanjeSpremnika="Pun";
			ventil.setOtvoren_upaljena(false);
		}
	
	if(razinaTek>=450 && razinaTek<=490)
		{
			pun.setAktiviran(false);
			vrloVisoko.setDetektira(true);
			stanjeSpremnika="Vrlo visoko:";
			stanja="T,f,f,f";
		}
		
	if(razinaTek<=440 && razinaTek>=350)
		{
			vrloVisoko.setDetektira(false);
			visoko.setDetektira(true);
			stanjeSpremnika="Visoko:";
			stanja="f,T,f,f";
		}
	
	if(razinaTek==300 || razinaTek==295)
		{
			visoko.setDetektira(false);
			stanjeSpremnika="Srednje:";
			stanja="f,f,f,f";
		}
	if(razinaTek<=150 && razinaTek>=60)
		{
			nisko.setDetektira(true);
			stanjeSpremnika="Nisko:";
			stanja="f,f,T,f";
		}
	
	if( razinaTek <=50 && razinaTek >=10 )
		{
			vrloNisko.setDetektira(true);
			nisko.setDetektira(false);
			stanjeSpremnika="Vrlo nisko:";
			stanja="f,f,f,T";
		}
	
	if(razinaTek<=0)
		{
			vrloNisko.setDetektira(false);
			prazan.setAktiviran(true);
			stanjeSpremnika="Prazan:";
			stanja="f,f,f,f";		
		}
	System.out.println(stanjeSpremnika +" "+ stanja);		//-----postavljanje vrijednosti na senzore,aktiviranje odgovarajuceg objekta kada je zadovoljen uvijet
														//	(postavljanje na true) //ispis stanja
	}
	
		public int pooling() {			
	
		int razina;
			razina=spremnik.getRazina();
					if(razina<=0)
					{
						razina=0;
					}
					if(razina>=500)
					{
						razina=500;
					}				
		
		return razina;	/*Funkcija koja uzima vrijednost objekta razine i salje u view za spremnik i u slucaju da je vrijednost manja od 0 postavlja vrijednost na 0,
						a ako je veca od 500 postavlja vrijednost 500 * granica */
		}
		
		
public void Datotekica (int razina)
	{
		//ovdje poèinje kod za unos u bazu
		
				try {
						Class.forName("com.mysql.jdbc.Driver");
						
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vodosprema","root","");
					    
						Statement stmt = (Statement) con.createStatement();
						String insert = " UPDATE razina set Razina = "+ razina +" where Id='1'";
					    stmt.executeUpdate(insert);
					    System.out.println(spremnik.getRazina());	
					    
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		//ovdje završava baza		
		
		File file = new File("vodica.txt");
		FileWriter writer=null;
	
	try {	
			writer = new FileWriter(file, true);
			writer.write(String.valueOf(razina));
			writer.write("\r \n");
			writer.flush();
			writer.close();
		}
	
		 catch (IOException e1) {
			 e1.printStackTrace();
		 }		/*Stavranje i spremanje u datoteku; \r\n je za ispis vrijednost jedno ispod drugog u txt datoteci */
	}
	
	
//	public void baza()
//		{
//		   //		
//		}
	
	public void UcitajizBaze(){

		try	{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://headless.eur.hr:3306/headless_riteatar","headless_razina","razina");

			Statement stmt = (Statement) con.createStatement();
			String select = "SELECT razina FROM Razina ORDER BY id DESC LIMIT 1";
			stmt.executeUpdate(select);		

			}

				catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
					e.printStackTrace();
			} 	catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
		
	public void UcitajDat() {
	
		File file = new File("vodica.txt");		
		String strLine=" ";
		StringBuilder text = new StringBuilder();
		if(file.exists())
		{
		try {
			FileReader fReader = new FileReader(file);
			BufferedReader bReader = new BufferedReader(fReader);
			while( (strLine=bReader.readLine()) != null  ){
					
				spremnik.setRazina(Integer.parseInt(strLine));
			}
			}

		catch (Exception e) 
			{
			spremnik.setRazina(250);
			}
		}
		else
		{
			spremnik.setRazina(250);
		}	
		}	/*funkcija ucitaj iz datoteke trazi datoteku ako postoji pa ako ju nade ucita vrijednost iz nje u slucaju ako nema datoteke vrijednost se postavlja na 250*/
	
}
