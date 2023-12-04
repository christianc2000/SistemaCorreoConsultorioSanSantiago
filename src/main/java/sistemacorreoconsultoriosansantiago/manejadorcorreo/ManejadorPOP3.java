package sistemacorreoconsultoriosansantiago.manejadorcorreo;


import io.github.cdimascio.dotenv.Dotenv;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.internet.MimeUtility;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Christian
 */
public class ManejadorPOP3 {

    Dotenv dotenv = Dotenv.load();

    private String host;
    private String user;//"infodevdevs@gmail.com";
    private String pass;
    private int port;
    private String comando = "";
    private String linea = "";
    private FormatoMensaje fm = new FormatoMensaje();
    Socket socket;
    BufferedReader entrada;
    DataOutputStream salida;
    private int lastMessageCount = 0;

    public ManejadorPOP3() {
        host = dotenv.get("MAIL_HOST");
        user = dotenv.get("MAIL_USER");
        pass = dotenv.get("MAIL_PASS");
        port = 110;
    }

    public void iniciar(){
        try {
            while (true) {
                // Crear un socket y conectarse al servidor
                socket = new Socket(host, port);
                entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                salida = new DataOutputStream(socket.getOutputStream());

                System.out.println("S : " + entrada.readLine() + "\r\n");
                comando = "USER " + user + "\r\n";
                System.out.print("C : " + comando);
                salida.writeBytes(comando);
                System.out.println("S : " + entrada.readLine() + "\r\n");
                comando = "PASS " + pass + "\r\n";
                System.out.print("C : " + comando);
                salida.writeBytes(comando);

                System.out.println("S : " + entrada.readLine() + "\r\n");
                comando = "STAT \r\n";
                System.out.print("C : " + comando);
                salida.writeBytes(comando);
                String response = entrada.readLine();
                System.out.println("S : " + response + "\r\n");
                String[] parts = response.split(" ");
                int numMessages = Integer.parseInt(parts[1]);

                if (numMessages > lastMessageCount) {//el numero de Mensaje que tiene
                    // Enviar el comando RETR para el último mensaje
                    System.out.println("*****RETR*******");
                    comando = "RETR " + numMessages + "\r\n";
                    System.out.print("C : " + comando);
                    salida.writeBytes(comando);
                    fm.getMultiline(entrada);
                    System.out.println("MENSAJE COD: " + fm.getMensaje());
                    String encoded = fm.getMensaje();
                    String decoded = MimeUtility.decodeText(encoded);
                    System.out.println("MENSAJE DEC: " + decoded);
                    System.out.println("S : \nDestino: " + fm.getCorreo() + "\nAsunto: " + fm.getAsunto() + "\nSubject: " + fm.getMensaje() + "\r\n");//imprime el último mensaje
                    Thread thread = new Thread(new ThreadAnalizeMessagePop3(fm.getMensaje().trim(), fm.getCorreo()));
                    thread.start();
                }

                // Actualizar el conteo de mensajes
                lastMessageCount = numMessages;
                comando = "QUIT\r\n";
                System.out.print("C : " + comando);
                salida.writeBytes(comando);
                System.out.println("S : " + entrada.readLine() + "\r\n");

                // Cerrar los flujos de entrada y salida y el socket
                entrada.close();
                salida.close();
                socket.close();

                try {
                    // Esperar 3 segundos antes de la próxima verificación
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ManejadorPOP3.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
