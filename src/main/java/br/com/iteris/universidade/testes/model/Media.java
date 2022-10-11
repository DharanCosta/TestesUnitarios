package br.com.iteris.universidade.testes.model;

public class Media {
    private Media() throws IllegalAccessException {
        throw new IllegalAccessException("Não pode instanciar essa classe.");
    }

    public static String simples(double n1, double n2) {
        if (n1 < 0 || n2 < 0) {
            throw new IllegalArgumentException("Os valores devem ser positivos.");
        }

        var media = (n1 + n2) / 2;

        return formatar(media);
    }

    public static String simples(double n1, double n2, double n3) {
        if (n1 < 0 || n2 < 0 || n3 < 0) {
            throw new IllegalArgumentException("Os valores devem ser positivos.");
        }

        var media = (n1 + n2 + n3) / 3;

        return formatar(media);
    }

    private static String formatar(double media) {
        return String.format("%,.1f", media);
    }
}
