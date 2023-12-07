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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import static sistemaconsultoriosansantiago.datos.User.convertStringToSqlDate;

/**
 *
 * @author Christian
 */
public class Historial {

    private String id;
    private String nombrePaciente;
    private String pacienteId;

    java.sql.Statement st;
    ResultSet rs;
    DB db = new DB();

    // Constructor
    public Historial(String id, String nombre_paciente, String pacienteId) {
        this.id = id;
        this.nombrePaciente = nombre_paciente;
        this.pacienteId = pacienteId;
    }

    public Historial() {
        this.id = "";
        this.nombrePaciente = "";
        this.pacienteId = "";
    }

    // Getters y setters (puedes generarlos automáticamente en muchos IDEs)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombre_paciente) {
        this.nombrePaciente = nombre_paciente;
    }

    public String getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(String pacienteId) {
        this.pacienteId = pacienteId;
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
        return "LISTA DE HISTORIAL";
    }

    public String tituloInsertar() {
        return "HISTORIAL REGISTRADO";
    }

    public String sintaxisListar() {
        return "LIST[\"historials\"]";
    }

    public String sintaxisInsertar() {
        return "INSERT[\"historials\":\"nombrePaciente\"=\"String\",\"pacienteId\"=\"int\"];";
    }

    public String[] getAtributos() {
        ArrayList<String> atributosConValor = new ArrayList<>();
        Field[] fields = Historial.class.getDeclaredFields();

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
        HashMap<Integer, Object> historials = new HashMap<>();
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
            String sql = "SELECT " + atr + " FROM historials;";

            ResultSet rs = st.executeQuery(sql);
            System.out.println("al momento de listaraaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            System.out.println(rs);
            while (rs.next()) {
                Historial historial = new Historial();
                if (atributos != null) {
                    for (String atributo : atributos) {
                        Field field = Historial.class.getDeclaredField(atributo);
                        field.setAccessible(true);
                        field.set(historial, rs.getString(atributo));
                    }
                    historials.put(nullIdKey++, historial); // Usa nullIdKey como clave y luego incrementa su valor
                } else {
                    historial.id = rs.getString("id");
                    historial.nombrePaciente = rs.getString("nombrePaciente");
                    historial.pacienteId = rs.getString("pacienteId");

                    System.out.println("nombre paciente: " + historial.nombrePaciente);
                    System.out.println("paciente: " + historial.pacienteId);
                    historials.put(Integer.parseInt(historial.id), historial);
                }
            }

            rs.close();
            connection.close();
            // System.out.println("EL REGISTRO SE REALIZO EXITOSAMENTE");
        } catch (SQLException | NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Error al conectar a la Base de datos: " + e.toString());
        }

        return historials;
    }

    public HashMap<Integer, Object> insertar(Historial historial) {
        HashMap<Integer, Object> historials = new HashMap<>();
        int nullIdKey = 1;

        try {
            Connection connection = db.establecerConexion();
            String sql = "INSERT INTO historials ( \"nombrePaciente\", \"pacienteId\") VALUES (?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, historial.getNombrePaciente());
            preparedStatement.setInt(5, Integer.parseInt(historial.getPacienteId()));
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La creación del users falló, no se insertaron filas.");
            }

            try ( ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    historial.id = generatedKeys.getString(1);
                } else {
                    throw new SQLException("La creación del historial falló, no se obtuvo el ID.");
                }
            }

            connection.close();
            historials.put(nullIdKey++, historial);
            return historials;
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
