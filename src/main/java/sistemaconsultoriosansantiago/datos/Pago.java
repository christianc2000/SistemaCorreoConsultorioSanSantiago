/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemaconsultoriosansantiago.datos;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

import java.net.URL;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Christian
 */
public class Pago {
     private String lcComerceID;
    private String lnMoneda;
    private String lnTelefono;
    private String lcRazonSocial;
    private String tnTipoServicio;
    private String pacienteId;
    private String pacienteNombre;
    private String descuento;
    private String servicioId;
    private String lnCiNit;
    private String lcNroPago;
    private String lnMontoClienteEmpresa;
    private String lcCorreo;
    private String lcUrlCallBack;
    private String lcUrlReturn;
    // private String laPedidoDetalle;
    private String lcUrl;

    java.sql.Statement st;
    ResultSet rs;
    DB db = new DB();
    
      // Constructor
    public Pago(String lcComerceID, String lnMoneda, String lnTelefono, String lcRazonSocial, String lnCiNit, String lcNroPago, String lnMontoClienteEmpresa, String lcCorreo, String lcUrlCallBack, String lcUrlReturn, String lcUrl) {
        this.lcComerceID = lcComerceID;
        this.lnMoneda = lnMoneda;
        this.lnTelefono = lnTelefono;
        this.lcRazonSocial = lcRazonSocial;
        this.lnCiNit = lnCiNit;
        this.lcNroPago = lcNroPago;
        this.lnMontoClienteEmpresa = lnMontoClienteEmpresa;
        this.lcCorreo = lcCorreo;
        this.lcUrlCallBack = lcUrlCallBack;
        this.lcUrlReturn = lcUrlReturn;
        // this.laPedidoDetalle = laPedidoDetalle;
        this.lcUrl = lcUrl;
    }

    public Pago() {
        this.lcComerceID = "";
        this.lnMoneda = "";
        this.lnTelefono = "";
        this.lcRazonSocial = "";
        this.lnCiNit = "";
        // this.lcNroPago = "grupo01-" + generarNumeroAleatorio(100000, 999999);
        this.lcNroPago = "";
        this.lnMontoClienteEmpresa = "";
        this.lcCorreo = "";
        this.lcUrlCallBack = "";
        this.lcUrlReturn = "";
        this.lcUrl = "";
//        this.laPedidoDetalle = "prueba";
    }
    // Método para generar un número aleatorio dentro de un rango
    private static int generarNumeroAleatorio(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    public String getLcComerceID() {
        return lcComerceID;
    }

    public void setLcComerceID(String lcComerceID) {
        this.lcComerceID = lcComerceID;
    }

    public String getLnMoneda() {
        return lnMoneda;
    }

    public void setLnMoneda(String lnMoneda) {
        this.lnMoneda = lnMoneda;
    }

    public String getLnTelefono() {
        return lnTelefono;
    }

    public String getTnTipoServicio() {
        return tnTipoServicio;
    }

    public void setTnTipoServicio(String tnTipoServicio) {
        this.tnTipoServicio = tnTipoServicio;
    }

    public String getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(String pacienteId) {
        this.pacienteId = pacienteId;
    }

    public String getPacienteNombre() {
        return pacienteNombre;
    }

    public void setPacienteNombre(String pacienteNombre) {
        System.out.println("al setear nonmbre oacienteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        System.out.println(pacienteNombre);
        this.pacienteNombre = pacienteNombre;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

    public String getServicioId() {
        return servicioId;
    }

    public void setServicioId(String servicioId) {
        this.servicioId = servicioId;
    }

    public void setLnTelefono(String lnTelefono) {
        this.lnTelefono = lnTelefono;
    }

    public String getLcRazonSocial() {
        return lcRazonSocial;
    }

    public void setLcRazonSocial(String lcRazonSocial) {
        this.lcRazonSocial = lcRazonSocial;
    }

    public String getLnCiNit() {
        return lnCiNit;
    }

    public void setLnCiNit(String lnCiNit) {
        this.lnCiNit = lnCiNit;
    }

    public String getLcNroPago() {
        return lcNroPago;
    }

    public void setLcNroPago(String lcNroPago) {
        this.lcNroPago = lcNroPago;
    }

    public String getLnMontoClienteEmpresa() {
        return lnMontoClienteEmpresa;
    }

    public void setLnMontoClienteEmpresa(String lnMontoClienteEmpresa) {
        this.lnMontoClienteEmpresa = lnMontoClienteEmpresa;
    }

    public String getLcCorreo() {
        return lcCorreo;
    }

    public void setLcCorreo(String lcCorreo) {
        this.lcCorreo = lcCorreo;
    }

    public String getLcUrlCallBack() {
        return lcUrlCallBack;
    }

    public void setLcUrlCallBack(String lcUrlCallBack) {
        this.lcUrlCallBack = lcUrlCallBack;
    }

    public String getLcUrlReturn() {
        return lcUrlReturn;
    }

    public void setLcUrlReturn(String lcUrlReturn) {
        this.lcUrlReturn = lcUrlReturn;
    }

    // public String getLaPedidoDetalle() {
    //     return laPedidoDetalle;
    // }

    // public void setLaPedidoDetalle(String laPedidoDetalle) {
    //     this.laPedidoDetalle = laPedidoDetalle;
    // }

    public String getLcUrl() {
        return lcUrl;
    }

    public void setLcUrl(String lcUrl) {
        this.lcUrl = lcUrl;
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
    
    public void datosDefecto() {
        this.lcComerceID = "d029fa3a95e174a19934857f535eb9427d967218a36ea014b70ad704bc6c8d1c";
        this.lnMoneda = "2";
        // this.lcNroPago = "grupo01-" + generarNumeroAleatorio(100000, 999999);
        this.lcNroPago = "grupo01-"+ getLcNroPago();
        this.tnTipoServicio = "1";
        //        this.lcUrlReturn = null;
        //        // this.laPedidoDetalle = "prueba";
        // this.lcUrl = null;
    }
    public String datosEnPago(){
        return "tnTelefono="+ getLnTelefono() + "&tcRazonSocial="+ getLcRazonSocial() +"&tcCiNit="+ getLnCiNit() +"&nroPago="+ getLcNroPago() +"&tnMonto="+ getLnMontoClienteEmpresa() +"&tcCorreo="+ getLcCorreo() +"&servicioId="+ getServicioId() +"&pacienteId="+ getPacienteId() +"&pacienteNombre="+ getPacienteNombre() +"&descuento="+ getDescuento() +"&tnTipoServicio="+ getTnTipoServicio();

    }
    
    public void consumirApi(){
        try {
            System.out.println("ingresaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa a consumir api");
            String apiUrl = "http://software.test/api/RecolectarDatos";
            
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            // Configurar el tipo de solicitud
            connection.setRequestMethod("POST");
            
            // Habilitar envío de datos
            connection.setDoOutput(true);
            // Crear datos para enviar en la solicitud POST
            System.out.println("********************************* api ***********************************************************");
            String postData = "tnTelefono="+ getLnTelefono() + "&tcRazonSocial="+ getLcRazonSocial() +"&tcCiNit="+ getLnCiNit() +"&nroPago="+ getLcNroPago() +"&tnMonto="+ getLnMontoClienteEmpresa() +"&tcCorreo="+ getLcCorreo() +"&servicioId="+ getServicioId() +"&pacienteId="+ getPacienteId() +"&pacienteNombre="+ getPacienteNombre() +"&descuento="+ getDescuento() +"&tnTipoServicio="+ getTnTipoServicio();

            System.out.println(postData);
            // Obtener flujo de salida y escribir datos en la solicitud
            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                byte[] postDataBytes = postData.getBytes(StandardCharsets.UTF_8);
                wr.write(postDataBytes);
            } catch (IOException ex) {
                Logger.getLogger(Pago.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            // Leer la respuesta de la API
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            // Cerrar la conexión y el lector
            reader.close();
            connection.disconnect();

            // Imprimir la respuesta de la API
            System.out.println("Respuesta de la API: " + response.toString());

        } catch (MalformedURLException ex) {
            Logger.getLogger(Pago.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(Pago.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Pago.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
