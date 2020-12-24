package modelo;

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
