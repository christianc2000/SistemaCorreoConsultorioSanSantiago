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
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author liz
 */
public class Turno {

    private String id;
    private String dia;
    private String horaInicio;
    private String horaFin;

    java.sql.Statement st;
    ResultSet rs;
    DB db = new DB();

    public Turno(String id, String dia, String horaInicio, String horaFin) {
        this.id = id;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public Turno() {
        this.id = null;
        this.dia = null;
        this.horaInicio = null;
        this.horaFin = null;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getId() {
        return id;
    }

    public String getDia() {
        return dia;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
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
        return "LISTA DE TURNO";
    }

    public String tituloInsertar() {
        return "TURNO REGISTRADO";
    }
  
       public String[] getAtributos() {
        ArrayList<String> atributosConValor = new ArrayList<>();
        Field[] fields = Turno.class.getDeclaredFields();

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
        HashMap<Integer, Object> turnos = new HashMap<>();
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
            String sql = "SELECT " + atr + " FROM turnos;";

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Turno turno = new Turno();
                if (atributos != null) {
                    for (String atributo : atributos) {
                        Field field = Turno.class.getDeclaredField(atributo);
                        field.setAccessible(true);
                        field.set(turno, rs.getString(atributo));
                    }
                    turnos.put(nullIdKey++, turno); // Usa nullIdKey como clave y luego incrementa su valor
                } else {
                  
                turno.id = rs.getString("id");
                turno.dia = rs.getString("dia");
                turno.horaFin = rs.getString("horaFin");
                turno.horaInicio = rs.getString("horaInicio");

                turnos.put(Integer.parseInt(turno.id), turno);
                }
            }

            rs.close();
            connection.close();
            // System.out.println("EL REGISTRO SE REALIZO EXITOSAMENTE");
        } catch (SQLException | NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Error al conectar a la Base de datos: " + e.toString());
        }

        return turnos;
    }

   

}
