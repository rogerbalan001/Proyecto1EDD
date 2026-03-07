/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biograph.util;

import biograph.logic.Arista;
import biograph.logic.Grafo;
import biograph.logic.Lista;
import biograph.logic.Vertice;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 * Clase utilitaria encargada de la persistencia de datos.
 * Maneja la lectura del archivo plano inicial y la escritura para la actualización del repositorio.
 */
public class ManejadorArchivos {

    /**
     * Procesa un archivo de texto con estructura separada por comas y carga
     * los datos automáticamente en el Grafo proporcionado.
     * Maneja las excepciones para asegurar la tolerancia a fallos.
     * * @param archivo El archivo físico a leer seleccionado por el usuario.
     * @param grafo Estructura de datos donde se insertarán las proteínas e interacciones.
     */
    public void cargarArchivo(File archivo, Grafo grafo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;
                
                String[] partes = linea.split(",");
                if (partes.length == 3) {
                    String origen = partes[0].trim();
                    String destino = partes[1].trim();
                    try {
                        int peso = Integer.parseInt(partes[2].trim());
                        grafo.insertarArista(origen, destino, peso);
                    } catch (NumberFormatException e) {
                        System.out.println("Peso inválido en línea: " + linea);
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Grafo cargado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error leyendo archivo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Escribe el estado actual del Grafo en un archivo de texto para actualizar el repositorio.
     * Evita guardar relaciones duplicadas inherentes a los grafos no dirigidos.
     * * @param archivo El archivo destino donde se guardarán los datos.
     * @param grafo La estructura de datos que contiene el modelo actualizado a exportar.
     */
    public void guardarArchivo(File archivo, Grafo grafo) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            Lista<Vertice> vertices = grafo.getVertices();
            
            for (int i = 0; i < vertices.size(); i++) {
                Vertice v = vertices.get(i);
                Lista<Arista> adyacencia = v.getAdyacencia();
                
                for (int j = 0; j < adyacencia.size(); j++) {
                    Arista a = adyacencia.get(j);
                    // Como el grafo es no dirigido, solo guardamos si el nombre del origen es "menor" al del destino alfabéticamente
                    if (v.getNombre().compareTo(a.getDestino().getNombre()) < 0) {
                        pw.println(v.getNombre() + "," + a.getDestino().getNombre() + "," + a.getPeso());
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Repositorio actualizado correctamente.", "Guardado", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error guardando archivo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}