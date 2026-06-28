package model;

import java.time.LocalDate;

/**
 * Classe abstrata que representa uma transação financeira genérica.
 * Não pode ser instanciada diretamente — apenas via subclasses (Receita, Despesa).
 *
 * Pilares aplicados:
 *   - Abstração: define contrato para todas as transações.
 *   - Encapsulamento: atributos private com getters/setters.
 *   - Polimorfismo: getValorParaSaldo() é abstrato e implementado diferente em cada subclasse.
 */
public abstract class Transacao {

    private String descricao;
    private double valor;
    private LocalDate data;
    private Categoria categoria;

    public Transacao(String descricao, double valor, LocalDate data, Categoria categoria) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.categoria = categoria;
    }

    /**
     * Retorna o valor que esta transação contribui para o saldo.
     * Receita → positivo | Despesa → negativo.
     */
    public abstract double getValorParaSaldo();

    /**
     * Retorna o tipo textual da transação ("Receita" ou "Despesa").
     * Usado na persistência e na TableView.
     */
    public abstract String getTipo();

    // ── Getters e Setters ──────────────────────────────────────────────────────

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
}
