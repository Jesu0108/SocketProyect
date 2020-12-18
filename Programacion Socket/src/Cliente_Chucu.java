import java.io.*;
import java.net.*;

public class Cliente_Chucu {
	
	private final static String HOST_SERGIO= "25.36.124.184";
	private final static String HOST_ALBERTO = "25.36.114.20";
	private final static String HOST_JESUS = "25.36.111.34";
	
    private static int PUERTO = 9999;

	public static void main(String[] args) {
		
		Socket socket;
		DataOutputStream mensaje;
		 

		try {
			// Creamos nuestro socket
			socket = new Socket(HOST_ALBERTO, PUERTO);

			mensaje = new DataOutputStream(socket.getOutputStream());

			// Enviamos un mensaje
			mensaje.writeUTF("Hola soy JOSE!!");

			// Cerramos la conexión
			socket.close();

		} catch (UnknownHostException e) {
			System.out.println("El host no existe o no está activo.");
		} catch (IOException e) {
			System.out.println("Error de entrada/salida.");
		}

	}

}
