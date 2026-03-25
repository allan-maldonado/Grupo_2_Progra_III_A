/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.miumg.grupo_2_progra_iii_a;

/**
 *
 * @author Jayron
 */
public class GestorEnlaces {

    public static void enlazarHorizontalmente(Celda filaHeader, Celda nueva) {
        Celda ant = filaHeader;
        while (ant.derecha != null && ant.derecha.columna < nueva.columna) {
            ant = ant.derecha;
        }
        
        nueva.derecha = ant.derecha;
        if (ant.derecha != null) {
            ant.derecha.izquierda = nueva;
        }
        ant.derecha = nueva;
        nueva.izquierda = ant;
    }

    public static void enlazarVerticalmente(Celda colHeader, Celda nueva) {
        Celda ant = colHeader;
        while (ant.abajo != null && ant.abajo.fila < nueva.fila) {
            ant = ant.abajo;
        }
        
        nueva.abajo = ant.abajo;
        if (ant.abajo != null) {
            ant.abajo.arriba = nueva;
        }
        ant.abajo = nueva;
        nueva.arriba = ant;
    }
    
    public static void desconectarNodo(Celda objetivo) {
        if (objetivo.izquierda != null) objetivo.izquierda.derecha = objetivo.derecha;
        if (objetivo.derecha != null) objetivo.derecha.izquierda = objetivo.izquierda;
        if (objetivo.arriba != null) objetivo.arriba.abajo = objetivo.abajo;
        if (objetivo.abajo != null) objetivo.abajo.arriba = objetivo.arriba;
    }
}