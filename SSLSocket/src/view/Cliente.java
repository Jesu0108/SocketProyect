package view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.math.BigInteger;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class Cliente {

	public static void main(String[] args) throws Exception {
       
		System.setProperty("javax.net.ssl.keyStore", "keytool/socketKey.jks");
        System.setProperty("javax.net.ssl.keyStorePassword","medac2020");
        System.setProperty("javax.net.ssl.trustStore", "keytool/clientTrustedCerts.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "medac2020");
		
        String Host = "25.84.175.186";
        int puerto = 9999;
       
//        System.out.println("PROGRAMA CLIENTE INICIADO....");
        SSLSocketFactory sfact = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket Cliente = (SSLSocket) sfact.createSocket(Host, puerto);
        
        // CREO FLUJO DE SALIDA AL SERVIDOR
        DataOutputStream flujoSalida = new DataOutputStream(Cliente.getOutputStream());
       
        // ENVIO UN SALUDO AL SERVIDOR   
        flujoSalida.writeUTF("---ME GUSTAN LOS MACARRONES---");
       
        // CREO FLUJO DE ENTRADA AL SERVIDOR
        DataInputStream flujoEntrada = new DataInputStream(Cliente.getInputStream());
       
        // EL SERVIDOR ME ENVIA UN MENSAJE
        System.out.println("Recibiendo del SERVIDOR: " + flujoEntrada.readUTF());
       
        //------------------------------------------------------------------------------
        //Información sobre la sesión SSL
        SSLSession session = ((SSLSocket) Cliente).getSession(); 
//        System.out.println("Host: "+session.getPeerHost());
//        System.out.println("Cifrado: " + session.getCipherSuite());
//        System.out.println("Protocolo: " + session.getProtocol());
//        System.out.println("IDentificador:" + new BigInteger(session.getId()));
//        System.out.println("Creación de la sesión: " + session.getCreationTime());
//
//        X509Certificate certificate = (X509Certificate)session.getPeerCertificates()[0];
//        System.out.println("Propietario: " + certificate.getSubjectDN());
//        System.out.println("Algoritmo: " + certificate.getSigAlgName());
//        System.out.println("Tipo: " + certificate.getType());
//        System.out.println("Emisor: " + certificate.getIssuerDN());
//        System.out.println("Número Serie: " + certificate.getSerialNumber());
       // ------------------------------------------------------------------------------
       
        // CERRAR STREAMS Y SOCKETS
        flujoEntrada.close();
        flujoSalida.close();
        Cliente.close();
	
}
}
