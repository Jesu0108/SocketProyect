package view;

import java.io.ObjectOutputStream;
import java.net.Socket;

import controller.CamionCtrl;
import modelo.Camion;
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

		// USUARIO
		private final int iNumEmpleados = 5;
		private String sUsuario = "";
		private String sContrasena = "";
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
				String sContrasena = "";
				byte bOpcion = 0;
				// CREA SOCKET
				Socket socket = new Socket(control.HOSTALBERTO, control.PUERTO);
				ObjectOutputStream mensaje = new ObjectOutputStream(socket.getOutputStream());
				/*
				 * ########### 
				 * # USUARIO # 
				 * ###########
				 */

				bOpcion = Menu_Cliente();
				
				mensaje.writeUTF("Un empleado se va a Registrar/Logear");
				switch (bOpcion) {
				case 1:
					byte bCont_Intentos = 0;
					// do - while para hacer un bucle para loggearse y salir el bucle
					// cuando hay muchos intentos, cuando el usuario se encuentra en la BD
					do {
						control.sUsuario = "Cliente";
						control.sContrasena = "cliente";
						bCont_Intentos++;
					} while (control.oCamion.ClienteExiste(new Camion(control.sUsuario)) || bCont_Intentos == 5);
					if (bCont_Intentos == 5) {
						System.out.println("Parece que el usuario no existe");

						bOpcion = Menu_Cliente();
					}
					break;
				case 2:
					System.out.println("[REGISTRO]");
					control.sUsuario = "Cliente";
					// Si existe este usuario
					if (control.oCamion.ClienteExiste(new Camion(control.sUsuario))) {
						System.out.println("Este cliente ya se ha registrado");
						bOpcion = Menu_Cliente();
					} else {
						do {
							// contraseña
							control.sContrasena = "cliente";
							sContrasena = "cliente";
							if (sContrasena.equals(control.sContrasena)) {
								System.err.println("LAS CONTRASEÑAS NO SON IGUALES");
							}
						} while (!control.sUsuario.equals(sContrasena));
					}

					break;
				default:
					System.err.println("ERROR OPCION");
					break;
				}

				// El cliente envia al server
				mensaje.writeUTF("El empleado " + id + " se loggea para ponerse a trabajar");
				mensaje.writeObject(new Camion(control.sUsuario, control.sContrasena));
				// Cerramos el socket
				socket.close();
				Thread.sleep(2000);
			} catch (Exception e) {
				System.err.println("ERROR " + e.getMessage());
			}
		}

	}

	private byte Menu_Cliente() {
		System.out.println("1.) Loggearse");
		System.out.println("2.) Registrarse");
		return (byte) valida.valida("Introduce una opcion: ", 1, 2, 3);
	}

	public void executeMultiThreading() {
		try {
			for (int iCont = 0; iCont < control.iNumEmpleados; iCont++) {
				new Thread(new Hilo_Usuario(iCont));
				Thread.sleep(2000);
			}
		} catch (Exception e) {
			System.err.println("ERROR " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		CamionView oCamion = new CamionView();
		oCamion.executeMultiThreading();
	}
}
