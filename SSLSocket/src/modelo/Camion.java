package modelo;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Camion implements ICamion{
	private String Usuario, Contrasenia;
	
	//Constructor PK
	
	public Camion(String usuario) {
		this.Usuario = usuario;
	}
	
	public Camion(String usuario, String contrasenia) {
		Usuario = usuario;
		Contrasenia = contrasenia;
	}

	public String getUsuario() {
		return Usuario;
	}

	public void setUsuario(String usuario) {
		Usuario = usuario;
	}

	public String getcontrasenia() {
		return Contrasenia;
	}
	@Override
	public boolean setcontrasenia(String contrasenia) {
		boolean bExito = false;
        if (contrasenia != null && contrasenia.length() >= MINCHARPASSWORD
                && contrasenia.length() <= MAXCHARPASSWORD) {
            this.Contrasenia = encryptSha512(contrasenia);
            bExito = true;
        }
        return bExito;
		
	}
	
	//Metodo para encriptar contraseña
	public String encryptSha512(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger no = new BigInteger(1, messageDigest);
			String hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String toString() {
		String sResultado = "";
		sResultado += "---CAMION---";
		sResultado += "Usuario: "+Usuario+"\n";
		sResultado += "Contrasenia: "+Contrasenia+"\n";
		return sResultado;
	}

	
	
}
