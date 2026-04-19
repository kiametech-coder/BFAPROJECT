package ao.bfa.solidario.ui;

import ao.bfa.solidario.model.Candidatura;
import ao.bfa.solidario.ui.secoes.Secao;
import ao.bfa.solidario.ui.secoes.SecaoPlaceholderPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * =============================================================================
 *  FORMULÁRIO PRINCIPAL — Área de Edição de Candidatura
 *  Responsável: Pessoa 2
 * =============================================================================
 *  Layout:
 *    ┌───────────────────────────────────────────────────────┐
 *    │  Banner com nome da org e botões Guardar / Submeter   │
 *    ├──────────────────┬────────────────────────────────────┤
 *    │  Lista lateral   │   Área central (painel da secção)  │
 *    │  com secções     │                                    │
 *    ├──────────────────┴────────────────────────────────────┤
 *    │  Barra de navegação: Anterior  |  Secção X / 10  | Próxima │
 *    └───────────────────────────────────────────────────────┘
 *
 *  Como as outras pessoas adicionam as suas secções:
 *    → chamar registarSecao(new SecaoBPane(...)) em MainApp.java
 *    → a lista lateral e navegação actualizam-se automaticamente.
 * =============================================================================
 */
public class FormularioPane extends BorderPane {

    // -------------------------------------------------------------------------
    // Cores
    // -------------------------------------------------------------------------
    private static final String COR_LARANJA    = "#E8611A";
    private static final String COR_LARANJA_ESC = "#C04E10";
    private static final String COR_BG         = "#F5F5F5";
    private static final String COR_BRANCO     = "#FFFFFF";
    private static final String COR_TEXTO      = "#1A1A2E";
    private static final String COR_SUBTEXTO   = "#6B6B80";
    private static final String COR_LINHA      = "#E0E0EC";
    private static final String COR_SIDEBAR_BG = "#1A1A2E";
    private static final String COR_ITEM_HOVER = "#2A2A3E";
    private static final String COR_ITEM_SEL   = "#E8611A";

    // -------------------------------------------------------------------------
    // Estado
    // -------------------------------------------------------------------------
    private final Map<String, Secao> secoes = new LinkedHashMap<>();
    private final List<String> ordemSecoes   = new ArrayList<>();
    private Candidatura candidaturaActual;
    private String secaoActiva;

    private ListView<String> listaNomes;
    private StackPane areaConteudo;
    private Label lblSecaoActual;
    private Label lblOrgBanner;
    private Button btnAnterior;
    private Button btnProximo;
    private Label lblPaginacao;

    // Callbacks para MainApp
    private Runnable onGuardar;
    private Runnable onGuardarComo;
    private Runnable onVoltar;   // voltar à página inicial

    public FormularioPane() {
        construirUI();
        // Registar placeholders para as secções das outras pessoas
        // (serão substituídos quando cada pessoa integrar o código)
        registarPlaceholders();
    }

    // -------------------------------------------------------------------------
    // API pública — MainApp chama estes métodos
    // -------------------------------------------------------------------------

    /**
     * Regista um painel de secção na área central.
     * Chamado por MainApp para cada membro da equipa.
     * A ordem de inserção define a ordem na lista lateral.
     */
    public void registarSecao(Secao secao) {
        String nome = secao.getNome();
        if (secoes.containsKey(nome)) {
            // Substituir placeholder se já existir
            secoes.put(nome, secao);
        } else {
            secoes.put(nome, secao);
            ordemSecoes.add(nome);
            listaNomes.getItems().add(nome);
        }
        // Se é a primeira, activar
        if (secaoActiva == null) navegarPara(nome);
    }

    /**
     * Define a candidatura que está a ser editada.
     * Carrega os dados em todos os painéis registados.
     */
    public void setCandidatura(Candidatura c) {
        this.candidaturaActual = c;
        String org = c.getNomeOrganizacao();
        lblOrgBanner.setText(org != null && !org.isEmpty()? org : "Nova Candidatura");
        // Carregar dados em cada secção registada
        for (Secao s : secoes.values()) {
            s.carregarDados(c);
        }
    }

    /**
     * Recolhe os dados de TODOS os painéis para o objecto Candidatura.
     * Chamado antes de guardar.
     */
    public void recolherDados() {
        if (candidaturaActual == null) return;
        for (Secao s : secoes.values()) {
            s.preencherDados(candidaturaActual);
        }
        candidaturaActual.setTimestampUltimaAlteracao(System.currentTimeMillis());
    }

    /**
     * Valida todas as secções e retorna lista de erros.
     * Cada erro é "NomeDaSecção: mensagem".
     */
    public List<String> validarTudo() {
        List<String> erros = new ArrayList<>();
        for (Secao s : secoes.values()) {
            String err = s.validar();
            if (err != null) erros.add(s.getNome() + ": " + err);
        }
        return erros;
    }

    public Candidatura getCandidaturaActual() { return candidaturaActual; }

    // -------------------------------------------------------------------------
    // Callbacks
    // -------------------------------------------------------------------------

    public void setOnGuardar(Runnable r)    { this.onGuardar = r; }
    public void setOnGuardarComo(Runnable r) { this.onGuardarComo = r; }
    public void setOnVoltar(Runnable r)     { this.onVoltar = r; }

    // -------------------------------------------------------------------------
    // Construção da UI
    // -------------------------------------------------------------------------

    private void construirUI() {
        setStyle("-fx-background-color: " + COR_BG + ";");
        setTop(criarBannerFormulario());
        setLeft(criarSidebar());
        setCenter(criarAreaCentral());
        setBottom(criarBarraNavegacao());
    }

    private HBox criarBannerFormulario() {
        HBox banner = new HBox(12);
        banner.setPadding(new Insets(12, 20, 12, 20));
        banner.setAlignment(Pos.CENTER_LEFT);
        banner.setStyle("-fx-background-color: " + COR_LARANJA + ";");

        // Botão voltar
        Button btnVoltar = new Button("← Candidaturas");
        btnVoltar.setStyle(
            "-fx-background-color: rgba(255,255,255,0.15); "
            + "-fx-text-fill: white; -fx-font-size: 12px; "
            + "-fx-background-radius: 5; -fx-padding: 5 12; -fx-cursor: hand;"
        );
        btnVoltar.setOnAction(e -> { if (onVoltar != null) onVoltar.run(); });

        // Nome da candidatura
        VBox infoOrg = new VBox(1);
        Label lblLabel = new Label("A editar:");
        lblLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: rgba(255,255,255,0.7);");
        lblOrgBanner = new Label("Nova Candidatura");
        lblOrgBanner.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");
        infoOrg.getChildren().addAll(lblLabel, lblOrgBanner);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Botões de acção
        Button btnGuardar = criarBotaoBanner("💾  Guardar", true);
        btnGuardar.setOnAction(e -> {
            recolherDados();
            if (onGuardar != null) onGuardar.run();
        });

        Button btnSubmeter = criarBotaoBanner("✔  Submeter", false);
        btnSubmeter.setOnAction(e -> submeterCandidatura());

        banner.getChildren().addAll(btnVoltar, infoOrg, spacer, btnGuardar, btnSubmeter);
        return banner;
    }

    private VBox criarSidebar() {
        VBox sidebar = new VBox(0);
        sidebar.setPrefWidth(220);
        sidebar.setStyle("-fx-background-color: " + COR_SIDEBAR_BG + ";");

        Label lblTitulo = new Label("SECÇÕES");
        lblTitulo.setStyle(
            "-fx-font-size: 10px; -fx-font-weight: bold; "
            + "-fx-text-fill: rgba(255,255,255,0.4); "
            + "-fx-padding: 16 16 8 16; -fx-letter-spacing: 2;"
        );

        listaNomes = new ListView<>();
        listaNomes.setStyle(
            "-fx-background-color: transparent; "
            + "-fx-border-color: transparent;"
        );
        VBox.setVgrow(listaNomes, Priority.ALWAYS);

        listaNomes.setCellFactory(lv -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null); setText(null);
                    setStyle("-fx-background-color: transparent;");
                    return;
                }
                // Extrair letra da secção (ex: "A - Introdução" → "A")
                String letra = item.contains(" - ") ? item.split(" - ")[0].trim() : item.substring(0, 1);
                String nome  = item.contains(" - ") ? item.split(" - ", 2)[1].trim() : item;

                // Indicador circular
                Circle circulo = new Circle(14);
                boolean activo = item.equals(secaoActiva);
                circulo.setFill(Color.web(activo ? COR_ITEM_SEL : "#2E3050"));
                circulo.setStroke(Color.web(activo ? COR_ITEM_SEL : "#3A3D60"));
                circulo.setStrokeWidth(1.5);

                Label lblLetra = new Label(letra);
                lblLetra.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: white;");

                StackPane icone = new StackPane(circulo, lblLetra);
                icone.setPrefSize(28, 28);

                Label lblNome = new Label(nome);
                lblNome.setStyle("-fx-font-size: 12px; "
                    + "-fx-text-fill: " + (activo ? "white" : "rgba(255,255,255,0.6)") + "; "
                    + (activo ? "-fx-font-weight: bold;" : "")
                );
                lblNome.setWrapText(true);
                lblNome.setMaxWidth(148);

                HBox hb = new HBox(10, icone, lblNome);
                hb.setAlignment(Pos.CENTER_LEFT);
                hb.setPadding(new Insets(6, 12, 6, 12));

                setGraphic(hb);
                setText(null);
                setStyle("-fx-background-color: " + (activo ? "rgba(232,97,26,0.15)" : "transparent") + "; "
                    + "-fx-cursor: hand;");
            }
        });

        listaNomes.getSelectionModel().selectedItemProperty().addListener((obs, old, novo) -> {
            if (novo != null) navegarPara(novo);
        });

        sidebar.getChildren().addAll(lblTitulo, listaNomes);
        return sidebar;
    }

    private BorderPane criarAreaCentral() {
        BorderPane central = new BorderPane();

        // Cabeçalho da secção
        HBox cabSecao = new HBox();
        cabSecao.setPadding(new Insets(12, 20, 10, 20));
        cabSecao.setStyle("-fx-background-color: white; -fx-border-color: " + COR_LINHA
                + " transparent " + COR_LINHA + " transparent;");
        lblSecaoActual = new Label("Selecione uma secção");
        lblSecaoActual.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; "
                + "-fx-text-fill: " + COR_TEXTO + ";");
        cabSecao.getChildren().add(lblSecaoActual);

        // Área de conteúdo rolável
        areaConteudo = new StackPane();
        areaConteudo.setStyle("-fx-background-color: " + COR_BG + ";");
        ScrollPane scroll = new ScrollPane(areaConteudo);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        ScrollPane.ScrollBarPolicy.AS_NEEDED.toString();

        central.setTop(cabSecao);
        central.setCenter(scroll);
        return central;
    }

    private HBox criarBarraNavegacao() {
        HBox barra = new HBox(12);
        barra.setPadding(new Insets(10, 20, 12, 20));
        barra.setAlignment(Pos.CENTER);
        barra.setStyle("-fx-background-color: white; -fx-border-color: " + COR_LINHA
                + " transparent transparent transparent;");

        btnAnterior = criarBotaoNav("← Anterior");
        btnAnterior.setOnAction(e -> navegarAnterior());

        lblPaginacao = new Label("1 / 10");
        lblPaginacao.setStyle("-fx-font-size: 13px; -fx-text-fill: " + COR_SUBTEXTO + ";");

        btnProximo = criarBotaoNav("Próxima →");
        btnProximo.setOnAction(e -> navegarProxima());

        barra.getChildren().addAll(btnAnterior, lblPaginacao, btnProximo);
        return barra;
    }

    // -------------------------------------------------------------------------
    // Navegação
    // -------------------------------------------------------------------------

    private void navegarPara(String nomeSecao) {
        if (!secoes.containsKey(nomeSecao)) return;
        secaoActiva = nomeSecao;

        Secao secao = secoes.get(nomeSecao);
        areaConteudo.getChildren().setAll(secao.getPane());
        lblSecaoActual.setText(nomeSecao);

        // Actualizar lista lateral
        listaNomes.getSelectionModel().select(nomeSecao);
        listaNomes.refresh();

        // Actualizar paginação
        int idx = ordemSecoes.indexOf(nomeSecao);
        lblPaginacao.setText((idx + 1) + " / " + ordemSecoes.size());
        btnAnterior.setDisable(idx == 0);
        btnProximo.setDisable(idx == ordemSecoes.size() - 1);
    }

    private void navegarAnterior() {
        int idx = ordemSecoes.indexOf(secaoActiva);
        if (idx > 0) navegarPara(ordemSecoes.get(idx - 1));
    }

    private void navegarProxima() {
        int idx = ordemSecoes.indexOf(secaoActiva);
        if (idx < ordemSecoes.size() - 1) navegarPara(ordemSecoes.get(idx + 1));
    }

    // -------------------------------------------------------------------------
    // Submissão com validação agregada
    // -------------------------------------------------------------------------

    private void submeterCandidatura() {
        recolherDados();
        List<String> erros = validarTudo();
        if (!erros.isEmpty()) {
            StringBuilder sb = new StringBuilder("Por favor corrija os seguintes problemas:\n\n");
            erros.forEach(e -> sb.append("• ").append(e).append("\n"));
            Alert a = new Alert(Alert.AlertType.WARNING, sb.toString(), ButtonType.OK);
            a.setTitle("Validação"); a.setHeaderText("Formulário incompleto"); a.showAndWait();
            // Navegar para a primeira secção com erro
            String nomeSecaoErro = erros.get(0).split(":")[0].trim();
            if (secoes.containsKey(nomeSecaoErro)) navegarPara(nomeSecaoErro);
            return;
        }
        // Marcar como submetida
        if (candidaturaActual != null) {
            candidaturaActual.setCertezaSubmissao(true);
            candidaturaActual.setDesejaSubmeter(true);
            candidaturaActual.setConfirmaDadosVerdadeiros(true);
        }
        if (onGuardar != null) onGuardar.run();
        Alert ok = new Alert(Alert.AlertType.INFORMATION,
                "A sua candidatura foi submetida com sucesso!", ButtonType.OK);
        ok.setTitle("Submetida"); ok.setHeaderText("✔  Candidatura Submetida"); ok.showAndWait();
        if (onVoltar != null) onVoltar.run();
    }

    // -------------------------------------------------------------------------
    // Placeholders (substituídos pela equipa)
    // -------------------------------------------------------------------------

    private void registarPlaceholders() {
        // Estas linhas serão substituídas em MainApp quando cada pessoa integrar o código:
        //   registarSecao(new SecaoAPane(candidaturaActual));  ← Pessoa 3
        //   registarSecao(new SecaoBPane(candidaturaActual));  ← Pessoa 3
        //   ...
        // Por agora, registar placeholders visuais:
        registarSecao(new SecaoPlaceholderPane("A - Introdução",
                "Pessoa 3 implementa esta secção.\nClasse: SecaoAPane"));
        registarSecao(new SecaoPlaceholderPane("B - Detalhes de Contacto",
                "Pessoa 3 implementa esta secção.\nClasse: SecaoBPane"));
        registarSecao(new SecaoPlaceholderPane("C - Descrição da Organização",
                "Pessoa 3 implementa esta secção.\nClasse: SecaoCPane"));
        registarSecao(new SecaoPlaceholderPane("D - Colaboradores e Liderança",
                "Pessoa 4 implementa esta secção.\nClasse: SecaoDPane"));
        registarSecao(new SecaoPlaceholderPane("E - Experiência e Resultados",
                "Pessoa 4 implementa esta secção.\nClasse: SecaoEPane"));
        registarSecao(new SecaoPlaceholderPane("F - Questões Financeiras",
                "Pessoa 5 implementa esta secção.\nClasse: SecaoFPane"));
        registarSecao(new SecaoPlaceholderPane("G - Proposta",
                "Pessoa 5 implementa esta secção.\nClasse: SecaoGPane"));
        registarSecao(new SecaoPlaceholderPane("H - Governo da Instituição",
                "Pessoa 4 implementa esta secção.\nClasse: SecaoHPane"));
        registarSecao(new SecaoPlaceholderPane("I - Referências",
                "Pessoa 4 implementa esta secção.\nClasse: SecaoIPane"));
        registarSecao(new SecaoPlaceholderPane("J - Termos e Condições",
                "Pessoa 5 implementa esta secção.\nClasse: SecaoJPane"));
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private Button criarBotaoBanner(String texto, boolean destaque) {
        Button btn = new Button(texto);
        btn.setStyle(
            "-fx-background-color: " + (destaque ? "rgba(255,255,255,0.2)" : COR_BRANCO) + "; "
            + "-fx-text-fill: " + (destaque ? "white" : COR_LARANJA) + "; "
            + "-fx-font-size: 13px; -fx-background-radius: 6; "
            + "-fx-padding: 7 16; -fx-cursor: hand;"
            + (destaque ? "" : "-fx-font-weight: bold;")
        );
        return btn;
    }

    private Button criarBotaoNav(String texto) {
        Button btn = new Button(texto);
        btn.setStyle(
            "-fx-background-color: white; -fx-text-fill: " + COR_TEXTO + "; "
            + "-fx-font-size: 13px; -fx-background-radius: 6; "
            + "-fx-border-color: " + COR_LINHA + "; -fx-border-radius: 6; "
            + "-fx-padding: 7 20; -fx-cursor: hand;"
        );
        return btn;
    }
}
