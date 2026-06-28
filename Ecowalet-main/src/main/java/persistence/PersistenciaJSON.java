package persistence;

import factory.TransacaoFactory;
import model.Categoria;
import model.Transacao;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsável exclusivamente pela persistência em JSON (Unidade IV — I/O).
 * Lê e grava a lista de transações no arquivo "transacoes.json" na raiz do projeto.
 */
public class PersistenciaJSON {

    private static final String ARQUIVO = "transacoes.json";

    /**
     * Salva toda a lista de transações no arquivo JSON.
     * Chamado após cada adição ou remoção.
     */
    @SuppressWarnings("unchecked")
    public static void salvar(List<Transacao> lista) {
        JSONArray array = new JSONArray();

        for (Transacao t : lista) {
            JSONObject obj = new JSONObject();
            obj.put("tipo",      t.getTipo());
            obj.put("descricao", t.getDescricao());
            obj.put("valor",     t.getValor());
            obj.put("data",      t.getData().toString());          // ISO-8601: yyyy-MM-dd
            obj.put("categoria", t.getCategoria().name());         // enum name para desserializar
            array.add(obj);
        }

        try (FileWriter fw = new FileWriter(ARQUIVO)) {
            fw.write(array.toJSONString());
        } catch (IOException e) {
            System.err.println("[PersistenciaJSON] Erro ao salvar: " + e.getMessage());
        }
    }

    /**
     * Carrega e retorna a lista de transações do arquivo JSON.
     * Chamado no initialize() do Controller.
     * Retorna lista vazia se o arquivo não existir ou estiver corrompido.
     */
    public static List<Transacao> carregar() {
        List<Transacao> lista = new ArrayList<>();
        File arquivo = new File(ARQUIVO);

        if (!arquivo.exists()) return lista;

        try (FileReader fr = new FileReader(arquivo)) {
            JSONParser parser = new JSONParser();
            JSONArray array = (JSONArray) parser.parse(fr);

            for (Object o : array) {
                JSONObject obj = (JSONObject) o;

                String tipo      = (String) obj.get("tipo");
                String descricao = (String) obj.get("descricao");
                LocalDate data   = LocalDate.parse((String) obj.get("data"));
                Categoria cat    = Categoria.valueOf((String) obj.get("categoria"));

                // json-simple pode deserializar números como Long ou Double
                Object valorObj  = obj.get("valor");
                double valor = (valorObj instanceof Long)
                        ? ((Long) valorObj).doubleValue()
                        : (Double) valorObj;

                lista.add(TransacaoFactory.criar(tipo, descricao, valor, data, cat));
            }

        } catch (IOException | ParseException e) {
            System.err.println("[PersistenciaJSON] Erro ao carregar: " + e.getMessage());
        }

        return lista;
    }
}
