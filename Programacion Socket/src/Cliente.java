import java.io.*;
import java.net.*;

public class Cliente {
	
	private static String HOST = "25.36.114.20";
    private static int PUERTO = 1234;

	public static void main(String[] args) {
		
		Socket socket;
		DataOutputStream mensaje;

		try {
			// Creamos nuestro socket
			socket = new Socket(HOST, PUERTO);

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
