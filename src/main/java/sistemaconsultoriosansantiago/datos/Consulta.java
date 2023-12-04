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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Christian
 */
public class Consulta {

    private String id;
    private String fecha;
    private String motivo;
    private String examenFisico;
    private String observaciones;
    private String diagnostico;
    private String historialId;
    java.sql.Statement st;
    ResultSet rs;
    DB db = new DB();

    public Consulta() {
        this.id = null;
        this.fecha = null;
        this.motivo = null;
        this.examenFisico = null;
        this.observaciones = null;
        this.diagnostico = null;
        this.historialId = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getExamenFisico() {
        return examenFisico;
    }

    public void setExamenFisico(String examenFisico) {
        this.examenFisico = examenFisico;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getHistorialId() {
        return historialId;
    }

    public void setHistorialId(String historialId) {
        this.historialId = historialId;
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

    public String[] getAtributos() {
        ArrayList<String> atributosConValor = new ArrayList<>();
        Field[] fields = Consulta.class.getDeclaredFields();

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
        HashMap<Integer, Object> consultas = new HashMap<>();
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
            String sql = "SELECT " + atr + " FROM consultas;";

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Consulta consulta = new Consulta();
                if (atributos != null) {
                    for (String atributo : atributos) {
                        Field field = Consulta.class.getDeclaredField(atributo);
                        field.setAccessible(true);
                        field.set(consulta, rs.getString(atributo));
                    }
                    consultas.put(nullIdKey++, consulta); // Usa nullIdKey como clave y luego incrementa su valor
                } else {
                    consulta.id = rs.getString("id");
                    consulta.fecha = rs.getString("fecha");
                    consulta.motivo = rs.getString("motivo");
                    consulta.examenFisico = rs.getString("examenFisico");
                    consulta.observaciones = rs.getString("observaciones");
                    consulta.diagnostico = rs.getString("diagnostico");
                    consulta.historialId = rs.getString("historialId");

                    consultas.put(nullIdKey++, consulta);
                }
            }

            rs.close();
            connection.close();
            // System.out.println("EL REGISTRO SE REALIZO EXITOSAMENTE");
        } catch (SQLException | NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Error al conectar a la Base de datos: " + e.toString());
        }

        return consultas;
    }

    public String tituloListar() {
        return "LISTA DE CONSULTAS";
    }

    public String tituloInsertar() {
        return "CONSULTAS REGISTRADA";
    }

    public HashMap<Integer, Object> insertar(Consulta consulta) {
        HashMap<Integer, Object> consultas = new HashMap<>();
        int nullIdKey = 1;

        System.out.println("examen Fisico: " + consulta.getExamenFisico());
        try {
            Connection connection = db.establecerConexion();
            String sql = "INSERT INTO consultas (\"fecha\", \"motivo\", \"observaciones\", \"examenFisico\", \"diagnostico\", \"historialId\") VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setTimestamp(1, convertirStringATimestamp(consulta.getFecha()));
            preparedStatement.setString(2, consulta.getMotivo());
            preparedStatement.setString(3, consulta.getObservaciones());
            preparedStatement.setString(4, consulta.getExamenFisico());
            preparedStatement.setString(5, consulta.getDiagnostico());
            preparedStatement.setInt(6, Integer.parseInt(consulta.getHistorialId()));

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La creación de la consulta falló, no se insertaron filas.");
            }

            try ( ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    consulta.id = generatedKeys.getString(1);
                } else {
                    throw new SQLException("La creación de la consulta falló, no se obtuvo el ID.");
                }
            }

            connection.close();
            consultas.put(nullIdKey++, consulta);
            return consultas;
        } catch (SQLException e) {
            System.out.println("Error al conectar a la Base de datos: " + e.toString());
            return null;
        }
    }

    public Timestamp convertirStringATimestamp(String fecha) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            Date parsedDate = dateFormat.parse(fecha);
            return new Timestamp(parsedDate.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
