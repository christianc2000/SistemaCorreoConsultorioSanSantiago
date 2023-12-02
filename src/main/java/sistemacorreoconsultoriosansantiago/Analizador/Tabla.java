/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemacorreoconsultoriosansantiago.Analizador;

import java.util.List;

/**
 *
 * @author Christian
 */
public class Tabla {

    private final String nombre;
    private final List<String> atributos;

    public Tabla(String nombre, List<String> atributos) {
        this.nombre = nombre;
        this.atributos = atributos;
    }

    public String getNombre() {
        return nombre;
    }

    public List<String> getAtributos() {
        return atributos;
    }
}
