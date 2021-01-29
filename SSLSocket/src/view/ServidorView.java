package view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import modelo.Cubo;

public class ServidorView {

	public class Control {
		// Esta cola es la de los cubos que le llegan al servidor
		Queue<Cubo> cubos = new LinkedList<Cubo>();
		SSLSocket clienteConectado = null;
		/*
		 * Esta cola es la de los cubos que han llegado al servidor teniendo que
		 * tratarse por su peso, es decir, necesitan de un camion, pero no habiendo
		 * ninguno logueado
		 */
		Queue<Cubo> cubosEsperando = new LinkedList<Cubo>();
		// Semaforo usado para abrir el hilo para enviar a los camiones solo si hay
		// cubos que necesiten atencion
		Semaphore semaforo = new Semaphore(0);
		// Puerto de coneccion con los camiones
		private final int PUERTOCAMION = 9999;
		// Puerto de coneccion con los cubos
		private final int PUERTOCUBO = 1234;
		Socket socketContenedor;
		Socket socketCamion;
		// Variable que empleamos para manejar los cubos que recojemos al hacer .poll a
		// la cola
		Cubo cubo;
		// Guardamos en una lista de Strings las ip de los camiones que estan logueados
		List<String> camionesLogueados = new ArrayList<String>();

		public Semaphore getSemaforo() {
			return semaforo;
		}

		public void setSemaforo(Semaphore semaforo) {
			this.semaforo = semaforo;
		}

	}

	private final Control control = new Control();

	public class Enviar implements Runnable {

		@Override
		public void run() {

			DataOutputStream salidaMensaje = null;

			while (true) {
				try {

					control.semaforo.acquire();

					organizacionCamiones(salidaMensaje);

				} catch (IOException e) {
					System.err.println("Error atun: " + e.getMessage());
				} catch (InterruptedException e) {
					System.err.println("Error nb: " + e.getMessage());
				}
			}
		}

	}

	public class RecibirCubo implements Runnable {

		@Override
		public void run() {
			

			try {
				// Creamos el socket del servidor
				SSLServerSocketFactory sfact = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		        SSLServerSocket servidorSSL = (SSLServerSocket) sfact.createServerSocket(control.PUERTOCUBO);
		        SSLSocket clienteConectado = null;
		        ObjectInputStream flujoEntrada = null;
		        
				System.out.println("*Esperando cubo*\n");
				while (true) {

					// Siempre estara escuchando peticiones

					// Espero a que un cliente se conecte
					clienteConectado = (SSLSocket) servidorSSL.accept();

					System.out.println("Cubo conectado--------------------------");
					// Leo el mensaje que me envia
					flujoEntrada = new ObjectInputStream(clienteConectado.getInputStream());
					control.cubos.add((Cubo) flujoEntrada.readObject());

					/*
					 * Compruebo el objeto que le llega al servidor para saber si la petición
					 * enviada por el contenedor de basura es relacionado con la temperatura o
					 * con el peso dado que si no supera el limite de ambos no envia ninguna
					 * petición.
					 */

					// Llamamos a la funcion que gestione la informacion de los cubos que le llegan
					// al servidor
					accionCubo();

					// Cerramos los flujos y sockets
					flujoEntrada.close();
					System.out.println("Conexion terminada Cubo--------------------------\n");
				}
			} catch (IOException ex) {
				System.err.println(ex.getMessage());
			} catch (ClassNotFoundException e) {
				System.err.println(e.getMessage());
			}
		}

	}

	public class RecibirCamion implements Runnable {

		@Override
		public void run() {
			SSLServerSocketFactory sfact = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
	        
	        DataInputStream flujoEntrada = null;

			try {
				SSLServerSocket servidorSSL = (SSLServerSocket) sfact.createServerSocket(control.PUERTOCAMION);
				System.out.println("*Esperando camion*\n");

				while (true) {
					control.clienteConectado = (SSLSocket) servidorSSL.accept();
					System.out.println("Camion conectado--------------------------");


					flujoEntrada = new DataInputStream(control.clienteConectado.getInputStream());

					String mensaje = flujoEntrada.readUTF();
					System.out.println(mensaje);

					// Aniadimos el mensaje que llega de los camiones para introducirlo en una lista
					// y asi saber los que estan logueados
					control.camionesLogueados.add(mensaje);
					// Cierro el socket
					flujoEntrada.close();
				}
			} catch (IOException e) {
				System.err.println("Error pene " + e.getMessage());
			}

		}

	}

	private void llamar112() {

		System.out.println("Se ha realizado una llamada al servicio de emergencia"
				+ " para que se encargue de la situación del contenedor con id: " + control.cubo.getiIdCubo());

	}

	private void accionCubo() {

		// Guardamos los cubos de la cola en una variable para poder interactuar con
		// ella cuanto queramos
		control.cubo = control.cubos.poll();

		// Es la condición para gestionar los cubos que necesitan atencion por su
		// temperatura
		if (control.cubo.getiTemp() != 0 && control.cubo.getfPeso() == 0) {

			System.out.println(control.cubo.toString());
			llamar112();

		}
		// Es la condicion para gestionar los cubos que necesitan atencion segun su peso
		else if (control.cubo.getiTemp() == 0 && control.cubo.getfPeso() != 0) {
			System.out.println(control.cubo.toString());
			if (control.camionesLogueados.size() == 0) {
				control.cubosEsperando.add(control.cubo);
			} else {
				control.cubosEsperando.add(control.cubo);
				control.semaforo.release();
			}

		}
		// Es la condicion para gestionar los cubos que no necestian atencion ninguna
		else {
			System.out.println(control.cubo.toString());
			System.out.println("Este cubo no requiere atención");

		}

	}

	private void organizacionCamiones(DataOutputStream salidaMensaje)
			throws InterruptedException, UnknownHostException, IOException {
		Thread.sleep(3000);

		// For necesario para enviar los cubos a los distintos camiones logueados
		for (int iContador = 0; iContador < control.camionesLogueados.size()
				&& iContador < control.cubosEsperando.size(); iContador++) {
			SSLSocketFactory sfact = (SSLSocketFactory) SSLSocketFactory.getDefault();
	        SSLSocket cliente = (SSLSocket) sfact.createSocket(control.camionesLogueados.get(iContador), control.PUERTOCAMION);
			
			salidaMensaje = new DataOutputStream(cliente.getOutputStream());
			// Le envio el id del cubo
			salidaMensaje.writeUTF("" + control.cubosEsperando.poll().getiIdCubo());

			
			salidaMensaje.close();
		}
	}

	public void executeMultiThreading() {
		System.setProperty("javax.net.ssl.keyStore", "keytool/socketKey.jks");
        System.setProperty("javax.net.ssl.keyStorePassword","medac2020");
        System.setProperty("javax.net.ssl.trustStore", "keytool/clientTrustedCerts.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "medac2020");

		new Thread(new Enviar()).start();
		new Thread(new RecibirCubo()).start();
		new Thread(new RecibirCamion()).start();
	}

	public static void main(String args[]) {

		try {
			ServidorView conexion = new ServidorView();
			conexion.executeMultiThreading();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

}
