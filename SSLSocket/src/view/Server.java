package view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class Server {
	

	public static void main(String[] arg) throws IOException {
        
        
       //System.setProperty("javax.net.ssl.keyStore", System.getProperty("keytool") + "\\clientTrustedCerts.jks");
       //System.setProperty("javax.net.ssl.keyStorePassword", "medac2020");
       
		System.setProperty("javax.net.ssl.keyStore", "keytool/socketKey.jks");
        System.setProperty("javax.net.ssl.keyStorePassword","medac2020");
        System.setProperty("javax.net.ssl.trustStore", "keytool/clientTrustedCerts.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "medac2020");
       
        int puerto = 9999;
        SSLServerSocketFactory sfact = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        SSLServerSocket servidorSSL = (SSLServerSocket) sfact.createServerSocket(puerto);
        SSLSocket clienteConectado = null;
        DataInputStream flujoEntrada = null; //FLUJO DE ENTRADA DE CLIENTE
        DataOutputStream flujoSalida = null; //FLUJO DE SALIDA AL CLIENTE
       
        for (int i = 1; i < 5; i++) {
            
              System.out.println("Esperando al cliente " + i);
              clienteConectado = (SSLSocket) servidorSSL.accept();
              flujoEntrada = new DataInputStream(clienteConectado.getInputStream());
              // EL CLIENTE ME ENVIA UN MENSAJE
              System.out.println("Recibiendo del CLIENTE: " + i + " \n\t" + flujoEntrada.readUTF());
              flujoSalida = new DataOutputStream(clienteConectado.getOutputStream());
              // ENVIO UN SALUDO AL CLIENTE
              flujoSalida.writeUTF("SaIudos al cliente del servidor");
             
        }// Fin de for
       
       
        // CERRAR STREAMS Y SOCKETS
        flujoEntrada.close();
        flujoSalida.close();
        clienteConectado.close();
        servidorSSL.close();

}
}
