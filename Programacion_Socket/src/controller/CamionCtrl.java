package controller;

import java.util.List;

import modelo.Camion;

public class CamionCtrl implements ICamionCtrl {

	public static List<Camion> lstCamiones;
	String url = "http://socketdatabase.tk/";

	@Override
	public void add(Camion oCamion) {

		try {

			url += "insertar_camion.php?usuario=" + oCamion.getUsuario() + "&contraseña=" + oCamion.getContrasena();
			ConexionPHP.peticionHttp(url);

		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
	}

	@Override
	public boolean CamionExiste(Camion oCamion) {
		boolean bCierto = false;

		try {

			url += "getCamion.php?usuario=" + oCamion.getUsuario();
			String respuesta = ConexionPHP.peticionHttp(url);
			
			
			
			if (respuesta == null || respuesta.equals("")) {

				bCierto = false;
			} else {
				bCierto = true;
			}

		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
		return bCierto;

	}

	@Override
	public boolean CamionLogueado(Camion oCamion) {
		boolean bCierto = false;

		try {

			url += "getCamionPassword.php?usuario=" + oCamion.getUsuario() + "&contraseña=" + oCamion.getContrasena();
			String respuesta = ConexionPHP.peticionHttp(url);

			if (respuesta == null || respuesta.equals("")) {

				bCierto = false;
			} else {
				bCierto = true;
			}

		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
		return bCierto;

	}
	
	

}
