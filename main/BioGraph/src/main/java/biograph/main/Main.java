/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biograph.main;

import biograph.gui.VentanaPrincipal;

/**
 * Clase principal encargada de arrancar la aplicación BioGraph.
 */
public class Main {
    
    /**
     * Método principal de entrada de la aplicación.
     * Inicializa la interfaz gráfica de usuario en el hilo de eventos de Swing.
     * * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaPrincipal().setVisible(true);
            }
        });
    }
}