package modelo;

public class Cubos {

	private int iIdCubo,iTemp,iPeso;

	public Cubos(int iIdCubo, int iTemp, int iPeso) {
		
		setiIdCubo(iIdCubo);
		setiPeso(iPeso);
		setiTemp(iTemp);
	}

	public int getiIdCubo() {
		return iIdCubo;
	}

	public void setiIdCubo(int iIdCubo) {
		this.iIdCubo = iIdCubo;
	}

	public int getiTemp() {
		return iTemp;
	}

	public void setiTemp(int iTemp) {
		this.iTemp = iTemp;
	}

	public int getiPeso() {
		return iPeso;
	}

	public void setiPeso(int iPeso) {
		this.iPeso = iPeso;
	}

	@Override
	public String toString() {
		return "Cubo: " + iIdCubo + "\nTemperatura: " + iTemp + "\nPeso: " + iPeso;
	}

}
