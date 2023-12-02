/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemacorreoconsultoriosansantiago.manejadorcorreo;

import java.util.HashMap;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import sistemaconsultoriosansantiago.datos.User;
import sistemacorreoconsultoriosansantiago.Analizador.AnalizarSintaxis;
import sistemacorreoconsultoriosansantiago.Analizador.InformacionComando;

/**
 *
 * @author Christian
 */
public class ThreadAnalizeMessage extends Thread {

    private Message message;
    private String destinatario;

    public ThreadAnalizeMessage(Message message, String destinatario) {
        this.message = message;
        this.destinatario = destinatario;
    }

    @Override
    public void run() {
        AnalizarSintaxis analizador = new AnalizarSintaxis();
        try {
            System.out.println("Email: " + InternetAddress.toString(message.getFrom()));
            destinatario = InternetAddress.toString(message.getFrom());
            System.out.println("Fecha de envío: " + message.getSentDate());
            System.out.println("Asunto: " + message.getSubject());

            Object content = message.getContent();
            if (content instanceof MimeMultipart) {
                MimeMultipart multipart = (MimeMultipart) content;
                for (int i = 0; i < multipart.getCount(); i++) {
                    BodyPart bodyPart = multipart.getBodyPart(i);
                    if (bodyPart.isMimeType("text/plain")) {
                        String command = (String) bodyPart.getContent();
                        InformacionComando ic = analizador.analizarSintaxis(command);
                        if (analizador.analizarTabla(ic)) {

                            if (ic.getAtributos() != null) {
                                //hay atributos
                            } else {
                                //no hay atributos, es un *
                                User user = new User();
                                HashMap<Integer, User> usuarios = user.listar();
                                SendCorreo sc = new SendCorreo();
                                sc.enviar(usuarios, destinatario);
                            }
                        } else {
//Mensaje de error
                        }
                    }
                }
            } else {
                System.out.println("Contenido: " + content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
