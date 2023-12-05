package sistemaconsultoriosansantiago.datos;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Christian
 */
public class Reporte {
   private String id;
   private String urlDocumento;
   private String fechaReporte;
   private String userId;
     java.sql.Statement st;
    ResultSet rs;
    DB db = new DB();
    
   public Reporte(){
       this.id=null;
       this.urlDocumento=null;
       this.fechaReporte=null;
       this.userId=null;
   }

    public String getId() {
        return id;
    }

    public String getUrlDocumento() {
        return urlDocumento;
    }

    public String getFechaReporte() {
        return fechaReporte;
    }

    public String getUserId() {
        return userId;
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
   public String[] getAtributos() {
        ArrayList<String> atributosConValor = new ArrayList<>();
        Field[] fields = Reporte.class.getDeclaredFields();

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
            String sql = "SELECT " + atr + " FROM reportes;";

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Reporte reporte = new Reporte();
                if (atributos != null) {
                    for (String atributo : atributos) {
                        Field field = Reporte.class.getDeclaredField(atributo);
                        field.setAccessible(true);
                        field.set(reporte, rs.getString(atributo));
                    }
                    consultas.put(nullIdKey++, reporte); // Usa nullIdKey como clave y luego incrementa su valor
                } else {
                    reporte.id = rs.getString("id");
                    reporte.fechaReporte = rs.getString("fechaReporte");
                    reporte.urlDocumento = rs.getString("urlDocumento");
                    reporte.userId = rs.getString("userId");

                    consultas.put(Integer.parseInt(reporte.id), reporte);
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
}
