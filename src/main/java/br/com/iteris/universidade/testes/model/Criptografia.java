package br.com.iteris.universidade.testes.model;

import java.util.*;

public class Criptografia {
    private Criptografia() throws IllegalAccessException {
        throw new IllegalAccessException("Não pode instanciar essa classe.");
    }

    public static String criptografar(String entrada) {
        if (entrada == null || entrada.isBlank()  || entrada.length() < 4)
            throw new IllegalArgumentException("Entrada é nula, vazia ou muito pequena.");

        var primeira= "";
        final var entradaCaracteres = entrada.toCharArray();

        for (var caractere :entradaCaracteres) {
            var codigo = 0;

            if(Character.isLetter(caractere)) {
                codigo = caractere + 3;
            }else {
                codigo = caractere;
            }
            primeira += (char)codigo;
        }
        var primeiraArray = primeira.toCharArray();
        String segunda = new StringBuilder(new String(primeiraArray)).reverse().toString();

        int metade = (entrada.length()/ 2);

        String terceira = segunda.substring(0, metade);
        for (int i = metade; i < segunda.length(); i++) {
            int codigo = segunda.charAt(i) - 1;
            terceira += (char)codigo;
        }
        return terceira;
    }

//    public static String getASCIIfase1(Character c){
//        String s = c.toString();
//        var actualPosition = 0;
//
//        Map<Integer, String> map = new HashMap<>();
//        for (int i = 32; i <= 126; i++) {
//            map.put(i ,Character.toString((char) i));
//            if(i>64 && i<91 || i>96 && i<123) {
//                if (map.get(i).equals(s))actualPosition = i + 3;
//
//            } else {
//                if (map.get(i).equals(s))actualPosition = i;
//            }
//        }
//        return map.get(actualPosition);
//    }
//    public static String getASCIIfase3(Character c){
//        String s = c.toString();
//        var actualPosition = 0;
//
//        Map<Integer, String> map = new HashMap<>();
//        for (int i = 32; i <= 126; i++) {
//            map.put(i ,Character.toString((char) i));
//            if (map.get(i).equals(s)) actualPosition = i - 1;
//        }
//        System.out.println(map.get(actualPosition)+" "+ actualPosition);
//        return map.get(actualPosition);
//    }


}

