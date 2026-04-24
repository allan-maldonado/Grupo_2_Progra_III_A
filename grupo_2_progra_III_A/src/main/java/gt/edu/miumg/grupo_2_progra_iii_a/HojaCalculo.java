/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hojacalculo;



public class HojaCalculo {
    private Celda raiz;
    //Metodo Creacion de celdas
    public HojaCalculo() {
        raiz = new Celda(0, 0, "RAIZ");
    }
    //Metodo para insertar valores en las filas
    public void insertar(int fila, int columna, String valor) {
        if (valor == null || valor.isEmpty()) {
            eliminar(fila, columna);
            return;
        }

        Celda existente = buscarCelda(fila, columna);
        if (existente != null) {
            existente.contenido = valor;
            return;
        }

        crearYNuevoNodo(fila, columna, valor);
    }
    //Metodo para crear nuevos nodos o celdas para uso
    private void crearYNuevoNodo(int fila, int columna, String valor) {
        Celda cabFila = buscarOCrearCabeceraFila(fila);
        Celda cabCol = buscarOCrearCabeceraColumna(columna);
        Celda nueva = new Celda(fila, columna, valor);

        enlazarHorizontalmente(cabFila, nueva);
        enlazarVerticalmente(cabCol, nueva);
    }
    //Metodo para elimiacion de filas vacias o con datos.
    public void eliminar(int fila, int columna) {
        Celda objetivo = buscarCelda(fila, columna);
        if (objetivo == null) return;

        // Eliminacion de las listas enlazadas
        if (objetivo.izquierda != null) objetivo.izquierda.derecha = objetivo.derecha;
        if (objetivo.derecha != null) objetivo.derecha.izquierda = objetivo.izquierda;
        if (objetivo.arriba != null) objetivo.arriba.abajo = objetivo.abajo;
        if (objetivo.abajo != null) objetivo.abajo.arriba = objetivo.arriba;
    }
    //Metodo de busqueda de celdas.
    public Celda buscarCelda(int fila, int columna) {
        Celda filaAux = raiz;
        // Navegación vertical hasta la fila
        while (filaAux != null && filaAux.fila < fila) filaAux = filaAux.abajo;
        
        if (filaAux == null || filaAux.fila != fila) return null;

        // Navegación horizontal hasta la columna
        Celda actual = filaAux;
        while (actual != null && actual.columna < columna) actual = actual.derecha;
        
        return (actual != null && actual.columna == columna) ? actual : null;
    }

    // Metodo de enlace horizontal

    private void enlazarHorizontalmente(Celda filaHeader, Celda nueva) {
        Celda ant = filaHeader;
        while (ant.derecha != null && ant.derecha.columna < nueva.columna) ant = ant.derecha;
        
        nueva.derecha = ant.derecha;
        if (ant.derecha != null) ant.derecha.izquierda = nueva;
        ant.derecha = nueva;
        nueva.izquierda = ant;
    }
    private void enlazarVerticalmente(Celda colHeader, Celda nueva) {
        Celda ant = colHeader;
        while (ant.abajo != null && ant.abajo.fila < nueva.fila) ant = ant.abajo;
        
        nueva.abajo = ant.abajo;
        if (ant.abajo != null) ant.abajo.arriba = nueva;
        ant.abajo = nueva;
        nueva.arriba = ant;
    }
    //Metodo crear filas
    private Celda buscarOCrearCabeceraFila(int fila) {
        Celda aux = raiz;
        while (aux.abajo != null && aux.abajo.fila < fila) aux = aux.abajo;
        
        if (aux.abajo != null && aux.abajo.fila == fila) return aux.abajo;

        Celda nueva = new Celda(fila, 0, "H_FILA");
        nueva.abajo = aux.abajo;
        if (aux.abajo != null) aux.abajo.arriba = nueva;
        aux.abajo = nueva;
        nueva.arriba = aux;
        return nueva;
    }
    //Metodo Crear Columnas
    private Celda buscarOCrearCabeceraColumna(int columna) {
        Celda aux = raiz;
        while (aux.derecha != null && aux.derecha.columna < columna) aux = aux.derecha;
        
        if (aux.derecha != null && aux.derecha.columna == columna) return aux.derecha;

        Celda nueva = new Celda(0, columna, "H_COL");
        nueva.derecha = aux.derecha;
        if (aux.derecha != null) aux.derecha.izquierda = nueva;
        aux.derecha = nueva;
        nueva.izquierda = aux;
        return nueva;
    }
}