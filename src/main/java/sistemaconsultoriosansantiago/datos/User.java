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
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Christian
 */
public class User {

    private String id;
    private String ci;
    private String nombre;
    private String apellido;
    private String fechaNacimiento;
    private String celular;
    private String tipo;
    private String genero;
    private String residenciaActual;
    private String email;
    private String password;
    private String urlFoto;
    private String formacion;
    private String sueldo;
    private String hijo;
    private String ocupacion;

    java.sql.Statement st;
    ResultSet rs;
    DB db = new DB();

    // Constructor
    public User(String id, String ci, String nombre, String apellido, String fechaNacimiento, String celular, String tipo, String genero, String residenciaActual, String email, String password, String urlFoto, String formacion, String sueldo, String hijo, String ocupacion) {
        this.id = id;
        this.ci = ci;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.celular = celular;
        this.tipo = tipo;
        this.genero = genero;
        this.residenciaActual = residenciaActual;
        this.email = email;
        this.password = password;
        this.urlFoto = urlFoto;
        this.formacion = formacion;
        this.sueldo = sueldo;
        this.hijo = hijo;
        this.ocupacion = ocupacion;
    }

    public User() {
        this.id = null;
        this.ci = null;
        this.nombre = null;
        this.apellido = null;
        this.fechaNacimiento = null;
        this.celular = null;
        this.tipo = null;
        this.genero = null;
        this.residenciaActual = null;
        this.email = null;
        this.password = null;
        this.urlFoto = null;
        this.formacion = null;
        this.sueldo = null;
        this.hijo = null;
        this.ocupacion = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getResidenciaActual() {
        return residenciaActual;
    }

    public void setResidenciaActual(String residenciaActual) {
        this.residenciaActual = residenciaActual;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getFormacion() {
        return formacion;
    }

    public void setFormacion(String formacion) {
        this.formacion = formacion;
    }

    public String getSueldo() {
        return sueldo;
    }

    public void setSueldo(String sueldo) {
        this.sueldo = sueldo;
    }

    public String getHijo() {
        return hijo;
    }

    public void setHijo(String hijo) {
        this.hijo = hijo;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
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
        Field[] fields = User.class.getDeclaredFields();

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
        HashMap<Integer, Object> usuarios = new HashMap<>();
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
            String sql = "SELECT " + atr + " FROM users;";

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                User usuario = new User();
                if (atributos != null) {
                    for (String atributo : atributos) {
                        Field field = User.class.getDeclaredField(atributo);
                        field.setAccessible(true);
                        field.set(usuario, rs.getString(atributo));
                    }
                    usuarios.put(nullIdKey++, usuario); // Usa nullIdKey como clave y luego incrementa su valor
                } else {
                    usuario.id = rs.getString("id");
                    usuario.ci = rs.getString("ci");
                    usuario.nombre = rs.getString("nombre");
                    usuario.apellido = rs.getString("apellido");
                    usuario.fechaNacimiento = rs.getString("fechaNacimiento");
                    usuario.celular = rs.getString("celular");
                    usuario.tipo = rs.getString("tipo");
                    usuario.genero = rs.getString("genero");
                    usuario.residenciaActual = rs.getString("residenciaActual");
                    usuario.email = rs.getString("email");
                    usuario.password = rs.getString("password");
                    usuario.urlFoto = rs.getString("urlFoto");
                    usuario.formacion = rs.getString("formacion");
                    usuario.sueldo = rs.getString("sueldo");
                    usuario.hijo = rs.getString("hijo");
                    usuario.ocupacion = rs.getString("ocupacion");
                    System.out.println("formacion: " + usuario.formacion);
                    System.out.println("sueldo: " + usuario.sueldo);
                    System.out.println("hijo: " + usuario.hijo);
                    System.out.println("ocupacion: " + usuario.ocupacion);
                    usuarios.put(Integer.parseInt(usuario.id), usuario);
                }
            }

            rs.close();
            connection.close();
            // System.out.println("EL REGISTRO SE REALIZO EXITOSAMENTE");
        } catch (SQLException | NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Error al conectar a la Base de datos: " + e.toString());
        }

        return usuarios;
    }

}
