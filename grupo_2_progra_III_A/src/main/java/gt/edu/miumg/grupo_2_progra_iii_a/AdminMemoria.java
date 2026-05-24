
package Manejo_de_Memoria;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author jayron
 */
public class AdminMemoria {

    private static void visualizarMemoria(ArrayList<Bloque> bloques) {
        System.out.println(" +--------+----------+----------+----------+");
        System.out.println(" | Bloque | Tamano   | Estado   | Proceso  |");
        System.out.println(" +--------+----------+----------+----------+");
        for (Bloque b : bloques) {
            String estado;
            if (b.ocupado) {
                estado = "OCUPADO";
            } else {
                estado = "LIBRE";
            }
            String proceso;
            if (b.proceso != null) {
                proceso = b.proceso;
            } else {
                proceso = "---";
            }
            System.out.printf(" | %2d | %4d KB | %-8s | %-8s |%n", b.id, b.tamano, estado, proceso);
        }
        System.out.println(" +--------+----------+----------+----------+");
    }

    private static void pausar(Scanner sc) {
        System.out.println("\nPresione ENTER para continuar...");
        sc.nextLine();
    }

    static class Bloque {

        int id;
        int tamano;
        boolean ocupado;
        String proceso;

        Bloque(int id, int tamano) {
            this.id = id;
            this.tamano = tamano;
            this.ocupado = false;
            this.proceso = null;
        }
    }

    public static void ejecutarPrimerAjuste(Scanner sc, int[] bloquesUsuario) {

        System.out.println("\n   |========================================| ");
        System.out.println("   |      PRIMER AJUSTE                     |    ");
        System.out.println("   |========================================| ");

        // MEMORIA DINAMICA
        ArrayList<Bloque> bloques = new ArrayList<>();

        // CREAR BLOQUES
        for (int i = 0; i < bloquesUsuario.length; i++) {

            Bloque nuevo = new Bloque(i + 1, bloquesUsuario[i]);

            // PREGUNTAR SI EL BLOQUE ESTARA OCUPADO
            System.out.print(
                    "\n  El Bloque "
                    + (i + 1)
                    + " estara ocupado? (si/no): "
            );

            String respuesta = sc.nextLine();

            if (respuesta.equalsIgnoreCase("si")) {

                nuevo.ocupado = true;

                System.out.print(
                        "Ingrese nombre del proceso: "
                );

                nuevo.proceso = sc.nextLine();
            }

            bloques.add(nuevo);
        }

        // MOSTRAR MEMORIA INICIAL
        System.out.println("\n  ─── MEMORIA INICIAL ───");

        visualizarMemoria(bloques);

        // INGRESAR PROCESOS
        System.out.print("\nIngrese cantidad de procesos: ");

        int cantidadProcesos = Integer.parseInt(sc.nextLine());

        String[] nombresProcesos = new String[cantidadProcesos];
        int[] tamanosProcesos = new int[cantidadProcesos];

        for (int i = 0; i < cantidadProcesos; i++) {

            nombresProcesos[i] = "P" + (i + 1);

            System.out.print("Ingrese memoria para "
                    + nombresProcesos[i] + ": ");

            tamanosProcesos[i] = Integer.parseInt(sc.nextLine());
        }

        // primer ajuste
        System.out.println("\n  ─── APLICANDO PRIMER AJUSTE ───");

        for (int p = 0; p < nombresProcesos.length; p++) {

            int restante = tamanosProcesos[p];

            System.out.println(
                    "\nAsignando "
                    + nombresProcesos[p]
                    + " (" + tamanosProcesos[p] + " KB)"
            );

            // ciclo bloques
            for (int i = 0; i < bloques.size(); i++) {

                Bloque b = bloques.get(i);

                // bloques libres
                if (!b.ocupado && restante > 0) {

                    // SI CABE EXACTO
                    if (b.tamano == restante) {

                        b.ocupado = true;
                        b.proceso = nombresProcesos[p];

                        System.out.println(
                                ">> "
                                + nombresProcesos[p]
                                + " uso Bloque "
                                + b.id
                                + " exacto -> "
                                + b.tamano
                                + " KB"
                        );

                        restante = 0;
                    } // si no se usa el bloque completo
                    else if (b.tamano > restante) {

                        int tamanoOriginal = b.tamano;

                        int sobrante = tamanoOriginal - restante;

                        System.out.println(
                                ">> "
                                + nombresProcesos[p]
                                + " uso parte del Bloque "
                                + b.id
                                + " -> "
                                + restante
                                + " KB"
                        );

                        // bloque en uso
                        b.tamano = restante;

                        b.ocupado = true;

                        b.proceso = nombresProcesos[p];

                        // seteando nuevo bloque libre
                        Bloque nuevoBloque = new Bloque(
                                0,
                                sobrante
                        );

                        // INSERTAR DESPUES
                        bloques.add(i + 1, nuevoBloque);

                        System.out.println(
                                ">> Nuevo bloque libre creado -> "
                                + sobrante
                                + " KB"
                        );

                        restante = 0;
                    } // SI NECESITA TODO EL BLOQUE
                    else {

                        restante -= b.tamano;

                        b.ocupado = true;

                        b.proceso = nombresProcesos[p];

                        System.out.println(
                                ">> "
                                + nombresProcesos[p]
                                + " uso Bloque "
                                + b.id
                                + " completo -> "
                                + b.tamano
                                + " KB"
                        );
                    }
                }
            }

            // RESULTADO FINAL
            if (restante > 0) {

                System.out.println(
                        ">> No se pudo completar la asignacion."
                );

                System.out.println(
                        ">> Faltaron "
                        + restante
                        + " KB"
                );

            } else {

                System.out.println(
                        ">> Proceso asignado correctamente."
                );
            }
        }

        // ordenamiento
        for (int i = 0; i < bloques.size(); i++) {

            bloques.get(i).id = i + 1;
        }

        // muestra la memoria al final
        System.out.println("\n ===== ESTADO DESPUES DE LA ASIGNACION ===");

        visualizarMemoria(bloques);

        pausar(sc);
    }
    
    // ══════════════════════════════════════════════════════════════
    //  2. MEJOR AJUSTE 
    // ══════════════════════════════════════════════════════════════
    public static void ejecutarMejorAjuste(Scanner sc, int[] bloquesUsuario) {

        System.out.println("\n   |========================================| ");
        System.out.println("   |        MEJOR AJUSTE                    |    ");
        System.out.println("   |========================================| ");

        // asignacion memoria dinamica
        ArrayList<Bloque> bloques = new ArrayList<>();

        // ciclo crea bloques
        for (int i = 0; i < bloquesUsuario.length; i++) {

            Bloque nuevo = new Bloque(i + 1, bloquesUsuario[i]);

            // consultando bloques libres o ocupados
            System.out.print(
                    "\n El Bloque "
                    + (i + 1)
                    + " estara ocupado? (si/no): "
            );

            String respuesta = sc.nextLine();

            if (respuesta.equalsIgnoreCase("si")) {

                nuevo.ocupado = true;

                System.out.print(
                        "Ingrese nombre del proceso: "
                );

                nuevo.proceso = sc.nextLine();
            }

            bloques.add(nuevo);
        }

        // pintado de bloques libres y usados
        System.out.println("\n  ─── MEMORIA INICIAL ───");

        visualizarMemoria(bloques);

        // ingreso nuevos procesos
        System.out.print("\nIngrese cantidad de procesos: ");

        int cantidadProcesos = Integer.parseInt(sc.nextLine());

        String[] nombresProcesos = new String[cantidadProcesos];
        int[] tamanosProcesos = new int[cantidadProcesos];

        for (int i = 0; i < cantidadProcesos; i++) {

            nombresProcesos[i] = "P" + (i + 1);

            System.out.print("Ingrese memoria para "
                    + nombresProcesos[i] + ": ");

            tamanosProcesos[i] = Integer.parseInt(sc.nextLine());
        }

        // mejor ajuste
        System.out.println("\n  === APLICANDO MEJOR AJUSTE ====");

        for (int p = 0; p < nombresProcesos.length; p++) {

            int restante = tamanosProcesos[p];

            System.out.println(
                    "\nAsignando "
                    + nombresProcesos[p]
                    + " (" + tamanosProcesos[p] + " KB)"
            );

            while (restante > 0) {

                int mejorIndice = -1;
                int menorBloque = Integer.MAX_VALUE;

                // busqueda bloque mas pequeño donde pueda alojarse
                for (int i = 0; i < bloques.size(); i++) {

                    Bloque b = bloques.get(i);

                    if (!b.ocupado
                            && b.tamano >= restante
                            && b.tamano < menorBloque) {

                        menorBloque = b.tamano;
                        mejorIndice = i;
                    }
                }

                // si no hay bloque disponible
                if (mejorIndice == -1) {

                    break;
                }

                Bloque b = bloques.get(mejorIndice);

                // tamaño exacto
                if (b.tamano == restante) {

                    b.ocupado = true;
                    b.proceso = nombresProcesos[p];

                    System.out.println(
                            ">> "
                            + nombresProcesos[p]
                            + " uso Bloque "
                            + b.id
                            + " exacto -> "
                            + b.tamano
                            + " KB"
                    );

                    restante = 0;
                } // uso de una porcion
                else if (b.tamano > restante) {

                    int tamanoOriginal = b.tamano;

                    int sobrante = tamanoOriginal - restante;

                    System.out.println(
                            ">> "
                            + nombresProcesos[p]
                            + " uso parte del Bloque "
                            + b.id
                            + " -> "
                            + restante
                            + " KB"
                    );

                    // bloque ocupado
                    b.tamano = restante;

                    b.ocupado = true;

                    b.proceso = nombresProcesos[p];

                    // asignando nuevo bloque libre
                    Bloque nuevoBloque = new Bloque(
                            0,
                            sobrante
                    );

                    bloques.add(mejorIndice + 1, nuevoBloque);

                    System.out.println(
                            ">> Nuevo bloque libre creado -> "
                            + sobrante
                            + " KB"
                    );

                    restante = 0;
                } // si uso todo el bloque
                else {

                    restante -= b.tamano;

                    b.ocupado = true;

                    b.proceso = nombresProcesos[p];

                    System.out.println(
                            ">> "
                            + nombresProcesos[p]
                            + " uso Bloque "
                            + b.id
                            + " completo -> "
                            + b.tamano
                            + " KB"
                    );
                }
            }

            // pintado final
            if (restante > 0) {

                System.out.println(
                        ">> No se pudo completar."
                );

            } else {

                System.out.println(
                        ">> Proceso asignado correctamente."
                );
            }
        }

        // ordenando por id
        for (int i = 0; i < bloques.size(); i++) {

            bloques.get(i).id = i + 1;
        }

        // memoria con nuevos procesos
        System.out.println("\n === ESTADO DESPUES DE LA ASIGNACION ===");

        visualizarMemoria(bloques);

        pausar(sc);
    }

    // ══════════════════════════════════════════════════════════════
    //  3. PEOR AJUSTE 
    // ══════════════════════════════════════════════════════════════
    public static void ejecutarPeorAjuste(Scanner sc, int[] bloquesUsuario) {

        System.out.println("\n   |========================================| ");
        System.out.println("   |            PEOR AJUSTE                 |    ");
        System.out.println("   |========================================| ");

        //asignacion de tamaños memoria
        ArrayList<Bloque> bloques = new ArrayList<>();

        // creacion de bloques
        for (int i = 0; i < bloquesUsuario.length; i++) {

            Bloque nuevo = new Bloque(i + 1, bloquesUsuario[i]);

            // pregunta si los bloques estaran ocupados
            System.out.print(
                    "\n  El Bloque "
                    + (i + 1)
                    + " estara ocupado? (si/no): "
            );

            String respuesta = sc.nextLine();

            if (respuesta.equalsIgnoreCase("si")) {

                nuevo.ocupado = true;

                System.out.print(
                        "Ingrese nombre del proceso: "
                );

                nuevo.proceso = sc.nextLine();
            }

            bloques.add(nuevo);
        }

        // mostrar primer estado memoria
        System.out.println("\n === MEMORIA INICIAL ===");

        visualizarMemoria(bloques);

        // ingreso procesos
        System.out.print("\nIngrese cantidad de procesos: ");

        int cantidadProcesos = Integer.parseInt(sc.nextLine());

        String[] nombresProcesos = new String[cantidadProcesos];
        int[] tamanosProcesos = new int[cantidadProcesos];

        for (int i = 0; i < cantidadProcesos; i++) {

            nombresProcesos[i] = "P" + (i + 1);

            System.out.print("Ingrese memoria para "
                    + nombresProcesos[i] + ": ");

            tamanosProcesos[i] = Integer.parseInt(sc.nextLine());
        }

        // peor ajuste
        System.out.println("\n === APLICANDO PEOR AJUSTE ===");

        for (int p = 0; p < nombresProcesos.length; p++) {

            int restante = tamanosProcesos[p];

            System.out.println(
                    "\nAsignando "
                    + nombresProcesos[p]
                    + " (" + tamanosProcesos[p] + " KB)"
            );

            while (restante > 0) {

                int peorIndice = -1;
                int mayorBloque = -1;

                // busqueda del bloque mas grande donde quepa
                for (int i = 0; i < bloques.size(); i++) {

                    Bloque b = bloques.get(i);

                    if (!b.ocupado
                            && b.tamano >= restante
                            && b.tamano > mayorBloque) {

                        mayorBloque = b.tamano;
                        peorIndice = i;
                    }
                }

                // si no hay disponibles
                if (peorIndice == -1) {

                    break;
                }

                Bloque b = bloques.get(peorIndice);

                // si cabe exacto
                if (b.tamano == restante) {

                    b.ocupado = true;
                    b.proceso = nombresProcesos[p];

                    System.out.println(
                            ">> "
                            + nombresProcesos[p]
                            + " uso Bloque "
                            + b.id
                            + " exacto -> "
                            + b.tamano
                            + " KB"
                    );

                    restante = 0;
                } // uso de una porcion
                else if (b.tamano > restante) {

                    int tamanoOriginal = b.tamano;

                    int sobrante = tamanoOriginal - restante;

                    System.out.println(
                            ">> "
                            + nombresProcesos[p]
                            + " uso parte del Bloque "
                            + b.id
                            + " -> "
                            + restante
                            + " KB"
                    );

                    // bloque ocupado
                    b.tamano = restante;

                    b.ocupado = true;

                    b.proceso = nombresProcesos[p];

                    // nuevo bloque libre
                    Bloque nuevoBloque = new Bloque(
                            0,
                            sobrante
                    );

                    // insertar despues
                    bloques.add(peorIndice + 1, nuevoBloque);

                    System.out.println(
                            ">> Nuevo bloque libre creado -> "
                            + sobrante
                            + " KB"
                    );

                    restante = 0;
                } // ocupa bloque completo
                else {

                    restante -= b.tamano;

                    b.ocupado = true;

                    b.proceso = nombresProcesos[p];

                    System.out.println(
                            ">> "
                            + nombresProcesos[p]
                            + " uso Bloque "
                            + b.id
                            + " completo -> "
                            + b.tamano
                            + " KB"
                    );
                }
            }

            // resultado
            if (restante > 0) {

                System.out.println(
                        ">> No se pudo completar."
                );

            } else {

                System.out.println(
                        ">> Proceso asignado correctamente."
                );
            }
        }

        // ordenamiento por id
        for (int i = 0; i < bloques.size(); i++) {

            bloques.get(i).id = i + 1;
        }

        // mostrar final
        System.out.println("\n  ===  ESTADO DESPUES DE LA ASIGNACION ===");

        visualizarMemoria(bloques);

        pausar(sc);
    }

}
