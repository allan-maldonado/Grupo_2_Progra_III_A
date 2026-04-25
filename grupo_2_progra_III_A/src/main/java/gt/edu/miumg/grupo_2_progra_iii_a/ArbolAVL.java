package biblioteca;


class NodoAVL {
    int     clave;
    Libro   libro;
    NodoAVL izq, der;
    int     altura;

    NodoAVL(Libro libro) {
        this.clave  = libro.codigoLibro;
        this.libro  = libro;
        this.altura = 1;
    }
}

public class ArbolAVL {

    private NodoAVL raiz;

   
    public int  rotaciones  = 0;
    public int  operaciones = 0;
    public long tiempoNs    = 0;

    public void resetMetricas() {
        rotaciones  = 0;
        operaciones = 0;
        tiempoNs    = 0;
    }

   
    private int altura(NodoAVL n) {
        return (n == null) ? 0 : n.altura;
    }

    private int balance(NodoAVL n) {
        return (n == null) ? 0 : altura(n.izq) - altura(n.der);
    }

    private void actualizarAltura(NodoAVL n) {
        n.altura = 1 + Math.max(altura(n.izq), altura(n.der));
    }

  
    private NodoAVL rotarDerecha(NodoAVL y) {
        NodoAVL x  = y.izq;
        NodoAVL T2 = x.der;
        x.der = y;
        y.izq = T2;
        actualizarAltura(y);
        actualizarAltura(x);
        rotaciones++;
        return x;
    }

   
    private NodoAVL rotarIzquierda(NodoAVL x) {
        NodoAVL y  = x.der;
        NodoAVL T2 = y.izq;
        y.izq = x;
        x.der = T2;
        actualizarAltura(x);
        actualizarAltura(y);
        rotaciones++;
        return y;
    }

    
    private NodoAVL balancear(NodoAVL nodo, int clave) {
        int b = balance(nodo);

        if (b > 1  && clave < nodo.izq.clave) return rotarDerecha(nodo);           
        if (b < -1 && clave > nodo.der.clave) return rotarIzquierda(nodo);         
        if (b > 1  && clave > nodo.izq.clave) {                                     
            nodo.izq = rotarIzquierda(nodo.izq);
            return rotarDerecha(nodo);
        }
        if (b < -1 && clave < nodo.der.clave) {                                    
            nodo.der = rotarDerecha(nodo.der);
            return rotarIzquierda(nodo);
        }
        return nodo;
    }

  
    public void insertar(Libro libro) {
        operaciones++;
        long t1 = System.nanoTime();
        raiz = insertarNodo(raiz, libro);
        tiempoNs += System.nanoTime() - t1;
    }

    private NodoAVL insertarNodo(NodoAVL nodo, Libro libro) {
        if (nodo == null) return new NodoAVL(libro);
        if      (libro.codigoLibro < nodo.clave) nodo.izq = insertarNodo(nodo.izq, libro);
        else if (libro.codigoLibro > nodo.clave) nodo.der = insertarNodo(nodo.der, libro);
        else { nodo.libro = libro; return nodo; }
        actualizarAltura(nodo);
        return balancear(nodo, libro.codigoLibro);
    }

    
    public Libro buscar(int clave) {
        operaciones++;
        long t1 = System.nanoTime();
        NodoAVL nodo = raiz;
        Libro resultado = null;
        while (nodo != null) {
            if      (clave == nodo.clave) { resultado = nodo.libro; break; }
            else if (clave  < nodo.clave) nodo = nodo.izq;
            else                          nodo = nodo.der;
        }
        tiempoNs = System.nanoTime() - t1;
        return resultado;
    }


    public void eliminar(int clave) {
        operaciones++;
        long t1 = System.nanoTime();
        raiz = eliminarNodo(raiz, clave);
        tiempoNs = System.nanoTime() - t1;
    }

    private NodoAVL eliminarNodo(NodoAVL nodo, int clave) {
        if (nodo == null) return null;
        if      (clave < nodo.clave) nodo.izq = eliminarNodo(nodo.izq, clave);
        else if (clave > nodo.clave) nodo.der = eliminarNodo(nodo.der, clave);
        else {
            if (nodo.izq == null) return nodo.der;
            if (nodo.der == null) return nodo.izq;
            NodoAVL sucesor = minimoNodo(nodo.der);
            nodo.clave = sucesor.clave;
            nodo.libro = sucesor.libro;
            nodo.der   = eliminarNodo(nodo.der, sucesor.clave);
        }
        actualizarAltura(nodo);
        return balancear(nodo, nodo.clave);
    }

    private NodoAVL minimoNodo(NodoAVL nodo) {
        while (nodo.izq != null) nodo = nodo.izq;
        return nodo;
    }

    public int alturaTotal() { return altura(raiz); }

   
    public void inorden() {
        System.out.println("\n  [ AVL - Libros en orden ]");
        inordenRec(raiz);
        System.out.println();
    }

    private void inordenRec(NodoAVL nodo) {
        if (nodo == null) return;
        inordenRec(nodo.izq);
        System.out.println("    " + nodo.libro);
        inordenRec(nodo.der);
    }
}