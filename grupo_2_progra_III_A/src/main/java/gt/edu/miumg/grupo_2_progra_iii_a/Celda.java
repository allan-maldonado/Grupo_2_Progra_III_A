
package hojacalculo;


public class Celda {
    public int fila;
    public int columna;
    public String contenido;
    
    // Punteros para la red ortogonal
    public Celda arriba, abajo, izquierda, derecha;

    public Celda(int fila, int columna, String contenido) {
        this.fila = fila;
        this.columna = columna;
        this.contenido = contenido;
        this.arriba = this.abajo = this.izquierda = this.derecha = null;
    }
}