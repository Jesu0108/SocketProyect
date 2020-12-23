import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

import modelo.Cubo;

public class ServidorConexion {
	
	public class Control{
		
		Queue<Cubo> cubos = new LinkedList<Cubo>();
		Semaphore semaforo = new Semaphore(0);
		private final int PUERTO = 9999;
		private final String HOST = "25.85.119.209";
		Socket socketContenedor;
		Socket socketCamion;

		public Semaphore getSemaforo() {
			return semaforo;
		}

		public void setSemaforo(Semaphore semaforo) {
			this.semaforo = semaforo;
		}	
		
	}
	
	private final Control control = new Control();
	
	public class Enviar implements Runnable{

		@Override
		public void run() {

	        DataOutputStream salidaMensaje;
			
			try {
				while(true) {
			
			control.semaforo.acquire();		
			control.socketCamion = new Socket(control.HOST, control.PUERTO);
			salidaMensaje = new DataOutputStream(control.socketCamion.getOutputStream());
				
				 //Le envio un mensaje
            salidaMensaje.writeUTF("Es necesario vaciar el contenedor con id: " +control.cubos.poll().getiIdCubo());
            
            salidaMensaje.close();
            control.socketCamion.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	public class Recibir implements Runnable{
		
		
		

		@Override
		public void run() {
			ServerSocket servidor;
        
        DataInputStream entradaMensaje;
        ObjectInputStream entradaObjeto;
		
		try {
            //Creamos el socket del servidor
            servidor = new ServerSocket(control.PUERTO);
            System.out.println("*Servidor iniciado*\n");
 
            //Siempre estara escuchando peticiones
            while (true) {
 
                //Espero a que un cliente se conecte
            	control.socketContenedor = servidor.accept();
 
                System.out.println("Cliente conectado--------------------------");
                entradaMensaje = new DataInputStream(control.socketContenedor.getInputStream());
                entradaObjeto = new ObjectInputStream(control.socketContenedor.getInputStream());
                
 
                //Leo el mensaje que me envia
                String mensaje = entradaMensaje.readUTF();
                control.cubos.add((Cubo) entradaObjeto.readObject());
                
                //Compruebo el objeto que le llega al servidor para saber si la petición enviada por el contenedor de basura
                //es relacionado con la temperatura o con el peso dado que si no supera el limite de ambos no envia ninguna petición.
                if(comprobarTemperaturaCubo()) {
                	
                	llamar112();
                	
                }
                else {
                	
                	control.semaforo.release();
                	
                	
                }
                System.out.println(mensaje);
 
                //Cierro el socket
                entradaMensaje.close();
                entradaObjeto.close();
                control.socketContenedor.close();
                System.out.println("Conexion terminada--------------------------\n");
 
            }
 
        } catch (IOException ex) {
			System.out.println("Error de entrada/salida: " + ex.getMessage());
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		}

		private void llamar112() {
			
			System.out.println("Se ha realizado una llamada al servicio de emergencia"
					+ " para que se encargue de la situación del contenedor con id: "+control.cubos.poll().getiIdCubo());
			
		}

		private boolean comprobarTemperaturaCubo() {
			
			boolean bCierto;
			
			if(control.cubos.peek().getiTemp()!=0) {
				
				bCierto = true;
			}
			else {
				bCierto = false;
			}
			return bCierto;
		}
		
	}
	
	public void executeMultiThreading() {
		
		new Thread(new Enviar()).start();
		new Thread(new Recibir()).start();
		
		
	}
	
	

	public static void main(String args[]) {

		try {
			ServidorConexion conexion = new ServidorConexion();
			conexion.executeMultiThreading();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}


}
