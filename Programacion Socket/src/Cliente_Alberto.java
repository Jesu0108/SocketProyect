import java.io.*;
import java.net.*;

public class Cliente_Alberto {
	
	
	
	private static String HOSTJESUS = "25.85.14.114";
	private static String HOSTSERGIO = "25.84.193.39";
	private static String HOSTALBERTO = "25.84.175.186";
	private static String HOSTCHUCU = "25.85.119.209";
	
    private static int PUERTO = 5678;

	public static void main(String[] args) {
		
		Socket socket;
		DataOutputStream mensaje;

		try {
			// Creamos nuestro socket
			socket = new Socket(HOSTJESUS, PUERTO);

			mensaje = new DataOutputStream(socket.getOutputStream());
			DataInputStream in = new DataInputStream(socket.getInputStream());
			// Enviamos un mensaje
			mensaje.writeUTF("Hola soy Alberto!!");

			String resultado = in.readUTF();
			 
            System.out.println(resultado);
			
			
			// Cerramos la conexión
			socket.close();

		} catch (UnknownHostException e) {
			System.out.println("El host no existe o no está activo.");
		} catch (IOException e) {
			System.out.println("Error de entrada/salida.");
		}

	}

}
