import java.io.*;
import java.net.*;
import java.util.concurrent.Semaphore;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

public class Cliente_Chucu {

	public class Controller {
		private final String HOST_SERGIO = "25.84.193.39";
		private final String HOST_ALBERTO = "25.84.175.186";
		private final String HOST_JESUS = "25.85.14.114";
		private Socket socket;
		private final int PUERTO = 5678;
		private Semaphore oSemConexion = new Semaphore(0);
		
		public Semaphore getoSemConexion() {
			return oSemConexion;
		}
		public void setoSemConexion(Semaphore oSemConexion) {
			this.oSemConexion = oSemConexion;
		}
		
	}

	final Controller control = new Controller();

	public class SERVER_CLIENTE implements Runnable {

		@Override
		public void run() {
			DataInputStream in;
			try {
				control.oSemConexion.acquire();
				while (true) {
					if (control.socket.getInputStream()!=null) {
						in = new DataInputStream(control.socket.getInputStream());
						
						String resultado = in.readUTF();
						System.out.println(resultado);
					}else {
						System.out.println("No hay mensaje");
					}
				}
			} catch (UnknownHostException e1) {
				System.err.println("ERROR "+e1.getMessage());
				e1.printStackTrace();
			} catch (IOException e1) {
				System.err.println("ERROR "+e1.getMessage());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}       
		}
	}

	public class CLIENTE_SERVER implements Runnable {

		@Override
		public void run() {
			DataOutputStream mensaje;

			try {
				// Creamos nuestro socket
				control.socket = new Socket(control.HOST_SERGIO, control.PUERTO);

				mensaje = new DataOutputStream(control.socket.getOutputStream());

				// Enviamos un mensaje
				mensaje.writeUTF("Hola soy JOSE!!");

				// Cerramos la conexión
				//control.socket.close();
				control.oSemConexion.release();;

			} catch (UnknownHostException e) {
				System.out.println("El host no existe o no está activo.");
			} catch (IOException e) {
				System.out.println("Error de entrada/salida.");
			}

		}

	}

	public void executeMultiThreading() {
		new Thread(new CLIENTE_SERVER()).start();
		
		new Thread(new SERVER_CLIENTE()).start();
	}

	public static void main(String[] args) {
		Cliente_Chucu oChu = new Cliente_Chucu();
		oChu.executeMultiThreading();
	}

}
