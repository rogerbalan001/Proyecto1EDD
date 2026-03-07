/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biograph.logic;

/**
 * Clase genérica que implementa una Lista Simplemente Enlazada construida desde cero.
 * Esta clase sustituye el uso de librerías nativas como ArrayList, cumpliendo con las restricciones del proyecto.
 * * @param <T> El tipo de elementos que contendrá la lista.
 */
public class Lista<T> {
    private Nodo<T> pFirst;
    private int size;

    /**
     * Constructor que inicializa una lista vacía.
     */
    public Lista() {
        this.pFirst = null;
        this.size = 0;
    }

    /**
     * Agrega un nuevo elemento al final de la lista.
     * * @param dato El elemento a insertar.
     */
    public void add(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (isEmpty()) {
            pFirst = nuevo;
        } else {
            Nodo<T> aux = pFirst;
            while (aux.getSiguiente() != null) {
                aux = aux.getSiguiente();
            }
            aux.setSiguiente(nuevo);
        }
        size++;
    }

    /**
     * Obtiene un elemento de la lista dada su posición (índice).
     * * @param index La posición del elemento a obtener (inicia en 0).
     * @return El elemento en el índice indicado, o null si el índice es inválido.
     */
    public T get(int index) {
        if (index < 0 || index >= size) return null;
        Nodo<T> aux = pFirst;
        for (int i = 0; i < index; i++) {
            aux = aux.getSiguiente();
        }
        return aux.getDato();
    }

    /**
     * Elimina la primera ocurrencia de un dato específico en la lista.
     * * @param dato El elemento a eliminar.
     */
    public void remove(T dato) {
        if (isEmpty()) return;

        if (pFirst.getDato().equals(dato)) {
            pFirst = pFirst.getSiguiente();
            size--;
            return;
        }

        Nodo<T> aux = pFirst;
        while (aux.getSiguiente() != null) {
            if (aux.getSiguiente().getDato().equals(dato)) {
                aux.setSiguiente(aux.getSiguiente().getSiguiente());
                size--;
                return;
            }
            aux = aux.getSiguiente();
        }
    }

    /**
     * Verifica si la lista se encuentra vacía.
     * * @return true si la lista no contiene elementos, false en caso contrario.
     */
    public boolean isEmpty() {
        return pFirst == null;
    }

    /**
     * Obtiene la cantidad de elementos almacenados en la lista.
     * * @return El tamaño actual de la lista.
     */
    public int size() {
        return size;
    }
}