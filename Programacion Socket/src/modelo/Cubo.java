package modelo;

import java.io.Serializable;

public class Cubo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int iIdCubo = 0, iTemp = 0;
	private float fPeso = 0;

	public Cubo(int iIdCubo, int iTemp, float fPeso) {

		setiIdCubo(iIdCubo);
		setfPeso(fPeso);
		setiTemp(iTemp);
	}

	public Cubo(int iIdCubo, float fPeso) {

		setiIdCubo(iIdCubo);
		setfPeso(fPeso);

	}

	public Cubo(int iIdCubo, int iTemp) {

		setiIdCubo(iIdCubo);
		setiTemp(iTemp);
	}

	//----------------------------------------------------------------------------------
	
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

	public float getfPeso() {
		return fPeso;
	}

	public void setfPeso(float fPeso) {
		this.fPeso = fPeso;
	}

	@Override
	public String toString() {
		return "Cubo: " + iIdCubo + "\nTemperatura: " + iTemp + "\nPeso: " + fPeso;
	}

}
