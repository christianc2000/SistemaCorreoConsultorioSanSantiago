/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemacorreoconsultoriosansantiago.Analizador;

import java.util.HashMap;

/**
 *
 * @author Christian
 */
public class AtributoValor {

    private String atributo;
    private String valor;

    public AtributoValor(String atr, String v) {
        this.atributo = atr;
        this.valor = v;
    }

    public AtributoValor() {
        this.atributo = null;
        this.valor = null;
    }

    public String getAtributo() {
        return atributo;
    }

    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public AtributoValor[] convertAtributoValor(String[] atributos) {
        AtributoValor[] a = new AtributoValor[atributos.length];
        for (int i = 0; i < atributos.length; i++) {
            String[] parts = atributos[i].split("=");
            System.out.println("atr: " + parts[0] + "; valor: " + parts[1]);
            a[i] = new AtributoValor(parts[0], parts[1]);
        }
        return a;
    }

    public HashMap<String, String> convertAtributoValorHashMap(String[] atributos) {
        HashMap<String, String> map = new HashMap<>();
        for (String atributo : atributos) {
            String[] parts = atributo.split("=");
            System.out.println("atr: " + parts[0] + "; valor: " + parts[1]);
            map.put(parts[0], parts[1]);
        }
        return map;
    }

    public String[] getAtributos(AtributoValor[] av) {
        String a[] = new String[av.length];
        for (int i = 0; i < av.length; i++) {
            System.out.println("atr sólo: " + av[i].getAtributo());
            a[i] = av[i].getAtributo().trim();
        }
        return a;
    }

}
