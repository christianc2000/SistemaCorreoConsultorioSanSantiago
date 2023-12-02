/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemacorreoconsultoriosansantiago.Analizador;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Christian
 */
public class AnalizarSintaxis {

    private static final String COMMAND_PATTERN = "^([A-Z]+)\\[(.+)\\];$";
    private static final String LIST_PATTERN = "^\"([a-zA-Z]+)\"(:(\"[a-zA-Z]+\"(,\"[a-zA-Z]+\")*))?$";

    public InformacionComando analizarSintaxis(String command) {
        Pattern pattern = Pattern.compile(COMMAND_PATTERN);
        Matcher matcher = pattern.matcher(command);
        InformacionComando respuesta = new InformacionComando(null, null, null);

        if (matcher.find()) {
            String action = matcher.group(1);
            String consult = matcher.group(2);

            switch (action) {
                case "LIST":
                    // Realizar acción para LIST
                    System.out.println("Listando todos los elementos de la tabla " + consult);
                    Pattern listPattern = Pattern.compile(LIST_PATTERN);
                    Matcher listMatcher = listPattern.matcher(consult);
                    if (listMatcher.matches()) {
                        String table = listMatcher.group(1);
                        String attributes = listMatcher.group(2);
                        respuesta.setAccion("LIST");
                        respuesta.setTabla(table);
                        if (attributes != null) {
                            attributes = attributes.substring(0, attributes.length() - 1);
                            respuesta.setAtributos(attributes.split(","));
                        }
                        //System.out.println("Sintaxis de consulta correcta: tabla=" + table + ", " + attributes.substring(1, attributes.length() - 1));
                    } else {
                        System.out.println("Sintaxis de LIST inválida");
                    }
                    break;
                case "INSERT":
                    // Realizar acción para INSE
                    System.out.println("Insertando en la tabla " + consult);
                //return new CommandInfo(action, table, attributes.split(","));
                case "UPDATE":
                    // Realizar acción para EDIT
                    System.out.println("Editando en la tabla " + consult);
                    break;
                case "SHOW":
                    // Realizar acción para MOST
                    System.out.println("Mostrando de la tabla " + consult);
                    //return new CommandInfo(action, table, attributes.split(","));
                    break;
                case "DELETE":
                    // Realizar acción para DELE
                    System.out.println("Eliminando de la tabla " + consult);
                    //return new CommandInfo(action, table, attributes.split(","));
                    break;
                default:
                    System.out.println("Comando desconocido: " + action);
            }
        } else {
            System.out.println("Comando inválido");
        }
        return respuesta;
    }

    public boolean analizarTabla(InformacionComando ic) { //[accion,tabla,String[]]
        TablasDefault td = new TablasDefault();
        for (int i = 0; i < td.getTablas().length; i++) {
            if (ic.getTabla().equals(td.getTablas()[i].getNombre())) {
                if (ic.getAtributos() != null) {
                    if (analizarAtributos(ic.getAtributos(), td.getTablas()[i].getAtributos())) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
//LISTAR CON *
                    return true;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean analizarAtributos(String atributos[], List<String> atributosComparacion) {
        for (int i = 0; i < atributosComparacion.size(); i++) {
            for (int j = 0; j < atributos.length; j++) {
                String atributo=atributos[j].substring(1,atributos[j].length()-2);
                if (atributosComparacion.get(i) != atributo) {
                    return false;
                }
            }
        }
        return true;
    }
}
