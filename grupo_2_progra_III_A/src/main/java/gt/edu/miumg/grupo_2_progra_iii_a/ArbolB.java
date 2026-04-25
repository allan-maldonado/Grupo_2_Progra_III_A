package biblioteca;


class NodoB {
    int[]   claves = new int[4];
    Libro[] libros = new Libro[4];
    NodoB[] hijos  = new NodoB[5];
    int     numClaves = 0;
    boolean esHoja    = true;
}

public class ArbolB {

    private static final int M   = 5;
    private static final int MAX = M - 1; 
    private static final int MIN = M / 2; 
    private NodoB raiz;

   
    public int  divisiones       = 0;
    public int  fusiones         = 0;
    public int  redistribuciones = 0;
    public int  operaciones      = 0;
    public long tiempoNs         = 0;

    public ArbolB() { raiz = new NodoB(); }

    public void resetMetricas() {
        divisiones       = 0;
        fusiones         = 0;
        redistribuciones = 0;
        operaciones      = 0;
        tiempoNs         = 0;
    }

    
    public void insertar(Libro libro) {
        operaciones++;
        long t1 = System.nanoTime();

        if (raiz.numClaves == MAX) {
            NodoB nuevaRaiz = new NodoB();
            nuevaRaiz.esHoja   = false;
            nuevaRaiz.hijos[0] = raiz;
            dividirHijo(nuevaRaiz, 0, raiz);
            raiz = nuevaRaiz;
        }
        insertarNoLleno(raiz, libro);

        tiempoNs += System.nanoTime() - t1;
    }

    private void insertarNoLleno(NodoB nodo, Libro libro) {
        int i = nodo.numClaves - 1;
        if (nodo.esHoja) {
            while (i >= 0 && libro.codigoLibro < nodo.claves[i]) {
                nodo.claves[i + 1] = nodo.claves[i];
                nodo.libros[i + 1] = nodo.libros[i];
                i--;
            }
            nodo.claves[i + 1] = libro.codigoLibro;
            nodo.libros[i + 1] = libro;
            nodo.numClaves++;
        } else {
            while (i >= 0 && libro.codigoLibro < nodo.claves[i]) i--;
            i++;
            if (nodo.hijos[i].numClaves == MAX) {
                dividirHijo(nodo, i, nodo.hijos[i]);
                if (libro.codigoLibro > nodo.claves[i]) i++;
            }
            insertarNoLleno(nodo.hijos[i], libro);
        }
    }

    private void dividirHijo(NodoB padre, int i, NodoB hijoLleno) {
        divisiones++;
        NodoB nuevoHijo = new NodoB();
        nuevoHijo.esHoja    = hijoLleno.esHoja;
        nuevoHijo.numClaves = MAX - MIN;

        for (int j = 0; j < MAX - MIN; j++) {
            nuevoHijo.claves[j] = hijoLleno.claves[j + MIN];
            nuevoHijo.libros[j] = hijoLleno.libros[j + MIN];
        }
        if (!hijoLleno.esHoja) {
            for (int j = 0; j <= MAX - MIN; j++)
                nuevoHijo.hijos[j] = hijoLleno.hijos[j + MIN];
        }
        hijoLleno.numClaves = MIN - 1;

        for (int j = padre.numClaves; j > i; j--) {
            padre.hijos[j + 1] = padre.hijos[j];
            padre.claves[j]    = padre.claves[j - 1];
            padre.libros[j]    = padre.libros[j - 1];
        }
        padre.hijos[i + 1] = nuevoHijo;
        padre.claves[i]    = hijoLleno.claves[MIN - 1];
        padre.libros[i]    = hijoLleno.libros[MIN - 1];
        padre.numClaves++;
    }

    
    public Libro buscar(int clave) {
        operaciones++;
        long t1 = System.nanoTime();
        Libro resultado = buscarEnNodo(raiz, clave);
        tiempoNs = System.nanoTime() - t1;
        return resultado;
    }

    private Libro buscarEnNodo(NodoB nodo, int clave) {
        int i = 0;
        while (i < nodo.numClaves && clave > nodo.claves[i]) i++;
        if (i < nodo.numClaves && clave == nodo.claves[i]) return nodo.libros[i];
        if (nodo.esHoja) return null;
        return buscarEnNodo(nodo.hijos[i], clave);
    }

    
    public void eliminar(int clave) {
        operaciones++;
        long t1 = System.nanoTime();
        eliminarDeNodo(raiz, clave);
        if (raiz.numClaves == 0 && !raiz.esHoja) raiz = raiz.hijos[0];
        tiempoNs = System.nanoTime() - t1;
    }

    private void eliminarDeNodo(NodoB nodo, int clave) {
        int i = posicion(nodo, clave);
        if (i < nodo.numClaves && clave == nodo.claves[i]) {
            if (nodo.esHoja) eliminarDeHoja(nodo, i);
            else             eliminarDeInterior(nodo, i);
        } else {
            if (nodo.esHoja) return;
            boolean esUltimo = (i == nodo.numClaves);
            if (nodo.hijos[i].numClaves < MIN) {
                rellenarHijo(nodo, i);
                if (esUltimo && i > nodo.numClaves) i--;
            }
            eliminarDeNodo(nodo.hijos[i], clave);
        }
    }

    private int posicion(NodoB nodo, int clave) {
        int i = 0;
        while (i < nodo.numClaves && clave > nodo.claves[i]) i++;
        return i;
    }

    private void eliminarDeHoja(NodoB nodo, int i) {
        for (int j = i + 1; j < nodo.numClaves; j++) {
            nodo.claves[j - 1] = nodo.claves[j];
            nodo.libros[j - 1] = nodo.libros[j];
        }
        nodo.numClaves--;
    }

    private void eliminarDeInterior(NodoB nodo, int i) {
        int clave = nodo.claves[i];
        if (nodo.hijos[i].numClaves >= MIN) {
            Libro pred = obtenerPredecesor(nodo.hijos[i]);
            nodo.claves[i] = pred.codigoLibro;
            nodo.libros[i] = pred;
            eliminarDeNodo(nodo.hijos[i], pred.codigoLibro);
        } else if (nodo.hijos[i + 1].numClaves >= MIN) {
            Libro suc = obtenerSucesor(nodo.hijos[i + 1]);
            nodo.claves[i] = suc.codigoLibro;
            nodo.libros[i] = suc;
            eliminarDeNodo(nodo.hijos[i + 1], suc.codigoLibro);
        } else {
            fusionar(nodo, i);
            eliminarDeNodo(nodo.hijos[i], clave);
        }
    }

    private Libro obtenerPredecesor(NodoB nodo) {
        while (!nodo.esHoja) nodo = nodo.hijos[nodo.numClaves];
        return nodo.libros[nodo.numClaves - 1];
    }

    private Libro obtenerSucesor(NodoB nodo) {
        while (!nodo.esHoja) nodo = nodo.hijos[0];
        return nodo.libros[0];
    }

    private void rellenarHijo(NodoB padre, int i) {
        if (i > 0 && padre.hijos[i - 1].numClaves >= MIN) {
            prestaDeLaIzquierda(padre, i);
            redistribuciones++;
        } else if (i < padre.numClaves && padre.hijos[i + 1].numClaves >= MIN) {
            prestaDeLaDerecha(padre, i);
            redistribuciones++;
        } else {
            if (i < padre.numClaves) fusionar(padre, i);
            else                     fusionar(padre, i - 1);
        }
    }

    private void prestaDeLaIzquierda(NodoB padre, int i) {
        NodoB hijo    = padre.hijos[i];
        NodoB hermano = padre.hijos[i - 1];
        for (int j = hijo.numClaves - 1; j >= 0; j--) {
            hijo.claves[j + 1] = hijo.claves[j];
            hijo.libros[j + 1] = hijo.libros[j];
        }
        if (!hijo.esHoja)
            for (int j = hijo.numClaves; j >= 0; j--) hijo.hijos[j + 1] = hijo.hijos[j];
        hijo.claves[0] = padre.claves[i - 1];
        hijo.libros[0] = padre.libros[i - 1];
        if (!hijo.esHoja) hijo.hijos[0] = hermano.hijos[hermano.numClaves];
        padre.claves[i - 1] = hermano.claves[hermano.numClaves - 1];
        padre.libros[i - 1] = hermano.libros[hermano.numClaves - 1];
        hijo.numClaves++;
        hermano.numClaves--;
    }

    private void prestaDeLaDerecha(NodoB padre, int i) {
        NodoB hijo    = padre.hijos[i];
        NodoB hermano = padre.hijos[i + 1];
        hijo.claves[hijo.numClaves] = padre.claves[i];
        hijo.libros[hijo.numClaves] = padre.libros[i];
        if (!hijo.esHoja) hijo.hijos[hijo.numClaves + 1] = hermano.hijos[0];
        padre.claves[i] = hermano.claves[0];
        padre.libros[i] = hermano.libros[0];
        for (int j = 1; j < hermano.numClaves; j++) {
            hermano.claves[j - 1] = hermano.claves[j];
            hermano.libros[j - 1] = hermano.libros[j];
        }
        if (!hermano.esHoja)
            for (int j = 1; j <= hermano.numClaves; j++) hermano.hijos[j - 1] = hermano.hijos[j];
        hijo.numClaves++;
        hermano.numClaves--;
    }

    private void fusionar(NodoB padre, int i) {
        fusiones++;
        NodoB izq = padre.hijos[i];
        NodoB der = padre.hijos[i + 1];
        izq.claves[MIN - 1] = padre.claves[i];
        izq.libros[MIN - 1] = padre.libros[i];
        for (int j = 0; j < der.numClaves; j++) {
            izq.claves[j + MIN] = der.claves[j];
            izq.libros[j + MIN] = der.libros[j];
        }
        if (!der.esHoja)
            for (int j = 0; j <= der.numClaves; j++) izq.hijos[j + MIN] = der.hijos[j];
        for (int j = i + 1; j < padre.numClaves; j++) {
            padre.claves[j - 1] = padre.claves[j];
            padre.libros[j - 1] = padre.libros[j];
            padre.hijos[j]      = padre.hijos[j + 1];
        }
        izq.numClaves += der.numClaves + 1;
        padre.numClaves--;
    }

    
    public int alturaTotal() {
        int h = 0;
        NodoB n = raiz;
        while (!n.esHoja) { h++; n = n.hijos[0]; }
        return h + 1;
    }

    
    public void inorden() {
        System.out.println("\n  [ Arbol B - Libros en orden ]");
        inordenRec(raiz);
        System.out.println();
    }

    private void inordenRec(NodoB nodo) {
        if (nodo == null) return;
        for (int i = 0; i < nodo.numClaves; i++) {
            if (!nodo.esHoja) inordenRec(nodo.hijos[i]);
            System.out.println("    " + nodo.libros[i]);
        }
        if (!nodo.esHoja) inordenRec(nodo.hijos[nodo.numClaves]);
    }
}