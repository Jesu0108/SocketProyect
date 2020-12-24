package controller;

import modelo.Camion;

public interface ICamionCtrl {
	/*
	 * ########
	 * # CRUD #
	 * ########
	 */
	public int add(Camion oCamion);
	public boolean ClienteExiste(Camion oCamion);
}
