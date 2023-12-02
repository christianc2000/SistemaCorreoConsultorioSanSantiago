/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemacorreoconsultoriosansantiago.Analizador;

/**
 *
 * @author Christian
 */
public class InformacionComando {
    private String accion;
    private String tabla;
    private String[] atributos;

    public InformacionComando(String accion, String tabla, String[] atributos) {
        this.accion = accion;
        this.tabla = tabla;
        this.atributos = atributos;
    }

    public void setAccion(String accion){
        this.accion=accion;
    }
    
    public void setTabla(String tabla){
        this.tabla=tabla;
    }
    
    public void setAtributos(String[] atributos){
        this.atributos=atributos;
    }
    
    public String getAccion() {
        return accion;
    }

    public String getTabla() {
        return tabla;
    }

    public String[] getAtributos() {
        return atributos;
    }
}
