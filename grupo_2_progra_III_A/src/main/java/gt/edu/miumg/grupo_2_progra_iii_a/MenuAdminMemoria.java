package Manejo_de_Memoria;

import java.util.Scanner;

public class  MenuAdminMemoria {
    private int[] bloques;
    private final Scanner scanner;

    public MenuAdminMemoria(Scanner scanner) {
        this.scanner = scanner;
        configurarMemoria();
    }

    public MenuAdminMemoria() {
        this.scanner = new Scanner(System.in);
        configurarMemoria();
    }

    // CONFIGURAR MEMORIA
    private void configurarMemoria() {

        System.out.println("\n========== CONFIGURACION DE MEMORIA ==========");

        System.out.print("Ingrese la memoria total de la computadora: ");
        int memoriaTotal = Integer.parseInt(scanner.nextLine());

        System.out.print("Ingrese cuantos bloques tendra la memoria: ");
        int cantidadBloques = Integer.parseInt(scanner.nextLine());

        bloques = new int[cantidadBloques];

        int suma = 0;

        for (int i = 0; i < cantidadBloques; i++) {

            System.out.print("Ingrese el tamaño del bloque " + (i + 1) + ": ");
            bloques[i] = Integer.parseInt(scanner.nextLine());

            suma += bloques[i];
        }

        if (suma > memoriaTotal) {
            System.out.println("\nLa suma de bloques supera la memoria total.");
            System.out.println("Se ajustara automaticamente.\n");
        }

        System.out.println("\nMemoria configurada correctamente.");
    }

    public void mostrar() {

        int opcion = 0;

        do {

            System.out.println("\n   _________________________________");
            System.out.println("    |   ADMINISTRACION DE MEMORIA     |");
            System.out.println("    |---------------------------------|");
            System.out.println("    |  1. Primer Ajuste               |");
            System.out.println("    |  2. Mejor Ajuste                |");
            System.out.println("    |  3. Peor Ajuste                 |");
            System.out.println("    |  4. Volver al menu principal    |");
            System.out.println("    |_________________________________|");

            System.out.print("Seleccione una opcion: ");

            String entrada = scanner.nextLine().trim().toLowerCase();

            switch (entrada) {

                
                case "1":
                   AdminMemoria.ejecutarPrimerAjuste(scanner, bloques);
                    break;

               
                case "2":
                   AdminMemoria.ejecutarMejorAjuste(scanner, bloques);
                    break;

                case "3":
                   AdminMemoria.ejecutarPeorAjuste(scanner, bloques);
                    break;

                
                case "4":
                    System.out.println("Volviendo al menu principal...");
                    opcion = 4;
                    break;

                default:
                    System.out.println("Opcion invalida.");
            }

        } while (opcion != 4);
    }
    
}
