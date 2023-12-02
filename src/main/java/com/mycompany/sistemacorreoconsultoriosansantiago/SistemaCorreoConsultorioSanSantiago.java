/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.mycompany.sistemacorreoconsultoriosansantiago;

import java.util.Scanner;
import sistemacorreoconsultoriosansantiago.Analizador.AnalizarSintaxis;
import sistemacorreoconsultoriosansantiago.Analizador.InformacionComando;
import sistemacorreoconsultoriosansantiago.manejadorcorreo.ManejadorCorreo;

/**
 *
 * @author Christian
 */
public class SistemaCorreoConsultorioSanSantiago {

    public static void main(String[] args) {
        ManejadorCorreo manejador = new ManejadorCorreo();
        manejador.iniciar();
    }
}
