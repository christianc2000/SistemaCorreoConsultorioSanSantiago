/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemacorreoconsultoriosansantiago.manejadorcorreo;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.HashMap;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import sistemaconsultoriosansantiago.datos.User;
import sistemacorreoconsultoriosansantiago.Analizador.AnalizarSintaxis;
import sistemacorreoconsultoriosansantiago.Analizador.InformacionComando;

/**
 *
 * @author Christian
 */
public class ManejadorCorreo {
    Dotenv dotenv = Dotenv.load();
    
    private final String user = dotenv.get("MAIL_USER");//"infodevdevs@gmail.com";
    private final String pass = dotenv.get("MAIL_PASS");
 
    
    public void iniciar() {
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        try {
            Session session = Session.getInstance(properties, null);
            IMAPStore store = (IMAPStore) session.getStore("imaps");
            store.connect("imap.gmail.com", user, pass);
            
            IMAPFolder inbox = (IMAPFolder) store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);
            inbox.addMessageCountListener(new MessageCountAdapter() {
                public void messagesAdded(MessageCountEvent ev) {
                    Message[] messages = ev.getMessages();
                    System.out.println(messages);
                    for (Message message : messages) {
                        // Crea un nuevo hilo para cada mensaje
                        Thread thread = new Thread(new ThreadAnalizeMessage(message));
                        thread.start();
                    }
                }
            });
            
            while (true) {
                inbox.idle();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
