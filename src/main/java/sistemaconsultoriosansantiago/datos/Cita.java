/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemaconsultoriosansantiago.datos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Christian
 */
public class Cita {
    
    private int id;
    private String fechaCita;
    private String estadoCita;
    private String costoCita;
    private int pacienteId;
    private int medicoId;

    java.sql.Statement st;
    ResultSet rs;
    DB db = new DB();

    // Constructor
    public Cita(int id, String estadoCita, String costoCita, String fechaCita, int pacienteId, int medicoId) {
        this.id = id;
        this.fechaCita = fechaCita;
        this.estadoCita = estadoCita;
        this.costoCita = costoCita;
        this.pacienteId = pacienteId;
        this.medicoId = medicoId;
    }

    public Cita() {
        this.id = 0;
        this.fechaCita = "";
        this.estadoCita = "";
        this.costoCita = "";
        this.pacienteId = 0;
        this.medicoId = 0;
    }

    // Getters y setters (puedes generarlos automáticamente en muchos IDEs)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFechaCita() {
        return fechaCita;
    }

    public String getEstadoCita() {
        return estadoCita;
    }

    public String getCostoCita() {
        return costoCita;
    }

    public void setFechaCita(String fechaCita) {
        this.fechaCita = fechaCita;
    }

    public void setEstadoCita(String estadoCita) {
        this.estadoCita = estadoCita;
    }

    public void setCostoCita(String costoCita) {
        this.costoCita = costoCita;
    }

    public int getPacienteId() {
        return pacienteId;
    }

    public int getMedicoId() {
        return medicoId;
    }

    public void setPacienteId(int pacienteId) {
        this.pacienteId = pacienteId;
    }

    public void setMedicoId(int medicoId) {
        this.medicoId = medicoId;
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

    public HashMap<Integer, Cita> listar() {
        HashMap<Integer, Cita> citas = new HashMap<>();
        try {
            Connection connection = db.establecerConexion();
            Statement st = connection.createStatement();
            String sql = "SELECT * FROM citas;";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Cita cita = new Cita();
                cita.id = rs.getInt("id");
                cita.estadoCita = rs.getString("estadoCita");
                cita.fechaCita = rs.getString("fechaCita");
                cita.costoCita = rs.getString("costoCita");
                cita.pacienteId = rs.getInt("pacienteId");
                cita.medicoId = rs.getInt("medicoId");

                citas.put(cita.id, cita);
            }

            rs.close();
            connection.close();
            // System.out.println("EL REGISTRO SE REALIZO EXITOSAMENTE");
        } catch (SQLException e) {
            System.out.println("Error al conectar a la Base de datos: " + e.toString());
        }

        return citas;
    }
}
