/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemacorreoconsultoriosansantiago.manejadorcorreo;

import java.util.HashMap;
import sistemaconsultoriosansantiago.datos.User;

/**
 *
 * @author Christian
 */
public class Respuesta {

    public String generarTablaHTML(HashMap<Integer, User> usuarios) {
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html>\n");
        html.append("<html>\n");
        html.append("<head>\n");
        html.append("<style>\n");
        html.append("table {width: 100%;border-collapse: collapse;}\n");
        html.append("th, td {border: 1px solid black;padding: 8px;text-align: left;}\n");
        html.append("</style>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        html.append("<table>\n");
        html.append("<tr><th>ID</th><th>CI</th><th>Nombre</th><th>Apellido</th><th>Fecha de Nacimiento</th><th>Residencia Actual</th><th>Celular</th><th>Género</th><th>Tipo</th><th>URL Foto</th><th>Email</th><th>Password</th></tr>\n");

        for (User usuario : usuarios.values()) {
            html.append("<tr>");
            html.append("<td>").append(usuario.getId()).append("</td>");
            html.append("<td>").append(usuario.getCi()).append("</td>");
            html.append("<td>").append(usuario.getNombre()).append("</td>");
            html.append("<td>").append(usuario.getApellido()).append("</td>");
            html.append("<td>").append(usuario.getFechaNacimiento()).append("</td>");
            html.append("<td>").append(usuario.getResidenciaActual()).append("</td>");
            html.append("<td>").append(usuario.getCelular()).append("</td>");
            html.append("<td>").append(usuario.getGenero()).append("</td>");
            html.append("<td>").append(usuario.getTipo()).append("</td>");
            html.append("<td>").append(usuario.getUrlFoto()).append("</td>");
            html.append("<td>").append(usuario.getUrlFoto()).append("</td>");
            html.append("<td>").append(usuario.getPassword()).append("</td>");
            html.append("</tr>\n");
        }

        html.append("</table>\n");
        html.append("</body>\n");
        html.append("</html>");

        return html.toString();
    }
   
   
}
