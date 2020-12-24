package modelo;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Camion implements ICamion{
	private String Usuario, Contrasena;
	
	//Constructor PK
	
	public Camion(String usuario) {
		this.Usuario = usuario;
	}
	
	public Camion(String usuario, String contrasena) {
		Usuario = usuario;
		Contrasena = contrasena;
	}

	public String getUsuario() {
		return Usuario;
	}

	public void setUsuario(String usuario) {
		Usuario = usuario;
	}

	public String getContrasena() {
		return Contrasena;
	}
	@Override
	public boolean setContrasena(String contrasena) {
		boolean bExito = false;
        if (Contrasena != null && Contrasena.length() >= MINCHARPASSWORD
                && Contrasena.length() <= MAXCHARPASSWORD) {
            this.Contrasena = encryptSha512(Contrasena);
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
		sResultado += "Contraseña: "+Contrasena+"\n";
		return sResultado;
	}
	
}
