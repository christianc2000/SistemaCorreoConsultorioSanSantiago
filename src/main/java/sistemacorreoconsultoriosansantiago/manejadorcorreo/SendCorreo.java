/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemacorreoconsultoriosansantiago.manejadorcorreo;

import io.github.cdimascio.dotenv.Dotenv;
import java.util.HashMap;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import sistemaconsultoriosansantiago.datos.User;

/**
 *
 * @author Christian
 */
public class SendCorreo {
    Dotenv dotenv = Dotenv.load();
    private final String user = dotenv.get("MAIL_USER");//"infodevdevs@gmail.com";
    private final String pass = dotenv.get("MAIL_PASS");
    
    public void enviar(HashMap<Integer, User> users, String destinatario){
          Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        // Creación de una nueva sesión con una autenticación simple
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });

        try {
            // Creación de un nuevo mensaje
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject("Listar Usuario");
            
            String html = new Respuesta().generarTablaHTML(users);

            message.setContent(html, "text/html; charset=utf-8");
            // Enviar el mensaje
            Transport.send(message);

            System.out.println("Mensaje enviado");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
