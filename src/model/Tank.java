package model;
/*
 Klasa Tank sa objektom razina(int) i generiranim get i set metodama
 */
public class Tank {
	
	private int razina = 0;
	
	public Tank()
	{
	}
	
	public Tank(int razina)
	{
		this.razina = razina;
	}

	public int getRazina() {
		return razina;
	}

	public void setRazina(int razina) {
		this.razina = razina;
	}

}
