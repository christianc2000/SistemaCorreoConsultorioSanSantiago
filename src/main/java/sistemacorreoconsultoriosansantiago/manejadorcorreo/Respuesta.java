/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemacorreoconsultoriosansantiago.manejadorcorreo;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import sistemaconsultoriosansantiago.datos.User;

/**
 *
 * @author Christian
 */
public class Respuesta {

    public String generarTablaHTML(String titulo[], HashMap<Integer, Object> Objeto) {
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
        html.append("<tr>");
        for (int i = 0; i < titulo.length; i++) {
            html.append("<th>").append(titulo[i]).append("</th>");
        }
        html.append("</tr>");

        for (Object obj : Objeto.values()) {
            html.append("<tr>");
            for (String atributo : titulo) {

                try {
                    Field field = obj.getClass().getDeclaredField(atributo);
                    field.setAccessible(true);
                    String valor = (String) field.get(obj);
                    html.append("<td>").append(valor).append("</td>");
                } catch (NoSuchFieldException ex) {
                    Logger.getLogger(Respuesta.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SecurityException ex) {
                    Logger.getLogger(Respuesta.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(Respuesta.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Respuesta.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            html.append("</tr>\n");
        }

        html.append("</table>\n");
        html.append("</body>\n");
        html.append("</html>");

        return html.toString();
    }

}
