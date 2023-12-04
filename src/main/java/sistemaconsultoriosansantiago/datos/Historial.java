/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemaconsultoriosansantiago.datos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Christian
 */
public class Historial {
    
    private int id;
    private String nombrePaciente;
    private int pacienteId;

    java.sql.Statement st;
    ResultSet rs;
    DB db = new DB();

    // Constructor
    public Historial(int id, String nombre_paciente, int pacienteId) {
        this.id = id;
        this.nombrePaciente = nombre_paciente;        
        this.pacienteId = pacienteId;
    }

    public Historial() {
        this.id = 0;
        this.nombrePaciente = "";
    }

    // Getters y setters (puedes generarlos automáticamente en muchos IDEs)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombre_paciente) {
        this.nombrePaciente = nombre_paciente;
    }

    public int getPacienteId() {
        return pacienteId;
    }
    
    public void setPacienteId(int pacienteId) {
        this.pacienteId = pacienteId;
    }

   
    public Statement getSt() {
        return st;
    }

    public ResultSet getRs() {
        return rs;
    }

    public DB getDb() {
        return db;
    }

    public HashMap<Integer, Historial> listar() {
        HashMap<Integer, Historial> historials = new HashMap<>();
        try {
            Connection connection = db.establecerConexion();
            Statement st = connection.createStatement();
            String sql = "SELECT * FROM historials;";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Historial historial = new Historial();
                historial.id = rs.getInt("id");
                historial.nombrePaciente = rs.getString("nombrePaciente");
                historial.pacienteId = rs.getInt("pacienteId");

                historials.put(historial.id, historial);
            }

            rs.close();
            connection.close();
            // System.out.println("EL REGISTRO SE REALIZO EXITOSAMENTE");
        } catch (SQLException e) {
            System.out.println("Error al conectar a la Base de datos: " + e.toString());
        }

        return historials;
    }
}
