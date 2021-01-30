package view;

import java.io.DataInputStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import controller.CamionCtrl;

public class CamionView {
	public class Control {
		final int PUERTO = 9999;

		CamionCtrl oCamion = new CamionCtrl();
		// IP HOST
		private final String HOSTJESUS = "25.85.14.114";
		private final String HOSTSERGIO = "25.84.193.39";
		private final String HOSTALBERTO = "25.84.175.186";
		private final String HOSTCHUCU = "25.98.3.115";
		private SSLSocket sslSocket;

		// QUEUE
		private Queue<String> listCubos = new LinkedList<String>();
	}

	final Control control = new Control();

	public class Hilo_Usuario implements Runnable {

		@Override
		public void run() {

			try {
				
				// Variables
				// El usuario entra a la opcion de usuario
				LoginView.opcion_usuario();
				// CREA SOCKET
				SSLSocketFactory sfact = (SSLSocketFactory) SSLSocketFactory.getDefault();
				control.sslSocket = (SSLSocket) sfact.createSocket(control.HOSTJESUS, control.PUERTO);
		        
		        DataOutputStream mensaje = new DataOutputStream(control.sslSocket.getOutputStream());
				
				// El camion manda el host
				mensaje.writeUTF(control.HOSTCHUCU);
				// Cerramos el socket.
				
				control.sslSocket.close();
				Thread.sleep(2000);
			} catch (Exception e) {
				System.err.println("ERROR " + e.getMessage());
			}
		}

	}

	public class Recibir_Server implements Runnable {

		@Override
		public void run() {
			try {
				// Creamos el socket del servidor

				SSLServerSocketFactory sfact = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		        SSLServerSocket servidorSSL = (SSLServerSocket) sfact.createServerSocket(control.PUERTO);
		        DataInputStream mensajeServer = null;

				// Siempre estara escuchando peticiones
				while (true) {

					control.sslSocket = (SSLSocket) servidorSSL.accept();

					mensajeServer = new DataInputStream(control.sslSocket.getInputStream());
					// Leo el mensaje que me envia

					control.listCubos.add(mensajeServer.readUTF());
					accionCamion();

					// Cierro el SSLsocket
					mensajeServer.close();
					control.sslSocket.close();
				}
			} catch (IOException | InterruptedException ex) {
				System.err.println("---Error de conexion: " + ex.getMessage());
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
		// Se ejecuta el hilo el usuario

		new Thread(new Hilo_Usuario()).start();

		new Thread(new Recibir_Server()).start();

	}

	public static void main(String[] args) {
		
		System.setProperty("javax.net.ssl.keyStore", "keytool/socketKey.jks");
        System.setProperty("javax.net.ssl.keyStorePassword","medac2020");
        System.setProperty("javax.net.ssl.trustStore", "keytool/clientTrustedCerts.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "medac2020");
		
		CamionView oCamion = new CamionView();
		oCamion.executeMultiThreading();
	}
}
