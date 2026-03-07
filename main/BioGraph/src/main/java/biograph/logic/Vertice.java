/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biograph.logic;

/**
 * Clase que representa una Proteína (Vértice o Nodo) dentro del Grafo.
 * Mantiene su propio nombre identificador y una lista de sus conexiones (aristas) con otras proteínas.
 */
public class Vertice {
    private String nombre;
    private Lista<Arista> adyacencia;

    /**
     * Constructor del vértice.
     * * @param nombre Identificador de la proteína (Ej. "P1").
     */
    public Vertice(String nombre) {
        this.nombre = nombre;
        this.adyacencia = new Lista<>();
    }

    /**
     * Obtiene el nombre o identificador de la proteína.
     * * @return El nombre del vértice.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Agrega una nueva arista (conexión) hacia otro vértice destino.
     * Evita la duplicidad de conexiones hacia el mismo destino.
     * * @param destino El vértice con el que interactúa.
     * @param peso El costo de la interacción.
     */
    public void agregarArista(Vertice destino, int peso) {
        // Validación simple para no repetir aristas al mismo destino
        for(int i=0; i < adyacencia.size(); i++){
            if(adyacencia.get(i).getDestino().getNombre().equals(destino.getNombre())){
                return; // Ya existe
            }
        }
        adyacencia.add(new Arista(destino, peso));
    }
    
    /**
     * Elimina una conexión específica dada la proteína destino.
     * * @param destino El vértice destino de la conexión que se desea borrar.
     */
    public void eliminarAristaConDestino(Vertice destino){
        Arista aEliminar = null;
        for(int i=0; i < adyacencia.size(); i++){
            if(adyacencia.get(i).getDestino().equals(destino)){
                aEliminar = adyacencia.get(i);
                break;
            }
        }
        if(aEliminar != null){
            adyacencia.remove(aEliminar);
        }
    }

    /**
     * Obtiene la lista de adyacencia que contiene todas las conexiones de esta proteína.
     * * @return Lista de objetos Arista.
     */
    public Lista<Arista> getAdyacencia() {
        return adyacencia;
    }
    
    /**
     * Compara si dos vértices son iguales basándose en su nombre.
     * * @param obj El objeto a comparar.
     * @return true si tienen el mismo nombre, false en caso contrario.
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Vertice){
            return this.nombre.equals(((Vertice)obj).nombre);
        }
        return false;
    }
    
    /**
     * Retorna el nombre del vértice.
     * * @return Identificador de la proteína.
     */
    @Override
    public String toString() {
        return nombre;
    }
}