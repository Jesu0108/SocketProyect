package modelo;

import java.io.Serializable;

public class Cubo implements Serializable, ICubo{

	private static final long serialVersionUID = 1L;
	
	private int iIdCubo = 0, iTemp = 0;
	private float fPeso = 0;

	public Cubo(int iIdCubo, int iTemp, float fPeso) {

		setiIdCubo(iIdCubo);
		setfPeso(fPeso);
		setiTemp(iTemp);
	}

	//----------------------------------------------------------------------------------
	
	@Override
	public int getiIdCubo() {
		return iIdCubo;
	}

	@Override
	public void setiIdCubo(int iIdCubo) {
		this.iIdCubo = iIdCubo;
	}

	@Override
	public int getiTemp() {
		return iTemp;
	}

	@Override
	public void setiTemp(int iTemp) {
		this.iTemp = iTemp;
	}

	@Override
	public float getfPeso() {
		return fPeso;
	}

	@Override
	public void setfPeso(float fPeso) {
		this.fPeso = fPeso;
	}

	@Override
	public String toString() {
		return "Cubo: " + iIdCubo + "\nTemperatura: " + iTemp + "\nPeso: " + fPeso;
	}

}
