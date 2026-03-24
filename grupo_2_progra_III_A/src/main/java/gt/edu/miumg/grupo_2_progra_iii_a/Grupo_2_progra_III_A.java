/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package gt.edu.miumg.grupo_2_progra_iii_a;

/**
 *
 * @author Tecno
 */
public class Grupo_2_progra_III_A {

   public static void main(String[] args) {
        HojaCalculo miHoja = new HojaCalculo();
        Evaluador motor = new Evaluador(miHoja);

        //Inserción de datos
        miHoja.insertar(1, 1, "50");   
        miHoja.insertar(2, 1, "100");  
        miHoja.insertar(3, 1, "200");  
        miHoja.insertar(1, 2, "10");
        miHoja.insertar(5, 5, "8"); 
        miHoja.insertar(5, 10, "8"); 

        // Pruebas de cálculo
        System.out.println("--- PRUEBAS DE CALCULO ---");
        System.out.println("Suma Simple (A1+A2): " + motor.procesar("=A1+A2"));
        System.out.println("Rango (SUMAR(A1:B4)): " + motor.procesar("=SUMAR(A1:B4)"));
        System.out.println("Multiplicacion Rango: " + motor.procesar("=MULTIPLICAR(A1:B1)"));

        // 3. Modificación y Visualización
        System.out.println("\n--- ESTADO DE LA HOJA ---");
        Visualizador.mostrar(miHoja, 10, 10);

        // 4. Eliminación
        System.out.println("\nEliminando A2...");
        miHoja.eliminar(2, 1);
        Visualizador.mostrar(miHoja, 10, 10);
        System.out.println("Nueva suma A1:B4: " + motor.procesar("=SUMAR(A1:B4)"));
    }
}
