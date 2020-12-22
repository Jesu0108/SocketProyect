import java.io.*;
import java.net.*;

public class Cliente_Sergio {
	
	private static String JESUS = "25.36.111.34";
	private static String ALBERTO = "25.36.114.20";
	private static String CHUCU = "25.36.177.203";
    private static int PUERTO = 9999;

	public static void main(String[] args) {
		
		Socket socket;
		DataOutputStream mensaje;

		try {
			// Creamos nuestro socket
			socket = new Socket(ALBERTO , PUERTO);

			mensaje = new DataOutputStream(socket.getOutputStream());

			// Enviamos un mensaje
			mensaje.writeUTF("Hola soy Sergio!!");

			// Cerramos la conexión
			socket.close();

		} catch (UnknownHostException e) {
			System.out.println("El host no existe o no está activo.");
		} catch (IOException e) {
			System.out.println("Error de entrada/salida.");
		}

	}

}
