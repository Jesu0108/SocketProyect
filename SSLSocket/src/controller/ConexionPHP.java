package controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import modelo.Camion;

public class ConexionPHP {

	public static List<Camion> JsonToCamiones(String respuesta) {

		List<Camion> lstResultado = new ArrayList<>();

		JSONArray jsonA = new JSONArray(respuesta);

		for (int i = 0; i < jsonA.length(); i++) {

			JSONObject jsonO = jsonA.getJSONObject(i);

			Camion c = JsonToCamion(jsonO);

			lstResultado.add(c);

		}

		return lstResultado;
	}

	public static Camion JsonToCamion(JSONObject jsonO) {

		String usuario = jsonO.getString("usuario");

		Camion c = new Camion(usuario);

		return c;
	}

	public static String peticionHttp(String urlWebService) throws Exception {

		StringBuilder resultado = new StringBuilder();

		URL url = new URL(urlWebService);

		HttpURLConnection conexion = (HttpURLConnection) url.openConnection();

		conexion.setRequestMethod("GET");

		// Recoger los datos de respuesta
		BufferedReader rd = new BufferedReader(new InputStreamReader(conexion.getInputStream()));

		String linea;

		while ((linea = rd.readLine()) != null) {

			resultado.append(linea);

		}

		// cerramos el buffered
		rd.close();

		return resultado.toString();
	}

}
