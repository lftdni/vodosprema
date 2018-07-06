package model;
/* Klasa Motor sa atributom otvoren_upaljena(boolean) i generiranim get i set metodama
 * Motor pumpa-moze biti upaljena(ukljucena) ili ugasena (iskljucena)
 * Motor ventil-moze biti otvoren ili zatvoren
 * Klasa se odnosi na oba objekta pa radi lakseg snalazenja atribut otvoren_upaljena
 * vrijednost atributa:po defaultu false(pumpa ne radi ventil nije otvoren)*/
public class Motor {
	
	private boolean otvoren_upaljena=false;

	public boolean isOtvoren_upaljena() {
		return otvoren_upaljena;
	}

	public void setOtvoren_upaljena(boolean otvoren_upaljena) {
		this.otvoren_upaljena = otvoren_upaljena;
	}
	
	
}
