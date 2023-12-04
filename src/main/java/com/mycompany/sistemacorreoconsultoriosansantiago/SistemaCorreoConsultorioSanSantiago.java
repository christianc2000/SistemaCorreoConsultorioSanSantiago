package com.mycompany.sistemacorreoconsultoriosansantiago;

import sistemacorreoconsultoriosansantiago.manejadorcorreo.ManejadorPOP3;

/**
 *
 * @author Christian
 */
public class SistemaCorreoConsultorioSanSantiago {

    public static void main(String[] args) {
        //ManejadorCorreo manejador = new ManejadorCorreo();
        //manejador.iniciar();
        ManejadorPOP3 manejador = new ManejadorPOP3();
        manejador.iniciar();
    }
}
