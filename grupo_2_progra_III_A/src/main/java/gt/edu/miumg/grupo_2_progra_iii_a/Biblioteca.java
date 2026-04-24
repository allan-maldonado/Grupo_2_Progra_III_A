package biblioteca;

import java.util.List;
import java.util.Scanner;

public class Biblioteca {

    private ArbolAVL avl    = new ArbolAVL();
    private ArbolB   arbolB = new ArbolB();
    private Scanner  sc     = new Scanner(System.in);
    private boolean  cargado = false;

    public void iniciar() {
        mostrarMenu();
    }

    private void mostrarMenu() {
        int op = 0;
        do {
            System.out.println("\n   ___________________________________");
            System.out.println("    |      BIBLIOTECA DIGITAL           |");
            System.out.println("    |-----------------------------------|");
            System.out.println("    |  1. Cargar libros desde BD        |");
            System.out.println("    |  2. Insertar libro                |");
            System.out.println("    |  3. Buscar libro                  |");
            System.out.println("    |  4. Eliminar libro                |");
            System.out.println("    |  5. Mostrar libros en orden       |");
            System.out.println("    |  6. Ver estadisticas              |");
            System.out.println("    |  7. Volver al menu principal      |");
            System.out.println("    |___________________________________|");
            System.out.print("  Seleccione una opcion: ");

            try { op = Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) { op = 0; }

            switch (op) {
                case 1: cargarDesdeBD();  break;
                case 2: insertar();       break;
                case 3: buscar();         break;
                case 4: eliminar();       break;
                case 5: mostrarEnOrden(); break;
                case 6: estadisticas();   break;
                case 7: System.out.println("  Volviendo al menu principal..."); break;
                default: System.out.println("  Opcion invalida.");
            }
        } while (op != 7);
    }

    // ── 1. Cargar desde BD ────────────────────────────────────────────────
    private void cargarDesdeBD() {
        System.out.println("\n  Cargando libros desde la base de datos...");
        avl    = new ArbolAVL();
        arbolB = new ArbolB();

        List<Libro> libros = Conexion.cargarLibros();

        if (libros.isEmpty()) {
            System.out.println("  La tabla esta vacia. Ejecute el archivo .sql primero.");
            return;
        }

        long t1 = System.nanoTime();
        for (Libro libro : libros) avl.insertar(libro);
        long t2 = System.nanoTime();
        for (Libro libro : libros) arbolB.insertar(libro);
        long t3 = System.nanoTime();

        cargado = true;
        System.out.println("\n  Carga completada: " + libros.size() + " libros.");
        System.out.printf("  Tiempo carga AVL   : %,d ns%n", t2 - t1);
        System.out.printf("  Tiempo carga ArbolB: %,d ns%n", t3 - t2);
        estadisticas();
    }

    // ── 2. Insertar libro ─────────────────────────────────────────────────
    private void insertar() {
        System.out.println("\n  -- INSERTAR LIBRO --");
        try {
            System.out.print("  Codigo : "); int    cod  = Integer.parseInt(sc.nextLine().trim());
            System.out.print("  ISBN   : "); String isbn = sc.nextLine().trim();
            System.out.print("  Titulo : "); String tit  = sc.nextLine().trim();
            System.out.print("  Autor  : "); String aut  = sc.nextLine().trim();
            System.out.print("  Anio   : "); int    anio = Integer.parseInt(sc.nextLine().trim());
            System.out.print("  Categ. : "); String cat  = sc.nextLine().trim();

            Libro libro = new Libro(cod, isbn, tit, aut, anio, cat);

            avl.resetMetricas();
            arbolB.resetMetricas();

            avl.insertar(libro);
            arbolB.insertar(libro);

            System.out.println("\n  Libro insertado en ambas estructuras.");
            mostrarComparacion("INSERCION");

        } catch (NumberFormatException e) {
            System.out.println("  Error: codigo y anio deben ser numericos.");
        }
    }

    // ── 3. Buscar libro ───────────────────────────────────────────────────
    private void buscar() {
        System.out.println("\n  -- BUSCAR LIBRO --");
        System.out.print("  Codigo del libro: ");
        try {
            int cod = Integer.parseInt(sc.nextLine().trim());

            avl.resetMetricas();
            arbolB.resetMetricas();

            Libro rAVL = avl.buscar(cod);
            Libro rB   = arbolB.buscar(cod);

            System.out.println("\n  Resultado AVL   : " + (rAVL != null ? rAVL : "No encontrado"));
            System.out.println("  Resultado ArbolB: " + (rB   != null ? rB   : "No encontrado"));
            mostrarComparacion("BUSQUEDA");

        } catch (NumberFormatException e) {
            System.out.println("  Error: ingrese un codigo numerico.");
        }
    }

    // ── 4. Eliminar libro ─────────────────────────────────────────────────
    private void eliminar() {
        System.out.println("\n  -- ELIMINAR LIBRO --");
        System.out.print("  Codigo del libro: ");
        try {
            int cod = Integer.parseInt(sc.nextLine().trim());

            avl.resetMetricas();
            arbolB.resetMetricas();

            avl.eliminar(cod);
            arbolB.eliminar(cod);

            System.out.println("\n  Eliminacion completada en ambas estructuras.");
            mostrarComparacion("ELIMINACION");

        } catch (NumberFormatException e) {
            System.out.println("  Error: ingrese un codigo numerico.");
        }
    }

    // ── 5. Mostrar en orden ───────────────────────────────────────────────
    private void mostrarEnOrden() {
        if (!cargado) { System.out.println("  Primero cargue los datos (opcion 1)."); return; }
        avl.inorden();
        arbolB.inorden();
    }

    // ── 6. Estadísticas generales ─────────────────────────────────────────
    private void estadisticas() {
        System.out.println("\n  ============ ESTADISTICAS ============");
        System.out.println("  [ Arbol AVL ]");
        System.out.println("    Altura              : " + avl.alturaTotal());
        System.out.println("    Rotaciones totales  : " + avl.rotaciones);
        System.out.println("    Operaciones totales : " + avl.operaciones);
        System.out.printf ("    Tiempo acumulado    : %,d ns%n", avl.tiempoNs);

        System.out.println("\n  [ Arbol B - M=5 ]");
        System.out.println("    Altura              : " + arbolB.alturaTotal());
        System.out.println("    Divisiones          : " + arbolB.divisiones);
        System.out.println("    Fusiones            : " + arbolB.fusiones);
        System.out.println("    Redistribuciones    : " + arbolB.redistribuciones);
        System.out.println("    Operaciones totales : " + arbolB.operaciones);
        System.out.printf ("    Tiempo acumulado    : %,d ns%n", arbolB.tiempoNs);
        System.out.println("  ======================================");
    }

    // ── Comparación por operación ─────────────────────────────────────────
    private void mostrarComparacion(String operacion) {
        System.out.println("\n  -- COMPARACION: " + operacion + " --");
        System.out.println("  Estructura  | Altura | Reestructuraciones | Tiempo (ns)");
        System.out.println("  ------------|--------|-------------------|------------");
        System.out.printf ("  AVL         |  %4d  |  Rotaciones: %3d   | %,d%n",
                avl.alturaTotal(), avl.rotaciones, avl.tiempoNs);
        System.out.printf ("  Arbol B     |  %4d  |  Div:%2d Fus:%2d Red:%2d | %,d%n",
                arbolB.alturaTotal(), arbolB.divisiones, arbolB.fusiones,
                arbolB.redistribuciones, arbolB.tiempoNs);
    }
}