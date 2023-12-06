/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemaconsultoriosansantiago.datos;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Christian
 */
public class Cita {
    
    private String id;
    private String fechaCita;
    private String estadoCita;
    private String costoCita;
    private String pacienteId;
    private String medicoId;

    java.sql.Statement st;
    ResultSet rs;
    DB db = new DB();

    // Constructor
    public Cita(String id, String estadoCita, String costoCita, String fechaCita, String pacienteId, String medicoId) {
        this.id = id;
        this.fechaCita = fechaCita;
        this.estadoCita = estadoCita;
        this.costoCita = costoCita;
        this.pacienteId = pacienteId;
        this.medicoId = medicoId;
    }

    public Cita() {
        this.id = "";
        this.fechaCita = "";
        this.estadoCita = "";
        this.costoCita = "";
        this.pacienteId = "";
        this.medicoId = "";
    }

    // Getters y setters (puedes generarlos automáticamente en muchos IDEs)
    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getPacienteId() {
        return pacienteId;
    }

    public String getMedicoId() {
        return medicoId;
    }

    public void setPacienteId(String pacienteId) {
        this.pacienteId = pacienteId;
    }

    public void setMedicoId(String medicoId) {
        this.medicoId = medicoId;
    }

    public Statement getSt() {
        return st;
    }

    public void setSt(Statement st) {
        this.st = st;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public DB getDb() {
        return db;
    }

    public void setDb(DB db) {
        this.db = db;
    }

    public String tituloListar() {
        return "LISTA DE CITAS";
    }

    public String tituloInsertar() {
        return "CITA REGISTRADA";
    }
    
    public String[] getAtributos() {
        ArrayList<String> atributosConValor = new ArrayList<>();
        Field[] fields = Cita.class.getDeclaredFields();

        for (Field field : fields) {
            String nombreAtributo = field.getName();
            if (!nombreAtributo.equals("st") && !nombreAtributo.equals("rs") && !nombreAtributo.equals("db")) {
                field.setAccessible(true);
                try {
                    if (field.get(this) != null) {
                        atributosConValor.add(nombreAtributo);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return atributosConValor.toArray(new String[0]);
    }
    
    public HashMap<Integer, Object> listar(String atributos[]) {
        HashMap<Integer, Object> citas = new HashMap<>();
        int nullIdKey = 1; // Clave para usuarios sin id
        try {
            Connection connection = db.establecerConexion();
            Statement st = connection.createStatement();
            String atr = "";
            if (atributos != null) {
                for (int i = 0; i < atributos.length - 1; i++) {
                    atr = atr + "\"" + atributos[i] + "\",";
                }
                atr = atr + "\"" + atributos[atributos.length - 1] + "\"";
            } else {
                atr = "*";
            }
            String sql = "SELECT " + atr + " FROM citas;";

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Cita cita = new Cita();
                if (atributos != null) {
                    for (String atributo : atributos) {
                        Field field = Cita.class.getDeclaredField(atributo);
                        field.setAccessible(true);
                        field.set(cita, rs.getString(atributo));
                    }
                    citas.put(nullIdKey++, cita); // Usa nullIdKey como clave y luego incrementa su valor
                } else {
                    cita.id = rs.getString("id");
                    cita.fechaCita = rs.getString("fechaCita");
                    cita.estadoCita = rs.getString("estadoCita");
                    cita.costoCita = rs.getString("costoCita");
                    cita.pacienteId = rs.getString("pacienteId");
                    cita.medicoId = rs.getString("medicoId");
                   
                    System.out.println("fechaCita: " + cita.fechaCita);
                    System.out.println("estadoCita: " + cita.estadoCita);
                    System.out.println("costoCita: " + cita.costoCita);
                    System.out.println("pacienteId: " + cita.pacienteId);
                    System.out.println("medicoId: " + cita.medicoId);
                    citas.put(Integer.parseInt(cita.id), cita);
                }
            }

            rs.close();
            connection.close();
            // System.out.println("EL REGISTRO SE REALIZO EXITOSAMENTE");
        } catch (SQLException | NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Error al conectar a la Base de datos: " + e.toString());
        }

        return citas;
    }
    
    public HashMap<Integer, Object> insertar(Cita cita) {
        HashMap<Integer, Object> citas = new HashMap<>();
        int nullIdKey = 1;

        try {
            Connection connection = db.establecerConexion();
            String sql = "INSERT INTO citas ( \"fechaCita\", \"estadoCita\",\"costoCita\", \"pacienteId\",\"medicoId\") VALUES (?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, cita.getFechaCita());
            preparedStatement.setInt(5, Integer.parseInt(cita.getEstadoCita()));
            preparedStatement.setInt(5, Integer.parseInt(cita.getCostoCita()));
            preparedStatement.setInt(5, Integer.parseInt(cita.getPacienteId()));
            preparedStatement.setInt(5, Integer.parseInt(cita.getMedicoId()));
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La creación del users falló, no se insertaron filas.");
            }

            try ( ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    cita.id = generatedKeys.getString(1);
                } else {
                    throw new SQLException("La creación de la cita falló, no se obtuvo el ID.");
                }
            }

            connection.close();
            citas.put(nullIdKey++, cita);
            return citas;
        } catch (SQLException e) {
            System.out.println("Error al conectar a la Base de datos: " + e.toString());
            return null;
        }
    }
    
     public static java.sql.Date convertStringToSqlDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date utilDate = dateFormat.parse(dateString);
            // Convert java.util.Date to java.sql.Date
            return new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            // Handle the ParseException (e.g., log it, throw a specific exception, etc.)
            e.printStackTrace();
            return null;
        }
    }
}
