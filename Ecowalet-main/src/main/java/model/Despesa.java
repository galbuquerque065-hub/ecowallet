package model;

import java.time.LocalDate;

/**
 * Representa uma saída de dinheiro (conta, compra, etc.).
 * Herda de Transacao e implementa getValorParaSaldo() retornando valor negativo.
 */
public class Despesa extends Transacao {

    public Despesa(String descricao, double valor, LocalDate data, Categoria categoria) {
        super(descricao, valor, data, categoria);
    }

    /**
     * Despesa subtrai do saldo (valor × -1).
     */
    @Override
    public double getValorParaSaldo() {
        return getValor() * -1;
    }

    @Override
    public String getTipo() {
        return "Despesa";
    }
}
