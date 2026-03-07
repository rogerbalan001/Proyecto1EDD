/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biograph.logic;

/**
 * Estructura de Datos principal que modela la Red de Interacción Proteína-Proteína (PPIN).
 * Implementa un Grafo No Dirigido utilizando una Lista de Adyacencia y algoritmos de análisis.
 */
public class Grafo {
    private Lista<Vertice> vertices;

    /**
     * Constructor que inicializa el grafo vacío.
     */
    public Grafo() {
        this.vertices = new Lista<>();
    }

    /**
     * Busca una proteína en el grafo dado su nombre.
     * * @param nombre El nombre exacto de la proteína.
     * @return El objeto Vertice correspondiente, o null si no se encuentra.
     */
    public Vertice buscarVertice(String nombre) {
        for (int i = 0; i < vertices.size(); i++) {
            Vertice v = vertices.get(i);
            if (v.getNombre().equalsIgnoreCase(nombre)) {
                return v;
            }
        }
        return null;
    }

    /**
     * Inserta una nueva proteína en el grafo si no existe previamente.
     * * @param nombre El identificador de la nueva proteína.
     */
    public void insertarVertice(String nombre) {
        if (buscarVertice(nombre) == null) {
            vertices.add(new Vertice(nombre));
        }
    }

    /**
     * Establece una interacción bidireccional entre dos proteínas.
     * Crea los vértices automáticamente si no existen.
     * * @param origen Nombre de la primera proteína.
     * @param destino Nombre de la segunda proteína.
     * @param peso Costo de la interacción.
     */
    public void insertarArista(String origen, String destino, int peso) {
        Vertice vOrigen = buscarVertice(origen);
        Vertice vDestino = buscarVertice(destino);

        if (vOrigen == null) {
            insertarVertice(origen);
            vOrigen = buscarVertice(origen);
        }
        if (vDestino == null) {
            insertarVertice(destino);
            vDestino = buscarVertice(destino);
        }

        // Grafo no dirigido: Se conecta en ambos sentidos
        vOrigen.agregarArista(vDestino, peso);
        vDestino.agregarArista(vOrigen, peso);
    }
    
    /**
     * Elimina una proteína del grafo y limpia todas las conexiones hacia ella 
     * en el resto de los vértices para mantener la consistencia estructural.
     * * @param nombre Nombre de la proteína a eliminar.
     */
    public void eliminarVertice(String nombre){
        Vertice v = buscarVertice(nombre);
        if(v != null){
            // 1. Eliminar referencias en otros nodos
            for(int i = 0; i < vertices.size(); i++){
                vertices.get(i).eliminarAristaConDestino(v);
            }
            // 2. Eliminar el nodo
            vertices.remove(v);
        }
    }

    /**
     * Obtiene la lista completa de vértices en el grafo.
     * * @return Lista de vértices almacenados.
     */
    public Lista<Vertice> getVertices() {
        return vertices;
    }

    /**
     * Calcula la centralidad de grado para encontrar el "Hub" o Diana Terapéutica.
     * * @return La proteína (Vertice) con la mayor cantidad de interacciones.
     */
    public Vertice obtenerHub() {
        if (vertices.size() == 0) return null;
        
        Vertice hub = vertices.get(0);
        int maxConexiones = hub.getAdyacencia().size();
        
        for (int i = 1; i < vertices.size(); i++) {
            Vertice actual = vertices.get(i);
            if (actual.getAdyacencia().size() > maxConexiones) {
                hub = actual;
                maxConexiones = actual.getAdyacencia().size();
            }
        }
        return hub;
    }

    /**
     * Detecta todos los complejos proteicos (subgrafos conexos) en la red utilizando DFS.
     * * @return Una lista que contiene listas de vértices, agrupadas por complejo.
     */
    public Lista<Lista<Vertice>> obtenerComplejos() {
        Lista<Lista<Vertice>> complejos = new Lista<>();
        Lista<Vertice> visitados = new Lista<>();

        for (int i = 0; i < vertices.size(); i++) {
            Vertice v = vertices.get(i);
            if (!contieneVertice(visitados, v)) {
                Lista<Vertice> complejoActual = new Lista<>();
                dfs(v, visitados, complejoActual);
                complejos.add(complejoActual);
            }
        }
        return complejos;
    }

    /**
     * Algoritmo de Búsqueda en Profundidad (DFS) iterativo/recursivo auxiliar.
     * * @param v Vértice actual.
     * @param visitados Lista de nodos ya procesados.
     * @param complejoActual Agrupación actual del complejo proteico.
     */
    private void dfs(Vertice v, Lista<Vertice> visitados, Lista<Vertice> complejoActual) {
        visitados.add(v);
        complejoActual.add(v);
        Lista<Arista> ady = v.getAdyacencia();
        for (int i = 0; i < ady.size(); i++) {
            Vertice vecino = ady.get(i).getDestino();
            if (!contieneVertice(visitados, vecino)) {
                dfs(vecino, visitados, complejoActual);
            }
        }
    }

    /**
     * Verifica si un vértice específico se encuentra dentro de una lista proporcionada.
     * * @param lista Lista en donde se realizará la búsqueda.
     * @param v Vértice a buscar.
     * @return true si el vértice está en la lista, false en caso contrario.
     */
    private boolean contieneVertice(Lista<Vertice> lista, Vertice v) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getNombre().equals(v.getNombre())) return true;
        }
        return false;
    }

    /**
     * Implementa el algoritmo de Dijkstra para encontrar la ruta metabólica más corta
     * entre dos proteínas dadas.
     * * @param origen Proteína inicial de la ruta.
     * @param destino Proteína objetivo.
     * @return Una Lista ordenada con los vértices que componen la ruta óptima.
     */
    public Lista<Vertice> dijkstra(String origen, String destino) {
        Vertice vOrigen = buscarVertice(origen);
        Vertice vDestino = buscarVertice(destino);
        if (vOrigen == null || vDestino == null) return null;

        int n = vertices.size();
        int[] distancias = new int[n];
        Vertice[] previos = new Vertice[n];
        boolean[] visitados = new boolean[n];

        // Inicializar
        for (int i = 0; i < n; i++) {
            distancias[i] = Integer.MAX_VALUE;
            previos[i] = null;
            visitados[i] = false;
        }

        int indiceOrigen = obtenerIndice(vOrigen);
        distancias[indiceOrigen] = 0;

        for (int i = 0; i < n; i++) {
            int u = -1;
            int minPath = Integer.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                if (!visitados[j] && distancias[j] < minPath) {
                    minPath = distancias[j];
                    u = j;
                }
            }

            if (u == -1) break; 
            if (vertices.get(u).getNombre().equals(vDestino.getNombre())) break; 

            visitados[u] = true;
            Vertice verticeU = vertices.get(u);
            Lista<Arista> ady = verticeU.getAdyacencia();

            for (int j = 0; j < ady.size(); j++) {
                Vertice v = ady.get(j).getDestino();
                int peso = ady.get(j).getPeso();
                int indiceV = obtenerIndice(v);

                if (!visitados[indiceV] && distancias[u] != Integer.MAX_VALUE && distancias[u] + peso < distancias[indiceV]) {
                    distancias[indiceV] = distancias[u] + peso;
                    previos[indiceV] = verticeU;
                }
            }
        }

        Lista<Vertice> ruta = new Lista<>();
        int indiceActual = obtenerIndice(vDestino);
        if (distancias[indiceActual] == Integer.MAX_VALUE) return ruta; 

        Vertice actual = vDestino;
        while (actual != null) {
            ruta.add(actual);
            actual = previos[obtenerIndice(actual)];
        }
        return ruta;
    }

    /**
     * Obtiene el índice numérico de un vértice según su posición en la lista.
     * * @param v El vértice a localizar.
     * @return Índice entero de la posición, o -1 si no existe.
     */
    private int obtenerIndice(Vertice v) {
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).getNombre().equals(v.getNombre())) return i;
        }
        return -1;
    }
}