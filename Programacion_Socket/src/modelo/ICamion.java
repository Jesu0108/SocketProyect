package modelo;

public interface ICamion {
	// PK
	public String getUsuario();
	public void setUsuario(String usuario);
	// Atributos
	public String getContrasena();
	public boolean setContrasena(String contrasena);
	public final byte MINCHARPASSWORD = 8;
	public final byte MAXCHARPASSWORD = 30;
}
