/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemacorreoconsultoriosansantiago.manejadorcorreo;

import java.util.HashMap;
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
            InformacionComando ic = analizador.analizarSintaxis(message); //null, null, null, accion,tabla,null]
            if (analizador.analizarTabla(ic)) {
                switch (ic.getAccion()) {

                    case "LIST":
                        SendCorreo sc = new SendCorreo();
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

                                sc.enviar(tituloUsuario, usuarios, destinatario);

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

                                sc.enviar(tituloConsulta, consultas, destinatario);
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

                                    sc.enviar(tituloReporte, reportes, destinatario);
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
