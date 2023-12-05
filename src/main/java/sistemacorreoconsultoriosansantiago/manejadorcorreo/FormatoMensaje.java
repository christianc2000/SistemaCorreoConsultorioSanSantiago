/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemacorreoconsultoriosansantiago.manejadorcorreo;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.internet.MimeUtility;

/**
 *
 * @author Christian
 */
public class FormatoMensaje {

    private String correo;
    private String asunto;
    private String mensaje;

    public String getCorreo() {
        return correo;
    }

    public String getAsunto() {
        return asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public FormatoMensaje() {
        this.correo = "";
        this.asunto = "";
        this.mensaje = "";
    }

    public void getMultiline(BufferedReader in) {
        String from = "", subject = "", text = "";
        boolean isHeader = true, isTextPlain = false;
        while (true) {
            try {
                String line = in.readLine();
                if (line == null) {
                    // Server closed connection
                    throw new EOFException("No CRLF line");
                }

                if (line.equals(".")) {
                    System.out.println("MENSAJE DECODIFICADO: " + mensajeDecodificado(text));
                    correo = extraerCorreo(from);
                    asunto = subject;
                    mensaje = mensajeDecodificado(text);
                    return;
                }
                if (isHeader) {
                    if (line.startsWith("From: ")) {
                        from = line.substring(6);
                    } else if (line.startsWith("Subject: ")) {
                        subject = line.substring(9);
                    } else if (line.equals("")) {
                        isHeader = false;
                    }
                } else {
                    if (line.startsWith("--")) {
                        // Skip the boundary line
                        continue;
                    }
                    if (line.startsWith("Content-Type: text/plain")) {
                        // Skip the content type line and start recording text
                        isTextPlain = true;
                        continue;
                    }
                    if (line.startsWith("Content-Type: text/html")) {
                        // Skip the content type line and stop recording text
                        isTextPlain = false;
                        continue;
                    }
                    // Add read line to the text if it's plain text
                    if (isTextPlain) {
                        text = text + "\n" + line;
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(FormatoMensaje.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MessagingException ex) {
                Logger.getLogger(FormatoMensaje.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String extraerCorreo(String destino) {
        int i = destino.indexOf("<") + 1;
        int f = destino.length() - 1;
        return destino.substring(i, f);
    }

    public String mensajeDecodificado(String msj) throws MessagingException, IOException {
        msj = msj.trim();
        System.out.println("TEXTO VIEJO: " + msj);
        ByteArrayInputStream is = new ByteArrayInputStream(msj.getBytes());
        Reader reader = new InputStreamReader(MimeUtility.decode(is, "quoted-printable"));
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuilder decodedText = new StringBuilder();
        String line;
        boolean startDecoding = false;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.startsWith("Content-Transfer-Encoding:")) {
                if (line.contains("quoted-printable")) {
                    startDecoding = true;
                }
                continue;
            }
            if (startDecoding) {
                decodedText.append(line).append("\n");
            }
        }
        String finalDecodedText = decodedText.toString().replace("\n", " ");

        if (startDecoding) {
            System.out.println("TEXT CODIFICADO: " + finalDecodedText);
            return finalDecodedText;
        } else {
            return msj;
        }
    }

}
