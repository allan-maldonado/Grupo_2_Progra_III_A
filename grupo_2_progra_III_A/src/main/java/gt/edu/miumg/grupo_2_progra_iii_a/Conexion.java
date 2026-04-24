package biblioteca;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Conexion {

    private static final String URL     = "jdbc:sqlserver://localhost;databaseName=Biblioteca;encrypt=true;trustServerCertificate=true";
    private static final String USUARIO = "daniel_1";
    private static final String CLAVE   = "Admin1234";

    public static List<Libro> cargarLibros() {
        List<Libro> lista = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(URL, USUARIO, CLAVE);
             Statement  st  = con.createStatement();
             ResultSet  rs  = st.executeQuery("SELECT * FROM libro")) {

            while (rs.next()) {
                lista.add(new Libro(
                    rs.getInt("codigoLibro"),
                    rs.getString("isbn"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getInt("anio"),
                    rs.getString("categoria")
                ));
            }
            System.out.println("  " + lista.size() + " libros cargados desde SQL Server.");

        } catch (SQLException e) {
            System.out.println("  Error de conexion: " + e.getMessage());
        }
        return lista;
    }
}