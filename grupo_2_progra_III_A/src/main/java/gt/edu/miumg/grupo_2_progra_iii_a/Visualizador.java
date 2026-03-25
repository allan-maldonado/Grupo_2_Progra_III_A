/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.miumg.grupo_2_progra_iii_a;

public class Visualizador {
    public static void mostrar(HojaCalculo hoja, int filas, int columnas) {
        System.out.print("      ");
        for (int c = 1; c <= columnas; c++) {
            System.out.printf("|   %c   ", (char) ('A' + c - 1));
        }
        System.out.println("|");
        
        for (int f = 1; f <= filas; f++) {
            System.out.printf("%4d  ", f);
            for (int c = 1; c <= columnas; c++) {
                Celda celda = hoja.buscarCelda(f, c);
                String txt = (celda != null) ? celda.contenido : "";
                if (txt.length() > 7) txt = txt.substring(0, 7);
                System.out.printf("| %-6s", txt);
            }
            System.out.println("|");
        }
    }
}