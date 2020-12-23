package view;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import modelo.Cubo;

public class CubosView {

	// INFO HOST
	final int PUERTO = 1234;
	// -------------IPs HOST-------------

	// private static String HOSTJESUS = "25.85.14.114";
	// private static String HOSTSERGIO = "25.84.193.39";
	private static String HOSTALBERTO = "25.84.175.186";
	// private static String HOSTCHUCU = "25.85.119.209";

	// INFO CUBOS
	private static int iNumCubos = 5;
	private static int iTempCubo = 0;
	private static float fPesoCubo = 0;

	// ---------------------------------------------------------------------------------------------------------

	public class Enviador implements Runnable {

		private int iId;

		public Enviador(int iId) {
			setiId(iId);
		}

		public int getiId() {
			return iId;
		}

		public void setiId(int iId) {
			this.iId = iId;
		}

		@Override
		public void run() {

			try {
							
				while (true) {
					// Creamos nuestro socket
					Socket socket =  new Socket(HOSTALBERTO, PUERTO);
					ObjectOutputStream oCubo = new ObjectOutputStream(socket.getOutputStream());
					DataOutputStream sMensaje = new DataOutputStream(socket.getOutputStream());
					// Si el peso o la temperatura del cubo son altos
					// Enviamos un mensaje al servidor
					if (calorCubo()) {
						System.err.println("A");
						
						//Reanudamos la conexion para mandar un mensaje
						//socket = new Socket(HOSTALBERTO, PUERTO);
						
						sMensaje.writeUTF("Cubo " + getiId() + " ARDIENDO.");
						oCubo.writeObject(new Cubo(getiId(), iTempCubo));
						
						// Cerramos el socket
						//socket.close();
						
						//Lo dormimos para que sea mas facil de leer
						Thread.sleep(1000);
						
					} else if (pesoCubo()) {
						System.err.println("B");
						
						//Reanudamos la conexion para mandar un mensaje
						//socket = new Socket(HOSTALBERTO, PUERTO);

						
						sMensaje.writeUTF("Cubo " + getiId() + " LLENO.");
						oCubo.writeObject(new Cubo(getiId(),fPesoCubo));
						
						// Cerramos el socket
						//socket.close();
						
						//Lo dormimos para que sea mas facil de leer
						Thread.sleep(1000);
					} else {
						System.out.println("Cubo " + getiId() + " sin temperatura ni peso suficientes para notificar.");
						
						// Cerramos el socket
						//socket.close();
						
						//Lo dormimos para que sea mas facil de leer
						Thread.sleep(1000);
					}
					socket.close();
				}
				
			} catch (IOException | InterruptedException e) {
				System.out.println("Error al conectar");

			}

		}
	}

	// ---------------------------------------------------------------------------------------------------------

	private void executeMultiThreading() throws InterruptedException {

		for (int i = 0; i < iNumCubos; i++) {
			new Thread(new Enviador(i)).start();
			Thread.sleep(300);
		}
	}

	public static void main(String[] args) {

		try {
			CubosView cubos = new CubosView();
			cubos.executeMultiThreading();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static boolean calorCubo() {
		boolean boFuego = false;
		int iGrados = 0;

		iGrados = (int) ((Math.random() * 100) + 1);

		if (iGrados > 40) {
			boFuego = true;
		} else {
			boFuego = false;
		}

		iTempCubo = iGrados;

		return boFuego;
	}

	public static boolean pesoCubo() {
		boolean boRecoger = false;
		float fPeso = 0;

		fPeso = (float) ((Math.random() * 100) + 1);

		if (fPeso >= 60) {
			boRecoger = true;
		} else {
			boRecoger = false;
		}

		fPesoCubo = fPeso;

		return boRecoger;
	}
}
