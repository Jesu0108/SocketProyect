package Camion;

public class Camion_Control implements ICamion_Control{

	@Override
	public int add(Camion_Modelo oCamion) {
		int iRes = 0;
		//A�adir
		String sql = "INSERT INTO CAMION (USUARIO, CONTRASE�A) VALUES "
				+ "('"+oCamion.getUsuario()+"','"+oCamion.getContrasena()+"');";
		return iRes;
	}

	@Override
	public boolean ClienteExiste(Camion_Modelo oCamion) {
		boolean bRes = true;
		
		//Verificar
		String sql = "SELECT USUARIO FROM CAMION WHERE USUARIO = '"+oCamion.getUsuario()+"'";
		return bRes;
	}

}
