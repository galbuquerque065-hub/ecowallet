package service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Transacao;

/**
 * Responsabilidade Única (SRP): esta classe faz APENAS duas coisas —
 *   1. Manter a lista de transações.
 *   2. Calcular o saldo total.
 *
 * Nada de interface gráfica, nada de persistência — cada responsabilidade
 * fica na sua camada.
 *
 * OCP: o método calcularSaldo() usa getValorParaSaldo() polimórfico,
 * portanto funciona com qualquer subclasse futura sem precisar de alteração.
 */
public class GerenciadorFinancas {

    private final ObservableList<Transacao> transacoes = FXCollections.observableArrayList();

    /** Adiciona uma transação à lista. */
    public void adicionarTransacao(Transacao t) {
        transacoes.add(t);
    }

    /** Remove uma transação da lista. */
    public void removerTransacao(Transacao t) {
        transacoes.remove(t);
    }

    /**
     * Soma todos os valores via polimorfismo.
     * Receita → positivo, Despesa → negativo.
     */
    public double calcularSaldo() {
        return transacoes.stream()
                .mapToDouble(Transacao::getValorParaSaldo)
                .sum();
    }

    /** Retorna a lista observável (usada diretamente pelo TableView). */
    public ObservableList<Transacao> getTransacoes() {
        return transacoes;
    }
}
