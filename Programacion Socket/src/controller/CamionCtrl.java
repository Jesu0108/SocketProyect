package controller;

import modelo.Camion;

public class CamionCtrl implements ICamionCtrl{

	@Override
	public int add(Camion oCamion) {
		int iRes = 0;
		//A�adir
		String sql = "INSERT INTO CAMION (USUARIO, CONTRASE�A) VALUES "
				+ "('"+oCamion.getUsuario()+"','"+oCamion.getContrasena()+"');";
		return iRes;
	}

	@Override
	public boolean ClienteExiste(Camion oCamion) {
		boolean bRes = true;
		
		//Verificar
		String sql = "SELECT USUARIO FROM CAMION WHERE USUARIO = '"+oCamion.getUsuario()+"'";
		return bRes;
	}

}
