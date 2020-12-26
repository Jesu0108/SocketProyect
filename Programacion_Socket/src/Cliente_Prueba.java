import java.io.*;
import java.net.*;
import java.util.concurrent.Semaphore;

public class Cliente_Prueba {
	// private static String HOSTJESUS = "25.85.14.114";
	// private static String HOSTSERGIO = "25.84.193.39";
	private static String HOSTALBERTO = "25.84.175.186";
	// private static String HOSTCHUCU = "25.85.119.209";

	public class Control {

		Semaphore semaforoRecibir = new Semaphore(0);

		public Semaphore getSemaforoRecibir() {
			return semaforoRecibir;
		}

		public void setSemaforoRecibir(Semaphore semaforoRecibir) {
			this.semaforoRecibir = semaforoRecibir;
		}
	}

	private Socket socket;
	final int PUERTO = 1234;

	private final Control control = new Control();

	public class Recibidor implements Runnable {

		@Override
		public void run() {

			try {
				control.semaforoRecibir.acquire();
				while (true) {
					if (socket.getInputStream() != null && socket.getInputStream() != null) {
						DataInputStream in = new DataInputStream(socket.getInputStream());

						String resultado = in.readUTF();

						System.out.println(resultado);
					}
				}

			} catch (UnknownHostException e) {
				System.out.println("El host no existe o no está activo.");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}

		}
	}

	public class Enviador implements Runnable {

		@Override
		public void run() {

			try {
				// Creamos nuestro socket
				socket = new Socket(HOSTALBERTO, PUERTO);

				DataOutputStream mensaje = new DataOutputStream(socket.getOutputStream());
				// Enviamos un mensaje
				mensaje.writeUTF("Hola");
				control.semaforoRecibir.release();
			} catch (IOException e) {
				System.out.println("Error al conectar");

			}
		}
	}

	private void executeMultiThreading() throws InterruptedException {

		Thread recibidor = new Thread(new Recibidor());

		Thread enviador = new Thread(new Enviador());

		recibidor.start();
		enviador.start();

	}

	public static void main(String[] args) {

		try {
			Cliente_Prueba prueba = new Cliente_Prueba();
			prueba.executeMultiThreading();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
