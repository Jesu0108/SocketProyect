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
	private static int iTempCubo;
	private static float fPesoCubo;

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
					
					//Reseteamos los valores a 0
					iTempCubo = 0;
					fPesoCubo = 0;

					// Creamos nuestro socket
					Socket socket = new Socket(HOSTALBERTO, PUERTO);
					ObjectOutputStream oCubo = new ObjectOutputStream(socket.getOutputStream());
					DataOutputStream sMensaje = new DataOutputStream(socket.getOutputStream());

					// Si el peso o la temperatura del cubo son altos
					// Enviamos un mensaje al servidor
					if (calorCubo()) {
						sMensaje.writeUTF("Cubo " + getiId() + " ARDIENDO.");
						oCubo.writeObject(new Cubo(getiId(), iTempCubo, 0));

						// Lo dormimos para que sea mas facil de leer
						Thread.sleep(1000);

					} else if (pesoCubo()) {
						
						sMensaje.writeUTF("Cubo " + getiId() + " LLENO.");
						oCubo.writeObject(new Cubo(getiId(), 0, fPesoCubo));

						// Lo dormimos para que sea mas facil de leer
						Thread.sleep(1000);
					} else {

						sMensaje.writeUTF("Cubo " + getiId() + " sin accion necesaria...");
						oCubo.writeObject(new Cubo(getiId(), iTempCubo, fPesoCubo));

						// Lo dormimos para que sea mas facil de leer
						Thread.sleep(1000);
					}
					// Cerramos el socket
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

		if (iGrados > 70) {
			boFuego = true;
			iTempCubo = iGrados;
		} else {
			boFuego = false;
		}

		return boFuego;
	}

	public static boolean pesoCubo() {
		boolean boRecoger = false;
		float fPeso = 0;

		fPeso = (float) ((Math.random() * 100) + 1);

		if (fPeso >= 50) {
			boRecoger = true;
			fPesoCubo = fPeso;
		} else {
			boRecoger = false;
		}

		return boRecoger;
	}
}
