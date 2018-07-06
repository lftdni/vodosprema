package model;
/* Klasa Alarm sa atributom aktiviran(boolean) i generiranim get i set metodama
 * vrijednost atributa:po defaultu false(alarm nije aktiviran)*/
public class Alarm {
	
	private boolean aktiviran=false;

	public boolean isAktiviran() {
		return aktiviran;
	}

	public void setAktiviran(boolean aktiviran) {
		this.aktiviran = aktiviran;
	}
}
