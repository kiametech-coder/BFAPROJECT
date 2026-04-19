package ao.bfa.solidario;

import ao.bfa.solidario.model.Candidatura;
import ao.bfa.solidario.service.GerenciadorArquivo;
import ao.bfa.solidario.ui.FormularioPane;
import ao.bfa.solidario.ui.PaginaInicialPane;
// ============================================================
// IMPORTS DAS SECÇÕES — descomente quando integrar cada uma:
// ============================================================
// import ao.bfa.solidario.ui.secoes.SecaoAPane;  // Pessoa 3
// import ao.bfa.solidario.ui.secoes.SecaoBPane;  // Pessoa 3
// import ao.bfa.solidario.ui.secoes.SecaoCPane;  // Pessoa 3
// import ao.bfa.solidario.ui.secoes.SecaoDPane;  // Pessoa 4
// import ao.bfa.solidario.ui.secoes.SecaoEPane;  // Pessoa 4
// import ao.bfa.solidario.ui.secoes.SecaoHPane;  // Pessoa 4
// import ao.bfa.solidario.ui.secoes.SecaoIPane;  // Pessoa 4
// import ao.bfa.solidario.ui.secoes.SecaoFPane;  // Pessoa 5
// import ao.bfa.solidario.ui.secoes.SecaoGPane;  // Pessoa 5
// import ao.bfa.solidario.ui.secoes.SecaoJPane;  // Pessoa 5

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * =============================================================================
 *  MAIN APP — Ponto de entrada da aplicação JavaFX
 *  Responsável: Pessoa 2
 * =============================================================================
 *  Estrutura da janela principal:
 *    ┌──────────────────────────────────────┐
 *    │  MenuBar (Ficheiro | Ajuda)          │
 *    ├──────────────────────────────────────┤
 *    │  StackPane  ← troca entre:          │
 *    │    • PaginaInicialPane (tabela)      │
 *    │    • FormularioPane   (edição)       │
 *    └──────────────────────────────────────┘
 *
 *  COMO INTEGRAR AS SECÇÕES (leia isto antes de começar):
 *  -------------------------------------------------------
 *  1. Crie a vossa classe (ex: SecaoBPane) no pacote ui.secoes
 *  2. A classe deve implementar a interface Secao
 *  3. Descomente o import correspondente acima
 *  4. No método registarSecoes(), descomente a linha correspondente
 *  5. Compile e execute — o painel aparece automaticamente na lista lateral
 * =============================================================================
 */
public class MainApp extends Application {

    // -------------------------------------------------------------------------
    // Estado global
    // -------------------------------------------------------------------------
    private Stage primaryStage;
    private Scene scene;

    private final GerenciadorArquivo gerenciador = new GerenciadorArquivo();
    private PaginaInicialPane paginaInicial;
    private FormularioPane formulario;

    private StackPane rootStack;     // troca entre páginas
    private BorderPane mainLayout;   // layout com menu
    private int indiceEdicaoActual = -1; // -1 = nova candidatura

    // -------------------------------------------------------------------------
    // JavaFX start
    // -------------------------------------------------------------------------

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        stage.setTitle("BFA Solidário — Sistema de Candidaturas");
        stage.setMinWidth(1000);
        stage.setMinHeight(680);

        // Construir layout principal
        mainLayout = new BorderPane();
        mainLayout.setTop(criarMenuBar());

        rootStack = new StackPane();
        mainLayout.setCenter(rootStack);

        // Criar as duas "páginas"
        paginaInicial = new PaginaInicialPane(gerenciador);
        formulario = new FormularioPane();

        // Configurar callbacks da página inicial
        paginaInicial.setOnNovaCandidatura(this::abrirNovaCandiatura);
        paginaInicial.setOnAbrirCandidatura(this::abrirCandidaturaExistente);

        // Configurar callbacks do formulário
        formulario.setOnGuardar(this::guardar);
        formulario.setOnGuardarComo(this::guardarComo);
        formulario.setOnVoltar(this::voltarParaPaginaInicial);

        // Registar secções das outras pessoas
        registarSecoes();

        // Mostrar página inicial
        mostrarPaginaInicial();

        scene = new Scene(mainLayout, 1200, 750);
        stage.setScene(scene);
        stage.show();
    }

    // -------------------------------------------------------------------------
    // REGISTAR SECÇÕES — Cada pessoa descomenta a sua linha aqui
    // -------------------------------------------------------------------------

    /**
     * Regista os painéis de cada secção no formulário.
     *
     * Quando a vossa secção estiver pronta:
     *   1. Crie a classe no pacote ui.secoes implementando Secao
     *   2. Descomente o import no topo do ficheiro
     *   3. Descomente a linha correspondente abaixo
     *   4. Apague ou comente o placeholder correspondente em FormularioPane.registarPlaceholders()
     */
    private void registarSecoes() {
        // --- Pessoa 3 ---
        // formulario.registarSecao(new SecaoAPane());
        // formulario.registarSecao(new SecaoBPane());
        // formulario.registarSecao(new SecaoCPane());

        // --- Pessoa 4 ---
        // formulario.registarSecao(new SecaoDPane());
        // formulario.registarSecao(new SecaoEPane());
        // formulario.registarSecao(new SecaoHPane());
        // formulario.registarSecao(new SecaoIPane());

        // --- Pessoa 5 ---
        // formulario.registarSecao(new SecaoFPane());
        // formulario.registarSecao(new SecaoGPane());
        // formulario.registarSecao(new SecaoJPane());
    }

    // -------------------------------------------------------------------------
    // Navegação entre páginas
    // -------------------------------------------------------------------------

    private void mostrarPaginaInicial() {
        paginaInicial.recarregarTabela();
        rootStack.getChildren().setAll(paginaInicial);
    }

    private void mostrarFormulario() {
        rootStack.getChildren().setAll(formulario);
    }

    private void voltarParaPaginaInicial() {
        indiceEdicaoActual = -1;
        mostrarPaginaInicial();
    }

    // -------------------------------------------------------------------------
    // Lógica de candidaturas
    // -------------------------------------------------------------------------

    private void abrirNovaCandiatura() {
        if (!gerenciador.temArquivoDefinido()) {
            // Pedir onde guardar antes de criar
            FileChooser fc = criarFileChooser("Guardar Nova Candidatura", true);
            File f = fc.showSaveDialog(primaryStage);
            if (f == null) return;
            gerenciador.setArquivo(f);
        }
        indiceEdicaoActual = -1;
        Candidatura nova = new Candidatura();
        formulario.setCandidatura(nova);
        mostrarFormulario();
    }

    private void abrirCandidaturaExistente(int indice) {
        try {
            List<Candidatura> lista = gerenciador.listarCandidaturas();
            if (indice < 0 || indice >= lista.size()) return;
            indiceEdicaoActual = indice;
            formulario.setCandidatura(lista.get(indice));
            mostrarFormulario();
        } catch (IOException e) {
            mostrarErro("Erro ao abrir candidatura", e.getMessage());
        }
    }

    // -------------------------------------------------------------------------
    // Menu Bar
    // -------------------------------------------------------------------------

    private MenuBar criarMenuBar() {
        MenuBar menuBar = new MenuBar();
        menuBar.setStyle("-fx-font-size: 13px;");

        // ---- Menu Ficheiro ----
        Menu mFicheiro = new Menu("Ficheiro");

        MenuItem miNovo = new MenuItem("Nova Candidatura");
        miNovo.setOnAction(e -> abrirNovaCandiatura());

        MenuItem miAbrir = new MenuItem("Abrir Ficheiro...");
        miAbrir.setOnAction(e -> abrirFicheiro());

        MenuItem miGuardar = new MenuItem("Guardar");
        miGuardar.setOnAction(e -> { formulario.recolherDados(); guardar(); });

        MenuItem miGuardarComo = new MenuItem("Guardar Como...");
        miGuardarComo.setOnAction(e -> guardarComo());

        SeparatorMenuItem sep = new SeparatorMenuItem();

        MenuItem miSair = new MenuItem("Sair");
        miSair.setOnAction(e -> {
            Alert conf = new Alert(Alert.AlertType.CONFIRMATION,
                    "Tem a certeza que deseja sair?", ButtonType.YES, ButtonType.NO);
            conf.setTitle("Sair");
            conf.showAndWait().ifPresent(bt -> {
                if (bt == ButtonType.YES) Platform.exit();
            });
        });

        mFicheiro.getItems().addAll(miNovo, miAbrir, new SeparatorMenuItem(),
                miGuardar, miGuardarComo, sep, miSair);

        // ---- Menu Ajuda ----
        Menu mAjuda = new Menu("Ajuda");

        MenuItem miSobre = new MenuItem("Sobre");
        miSobre.setOnAction(e -> {
            Alert sobre = new Alert(Alert.AlertType.INFORMATION,
                    "BFA Solidário — Sistema de Candidaturas\n"
                    + "5ª Edição\n\n"
                    + "Desenvolvido em JavaFX\n"
                    + "Banco de Fomento Angola, S.A.",
                    ButtonType.OK);
            sobre.setTitle("Sobre");
            sobre.setHeaderText("BFA Solidário v1.0");
            sobre.showAndWait();
        });

        mAjuda.getItems().add(miSobre);
        menuBar.getMenus().addAll(mFicheiro, mAjuda);
        return menuBar;
    }

    // -------------------------------------------------------------------------
    // Operações de ficheiro
    // -------------------------------------------------------------------------

    private void abrirFicheiro() {
        FileChooser fc = criarFileChooser("Abrir Ficheiro de Candidaturas", false);
        File f = fc.showOpenDialog(primaryStage);
        if (f == null) return;
        gerenciador.setArquivo(f);
        mostrarPaginaInicial();
    }

    private void guardar() {
        if (!gerenciador.temArquivoDefinido()) { guardarComo(); return; }
        try {
            Candidatura c = formulario.getCandidaturaActual();
            if (c == null) return;

            if (indiceEdicaoActual == -1) {
                // Nova candidatura
                gerenciador.adicionarCandidatura(c);
                // Descobrir o índice que foi atribuído
                List<Candidatura> lista = gerenciador.listarCandidaturas();
                indiceEdicaoActual = lista.size() - 1;
            } else {
                gerenciador.atualizarCandidatura(indiceEdicaoActual, c);
            }
        } catch (IOException e) {
            mostrarErro("Erro ao guardar", e.getMessage());
        }
    }

    private void guardarComo() {
        FileChooser fc = criarFileChooser("Guardar Como", true);
        File f = fc.showSaveDialog(primaryStage);
        if (f == null) return;
        gerenciador.setArquivo(f);
        guardar();
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private FileChooser criarFileChooser(String titulo, boolean guardar) {
        FileChooser fc = new FileChooser();
        fc.setTitle(titulo);
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Ficheiros de Candidatura (*.bfa)", "*.bfa"));
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Todos os ficheiros (*.*)", "*.*"));
        return fc;
    }

    private void mostrarErro(String titulo, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        a.setTitle(titulo); a.setHeaderText(titulo); a.showAndWait();
    }

    // -------------------------------------------------------------------------
    // Entry point
    // -------------------------------------------------------------------------

    public static void main(String[] args) {
        launch(args);
    }
}
