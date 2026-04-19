package ao.bfa.solidario.ui.secoes;

import ao.bfa.solidario.model.Candidatura;
import javafx.scene.layout.Pane;

/**
 * Interface que TODOS os painéis de secção devem implementar.
 * Cada pessoa (1,3,4,5) cria a sua classe SecaoXPane implementando esta interface.
 *
 * Contrato:
 *  - getPane()          → retorna o nó JavaFX a exibir na área central
 *  - getNome()          → nome curto da secção (ex: "B - Contactos")
 *  - validar()          → valida os campos; retorna null se OK, senão mensagem de erro
 *  - preencherDados()   → lê os campos e preenche a Candidatura partilhada
 *  - carregarDados()    → carrega os dados da Candidatura nos campos visuais
 */
public interface Secao {

    /** Retorna o painel JavaFX desta secção */
    Pane getPane();

    /** Nome de exibição na navegação lateral / aba */
    String getNome();

    /**
     * Valida os campos obrigatórios e formatos.
     * @return null se tudo OK; string com a mensagem de erro caso contrário
     */
    String validar();

    /**
     * Lê os valores dos campos e actualiza o objecto Candidatura fornecido.
     * @param candidatura objecto partilhado a actualizar
     */
    void preencherDados(Candidatura candidatura);

    /**
     * Preenche os campos visuais a partir dos dados da Candidatura.
     * Usado ao abrir um ficheiro existente.
     * @param candidatura objecto com os dados a exibir
     */
    void carregarDados(Candidatura candidatura);
}
