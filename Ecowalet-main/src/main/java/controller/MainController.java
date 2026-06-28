package controller;

import factory.TransacaoFactory;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Categoria;
import model.Transacao;
import persistence.PersistenciaJSON;
import service.GerenciadorFinancas;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Controller MVC: faz a ponte entre a View (FXML) e o Model.
 *
 * Responsabilidades:
 *   - Capturar eventos de UI (cliques, inputs).
 *   - Validar entradas (NumberFormatException para valor).
 *   - Delegar lógica ao GerenciadorFinancas (SRP).
 *   - Manter o lblSaldo em sincronia com o Model via ListChangeListener (Binding).
 */
public class MainController {

    // ── Componentes FXML ──────────────────────────────────────────────────────

    @FXML private TextField               tfDescricao;
    @FXML private TextField               tfValor;
    @FXML private ComboBox<String>        cbTipo;
    @FXML private ComboBox<Categoria>     cbCategoria;
    @FXML private Button                  btnAdicionar;
    @FXML private Button                  btnRemover;
    @FXML private Label                   lblErro;
    @FXML private Label                   lblSaldo;

    @FXML private TableView<Transacao>               tableView;
    @FXML private TableColumn<Transacao, String>     colTipo;
    @FXML private TableColumn<Transacao, String>     colDescricao;
    @FXML private TableColumn<Transacao, String>     colValor;
    @FXML private TableColumn<Transacao, Categoria>  colCategoria;
    @FXML private TableColumn<Transacao, String>     colData;

    // ── Serviço ───────────────────────────────────────────────────────────────

    private final GerenciadorFinancas gerenciador = new GerenciadorFinancas();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // ── Inicialização ─────────────────────────────────────────────────────────

    @FXML
    public void initialize() {
        configurarComboBoxes();
        configurarColunas();
        configurarBinding();
        carregarDados();
    }

    private void configurarComboBoxes() {
        cbTipo.getItems().addAll("Receita", "Despesa");
        cbCategoria.getItems().addAll(Categoria.values());
    }

    private void configurarColunas() {
        colTipo.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getTipo()));

        colDescricao.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getDescricao()));

        // Valor formatado como moeda, colorido por tipo
        colValor.setCellValueFactory(data ->
                new SimpleStringProperty(String.format("R$ %.2f", data.getValue().getValor())));

        colValor.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    Transacao t = getTableView().getItems().get(getIndex());
                    setStyle(t.getTipo().equals("Receita")
                            ? "-fx-text-fill: #2d6a4f; -fx-font-weight: bold;"
                            : "-fx-text-fill: #e63946; -fx-font-weight: bold;");
                }
            }
        });

        colCategoria.setCellValueFactory(data ->
                new SimpleObjectProperty<>(data.getValue().getCategoria()));

        colData.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getData().format(FORMATTER)));

        tableView.setItems(gerenciador.getTransacoes());
    }

    /**
     * Binding: o lblSaldo é atualizado automaticamente sempre que a lista muda.
     * Equivale a "vincular a propriedade de texto à lógica do Model".
     */
    private void configurarBinding() {
        gerenciador.getTransacoes().addListener((ListChangeListener<Transacao>) change ->
                atualizarSaldo());
    }

    /** Carrega do JSON ao iniciar (initialize). */
    private void carregarDados() {
        PersistenciaJSON.carregar().forEach(gerenciador::adicionarTransacao);
        atualizarSaldo();
    }

    // ── Handlers de Evento ────────────────────────────────────────────────────

    @FXML
    private void handleAdicionar() {
        lblErro.setText("");

        String descricao = tfDescricao.getText().trim();
        String tipo      = cbTipo.getValue();
        Categoria cat    = cbCategoria.getValue();
        String valorStr  = tfValor.getText().trim();

        // Validação de campos vazios
        if (descricao.isEmpty() || tipo == null || cat == null || valorStr.isEmpty()) {
            lblErro.setText("⚠ Preencha todos os campos antes de adicionar.");
            return;
        }

        // Validação de formato numérico — trata NumberFormatException
        double valor;
        try {
            valor = Double.parseDouble(valorStr.replace(",", "."));
            if (valor <= 0) throw new NumberFormatException("Valor deve ser positivo.");
        } catch (NumberFormatException e) {
            lblErro.setText("⚠ Valor inválido. Digite apenas números positivos (ex: 150,00).");
            return;
        }

        Transacao t = TransacaoFactory.criar(tipo, descricao, valor, LocalDate.now(), cat);
        gerenciador.adicionarTransacao(t);
        PersistenciaJSON.salvar(gerenciador.getTransacoes());   // persiste imediatamente

        limparFormulario();
    }

    @FXML
    private void handleRemover() {
        lblErro.setText("");
        Transacao selecionada = tableView.getSelectionModel().getSelectedItem();

        if (selecionada == null) {
            lblErro.setText("⚠ Selecione uma transação na tabela para remover.");
            return;
        }

        gerenciador.removerTransacao(selecionada);
        PersistenciaJSON.salvar(gerenciador.getTransacoes());   // persiste imediatamente
    }

    // ── Auxiliares ────────────────────────────────────────────────────────────

    private void atualizarSaldo() {
        double saldo = gerenciador.calcularSaldo();
        lblSaldo.setText(String.format("R$ %.2f", saldo));
        lblSaldo.setStyle(saldo >= 0
                ? "-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2d6a4f;"
                : "-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #e63946;");
    }

    private void limparFormulario() {
        tfDescricao.clear();
        tfValor.clear();
        cbTipo.setValue(null);
        cbCategoria.setValue(null);
    }
}
