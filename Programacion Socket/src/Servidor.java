import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	private static int PUERTO = 5678;

	public static void main(String args[]) {

		ServerSocket servidor;
        Socket socket;
        DataInputStream entradaMensaje;
        DataOutputStream salidaMensaje;
		
		try {
            //Creamos el socket del servidor
            servidor = new ServerSocket(PUERTO);
            System.out.println("*Servidor iniciado*\n");
 
            //Siempre estara escuchando peticiones
            while (true) {
 
                //Espero a que un cliente se conecte
            	socket = servidor.accept();
 
                System.out.println("Cliente conectado--------------------------");
                entradaMensaje = new DataInputStream(socket.getInputStream());
                salidaMensaje = new DataOutputStream(socket.getOutputStream());
 
                //Leo el mensaje que me envia
                String mensaje = entradaMensaje.readUTF();
 
                System.out.println(mensaje);
 
                //Le envio un mensaje
                salidaMensaje.writeUTF("-Mensaje aqui-");
                salidaMensaje.writeUTF("Pokemon > Digimon");
                salidaMensaje.writeUTF("A");
                salidaMensaje.writeUTF("E");
                salidaMensaje.writeUTF("U");
                salidaMensaje.writeUTF("Rojo");
                salidaMensaje.writeUTF("Lasaña");
                salidaMensaje.writeUTF("Leche");
                salidaMensaje.writeUTF("Croquetas");
                //Cierro el socket
                socket.close();
                System.out.println("Cliente conectado--------------------------\n");
 
            }
 
        } catch (IOException ex) {
			System.out.println("Error de entrada/salida: " + ex.getMessage());
        }
	}

	}
