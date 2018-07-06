package model;

/* Klasa senzor sa atributom detektira(boolean) i generiranim get i set metodama
 * po defaultu false(detektor ne detektira)*/
public class Senzor {

	private boolean detektira=false;

	public boolean isDetektira() {
		return detektira;
	}

	public void setDetektira(boolean detektira) {
		this.detektira = detektira;
	}
	
}
