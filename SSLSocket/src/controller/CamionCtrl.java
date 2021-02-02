package controller;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import modelo.Camion;

public class CamionCtrl implements ICamionCtrl {
	List<Camion> listCamion = new ArrayList<Camion>();

	@Override
	public void add(Camion oCamion) {

		try {
			dbm.Dbm.openConnectionMySQL();
			String query = "INSERT INTO CAMION (USUARIO, CONTRASENIA) VALUES ('"+oCamion.getUsuario()+"', '"+oCamion.getcontrasenia()+"'";
			dbm.Dbm.ExecuteQuery(query);
			dbm.Dbm.closeConnection();

		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
	}

	@Override
	public boolean CamionExiste(Camion oCamion) {
		boolean bCierto = false;

		try {
			dbm.Dbm.openConnectionMySQL();
			String query = "SELECT USUARIO FROM CAMION WHERE USUARIO = '"+oCamion.getUsuario()+"'";
			CachedRowSet crs = dbm.Dbm.ExecuteQuery(query);
			listCamion.add(new Camion(crs.getString("USUARIO"),null));
			dbm.Dbm.closeConnection();
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

			dbm.Dbm.openConnectionMySQL();
			String query = "SELECT * FROM CAMION WHERE USUARIO = '"+oCamion.getUsuario()+ "' AND CONTRASENIA = '"+oCamion.getcontrasenia()+"'";
			CachedRowSet crs = dbm.Dbm.ExecuteQuery(query);
			listCamion.add(new Camion(crs.getString("USUARIO"),crs.getString("CONTRASENIA")));
			dbm.Dbm.closeConnection();

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
