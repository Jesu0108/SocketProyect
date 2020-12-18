import java.io.*;
import java.net.*;

public class Cliente_Jesus {
	
	private static String HOST_ALBERTO = "25.36.114.20";
	private static String HOST_SERGIO = "25.36.124.184";
	private static String HOST_CHUCU = "25.40.177.203";
    
	private static int PUERTO = 9999;

	public static void main(String[] args) {
		BufferedReader entrada;
		Socket socket;
		DataOutputStream mensaje;

		try {
			// Creamos nuestro socket
			
			//socket = new Socket(HOST_ALBERTO, PUERTO);
			//socket = new Socket(HOST_SERGIO, PUERTO);
			socket = new Socket(HOST_ALBERTO, PUERTO);

			entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			mensaje = new DataOutputStream(socket.getOutputStream());

			// Enviamos un mensaje
			mensaje.writeUTF("Hola soy Jesus!!");

			// Para recibir el mensaje

			String mensajeRecibido = entrada.readLine();

			System.out.println(mensajeRecibido);
			
			// Cerramos la conexión
			socket.close();

		} catch (UnknownHostException e) {
			System.out.println("El host no existe o no está activo.");
		} catch (IOException e) {
			System.out.println("Error de entrada/salida.");
		}

	}

}
