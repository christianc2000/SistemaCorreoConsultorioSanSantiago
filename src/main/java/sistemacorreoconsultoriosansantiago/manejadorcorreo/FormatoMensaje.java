/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemacorreoconsultoriosansantiago.manejadorcorreo;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;

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

    public void getMultiline(BufferedReader in) throws IOException {
        String from = "", subject = "", text = "";
        boolean isHeader = true, isTextPlain = false;
        while (true) {
            String line = in.readLine();
            if (line == null) {
                // Server closed connection
                throw new EOFException("No CRLF line");
            }
            if (line.equals(".")) {
                // End of text, return the text
                correo = extraerCorreo(from);
                asunto = subject;
                mensaje = text.trim();
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
        }
    }

    public String extraerCorreo(String destino) {
        int i = destino.indexOf("<") + 1;
        int f = destino.length() - 1;
        return destino.substring(i, f);
    }
}
