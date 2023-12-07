/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemacorreoconsultoriosansantiago.manejadorcorreo;

import java.util.HashMap;
import java.util.Map;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import sistemaconsultoriosansantiago.datos.Cita;
import sistemaconsultoriosansantiago.datos.Consulta;
import sistemaconsultoriosansantiago.datos.Historial;
import sistemaconsultoriosansantiago.datos.Pago;
import sistemaconsultoriosansantiago.datos.Reporte;
import sistemaconsultoriosansantiago.datos.Servicio;
//import sistemaconsultoriosansantiago.datos.Servicio;
//import sistemaconsultoriosansantiago.datos.Turno;
import sistemaconsultoriosansantiago.datos.User;
import sistemacorreoconsultoriosansantiago.Analizador.AnalizarSintaxis;
import sistemacorreoconsultoriosansantiago.Analizador.AtributoValor;
import sistemacorreoconsultoriosansantiago.Analizador.InformacionComando;

/**
 *
 * @author Christian
 */
public class ThreadAnalizeMessagePop3 extends Thread {

    private String message;
    private String destinatario;

    public ThreadAnalizeMessagePop3(String message, String destinatario) {
        this.message = message;
        this.destinatario = destinatario;
    }

    @Override
    public void run() {
        AnalizarSintaxis analizador = new AnalizarSintaxis();
        try {
            InformacionComando ic = analizador.analizarSintaxis(message.replace("\n", "")); //null, null, null, accion,tabla,null]
            SendCorreo sc = new SendCorreo();
            if (ic.getAccion() == "INICIAR") {
                sc.enviar(null, null, destinatario, null, null, ic.getAccion());

            } else if (ic.getAccion() == "PAGO") {
                        System.out.println("INGRESA A REALIZAR PAGO POR FACIL");
                        AtributoValor p = new AtributoValor();
                        HashMap<String, String> atrvu = p.convertAtributoValorHashMap(ic.getAtributos());
                        System.out.println("------------------------------------------------");
                                for (Map.Entry<String, String> entry : atrvu.entrySet()) {
                                    System.out.print("atributo: " + entry.getKey() + "= ");
                                    System.out.println("valor: " + entry.getValue());

                                }
                        System.out.println("------------------------------------------------");
                                Pago pago = new Pago();
                                // pago.setLcComerceID(atrvu.get("lcComerceID"));
                                // pago.setLnMoneda(atrvu.get("lnMoneda"));
                                pago.setLnTelefono(atrvu.get("telefono"));
                                pago.setLcRazonSocial(atrvu.get("razonSocial"));
                                pago.setLnCiNit(atrvu.get("ciNit"));
                                pago.setLcNroPago(atrvu.get("nroPago"));
                                pago.setLnMontoClienteEmpresa(atrvu.get("MontoClienteEmpresa"));
                                pago.setLcCorreo(atrvu.get("correo"));
//                                pago.setLcUrlCallBack(atrvu.get("urlCallBack"));
//                                pago.setLcUrlReturn(atrvu.get("urlReturn"));
//                                pago.setLcUrl(atrvu.get("url"));
//                                pago.setTnTipoServicio(atrvu.get("tipoServicio"));// 1 qr, 2 pago
                                pago.setPacienteId(atrvu.get("pacienteId"));
                                pago.setPacienteNombre(atrvu.get("pacienteNombre"));
                                pago.setDescuento(atrvu.get("descuento"));
                                pago.setServicioId(atrvu.get("servicioId"));
                                pago.datosDefecto();
                                System.out.println("lo que hay en pagoooooooooooooooooooooooooooo");
                                System.out.println(pago.datosEnPago());
                                pago.consumirApi();
//                                if (pagoi.size() > 0) {
//                                    Pago primerUser = (Pago) pagoi.get(1);
//                                    String[] tituloPago = primerUser.getAtributos();
//                                    sc.enviar(tituloUser, pagoi, destinatario, pago.tituloInsertar());
//                                } else {
//                                    System.out.println("ERROR NO SE REGISTRÓ ");
//                                }
            }else {
                if (analizador.analizarTabla(ic)) {

                    switch (ic.getAccion()) {

                        case "LIST":

                            switch (ic.getTabla()) {
                                case "users":
                                    User user = new User();
                                    //System.out.println("atributos: " + ic.getAtributos()[0]);
                                    HashMap<Integer, Object> usuarios = user.listar(ic.getAtributos());
                                    User primerUsuario = (User) usuarios.get(1);
                                    String[] tituloUsuario = primerUsuario.getAtributos();

                                    for (int i = 0; i < tituloUsuario.length; i++) {
                                        System.out.println("titulo " + i + ": " + tituloUsuario[i]);
                                    }

                                    sc.enviar(tituloUsuario, usuarios, destinatario, user.tituloListar(), user.sintaxisInsertar(), "Insertar Usuario");

                                    break;
                                case "consultas":
                                    Consulta consulta = new Consulta();
                                    //System.out.println("atributos: " + ic.getAtributos()[0]);
                                    HashMap<Integer, Object> consultas = consulta.listar(ic.getAtributos());
                                    Consulta primerConsulta = (Consulta) consultas.get(1);
                                    String[] tituloConsulta = primerConsulta.getAtributos();

                                    for (int i = 0; i < tituloConsulta.length; i++) {
                                        System.out.println("titulo " + i + ": " + tituloConsulta[i]);
                                    }

                                    sc.enviar(tituloConsulta, consultas, destinatario, consulta.tituloListar(), consulta.sintaxisInsertar(), "Insertar Consulta");
                                    break;

                                case "reportes":
                                    Reporte reporte = new Reporte();
                                    //System.out.println("atributos: " + ic.getAtributos()[0]);
                                    HashMap<Integer, Object> reportes = reporte.listar(ic.getAtributos());
                                    if (reportes.size() > 0) {
                                        Reporte primerReporte = (Reporte) reportes.get(1);
                                        String[] tituloReporte = primerReporte.getAtributos();

                                        for (int i = 0; i < tituloReporte.length; i++) {
                                            System.out.println("titulo " + i + ": " + tituloReporte[i]);
                                        }

                                        sc.enviar(tituloReporte, reportes, destinatario, reporte.tituloListar(), reporte.sintaxisInsertar(), "Insertar Reporte");
                                    } else {
                                        System.out.println("ENVIAR ERROR QUE EST�? VAC�?O");
                                    }
                                    break;

                                case "citas":
                                    Cita cita = new Cita();
                                    //System.out.println("atributos: " + ic.getAtributos()[0]);
                                    HashMap<Integer, Object> citas = cita.listar(ic.getAtributos());
                                    Cita primerCita = (Cita) citas.get(1);
                                    String[] tituloCita = primerCita.getAtributos();

                                    for (int i = 0; i < tituloCita.length; i++) {
                                        System.out.println("titulo " + i + ": " + tituloCita[i]);
                                    }

                                    sc.enviar(tituloCita, citas, destinatario, cita.tituloListar(), cita.sintaxisInsertar(), "Insertar Cita");
                                    break;

                                case "historials":
                                    Historial historial = new Historial();
                                    //System.out.println("atributos: " + ic.getAtributos()[0]);
                                    HashMap<Integer, Object> historials = historial.listar(ic.getAtributos());
                                    Historial primerHistorial = (Historial) historials.get(1);
                                    String[] tituloHistorial = primerHistorial.getAtributos();

                                    for (int i = 0; i < tituloHistorial.length; i++) {
                                        System.out.println("titulo " + i + ": " + tituloHistorial[i]);
                                    }

                                    sc.enviar(tituloHistorial, historials, destinatario, historial.tituloInsertar(), historial.sintaxisInsertar(), "Insertar Historial");

                                    break;
//                            case "turnos":
//                                Turno turno = new Turno();
//                                //System.out.println("atributos: " + ic.getAtributos()[0]);
//                                HashMap<Integer, Object> turnos = turno.listar(ic.getAtributos());
//                                if (turnos.size() > 0) {
//                                    Turno primerTurno = (Turno) turnos.get(1);
//                                    String[] tituloTurno = primerTurno.getAtributos();
//
//                                    for (int i = 0; i < tituloTurno.length; i++) {
//                                        System.out.println("titulo " + i + ": " + tituloTurno[i]);
//                                    }
//
//                                    sc.enviar(tituloTurno, turnos, destinatario,turno.tituloListar());
//                                } else {
//                                    System.out.println("ENVIAR ERROR QUE EST�? VAC�?O");
//                                }
//
//                                break;

                                case "servicios":
                                    Servicio servicio = new Servicio();
                                    //System.out.println("atributos: " + ic.getAtributos()[0]);
                                    HashMap<Integer, Object> servicios = servicio.listar(ic.getAtributos());
                                    if (servicios.size() > 0) {
                                        Servicio primerServicio = (Servicio) servicios.get(1);
                                        String[] tituloServicio = primerServicio.getAtributos();

                                        for (int i = 0; i < tituloServicio.length; i++) {
                                            System.out.println("titulo " + i + ": " + tituloServicio[i]);
                                        }

                                        sc.enviar(tituloServicio, servicios, destinatario, servicio.tituloListar(), servicio.sintaxisInsertar(), "Insertar Servicio");
                                    } else {
                                        System.out.println("ENVIAR ERROR QUE EST�? VAC�?O");
                                    }

                                    break;
                                default:
                                    throw new AssertionError();
                            }
                            break;
                        case "INSERT":
                            System.out.println("INGRESA A INSERTAR");
                            AtributoValor a = new AtributoValor();
                            switch (ic.getTabla()) {
                                case "servicios":
                                    HashMap<String, String> atrvs = a.convertAtributoValorHashMap(ic.getAtributos());
                                    for (Map.Entry<String, String> entry : atrvs.entrySet()) {
                                        System.out.print("atributo: " + entry.getKey() + "= ");
                                        System.out.println("valor: " + entry.getValue());

                                    }
                                    Servicio servicio = new Servicio();
                                    servicio.setNombre(atrvs.get("nombre"));
                                    servicio.setCosto(atrvs.get("costo"));
                                    servicio.setFormaCompra(atrvs.get("formaCompra"));
                                    servicio.setAtencion(atrvs.get("atencion"));

                                    HashMap<Integer, Object> servicioi = servicio.insertar(servicio);
                                    if (servicioi.size() > 0) {
                                        System.out.println("servicio 1: " + servicioi.get(1));
                                        Servicio primerServicio = (Servicio) servicioi.get(1);
                                        String[] tituloServicio = primerServicio.getAtributos();
                                        sc.enviar(tituloServicio, servicioi, destinatario, servicio.tituloInsertar(), servicio.sintaxisListar(), "Listar Servicio");
                                    } else {
                                        sc.enviar(null, null, destinatario, null, null, "ERROR");
                                    }
                                    break;
                                case "users":
                                    HashMap<String, String> atrvu = a.convertAtributoValorHashMap(ic.getAtributos());
                                    for (Map.Entry<String, String> entry : atrvu.entrySet()) {
                                        System.out.print("atributo: " + entry.getKey() + "= ");
                                        System.out.println("valor: " + entry.getValue());

                                    }
                                    User user = new User();
                                    user.setCi(atrvu.get("ci"));
                                    user.setNombre(atrvu.get("nombre"));
                                    user.setApellido(atrvu.get("apellido"));
                                    user.setFechaNacimiento(atrvu.get("fechaNacimiento"));
                                    user.setCelular(atrvu.get("celular"));
                                    user.setTipo(atrvu.get("tipo"));
                                    user.setResidenciaActual(atrvu.get("residenciaActual"));
                                    user.setGenero(atrvu.get("genero"));
                                    user.setEmail(atrvu.get("email"));
                                    user.setPassword(atrvu.get("password"));
                                    user.setUrlFoto(atrvu.get("urlFoto"));
                                    user.setFormacion(atrvu.get("formacion"));
                                    user.setSueldo(atrvu.get("sueldo"));
                                    user.setHijo(atrvu.get("hijo"));
                                    user.setOcupacion(atrvu.get("ocupacion"));
                                    HashMap<Integer, Object> useri = user.insertar(user);
                                    if (useri.size() > 0) {
                                        User primerUser = (User) useri.get(1);
                                        String[] tituloUser = primerUser.getAtributos();
                                        sc.enviar(tituloUser, useri, destinatario, user.tituloInsertar(), user.sintaxisListar(), "Listar Usuario");
                                    } else {
                                        System.out.println("ERROR NO SE REGISTRÓ ");
                                         sc.enviar(null, null, destinatario, null, null, "ERROR");
                                    }
                                    break;
                                case "consultas":
                                    HashMap<String, String> atrvc = a.convertAtributoValorHashMap(ic.getAtributos());
                                    for (Map.Entry<String, String> entry : atrvc.entrySet()) {
                                        System.out.print("atributo: " + entry.getKey() + "= ");
                                        System.out.println("valor: " + entry.getValue());

                                    }
                                    Consulta consulta = new Consulta();
                                    consulta.setHistorialId(atrvc.get("historialId"));
                                    consulta.setDiagnostico(atrvc.get("diagnostico"));
                                    consulta.setFecha(atrvc.get("fecha"));
                                    consulta.setExamenFisico(atrvc.get("examenFisico"));
                                    consulta.setMotivo(atrvc.get("motivo"));
                                    consulta.setObservaciones(atrvc.get("observaciones"));
                                    HashMap<Integer, Object> consultai = consulta.insertar(consulta);
                                    if (consultai.size() > 0) {
                                        Consulta primerConsulta = (Consulta) consultai.get(1);
                                        String[] tituloConsulta = primerConsulta.getAtributos();
                                        sc.enviar(tituloConsulta, consultai, destinatario, consulta.tituloInsertar(), consulta.sintaxisListar(), "Listar consultas");
                                    } else {
                                        System.out.println("ERROR NO SE REGISTRÓ ");
                                         sc.enviar(null, null, destinatario, null, null, "ERROR");
                                    }

                                    break;

                                case "reportes":

                                    HashMap<String, String> atrvr = a.convertAtributoValorHashMap(ic.getAtributos());
                                    for (Map.Entry<String, String> entry : atrvr.entrySet()) {
                                        System.out.print("atributo: " + entry.getKey() + "= ");
                                        System.out.println("valor: " + entry.getValue());

                                    }
                                    Reporte reporte = new Reporte();
                                    reporte.setFechaReporte(atrvr.get("fechaReporte"));
                                    reporte.setUrlDocumento(atrvr.get("urlDocumento"));
                                    reporte.setUserId(atrvr.get("userId"));

                                    HashMap<Integer, Object> reportei = reporte.insertar(reporte);
                                    if (reportei.size() > 0) {
                                        Reporte primerReporte = (Reporte) reportei.get(1);
                                        String[] tituloReporte = primerReporte.getAtributos();
                                        sc.enviar(tituloReporte, reportei, destinatario, reporte.tituloInsertar(), reporte.sintaxisListar(), "Listar Reportes");
                                    } else {
                                        System.out.println("ERROR NO SE REGISTRÓ ");
                                         sc.enviar(null, null, destinatario, null, null, "ERROR");
                                    }

                                    break;
                                case "historials":
                                    System.out.println("igresaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                                    HashMap<String, String> atrvh = a.convertAtributoValorHashMap(ic.getAtributos());
                                    for (Map.Entry<String, String> entry : atrvh.entrySet()) {
                                        System.out.print("atributo: " + entry.getKey() + "= ");
                                        System.out.println("valor: " + entry.getValue());

                                    }
                                    System.out.println("hasta aca bien");
                                    Historial historial = new Historial();
                                    historial.setNombrePaciente(atrvh.get("nombrePaciente"));
                                    historial.setPacienteId(atrvh.get("pacienteId"));
                                    HashMap<Integer, Object> historial1 = historial.insertar(historial);
                                    System.out.println("hasta aca tanbienbien");

                                    if (historial1.size() > 0) {
                                        Historial primerHistorial = (Historial) historial1.get(1);
                                        String[] tituloHistorial = primerHistorial.getAtributos();
                                        sc.enviar(tituloHistorial, historial1, destinatario, historial.tituloInsertar(), historial.sintaxisListar(), "Listar Historial");
                                    } else {
                                        System.out.println("ERROR NO SE REGISTRÓ ");
                                         sc.enviar(null, null, destinatario, null, null, "ERROR");
                                    }
                                    break;
                                case "citas":

                                    HashMap<String, String> atrvci = a.convertAtributoValorHashMap(ic.getAtributos());
                                    for (Map.Entry<String, String> entry : atrvci.entrySet()) {
                                        System.out.print("atributo: " + entry.getKey() + "= ");
                                        System.out.println("valor: " + entry.getValue());

                                    }
                                    Cita cita = new Cita();
                                    cita.setFechaCita(atrvci.get("fechaCita"));
                                    cita.setEstadoCita(atrvci.get("estadoCita"));
                                    cita.setCostoCita(atrvci.get("costoCita"));
                                    cita.setPacienteId(atrvci.get("pacienteId"));
                                    cita.setMedicoId(atrvci.get("medicoId"));
                                    HashMap<Integer, Object> cita1 = cita.insertar(cita);
                                    if (cita1.size() > 0) {
                                        Cita primerCita = (Cita) cita1.get(1);
                                        String[] tituloCital = primerCita.getAtributos();
                                        sc.enviar(tituloCital, cita1, destinatario, cita.tituloInsertar(), cita.sintaxisListar(), "Listar Cita");
                                    } else {
                                        System.out.println("ERROR NO SE REGISTRÓ ");
                                         sc.enviar(null, null, destinatario, null, null, "ERROR");
                                    }
                                    break;
                                default:
                                    throw new AssertionError();
                                    
                            }

                            break;
                        default:
                            throw new AssertionError();
                    }
                } else {
                    //Mensaje de error
                     sc.enviar(null, null, destinatario, null, null, "ERROR");
                    System.out.println("ERROR: accion: " + ic.getAccion() + ", tabla: " + ic.getTabla() + ", atributos: ");
                    if (ic.getAtributos() != null) {
                        for (int i = 0; i < ic.getAtributos().length; i++) {
                            System.out.print("," + ic.getAtributos()[i]);
                        }
                    } else {
                        System.out.println("SIN ATRIBUTOS");
                    }
                }
            }
        } catch (Exception e) {
             //sc.enviar(null, null, destinatario, null, null, "ERROR");
            e.printStackTrace();
        }
    }
}
