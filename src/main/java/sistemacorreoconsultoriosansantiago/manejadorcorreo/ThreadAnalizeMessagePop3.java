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
import sistemaconsultoriosansantiago.datos.Consulta;
import sistemaconsultoriosansantiago.datos.Reporte;
import sistemaconsultoriosansantiago.datos.Servicio;
import sistemaconsultoriosansantiago.datos.Turno;
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
            if (analizador.analizarTabla(ic)) {
                SendCorreo sc = new SendCorreo();
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

                                sc.enviar(tituloUsuario, usuarios, destinatario, user.tituloListar());

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

                                sc.enviar(tituloConsulta, consultas, destinatario, consulta.tituloListar());
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

                                    sc.enviar(tituloReporte, reportes, destinatario, reporte.tituloListar());
                                } else {
                                    System.out.println("ENVIAR ERROR QUE ESTÁ VACÍO");
                                }

                                break;

                            case "turnos":
                                Turno turno = new Turno();
                                //System.out.println("atributos: " + ic.getAtributos()[0]);
                                HashMap<Integer, Object> turnos = turno.listar(ic.getAtributos());
                                if (turnos.size() > 0) {
                                    Turno primerTurno = (Turno) turnos.get(1);
                                    String[] tituloTurno = primerTurno.getAtributos();

                                    for (int i = 0; i < tituloTurno.length; i++) {
                                        System.out.println("titulo " + i + ": " + tituloTurno[i]);
                                    }

                                    sc.enviar(tituloTurno, turnos, destinatario);
                                } else {
                                    System.out.println("ENVIAR ERROR QUE ESTÁ VACÍO");
                                }

                                break;

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

                                    sc.enviar(tituloServicio, servicios, destinatario);
                                } else {
                                    System.out.println("ENVIAR ERROR QUE ESTÁ VACÍO");
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
                                    sc.enviar(tituloUser, useri, destinatario, user.tituloInsertar());
                                } else {
                                    System.out.println("ERROR NO SE REGISTRÓ ");
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
                                    sc.enviar(tituloConsulta, consultai, destinatario, consulta.tituloInsertar());
                                } else {
                                    System.out.println("ERROR NO SE REGISTRÓ ");
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
                                    sc.enviar(tituloReporte, reportei, destinatario, reporte.tituloInsertar());
                                } else {
                                    System.out.println("ERROR NO SE REGISTRÓ ");
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
                System.out.println("ERROR: accion: " + ic.getAccion() + ", tabla: " + ic.getTabla() + ", atributos: ");
                if (ic.getAtributos() != null) {
                    for (int i = 0; i < ic.getAtributos().length; i++) {
                        System.out.print("," + ic.getAtributos()[i]);
                    }
                } else {
                    System.out.println("SIN ATRIBUTOS");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
