package Camion;

public interface ICamion_Control {
	/*
	 * ########
	 * # CRUD #
	 * ########
	 */
	public int add(Camion_Modelo oCamion);
	public boolean ClienteExiste(Camion_Modelo oCamion);
}
