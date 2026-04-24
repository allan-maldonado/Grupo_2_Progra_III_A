package gt.edu.miumg.grupo_2_progra_iii_a;

import biblioteca.Biblioteca;
import hojacalculo.MenuHojaCalculo;
import java.util.Scanner;

public class Grupo_2_Progra_III_A {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        do {
            System.out.println("\n|--------------------------------------|");
            System.out.println("  |     SISTEMA GRUPO 2 - PROGRA III     |");
            System.out.println("  |--------------------------------------|");
            System.out.println("  |  1. Hoja de Calculo                  |");
            System.out.println("  |  2. Arboles - Biblioteca Digital     |");
            System.out.println("  |  3. En Desarrollo                    |");
            System.out.println("  |  4. Salir                            |");
            System.out.println("  |--------------------------------------|");
            System.out.print("  Seleccione una opcion: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  Opcion invalida. Intente de nuevo.");
                continue;
            }

            switch (opcion) {
                case 1:
                    new MenuHojaCalculo().mostrar();
                    break;
                case 2:
                    new Biblioteca().iniciar();
                    break;
                case 3:
                    System.out.println("\n  Esta opcion esta en desarrollo.");
                    break;
                case 4:
                    System.out.println("\n  Saliendo del sistema. Hasta luego!");
                    break;
                default:
                    System.out.println("  Opcion invalida. Intente de nuevo.");
            }

        } while (opcion != 4);

        scanner.close();
    }
}