package hojacalculo;

import java.util.Scanner;

// Menu interactivo para la hoja de calculo
public class MenuHojaCalculo {

    private HojaCalculo hoja;
    private Evaluador evaluador;
    private Scanner scanner;

    public MenuHojaCalculo() {
        this.hoja      = new HojaCalculo();
        this.evaluador = new Evaluador(hoja);
        this.scanner   = new Scanner(System.in);
    }

    public void mostrar() {
        int opcion = 0;

        do {
            System.out.println("\n   ___________________________________");
            System.out.println("    |           HOJA DE CALCULO         |");
            System.out.println("    |-----------------------------------|");
            System.out.println("    |  1. Insertar valor en celda       |");
            System.out.println("    |  2. Eliminar celda                |");
            System.out.println("    |  3. Buscar celda                  |");
            System.out.println("    |  4. Ejecutar formula              |");
            System.out.println("    |  5. Visualizar hoja               |");
            System.out.println("    |  6. Volver al menu principal      |");
            System.out.println("    |___________________________________|");
            System.out.print("  Seleccione una opcion: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  Opcion invalida. Intente de nuevo.");
                continue;
            }

            switch (opcion) {
                case 1: insertarCelda();   break;
                case 2: eliminarCelda();   break;
                case 3: buscarCelda();     break;
                case 4: ejecutarFormula(); break;
                case 5: visualizarHoja();  break;
                case 6: System.out.println("  Volviendo al menu principal..."); break;
                default: System.out.println("  Opcion invalida. Intente de nuevo.");
            }

        } while (opcion != 6);
    }

    // ─── Insertar valor en celda ───────────────────────────────────────────
    private void insertarCelda() {
        System.out.println("\n  -- INSERTAR CELDA --");
        try {
            System.out.print("  Fila    : ");
            int fila = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("  Columna : ");
            int columna = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("  Valor   : ");
            String valor = scanner.nextLine().trim();

            hoja.insertar(fila, columna, valor);
            System.out.println("  Celda insertada correctamente.");

        } catch (NumberFormatException e) {
            System.out.println("  Error: fila y columna deben ser numericos.");
        }
    }

    // ─── Eliminar celda ────────────────────────────────────────────────────
    private void eliminarCelda() {
        System.out.println("\n  -- ELIMINAR CELDA --");
        try {
            System.out.print("  Fila    : ");
            int fila = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("  Columna : ");
            int columna = Integer.parseInt(scanner.nextLine().trim());

            hoja.eliminar(fila, columna);
            System.out.println("  Celda eliminada correctamente.");

        } catch (NumberFormatException e) {
            System.out.println("  Error: fila y columna deben ser numericos.");
        }
    }

    // ─── Buscar celda ──────────────────────────────────────────────────────
    private void buscarCelda() {
        System.out.println("\n  -- BUSCAR CELDA --");
        try {
            System.out.print("  Fila    : ");
            int fila = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("  Columna : ");
            int columna = Integer.parseInt(scanner.nextLine().trim());

            Celda celda = hoja.buscarCelda(fila, columna);
            if (celda != null) {
                System.out.println("  Contenido: " + celda.contenido);
            } else {
                System.out.println("  La celda esta vacia o no existe.");
            }

        } catch (NumberFormatException e) {
            System.out.println("  Error: fila y columna deben ser numericos.");
        }
    }

    // ─── Ejecutar formula ──────────────────────────────────────────────────
    private void ejecutarFormula() {
        System.out.println("\n  -- EJECUTAR FORMULA --");
        System.out.println("  Ejemplos: =A1+B2  |  =SUMAR(A1:B4)  |  =MULTIPLICAR(A1:B1)");
        System.out.print("  Ingrese la formula: ");
        String formula = scanner.nextLine().trim();

        try {
            double resultado = evaluador.procesar(formula);
            System.out.println("  Resultado: " + resultado);
        } catch (Exception e) {
            System.out.println("  Error al procesar la formula. Verifique el formato.");
        }
    }

    // ─── Visualizar hoja ───────────────────────────────────────────────────
    private void visualizarHoja() {
        System.out.println("\n  -- ESTADO DE LA HOJA --");
        System.out.print("  Numero de filas a mostrar    : ");
        try {
            int filas = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("  Numero de columnas a mostrar : ");
            int columnas = Integer.parseInt(scanner.nextLine().trim());
            Visualizador.mostrar(hoja, filas, columnas);
        } catch (NumberFormatException e) {
            System.out.println("  Error: ingrese valores numericos.");
        }
    }
}