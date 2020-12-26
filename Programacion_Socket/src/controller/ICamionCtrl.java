package controller;

import modelo.Camion;

public interface ICamionCtrl {
	public void add(Camion oCamion);
	public  boolean CamionExiste(Camion oCamion);
	public boolean CamionLogueado(Camion oCamion);
}
