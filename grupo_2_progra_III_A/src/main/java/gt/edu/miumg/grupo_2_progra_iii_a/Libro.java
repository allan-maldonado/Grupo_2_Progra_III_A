package biblioteca;

public class Libro {
    public int    codigoLibro;
    public String isbn;
    public String titulo;
    public String autor;
    public int    anio;
    public String categoria;

    public Libro(int codigoLibro, String isbn, String titulo, String autor, int anio, String categoria) {
        this.codigoLibro = codigoLibro;
        this.isbn        = isbn;
        this.titulo      = titulo;
        this.autor       = autor;
        this.anio        = anio;
        this.categoria   = categoria;
    }

    @Override
    public String toString() {
        return "[" + codigoLibro + "] " + titulo + " | " + autor + " | " + anio + " | " + categoria;
    }
}