package model;

/**
 * Enum que representa as categorias possíveis de uma transação financeira.
 * Seguindo o OCP: novos tipos de categoria podem ser adicionados aqui
 * sem alterar nenhuma outra classe do sistema.
 */
public enum Categoria {
    ALIMENTACAO("Alimentação"),
    TRANSPORTE("Transporte"),
    SAUDE("Saúde"),
    EDUCACAO("Educação"),
    LAZER("Lazer"),
    SALARIO("Salário"),
    INVESTIMENTO("Investimento"),
    MORADIA("Moradia"),
    OUTROS("Outros");

    private final String descricao;

    Categoria(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
