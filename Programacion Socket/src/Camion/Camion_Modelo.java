package Camion;

public class Camion_Modelo implements ICamion_Modelo{
	private String Usuario, Contrasena;
	
	//Constructor PK
	
	public Camion_Modelo(String usuario) {
		this.Usuario = usuario;
	}
	
	public Camion_Modelo(String usuario, String contrasena) {
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

	public void setContrasena(String contrasena) {
		Contrasena = contrasena;
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
