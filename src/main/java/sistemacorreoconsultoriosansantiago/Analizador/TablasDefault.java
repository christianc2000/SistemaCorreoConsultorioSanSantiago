/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemacorreoconsultoriosansantiago.Analizador;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Christian
 */
public class TablasDefault {
     private static Tabla tablas[];

    public TablasDefault() {
        List<String> usuarioAtr = Arrays.asList("id", "ci", "nombre", "apellido", "fecha_nacimiento", "residencia_actual", "celular", "genero", "tipo", "email", "password");
        
        Tabla tablaUsuario = new Tabla("users", usuarioAtr);
        
        this.tablas = new Tabla[]{tablaUsuario};
    }

    public Tabla[] getTablas() {
        return tablas;
    }
    
}
