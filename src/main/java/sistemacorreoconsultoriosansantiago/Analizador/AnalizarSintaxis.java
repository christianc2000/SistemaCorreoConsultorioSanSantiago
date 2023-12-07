/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemacorreoconsultoriosansantiago.Analizador;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import sistemacorreoconsultoriosansantiago.manejadorcorreo.ManejadorPOP3;

/**
 *
 * @author Christian
 */
public class AnalizarSintaxis {
    
    private static final String COMMAND_PATTERN = "^([A-Z]+)\\[(.+)\\];$";
    private static final String LIST_PATTERN = "^\"([a-zA-Z]+)\"(:(\"[a-zA-Z]+\"(,\"[a-zA-Z]+\")*))?$";
    private static final String INSERT_PATTER = "^\"([a-zA-Z]+)\"(:(\\\"[a-zA-Z]+\\\"=\\\"[^\\\",]+\\\"(,\\\"[a-zA-Z]+\\\"=\\\"[^\\\",]+\\\")*))";
    private static String patron = "\\[\"INICIAR\"\\];$";
    
    public InformacionComando analizarSintaxis(String command) {
        
        Pattern pattern = Pattern.compile(COMMAND_PATTERN);
        System.out.println("imprimiendo command: " + command);
        Matcher matcher = pattern.matcher(command);
        InformacionComando respuesta = new InformacionComando(null, null, null);
        
        if (matcher.find()) {
            System.out.println("INGRESAAAAA MATCH LISTAS");
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
                        System.out.println("TABLA: " + table);
                        System.out.println("ATRIBUTOS: " + attributes);
                        respuesta.setAccion("LIST");
                        respuesta.setTabla(table);
                        if (attributes != null) {
                            attributes = attributes.substring(1, attributes.length());
                            attributes = attributes.replace("\"", "");
                            respuesta.setAtributos(attributes.split(","));
                        }
                        //System.out.println("Sintaxis de consulta correcta: tabla=" + table + ", " + attributes.substring(1, attributes.length() - 1));
                    } else {
                        System.out.println("Sintaxis de LIST inválida");
                    }
                    break;
                case "INSERT":
                    System.out.println("Insertando en la tabla " + consult);
                    Pattern insertPattern = Pattern.compile(INSERT_PATTER);
                    Matcher insertMatcher = insertPattern.matcher(consult);
                    
                    if (insertMatcher.matches()) {
                        String table = insertMatcher.group(1);
                        String attributes = insertMatcher.group(2);
                        System.out.println("TABLA: " + table);
                        System.out.println("ATRIBUTOS: " + attributes);
                        respuesta.setAccion("INSERT");
                        respuesta.setTabla(table);
                        attributes = attributes.substring(1, attributes.length());
                        attributes = attributes.replace("\"", "");
                        System.out.println("ATRIBUTOS EN SINTAXIS: " + attributes);
                        respuesta.setAtributos(attributes.split(","));
                    } else {
                        System.out.println("Sintaxis de INSERT inválida");
                    }
                    break;
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
            Pattern patterns = Pattern.compile(patron);
            // Crea un objeto Matcher
            Matcher matchers = patterns.matcher(command);

            // Verifica si hay coincidencia
            if (matchers.matches()) {
                respuesta.setAccion("INICIAR");
                System.out.println("La cadena coincide con el patrón.");
            } else {
                System.out.println("Comando inválido");
            }
        }
        return respuesta;
    }
    
    public boolean analizarTabla(InformacionComando ic) { //[accion,tabla,String[]]
        TablasDefault td = new TablasDefault();
        for (int i = 0; i < td.getTablas().length; i++) {
            System.out.println("tabla: " + ic.getTabla() + ", tabla comparacion: " + td.getTablas()[i].getNombre());
            if (ic.getTabla().equals(td.getTablas()[i].getNombre())) {
                System.out.println("ic atributos: " + ic.getAtributos());
                switch (ic.getAccion()) {
                    case "LIST":
                        if (ic.getAtributos() != null) {
                            System.out.println("ATRIBUTO DISTINO DE NULL");
                            if (analizarAtributos(ic.getAtributos(), td.getTablas()[i].getAtributos())) {
                                return true;
                            } else {
                                System.out.println("ATRIBUTOS NULL");
                                return false;
                            }
                        } else {
//LISTAR CON *  System.out.println("ATRIBUTOS NULL");
                            System.out.println("atributos null");
                            return true;
                        }
                    case "INSERT":
                        System.out.println("INGRESA AL ANALIZAR TABLA INSERT");
                        AtributoValor a = new AtributoValor();
                        for (int j = 0; j < ic.getAtributos().length; j++) {
                            System.out.println(", " + ic.getAtributos()[j]);
                        }
                        AtributoValor atrv[] = a.convertAtributoValor(ic.getAtributos());
                        if (analizarAtributosc(a.getAtributos(atrv), td.getTablas()[i].getAtributos())) {
                            return true;
                        } else {
                            System.out.println("ATRIBUTOS NULL");
                            return false;
                        }
                    default:
                        throw new AssertionError();
                }
            }
        }
        return false;
    }
    
    public boolean analizarAtributos(String atributos[], List<String> atributosComparacion) {
        for (String atributo : atributos) {
            System.out.println("atributo: " + atributo);
            if (!atributosComparacion.contains(atributo)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean analizarAtributosc(String atributos[], List<String> atributosComparacion) {
        System.out.println("ENTRA A ANALIZAR ATRIBUTOS");
        Set<String> atributosSet = new HashSet<>(Arrays.asList(atributos));
        for (String atributoc : atributosComparacion) {
            if (!atributoc.equals("id") && !atributosSet.contains(atributoc)) {
                return false;
            }
        }
        return true;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AnalizarSintaxis a = new AnalizarSintaxis();
        InformacionComando ic = new InformacionComando(null, null, null);
        while (true) {
            System.out.println("Por favor, introduce un comando:");
            String command = scanner.nextLine();
            ic = a.analizarSintaxis(command);
            System.out.println("accion: " + ic.getAccion() + ", tabla: " + ic.getTabla() + ", atributos: ");
            for (int i = 0; i < ic.getAtributos().length; i++) {
                System.out.print(ic.getAtributos()[i] + ", ");
            }
        }
    }
}
