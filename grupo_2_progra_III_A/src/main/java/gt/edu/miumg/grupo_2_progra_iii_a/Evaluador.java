/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hojacalculo;



public class Evaluador {
    private HojaCalculo hoja;

    public Evaluador(HojaCalculo hoja) {
        this.hoja = hoja;
    }

    public double procesar(String entrada) {
        if (entrada == null) return 0;
        if (!entrada.startsWith("=")) return extraerNumero(entrada);

        String formula = entrada.substring(1).toUpperCase();

        if (formula.startsWith("SUMAR(")) return ejecutarOperacionRango(formula, "SUMAR");
        if (formula.startsWith("MULTIPLICAR(")) return ejecutarOperacionRango(formula, "MULTIPLICAR");

        return evaluarExpresionSimple(formula);
    }

    private double ejecutarOperacionRango(String formula, String tipo) {
        String rango = formula.substring(formula.indexOf("(") + 1, formula.indexOf(")"));
        String[] partes = rango.split(":");
        
        int fIni = extraerFila(partes[0]), cIni = extraerCol(partes[0]);
        int fFin = extraerFila(partes[1]), cFin = extraerCol(partes[1]);

        double resultado = tipo.equals("MULTIPLICAR") ? 1 : 0;

        for (int i = fIni; i <= fFin; i++) {
            for (int j = cIni; j <= cFin; j++) {
                Celda c = hoja.buscarCelda(i, j);
                double valor = (c != null) ? extraerNumero(c.contenido) : 0;
                
                if (tipo.equals("SUMAR")) resultado += valor;
                else if (tipo.equals("MULTIPLICAR")) resultado *= valor;
            }
        }
        return resultado;
    }

    private double evaluarExpresionSimple(String exp) {
        String[] tokens = exp.split("(?=[+\\-*/])|(?<=[+\\-*/])");
        double acumulado = obtenerValorDeRef(tokens[0]);

        for (int i = 1; i < tokens.length; i += 2) {
            String op = tokens[i];
            double sig = obtenerValorDeRef(tokens[i+1]);
            acumulado = calcular(acumulado, sig, op);
        }
        return acumulado;
    }

    private double calcular(double a, double b, String op) {
        switch (op) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/": return (b != 0) ? a / b : 0;
            default: return a;
        }
    }

    private double obtenerValorDeRef(String ref) {
        if (Character.isLetter(ref.charAt(0))) {
            Celda c = hoja.buscarCelda(extraerFila(ref), extraerCol(ref));
            return (c != null) ? extraerNumero(c.contenido) : 0;
        }
        return extraerNumero(ref);
    }

    private int extraerCol(String ref) { return ref.charAt(0) - 'A' + 1; }
    private int extraerFila(String ref) { return Integer.parseInt(ref.substring(1)); }
    private double extraerNumero(String s) {
        try { return Double.parseDouble(s); } catch (Exception e) { return 0; }
    }
}