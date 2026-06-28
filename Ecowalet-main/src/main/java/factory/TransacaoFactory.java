package factory;

import model.Categoria;
import model.Despesa;
import model.Receita;
import model.Transacao;

import java.time.LocalDate;

/**
 * Padrão Factory: centraliza a criação de objetos Transacao.
 *
 * A interface (Controller) não precisa saber qual subclasse instanciar —
 * apenas passa os dados e o tipo, e a factory decide.
 *
 * OCP: para adicionar um novo tipo (ex: "Investimento"), basta criar a subclasse
 * e adicionar um novo 'case' aqui, sem alterar o GerenciadorFinancas.
 */
public class TransacaoFactory {

    /**
     * Cria e retorna uma instância de Transacao (Receita ou Despesa)
     * com base no tipo informado.
     *
     * @param tipo      "Receita" ou "Despesa"
     * @param descricao descrição da transação
     * @param valor     valor absoluto (sempre positivo)
     * @param data      data da transação
     * @param categoria categoria do enum Categoria
     * @return instância concreta de Transacao
     * @throws IllegalArgumentException se o tipo for desconhecido
     */
    public static Transacao criar(String tipo, String descricao, double valor,
                                  LocalDate data, Categoria categoria) {
        return switch (tipo) {
            case "Receita"  -> new Receita(descricao, valor, data, categoria);
            case "Despesa"  -> new Despesa(descricao, valor, data, categoria);
            // Para adicionar "Investimento": new Investimento(descricao, valor, data, categoria)
            default -> throw new IllegalArgumentException("Tipo de transação desconhecido: " + tipo);
        };
    }
}
