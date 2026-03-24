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
        miHoja.insertar(1, 1, "452");
        miHoja.insertar(1, 2, "819");
        miHoja.insertar(1, 3, "92");
        miHoja.insertar(1, 4, "674");
        miHoja.insertar(2, 1, "135");
        miHoja.insertar(2, 2, "903");
        miHoja.insertar(2, 3, "218");
        miHoja.insertar(2, 4, "541");
        miHoja.insertar(3, 1, "327");
        miHoja.insertar(3, 2, "766");
        miHoja.insertar(3, 3, "48");
        miHoja.insertar(3, 4, "891");
        miHoja.insertar(4, 1, "254");
        miHoja.insertar(4, 2, "612");
        miHoja.insertar(4, 3, "107");
        miHoja.insertar(4, 4, "984");
        miHoja.insertar(5, 1, "333");
        miHoja.insertar(5, 2, "421");
        miHoja.insertar(5, 3, "559");
        miHoja.insertar(5, 4, "124");
        miHoja.insertar(6, 1, "730");
        miHoja.insertar(6, 2, "847");
        miHoja.insertar(6, 3, "299");
        miHoja.insertar(6, 4, "605");
        miHoja.insertar(7, 1, "182");
        miHoja.insertar(7, 2, "940");
        miHoja.insertar(7, 3, "516");
        miHoja.insertar(7, 4, "772");
        miHoja.insertar(8, 1, "368");
        miHoja.insertar(8, 2, "491");

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
