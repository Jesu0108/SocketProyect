package controller;

import java.util.ArrayList;
import java.util.List;

import modelo.Camion;

public class CamionCtrl implements ICamionCtrl {
	List<Camion> listCamion = new ArrayList<Camion>();

	@Override
	public void add(Camion oCamion) {

		try {
			String url = "http://socketdatabase.tk/insertar_camion.php?usuario=" + oCamion.getUsuario()
					+ "&contrasenia=" + oCamion.getContrasena();
			ConexionPHP.peticionHttp(url);

		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
	}

	@Override
	public boolean CamionExiste(Camion oCamion) {
		boolean bCierto = false;

		try {
			String url = "http://socketdatabase.tk/getCamion.php?usuario=" + oCamion.getUsuario();
			String respuesta = ConexionPHP.peticionHttp(url);

			listCamion = ConexionPHP.JsonToCamiones(respuesta);

			if (listCamion.isEmpty()) {
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

			String url = "http://socketdatabase.tk/getCamionPassword.php?usuario=" + oCamion.getUsuario()
					+ "&contrasenia=" + oCamion.getContrasena();
			String respuesta = ConexionPHP.peticionHttp(url);
			listCamion = ConexionPHP.JsonToCamiones(respuesta);

			if (listCamion.isEmpty()) {

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
