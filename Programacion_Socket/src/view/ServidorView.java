package view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Semaphore;

import modelo.Cubo;

public class ServidorView {

	public class Control {
		// Esta cola es la de los cubos que le llegan al servidor
		Queue<Cubo> cubos = new LinkedList<Cubo>();
		// Esta cola es la de los cubos que han llegado al servidor teniendo que
		// tratarse
		// por su peso, es decir, necesitan de un camion, pero no habiendo ninguno
		// logueado
		Queue<Cubo> cubosEsperando = new LinkedList<Cubo>();
		Semaphore semaforo = new Semaphore(0);
		private final int PUERTOCAMION = 8888;
		private final int PUERTOCUBO = 1234;
		Socket socketContenedor;
		Socket socketCamion;
		Cubo cubo;
		List<String> camionesLogueados = new ArrayList<String>();

		public Semaphore getSemaforo() {
			return semaforo;
		}

		public void setSemaforo(Semaphore semaforo) {
			this.semaforo = semaforo;
		}

	}

	private final Control control = new Control();
	private static int u = 0;

	public class Enviar implements Runnable {

		@Override
		public void run() {

			DataOutputStream salidaMensaje = null;

			while (true) {
				try {
					
					control.semaforo.acquire();
					control.socketCamion = new Socket(control.camionesLogueados.get(0),control.PUERTOCAMION);
					System.out.println(control.camionesLogueados.get(0));
					salidaMensaje = new DataOutputStream(control.socketCamion.getOutputStream());
					String mensaje = "" + control.cubosEsperando.poll().getiIdCubo();
					System.out.println(mensaje);
					salidaMensaje.writeUTF(mensaje);
					System.out.println("Mensaje enviado");
//					if (control.camionesLogueados.size() == (u + 1) && u != 0) {
//						u = 0;
//						organizacionCamiones(salidaMensaje);
//					} else {
//						organizacionCamiones(salidaMensaje);
//					}

				} catch (IOException e) {
					control.camionesLogueados.remove(u);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public class RecibirCubo implements Runnable {

		@Override
		public void run() {
			ServerSocket servidor;
			ObjectInputStream entradaObjeto;

			try {
				// Creamos el socket del servidor
				servidor = new ServerSocket(control.PUERTOCUBO);
				System.out.println("*Esperando cubo*\n");
				while (true) {

					// Siempre estara escuchando peticiones

					// Espero a que un cliente se conecte
					control.socketContenedor = servidor.accept();

					System.out.println("Cubo conectado--------------------------");
					// Leo el mensaje que me envia

					entradaObjeto = new ObjectInputStream(control.socketContenedor.getInputStream());
					control.cubos.add((Cubo) entradaObjeto.readObject());

					// Compruebo el objeto que le llega al servidor para saber si la petición
					// enviada por el contenedor de basura
					// es relacionado con la temperatura o con el peso dado que si no supera el
					// limite de ambos no envia ninguna petición.
					accionCubo();

					entradaObjeto.close();
					control.socketContenedor.close();
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
			ServerSocket servidor = null;
			DataInputStream entradaMensaje;

			try {
				servidor = new ServerSocket(control.PUERTOCAMION);
				System.out.println("*Esperando camion*\n");

				while (true) {
					control.socketContenedor = servidor.accept();
					System.out.println("Camion conectado--------------------------");

					entradaMensaje = new DataInputStream(control.socketContenedor.getInputStream());

					String mensaje = entradaMensaje.readUTF();
					System.out.println(mensaje);

					// Aniadimos el mensaje que llega de los camiones para introducirlo en una lista
					// y asi saber los que estan logueados
					control.camionesLogueados.add(mensaje);
					// Cierro el socket
					entradaMensaje.close();
					control.socketContenedor.close();
					System.out.println("Conexion terminada--------------------------\n");
				}
			} catch (IOException e) {
				System.err.println("Error " + e.getMessage());
			}

		}

	}

	private void llamar112() {

		System.out.println("Se ha realizado una llamada al servicio de emergencia"
				+ " para que se encargue de la situación del contenedor con id: " + control.cubo.getiIdCubo());

	}

	private void accionCubo() {

		control.cubo = control.cubos.poll();

		if (control.cubo.getiTemp() != 0 && control.cubo.getfPeso() == 0) {

			System.out.println(control.cubo.toString());
			llamar112();

		} else if (control.cubo.getiTemp() == 0 && control.cubo.getfPeso() != 0) {
			System.out.println(control.cubo.toString());
			if (control.camionesLogueados.size() == 0) {
				control.cubosEsperando.add(control.cubo);
			} else {
				control.semaforo.release();
				control.cubosEsperando.add(control.cubo);
			}

		} else {
			System.out.println(control.cubo.toString());
			System.out.println("Este cubo no requiere atención");

		}

	}

	private void organizacionCamiones(DataOutputStream salidaMensaje)
			throws InterruptedException, UnknownHostException, IOException {

		for (int iContador = u; iContador < control.camionesLogueados.size(); iContador++) {
			u = iContador;
			control.semaforo.acquire();
			control.socketCamion = new Socket(control.camionesLogueados.get(iContador), control.PUERTOCAMION);
			salidaMensaje = new DataOutputStream(control.socketCamion.getOutputStream());
			// Le envio un mensaje
			salidaMensaje.writeUTF(
					"Es necesario vaciar el contenedor con id: " + control.cubosEsperando.poll().getiIdCubo());

			salidaMensaje.close();
			control.socketCamion.close();
		}
	}

	public void executeMultiThreading() {

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
