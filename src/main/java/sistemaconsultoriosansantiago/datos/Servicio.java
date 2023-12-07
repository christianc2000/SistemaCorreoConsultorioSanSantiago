/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemaconsultoriosansantiago.datos;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author liz
 */
public class Servicio {

    private String id;
    private String nombre;
    private String costo;
    private String formaCompra;
    private String atencion;

    java.sql.Statement st;
    ResultSet rs;
    DB db = new DB();

    public Servicio(String id, String nombre, String costo, String formaCompra, String atencion) {
        this.id = id;
        this.nombre = nombre;
        this.costo = costo;
        this.formaCompra = formaCompra;
        this.atencion = atencion;
    }

    public Servicio() {
        this.id = null;
        this.nombre = null;
        this.costo = null;
        this.formaCompra = null;
        this.atencion = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getFormaCompra() {
        return formaCompra;
    }

    public void setFormaCompra(String formaCompra) {
        this.formaCompra = formaCompra;
    }

    public String getAtencion() {
        return atencion;
    }

    public void setAtencion(String atencion) {
        this.atencion = atencion;
    }

     public String tituloListar() {
        return "LISTA DE SERVICIO";
    }

    public String tituloInsertar() {
        return "SERVICIO REGISTRADO";
    }
    
     public String sintaxisListar() {
        return "LIST[\"servicios\"]";
    }

    public String sintaxisInsertar() {
        return "INSERT[\"servicios\":\"nombre\"=\"String\",\"costo\"=\"float\",\"formaCompra\"=\"String\",\"atencion\"=\"String\"];";
    }
    
    public String[] getAtributos() {
        ArrayList<String> atributosConValor = new ArrayList<>();
        Field[] fields = Servicio.class.getDeclaredFields();

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
        HashMap<Integer, Object> servicios = new HashMap<>();
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
            String sql = "SELECT " + atr + " FROM servicios;";

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Servicio servicio = new Servicio();
                if (atributos != null) {
                    for (String atributo : atributos) {
                        Field field = Turno.class.getDeclaredField(atributo);
                        field.setAccessible(true);
                        field.set(servicio, rs.getString(atributo));
                    }
                    servicios.put(nullIdKey++, servicio); // Usa nullIdKey como clave y luego incrementa su valor
                } else {

                    servicio.id = rs.getString("id");
                    servicio.nombre = rs.getString("nombre");
                    servicio.costo = rs.getString("costo");
                    servicio.formaCompra = rs.getString("formaCompra");
                    servicio.atencion = rs.getString("atencion");

                    servicios.put(Integer.parseInt(servicio.id), servicio);
                }
            }

            rs.close();
            connection.close();
            // System.out.println("EL REGISTRO SE REALIZO EXITOSAMENTE");
        } catch (SQLException | NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Error al conectar a la Base de datos: " + e.toString());
        }

        return servicios;
    }

}
