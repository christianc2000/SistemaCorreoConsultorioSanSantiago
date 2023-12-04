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
        List<String> usuarioAtr = Arrays.asList("id", "ci", "nombre", "apellido", "fechaNacimiento", "celular", "tipo", "genero", "residenciaActual", "email", "password", "urlFoto", "formacion", "sueldo", "hijo", "ocupacion");
        List<String> consultaAtr = Arrays.asList("id", "fecha", "motivo", "examenFisico", "observaciones", "diagnostico", "historialId");
        
        Tabla tablaUsuario = new Tabla("users", usuarioAtr);
        Tabla tablaConsulta = new Tabla("consultas", consultaAtr);
        this.tablas = new Tabla[]{tablaUsuario,tablaConsulta};
    }

    public Tabla[] getTablas() {
        return tablas;
    }
    
}
