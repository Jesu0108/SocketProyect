package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbm.Dbm;
import modelo.Camion;

public class CamionCtrl implements ICamionCtrl {
	List<Camion> listCamion = new ArrayList<Camion>();

	@Override
	public void add(Camion oCamion) {

		try {
			Dbm.openConnectionMySQL();
			String query = "INSERT INTO CAMION (USUARIO, CONTRASENIA) VALUES ('"+oCamion.getUsuario()+"', '"+oCamion.getcontrasenia()+"')";
			Dbm.executeInsert(query);
			Dbm.closeConnection();

		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
	}

	@Override
	public boolean CamionExiste(Camion oCamion) {

		try {
			Dbm.openConnectionMySQL();
			String query = "SELECT * FROM CAMION WHERE USUARIO = '"+oCamion.getUsuario()+"'";
			ResultSet r = Dbm.getData(query);
			conseguirDatos(r);
			Dbm.closeConnection();

		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
		return lstEmpty();

	}

	@Override
	public boolean CamionLogueado(Camion oCamion) {

		try {

			Dbm.openConnectionMySQL();
			String query = "SELECT * FROM CAMION WHERE USUARIO = '"+oCamion.getUsuario()+ "' AND CONTRASENIA = '"+oCamion.getcontrasenia()+"'";
			ResultSet crs = Dbm.getData(query);
			conseguirDatos(crs);
			Dbm.closeConnection();


		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
		return lstEmpty();

	}
	

	private void conseguirDatos(ResultSet r) throws SQLException {
		while(r.next()) {
			
			String usuario = r.getString("USUARIO");
			String contrasenia = r.getString("CONTRASENIA");
			
			listCamion.add(new Camion(usuario,contrasenia));
			
		}
	}

	private boolean lstEmpty() {
		boolean bCierto;
		if (listCamion.isEmpty()) {
			bCierto = false;
		} else {
			bCierto = true;
		}
		return bCierto;
	}
	
	

}
