package model;

import java.time.LocalDate;

/**
 * Representa uma entrada de dinheiro (salário, freelance, etc.).
 * Herda de Transacao e implementa getValorParaSaldo() retornando valor positivo.
 */
public class Receita extends Transacao {

    public Receita(String descricao, double valor, LocalDate data, Categoria categoria) {
        super(descricao, valor, data, categoria);
    }

    /**
     * Receita contribui positivamente para o saldo.
     */
    @Override
    public double getValorParaSaldo() {
        return getValor();
    }

    @Override
    public String getTipo() {
        return "Receita";
    }
}
