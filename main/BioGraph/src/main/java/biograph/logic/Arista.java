/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biograph.logic;

/**
 * Clase que representa una conexión (arista) entre dos proteínas en la Red de Interacción (PPIN).
 * Almacena el vértice destino y el peso/costo de la interacción.
 */
public class Arista {
    private Vertice destino;
    private int peso;

    /**
     * Constructor para inicializar una nueva conexión.
     * * @param destino El vértice (proteína) al que se dirige la conexión.
     * @param peso El valor numérico que representa el costo o fuerza de la interacción.
     */
    public Arista(Vertice destino, int peso) {
        this.destino = destino;
        this.peso = peso;
    }

    /**
     * Obtiene el vértice destino de la arista.
     * * @return Objeto Vertice de destino.
     */
    public Vertice getDestino() {
        return destino;
    }

    /**
     * Obtiene el costo o peso de la interacción.
     * * @return Valor numérico del peso.
     */
    public int getPeso() {
        return peso;
    }
    
    /**
     * Retorna una representación en texto de la arista.
     * * @return Cadena con el formato de la conexión.
     */
    @Override
    public String toString() {
        return "--(" + peso + ")--> " + destino.getNombre();
    }
}