import java.io.*;
import java.net.*;

public class Cliente {
	
	private static String HOST = "25.36.111.34";
    private static int PUERTO = 1234;

	public static void main(String[] args) {
		
		Socket socket;
		DataOutputStream mensaje;

		try {
			// Creamos nuestro socket
			socket = new Socket(HOST, PUERTO);

			mensaje = new DataOutputStream(socket.getOutputStream());

			// Enviamos un mensaje
<<<<<<< HEAD
			mensaje.writeUTF("Hola soy JOSE!!");
=======
			mensaje.writeUTF("Hola soy Cliente!!");
>>>>>>> e93e7e34861c53b5c5b2035d2af1be9dfabc8555

			// Cerramos la conexión
			socket.close();

		} catch (UnknownHostException e) {
			System.out.println("El host no existe o no está activo.");
		} catch (IOException e) {
			System.out.println("Error de entrada/salida.");
		}

	}

}
