package br.com.iteris.universidade.testes.model;

public class AreaCirculo {

    private AreaCirculo() throws IllegalAccessException {
        throw new IllegalAccessException("Não pode instanciar essa classe.");
    }

    public static String calcula(final double raio) {
        if(raio<0) throw new IllegalArgumentException("Raio deve ser positivo.");
        else if(raio==0) return "0";

        final var raioCalculado = Math.PI * Math.pow(raio, 2);
        return String.format("%,.4f", raioCalculado);
    }
}
