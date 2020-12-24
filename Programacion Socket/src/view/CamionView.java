package view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import controller.CamionCtrl;
import modelo.Camion;
import modelo.Cubo;
import validacion.valida;

public class CamionView {
	public class Control {
		final int PUERTO = 5678;

		CamionCtrl oCamion = new CamionCtrl();
		// IP HOST
		private final String HOSTJESUS = "25.85.14.114";
		private final String HOSTSERGIO = "25.84.193.39";
		private final String HOSTALBERTO = "25.84.175.186";
		private final String HOSTCHUCU = "25.85.119.209";
		private Socket socket;

		// USUARIO
		private final int iNumEmpleados = 5;
		// QUEUE
		private Queue<String> listCubos = new LinkedList<String>();
	}

	final Control control = new Control();

	public class Hilo_Usuario implements Runnable {
		private int id;

		public Hilo_Usuario(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		@Override
		public void run() {

			try {
				// Variables

				// CREA SOCKET
				control.socket = new Socket(control.HOSTALBERTO, control.PUERTO);
				DataOutputStream mensaje = new DataOutputStream(control.socket.getOutputStream());

				// El usuario entra a la opcion de usuario
				LoginView.opcion_usuario();
				// El camion manda el host
				mensaje.writeUTF(control.HOSTCHUCU);
				// Cerramos el socket

				Thread.sleep(2000);
			} catch (Exception e) {
				System.err.println("ERROR " + e.getMessage());
			}
		}

	}

	public class Recibir_Server implements Runnable {

		@Override
		public void run() {
			ServerSocket servidor = null;

			DataInputStream entradaMensaje;

			try {
				// Creamos el socket del servidor

				servidor = new ServerSocket(control.PUERTO);

				// Siempre estara escuchando peticiones
				while (true) {

					control.socket = servidor.accept();

					entradaMensaje = new DataInputStream(control.socket.getInputStream());

					// Leo el mensaje que me envia

					control.listCubos.add(entradaMensaje.readUTF());
					accionCamion();

					// Cierro el socket
					entradaMensaje.close();

					control.socket.close();
				}
			} catch (IOException | InterruptedException ex) {
				System.err.println("Error de conexion: " + ex.getMessage());
			}

		}

	}

	private void accionCamion() throws InterruptedException {
		// camion recoge la basura
		String sCubo_recoge = control.listCubos.poll();
		System.out.println("El camion recoge el cubo " + sCubo_recoge);
		// Tiempo que tarda en recoger el cubo
		Thread.sleep(2000);
		// Al terminar al recoger manda el mensaje
		System.out.println("El camion termino de recoger el cubo " + sCubo_recoge);

	}

	public void executeMultiThreading() {
		//Se ejecuta el hilo el usuario 
		try {
			for (int iCont = 0; iCont < control.iNumEmpleados; iCont++) {
				new Thread(new Hilo_Usuario(iCont)).start();
				Thread.sleep(2000);
			}
			new Thread(new Recibir_Server()).start();
			
		} catch (Exception e) {
			System.err.println("ERROR " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		CamionView oCamion = new CamionView();
		oCamion.executeMultiThreading();
	}
}
