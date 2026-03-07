/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biograph.logic;

/**
 * Clase genérica que representa un nodo individual dentro de una estructura de datos enlazada (Lista).
 * * @param <T> El tipo de dato que almacenará el nodo.
 */
public class Nodo<T> {
    private T dato;
    private Nodo<T> siguiente;

    /**
     * Constructor de la clase Nodo.
     * * @param dato El elemento o valor que guardará el nodo.
     */
    public Nodo(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    /**
     * Obtiene el dato almacenado en el nodo.
     * * @return El dato de tipo T.
     */
    public T getDato() {
        return dato;
    }

    /**
     * Establece o actualiza el dato almacenado en el nodo.
     * * @param dato El nuevo dato a almacenar.
     */
    public void setDato(T dato) {
        this.dato = dato;
    }

    /**
     * Obtiene la referencia al siguiente nodo enlazado.
     * * @return El siguiente nodo, o null si es el último.
     */
    public Nodo<T> getSiguiente() {
        return siguiente;
    }

    /**
     * Establece la referencia al siguiente nodo.
     * * @param siguiente El nodo que se enlazará a continuación.
     */
    public void setSiguiente(Nodo<T> siguiente) {
        this.siguiente = siguiente;
    }
}
