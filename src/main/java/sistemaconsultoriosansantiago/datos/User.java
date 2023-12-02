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
public class User {
    
    private int id;
    private String ci;
    private String nombre;
    private String apellido;
    private Date fechaNacimiento;
    private String residenciaActual;
    private Integer celular;
    private String genero;
    private String tipo;
    private String urlFoto;
    private String email;
    private String password;

    java.sql.Statement st;
    ResultSet rs;
    DB db = new DB();

    // Constructor
    public User(int id, String ci, String nombre, String apellido, Date fechaNacimiento,
            String residenciaActual, Integer celular, String genero, String tipo, String urlFoto,
            String email, String password) {
        this.id = id;
        this.ci = ci;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.residenciaActual = residenciaActual;
        this.celular = celular;
        this.genero = genero;
        this.tipo = tipo;
        this.urlFoto = urlFoto;
        this.email = email;
        this.password = password;
    }

    public User() {
        this.id = 0;
        this.ci = "";
        this.nombre = "";
        this.apellido = "";
        this.fechaNacimiento = null;
        this.residenciaActual = "";
        this.celular = 0;
        this.genero = "";
        this.tipo = "";
        this.urlFoto = "";
        this.email = "";
        this.password = "";
    }

    // Getters y setters (puedes generarlos automáticamente en muchos IDEs)
    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getApellido() {
        return apellido;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getResidenciaActual() {
        return residenciaActual;
    }

    public Integer getCelular() {
        return celular;
    }

    public String getGenero() {
        return genero;
    }

    public String getTipo() {
        return tipo;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
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

    public HashMap<Integer, User> listar() {
        HashMap<Integer, User> usuarios = new HashMap<>();
        try {
            Connection connection = db.establecerConexion();
            Statement st = connection.createStatement();
            String sql = "SELECT * FROM users;";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                User usuario = new User();
                usuario.id = rs.getInt("id");
                usuario.ci = rs.getString("ci");
                usuario.nombre = rs.getString("nombre");
                usuario.apellido = rs.getString("apellido");
                usuario.fechaNacimiento = rs.getDate("fecha_nacimiento");
                usuario.residenciaActual = rs.getString("residencia_actual");
                usuario.celular = rs.getInt("celular");
                usuario.genero = rs.getString("genero");
                usuario.tipo = rs.getString("tipo");
                usuario.urlFoto = rs.getString("url_foto");
                usuario.email = rs.getString("email");
                usuario.password = rs.getString("password");

                usuarios.put(usuario.id, usuario);
            }

            rs.close();
            connection.close();
            // System.out.println("EL REGISTRO SE REALIZO EXITOSAMENTE");
        } catch (SQLException e) {
            System.out.println("Error al conectar a la Base de datos: " + e.toString());
        }

        return usuarios;
    }
}
