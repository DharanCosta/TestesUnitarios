package br.com.iteris.universidade.testes.model;

public class Salario {
    private Salario() throws IllegalAccessException {
        throw new IllegalAccessException("Não pode instanciar essa classe.");
    }

    public static String comBonus(double salario, double vendas) {

        if(salario<500) throw new IllegalArgumentException("Salário deve ser válido, acima do piso de 500.");
        if(vendas<0) throw new IllegalArgumentException("Vendas não podem ser negativas.");

        if(vendas == 0){
            return formatar(salario);
        }
        var comBonus =  salario + (vendas * 0.15);
        return formatar(comBonus);
    }

    private static String formatar(double input) {
        return String.format("%,.2f", input);
    }
}
