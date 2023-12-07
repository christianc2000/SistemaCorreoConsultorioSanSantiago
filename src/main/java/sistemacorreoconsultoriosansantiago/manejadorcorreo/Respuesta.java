/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemacorreoconsultoriosansantiago.manejadorcorreo;

import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import sistemaconsultoriosansantiago.datos.User;

/**
 *
 * @author Christian
 */
public class Respuesta {

    public String generarTablaHTML(String cabezera[], HashMap<Integer, Object> Objeto, String titulo, String insertar, String accion) {
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html>\n");
        html.append("<html>\n");
        html.append("<head>\n");
        html.append("<style>\n");
        html.append("table {width: 100%;border-collapse: collapse;}\n");
        html.append("th, td {border: 1px solid black;padding: 8px;text-align: left;}\n");
        html.append("</style>\n");
        html.append("</head>\n");
        html.append("<a href=\"mailto:grupo01sc@tecnoweb.org.bo?subject=Listar&body=").append(insertar).append("\">\n").append("<button>").append(accion).append("</button>\n").append("</a>");
        html.append("<body>\n");
        html.append("<h1>").append(titulo).append("</h1>");
        html.append("<table>\n");
        html.append("<tr>");
        for (int i = 0; i < cabezera.length; i++) {
            html.append("<th>").append(cabezera[i]).append("</th>");
        }
        html.append("</tr>");

        for (Object obj : Objeto.values()) {
            html.append("<tr>");
            for (String atributo : cabezera) {

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

    public String generarTablaHTMLBoton(String cabezera[], HashMap<Integer, Object> Objeto, String titulo, String insertar, String accion) {
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html>\n");
        html.append("<html lang=\"es\">\n");
        html.append("<head>\n");
        html.append("<meta charset=\"UTF-8\">\n");
        html.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
        html.append("<title>").append(titulo).append("</title>\n");
        html.append("<style>\n");
        html.append("body { font-family: Arial, sans-serif; background-color: #f9f9f9; color: #333; margin: 0; padding: 0; }\n");
        html.append(".container { max-width: 1200px; margin: 0 auto; padding: 20px; }\n");
        html.append("h1, h2, h3, h4, h5, h6 { color: #555; }\n");
        html.append("table { width: 100%; border-collapse: collapse; margin-top: 20px; }\n");
        html.append("th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }\n");
        html.append("th { background-color: #f2f2f2; }\n");
        html.append(".btn { display: inline-block; padding: 8px 16px; margin-bottom: 10px; font-size: 14px; font-weight: bold; text-align: center; text-decoration: none; cursor: pointer; border-radius: 4px; }\n");
        html.append(".btn-primary { color: #fff; background-color: #3498db; }\n");
        html.append(".btn-primary:hover { background-color: #2980b9; }\n");
        html.append(".btn-warning { color: #fff; background-color: #f39c12; }\n");
        html.append(".btn-warning:hover { background-color: #e08e0b; }\n");
        html.append(".btn-danger { color: #fff; background-color: #e74c3c; }\n");
        html.append(".btn-danger:hover { background-color: #d62c1a; }\n");
        html.append("</style>\n");
        html.append("</head>\n");
        html.append("<body>\n");

        html.append("<div class=\"container\">\n");
        html.append("<h1 class=\"text-center mb-4\">").append(titulo).append("</h1>\n");
        html.append("<a class=\"btn btn-primary\" href=\"mailto:grupo01sc@tecnoweb.org.bo?subject=Listar&body=").append(insertar).append("\"><button>").append(accion).append("</button></a>");
        html.append("<a class=\"btn btn-primary\" href=\"mailto:grupo01sc@tecnoweb.org.bo?subject=Listar&body=[%22").append("INICIAR").append("%22];\" class=\"grid-item\">\n").append("<button>").append("INICIO").append("</button>\n").append("</a>");
        html.append("<table>\n");
        html.append("<thead>\n");
        html.append("<tr>\n");

        for (String columna : cabezera) {
            html.append("<th>").append(columna).append("</th>\n");
        }

        html.append("<th>Acciones</th>\n");
        html.append("</tr>\n");
        html.append("</thead>\n");
        html.append("<tbody>\n");

        for (Object obj : Objeto.values()) {
            html.append("<tr>\n");

            for (String atributo : cabezera) {
                try {
                    Field field = obj.getClass().getDeclaredField(atributo);
                    field.setAccessible(true);
                    String valor = (String) field.get(obj);
                    html.append("<td>").append(valor).append("</td>\n");
                } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                    // Maneja las excepciones aquí según sea necesario
                }
            }

            // Añadir el campo oculto con el id
            try {
                Field idField = obj.getClass().getDeclaredField("id"); // Reemplaza "id" con el nombre del campo en tu objeto
                idField.setAccessible(true);
                String idValor = String.valueOf(idField.get(obj));
                html.append("<td>\n");
                html.append("<form method=\"post\" action=\"\">\n");
                html.append("<input type=\"hidden\" name=\"delete_id\" value=\"").append(idValor).append("\">\n");
                html.append("<button type=\"submit\" class=\"btn btn-danger\" name=\"delete_button\">Eliminar</button>\n");
                html.append("</form>\n");
                html.append("</td>\n");
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                // Maneja las excepciones aquí según sea necesario
            }

            html.append("</tr>\n");
        }

        // Agrega el script para manejar el modal
        html.append("</tbody>\n");
        html.append("</table>\n");
        html.append("</div>\n");
        html.append("</body>\n");
        html.append("</html>");

        return html.toString();
    }

    public String generarHTML() {
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html>\n");
        html.append("<html lang=\"en\">\n");
        html.append("<head>\n");
        html.append("    <meta charset=\"UTF-8\">\n");
        html.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
        html.append("    <style>\n");
        html.append("        body {\n");
        html.append("            background-color: #f5f5f5;\n");
        html.append("            font-family: 'Arial', sans-serif;\n");
        html.append("            margin: 0;\n");
        html.append("            padding: 0;\n");
        html.append("        }\n");
        html.append("\n");
        html.append("        .header {\n");
        html.append("            background-color: #d9534f;\n");
        html.append("            color: white;\n");
        html.append("            text-align: center;\n");
        html.append("            padding: 20px;\n");
        html.append("            font-size: 24px;\n");
        html.append("        }\n");
        html.append("\n");
        html.append("        .grid-container {\n");
        html.append("            display: grid;\n");
        html.append("            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));\n");
        html.append("            gap: 20px;\n");
        html.append("            padding: 20px;\n");
        html.append("            justify-content: center;\n");
        html.append("        }\n");
        html.append("\n");
        html.append("        .grid-item {\n");
        html.append("            background-color: #428bca;\n");
        html.append("            color: white;\n");
        html.append("            border: none;\n");
        html.append("            padding: 40px;\n");
        html.append("            font-size: 20px;\n");
        html.append("            text-align: center;\n");
        html.append("            margin: 10px;\n");
        html.append("            cursor: pointer;\n");
        html.append("            transition: background-color 0.3s ease;\n");
        html.append("        }\n");
        html.append("\n");
        html.append("        .grid-item:hover {\n");
        html.append("            background-color: #3071a9;\n");
        html.append("        }\n");

        // Nuevo estilo para los elementos <a>
        html.append("        .grid-item a {\n");
        html.append("            display: block;\n");
        html.append("            width: calc(100% - 20px); /* Resta el doble del margen (10px a cada lado) */\n");
        html.append("            text-decoration: none;\n");
        html.append("        }\n");
        html.append("    </style>\n");
        html.append("    <title>Consultorio San Santiago</title>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        html.append("\n");
        html.append("<h2 class=\"header\">CONSULTORIO SAN SANTIAGO</h2>\n");
        html.append("\n");
        html.append("<div class=\"grid-container\">\n");
        html.append("<div class=\"grid-item\"><a href=\"mailto:grupo01sc@tecnoweb.org.bo?subject=Listar Usuario&body=LIST%5B%22users%22%5D;\">USUARIO</a></div>\n");
        html.append("<div class=\"grid-item\"><a href=\"mailto:grupo01sc@tecnoweb.org.bo?subject=Listar Servicio&body=LIST%5B%22servicios%22%5D;\">SERVICIO</a></div>\n");
        html.append("<div class=\"grid-item\"><a href=\"mailto:grupo01sc@tecnoweb.org.bo?subject=Listar Cita&body=LIST%5B%22citas%22%5D;\">CITA</a></div>\n");
        html.append("<div class=\"grid-item\"><a href=\"mailto:grupo01sc@tecnoweb.org.bo?subject=Listar Turnos&body=LIST%5B%22turnos%22%5D;\">TURNO</a></div>\n");
        html.append("<div class=\"grid-item\"><a href=\"mailto:grupo01sc@tecnoweb.org.bo?subject=Listar Pago&body=LIST%5B%22pagos%22%5D;\">PAGO</a></div>\n");
        html.append("<div class=\"grid-item\"><a href=\"mailto:grupo01sc@tecnoweb.org.bo?subject=Listar Consultas&body=LIST%5B%22consultas%22%5D;\">CONSULTA</a></div>\n");
        html.append("<div class=\"grid-item\"><a href=\"mailto:grupo01sc@tecnoweb.org.bo?subject=Listar Historial&body=LIST%5B%22historials%22%5D;\">HISTORIAL</a></div>\n");
        html.append("<div class=\"grid-item\"><a href=\"mailto:grupo01sc@tecnoweb.org.bo?subject=Listar Reporte&body=LIST%5B%22reportes%22%5D;\">REPORTE Y ESTADISTICA</a></div>\n");
        html.append("</div>\n");
        html.append("\n");
        html.append("</body>\n");
        html.append("</html>\n");

        return html.toString();
    }

    public String generarVistaComandos() {
        StringBuilder html = new StringBuilder();

        html.append("<html><head>");
        html.append("<title>Comandos y Error de Sintaxis</title>");
        html.append("<style>");
        html.append("body { font-family: Arial, sans-serif; background-color: #f9f9f9; color: #333; margin: 0; padding: 0; }");
        html.append(".container { max-width: 600px; margin: 0 auto; padding: 20px; }");
        html.append("h1 { color: #d9534f; }");
        html.append("p { color: #333; }");
        html.append("ul { list-style-type: none; padding: 0; }");
        html.append("li { margin-bottom: 10px; }");
        html.append("</style>");
        html.append("</head><body>");
        html.append("<a class=\"btn btn-primary\" href=\"mailto:grupo01sc@tecnoweb.org.bo?subject=Listar&body=[%22").append("INICIAR").append("%22];\" class=\"grid-item\">\n").append("<button>").append("INICIO").append("</button>\n").append("</a>");
        // Mensaje de Error de Sintaxis
        html.append("<div class=\"container\">");
        html.append("<h1>Error de Sintaxis</h1>");
        html.append("<p>La sintaxis del comando proporcionado es incorrecta.</p>");
        html.append("</div>");

        // Comandos Disponibles
        html.append("<div class=\"container\">");
        html.append("<h1>Comandos Disponibles</h1>");
        html.append("<p>Puede utilizar los siguientes comandos:</p>");
        html.append("<ul>");
        html.append("<li>INICIAR</li>");
        html.append("<li>LIST[\"tabla\"]</li>");
        html.append("<li>LIST[\"tabla\":\"atr1\",\"atr2\",...,\"atrn\"]</li>");
        html.append("<li>INSERT[\"tabla\":\"atr1\"=\"val1\",\"atr2\"=\"val2\",...,\"atrn\"=\"valn\"]</li>");
        html.append("</ul>");
        html.append("</div>");

        html.append("</body></html>");

        return html.toString();
    }

}
