package modelo;

public interface ICamion {
	// PK
	public String getUsuario();
	public void setUsuario(String usuario);
	// Atributos
	public String getcontrasenia();
	public boolean setcontrasenia(String contrasenia);
	public final byte MINCHARPASSWORD = 8;
	public final byte MAXCHARPASSWORD = 30;
}
