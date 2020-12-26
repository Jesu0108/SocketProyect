package view;

import controller.CamionCtrl;
import modelo.Camion;
import validacion.valida;

public class LoginView {
	private static boolean login_Usuario() {
		String sUsuario, sContrasena;
		CamionCtrl oCamion = new CamionCtrl();
		boolean bExito = false;
		// Usuario login
		sUsuario = valida.leer("Introduce un Usuario");
		sContrasena = valida.leer("Introduce una Contraseña");

		if (oCamion.CamionLogueado(new Camion(sUsuario, sContrasena))) {
			bExito = true;
		} else {
			bExito = false;
		}

		return bExito;

	}
	
	private static boolean registro_Usuario() {
		//Atributos del usuario
		boolean bExito = true;
		String sUsuario, sContrasena;
		CamionCtrl oCamion = new CamionCtrl();
		//El usuario introduce los datos
		sUsuario = valida.leer("Introduce un Usuario");
		sContrasena = valida.leer("Introduce una Contraseña");
		// Si existe este usuario
		if (oCamion.CamionExiste(new Camion(sUsuario))) {
			bExito = false;
		} else {
			oCamion.add(new Camion(sUsuario, sContrasena));
			bExito = true;
		}
		return bExito;
	}

	public static void opcion_usuario() {
		// String sUsuario, sContrasena;
		byte bOpcion = Menu_Usuario();
		switch (bOpcion) {
		case 1:
			System.out.println("[LOGIN]");
			if (login_Usuario()) {
				System.out.println("El empleado se ha logeado");
			} else {
				System.out.println("Parece que el usuario no existe");
				bOpcion = Menu_Usuario();
			}

			break;
		case 2:
			System.out.println("[REGISTRO]");
			if (registro_Usuario()) {
				System.out.println("El usuario se ha registrado CORRECTAMENTE");
			}else {
				System.out.println("Este usuario ya existe");
				bOpcion = Menu_Usuario();
			}
			break;
		default:
			System.err.println("ERROR OPCION");
			break;
		}
	}

	private static byte Menu_Usuario() {
		System.out.println("1.) Loggearse");
		System.out.println("2.) Registrarse");
		return (byte) valida.valida("Introduce una opcion: ", 1, 2, 3);
	}

}
