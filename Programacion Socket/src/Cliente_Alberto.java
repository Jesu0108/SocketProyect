import java.io.*;
import java.net.*;

public class Cliente_Alberto {
	
	
	
	private static String HOSTJESUS = "25.36.111.34";
	private static String HOSTSERGIO = "25.36.124.184";
	private static String HOSTALBERTO = "25.36.114.20";
	private static String HOSTCHUCU = "25.36.177.203";
	
    private static int PUERTO = 9999;

	public static void main(String[] args) {
		
		Socket socket;
		Socket socket2;
		ServerSocket serversocket;
		DataOutputStream mensaje;
		BufferedReader entrada;

		try {
			// Creamos nuestro socket
			socket = new Socket(HOSTJESUS, PUERTO);

			mensaje = new DataOutputStream(socket.getOutputStream());
			serversocket = new ServerSocket(PUERTO);
			// Enviamos un mensaje
			mensaje.writeUTF("Hola soy Alberto!!");

			
			
			socket2 = serversocket.accept();
			
			entrada = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
			
			String msg = entrada.readLine();
			
			System.out.println(msg);
			
			// Cerramos la conexión
			socket.close();

		} catch (UnknownHostException e) {
			System.out.println("El host no existe o no está activo.");
		} catch (IOException e) {
			System.out.println("Error de entrada/salida.");
		}

	}

}
