package ao.bfa.solidario.ui;

import ao.bfa.solidario.model.Candidatura;
import ao.bfa.solidario.service.GerenciadorArquivo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * =============================================================================
 *  PÁGINA INICIAL — Tabela de Candidaturas
 *  Responsável: Pessoa 2
 * =============================================================================
 *  Exibida ao arrancar a aplicação.
 *  Mostra todas as candidaturas guardadas no ficheiro actual.
 *  Permite abrir, criar nova ou apagar candidatura.
 * =============================================================================
 */
public class PaginaInicialPane extends BorderPane {

    // -------------------------------------------------------------------------
    // Cores BFA (palette oficial)
    // -------------------------------------------------------------------------
    private static final String COR_LARANJA   = "#E8611A";
    private static final String COR_LARANJA_ESC = "#C04E10";
    private static final String COR_CINZA_BG  = "#F5F5F5";
    private static final String COR_BRANCO    = "#FFFFFF";
    private static final String COR_TEXTO     = "#1A1A2E";
    private static final String COR_SUBTEXTO  = "#6B6B80";
    private static final String COR_LINHA     = "#E0E0EC";
    private static final String COR_VERDE     = "#2E7D32";
    private static final String COR_VERMELHO  = "#C62828";

    // -------------------------------------------------------------------------
    // Estado
    // -------------------------------------------------------------------------
    private final GerenciadorArquivo gerenciador;
    private TableView<CandidaturaRow> tabela;
    private ObservableList<CandidaturaRow> dados;
    private Label lblTotalCandidaturas;
    private Label lblArquivoActual;

    // Callbacks para MainApp
    private Runnable onNovaCandidatura;
    private java.util.function.Consumer<Integer> onAbrirCandidatura;

    public PaginaInicialPane(GerenciadorArquivo gerenciador) {
        this.gerenciador = gerenciador;
        construirUI();
    }

    // -------------------------------------------------------------------------
    // Construção da UI
    // -------------------------------------------------------------------------

    private void construirUI() {
        setStyle("-fx-background-color: " + COR_CINZA_BG + ";");

        setTop(criarCabecalho());
        setCenter(criarConteudo());
        setBottom(criarRodape());
    }

    private VBox criarCabecalho() {
        // Banner laranja
        VBox banner = new VBox(6);
        banner.setPadding(new Insets(20, 30, 20, 30));
        banner.setStyle("-fx-background-color: " + COR_LARANJA + ";");

        HBox linha1 = new HBox(12);
        linha1.setAlignment(Pos.CENTER_LEFT);

        // Logo / título
        VBox titulo = new VBox(2);
        Label lblBFA = new Label("BFA SOLIDÁRIO");
        lblBFA.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: white; "
                + "-fx-font-family: 'Georgia';");
        Label lblSub = new Label("Sistema de Gestão de Candidaturas — 5ª Edição");
        lblSub.setStyle("-fx-font-size: 12px; -fx-text-fill: rgba(255,255,255,0.85);");
        titulo.getChildren().addAll(lblBFA, lblSub);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Info arquivo actual
        lblArquivoActual = new Label("Nenhum ficheiro aberto");
        lblArquivoActual.setStyle("-fx-font-size: 11px; -fx-text-fill: rgba(255,255,255,0.75); "
                + "-fx-font-style: italic;");

        linha1.getChildren().addAll(titulo, spacer, lblArquivoActual);
        banner.getChildren().add(linha1);

        return banner;
    }

    private VBox criarConteudo() {
        VBox conteudo = new VBox(16);
        conteudo.setPadding(new Insets(24, 30, 16, 30));

        // --- Barra de acções ---
        HBox barraAccoes = new HBox(10);
        barraAccoes.setAlignment(Pos.CENTER_LEFT);

        Button btnNova = criarBotao("＋  Nova Candidatura", COR_LARANJA, COR_BRANCO, true);
        btnNova.setOnAction(e -> { if (onNovaCandidatura != null) onNovaCandidatura.run(); });

        Button btnRefresh = criarBotao("↺  Actualizar", COR_BRANCO, COR_TEXTO, false);
        btnRefresh.setOnAction(e -> recarregarTabela());

        Button btnAbrir = criarBotao("📂  Abrir Seleccionada", COR_BRANCO, COR_TEXTO, false);
        btnAbrir.setOnAction(e -> abrirSelecionada());

        Button btnApagar = criarBotao("🗑  Apagar", COR_BRANCO, COR_VERMELHO, false);
        btnApagar.setOnAction(e -> apagarSelecionada());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        lblTotalCandidaturas = new Label("0 candidaturas");
        lblTotalCandidaturas.setStyle("-fx-font-size: 13px; -fx-text-fill: " + COR_SUBTEXTO + "; "
                + "-fx-font-style: italic;");

        barraAccoes.getChildren().addAll(btnNova, btnRefresh, btnAbrir, btnApagar, spacer, lblTotalCandidaturas);

        // --- Tabela ---
        tabela = criarTabela();
        VBox.setVgrow(tabela, Priority.ALWAYS);

        // --- Painel vazio (quando não há candidaturas) ---
        conteudo.getChildren().addAll(barraAccoes, tabela);
        return conteudo;
    }

    @SuppressWarnings("unchecked")
    private TableView<CandidaturaRow> criarTabela() {
        TableView<CandidaturaRow> tv = new TableView<>();
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv.setStyle(
            "-fx-background-color: white; "
            + "-fx-border-color: " + COR_LINHA + "; "
            + "-fx-border-radius: 8; "
            + "-fx-background-radius: 8;"
        );
        tv.setPlaceholder(criarPlaceholder());

        // Duplo clique abre candidatura
        tv.setRowFactory(t -> {
            TableRow<CandidaturaRow> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && !row.isEmpty()) {
                    abrirSelecionada();
                }
            });
            return row;
        });

        // Colunas
        TableColumn<CandidaturaRow, Integer> colIdx = new TableColumn<>("#");
        colIdx.setCellValueFactory(new PropertyValueFactory<>("indice"));
        colIdx.setMaxWidth(50); colIdx.setMinWidth(40);
        colIdx.setStyle("-fx-alignment: CENTER;");

        TableColumn<CandidaturaRow, String> colOrg = new TableColumn<>("Organização");
        colOrg.setCellValueFactory(new PropertyValueFactory<>("nomeOrganizacao"));
        colOrg.setMinWidth(200);

        TableColumn<CandidaturaRow, String> colNif = new TableColumn<>("NIF");
        colNif.setCellValueFactory(new PropertyValueFactory<>("nif"));
        colNif.setMinWidth(100);

        TableColumn<CandidaturaRow, String> colProv = new TableColumn<>("Província");
        colProv.setCellValueFactory(new PropertyValueFactory<>("provincia"));
        colProv.setMinWidth(110);

        TableColumn<CandidaturaRow, String> colResp = new TableColumn<>("Responsável");
        colResp.setCellValueFactory(new PropertyValueFactory<>("responsavel"));
        colResp.setMinWidth(160);

        TableColumn<CandidaturaRow, String> colData = new TableColumn<>("Data Criação");
        colData.setCellValueFactory(new PropertyValueFactory<>("dataCriacao"));
        colData.setMinWidth(130);

        TableColumn<CandidaturaRow, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colEstado.setMinWidth(110);
        colEstado.setCellFactory(col -> new TableCell<CandidaturaRow, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) { setText(null); setGraphic(null); return; }
                Label chip = new Label(item);
                boolean completo = "Submetida".equals(item);
                chip.setStyle(
                    "-fx-background-color: " + (completo ? "#E8F5E9" : "#FFF3E0") + "; "
                    + "-fx-text-fill: " + (completo ? COR_VERDE : COR_LARANJA_ESC) + "; "
                    + "-fx-background-radius: 10; "
                    + "-fx-padding: 2 8 2 8; "
                    + "-fx-font-size: 11px; "
                    + "-fx-font-weight: bold;"
                );
                setGraphic(chip);
                setText(null);
            }
        });

        tv.getColumns().addAll(colIdx, colOrg, colNif, colProv, colResp, colData, colEstado);

        dados = FXCollections.observableArrayList();
        tv.setItems(dados);
        return tv;
    }

    private VBox criarPlaceholder() {
        VBox vb = new VBox(12);
        vb.setAlignment(Pos.CENTER);
        vb.setPadding(new Insets(40));

        Label icon = new Label("📋");
        icon.setStyle("-fx-font-size: 48px;");

        Label msg = new Label("Nenhuma candidatura registada.");
        msg.setStyle("-fx-font-size: 15px; -fx-text-fill: " + COR_SUBTEXTO + ";");

        Label sub = new Label("Clique em \"Nova Candidatura\" para começar,\nou abra um ficheiro existente.");
        sub.setStyle("-fx-font-size: 12px; -fx-text-fill: " + COR_SUBTEXTO + "; -fx-text-alignment: center;");
        sub.setTextAlignment(TextAlignment.CENTER);

        vb.getChildren().addAll(icon, msg, sub);
        return vb;
    }

    private HBox criarRodape() {
        HBox rodape = new HBox();
        rodape.setPadding(new Insets(8, 30, 10, 30));
        rodape.setStyle("-fx-background-color: " + COR_BRANCO + "; -fx-border-color: " + COR_LINHA + " transparent transparent transparent;");

        Label lblInfo = new Label("Banco de Fomento Angola, S.A.  |  BFA Solidário 5ª Edição  |  Sistema de Candidaturas v1.0");
        lblInfo.setStyle("-fx-font-size: 10px; -fx-text-fill: " + COR_SUBTEXTO + ";");

        rodape.getChildren().add(lblInfo);
        return rodape;
    }

    // -------------------------------------------------------------------------
    // Lógica
    // -------------------------------------------------------------------------

    public void recarregarTabela() {
        dados.clear();
        if (!gerenciador.temArquivoDefinido()) {
            lblArquivoActual.setText("Nenhum ficheiro aberto");
            lblTotalCandidaturas.setText("0 candidaturas");
            return;
        }
        try {
            List<Candidatura> lista = gerenciador.listarCandidaturas();
            lblArquivoActual.setText("📁  " + gerenciador.getArquivo().getName());
            for (int i = 0; i < lista.size(); i++) {
                dados.add(new CandidaturaRow(i, lista.get(i)));
            }
            int total = lista.size();
            lblTotalCandidaturas.setText(total + (total == 1 ? " candidatura" : " candidaturas"));
        } catch (IOException e) {
            mostrarErro("Erro ao carregar candidaturas", e.getMessage());
        }
    }

    private void abrirSelecionada() {
        CandidaturaRow sel = tabela.getSelectionModel().getSelectedItem();
        if (sel == null) {
            mostrarAviso("Selecione uma candidatura na tabela primeiro.");
            return;
        }
        if (onAbrirCandidatura != null) onAbrirCandidatura.accept(sel.getIndice());
    }

    private void apagarSelecionada() {
        CandidaturaRow sel = tabela.getSelectionModel().getSelectedItem();
        if (sel == null) {
            mostrarAviso("Selecione uma candidatura na tabela primeiro.");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Tem a certeza que deseja apagar a candidatura de\n\"" + sel.getNomeOrganizacao() + "\"?",
                ButtonType.YES, ButtonType.NO);
        confirm.setTitle("Confirmar Eliminação");
        confirm.setHeaderText("Apagar Candidatura");
        confirm.showAndWait().ifPresent(bt -> {
            if (bt == ButtonType.YES) {
                try {
                    gerenciador.apagarCandidatura(sel.getIndice());
                    recarregarTabela();
                } catch (IOException e) {
                    mostrarErro("Erro ao apagar", e.getMessage());
                }
            }
        });
    }

    // -------------------------------------------------------------------------
    // Helpers de UI
    // -------------------------------------------------------------------------

    private Button criarBotao(String texto, String bgColor, String fgColor, boolean destaque) {
        Button btn = new Button(texto);
        btn.setStyle(
            "-fx-background-color: " + bgColor + "; "
            + "-fx-text-fill: " + fgColor + "; "
            + "-fx-font-size: 13px; "
            + "-fx-background-radius: 6; "
            + "-fx-padding: 7 16 7 16; "
            + "-fx-cursor: hand; "
            + (destaque ? "-fx-font-weight: bold;" : "")
            + (!destaque ? "-fx-border-color: " + COR_LINHA + "; -fx-border-radius: 6;" : "")
        );
        btn.setOnMouseEntered(e -> btn.setStyle(
            "-fx-background-color: " + (destaque ? COR_LARANJA_ESC : "#EBEBEB") + "; "
            + "-fx-text-fill: " + fgColor + "; "
            + "-fx-font-size: 13px; "
            + "-fx-background-radius: 6; "
            + "-fx-padding: 7 16 7 16; "
            + "-fx-cursor: hand; "
            + (destaque ? "-fx-font-weight: bold;" : "")
        ));
        btn.setOnMouseExited(e -> btn.setStyle(
            "-fx-background-color: " + bgColor + "; "
            + "-fx-text-fill: " + fgColor + "; "
            + "-fx-font-size: 13px; "
            + "-fx-background-radius: 6; "
            + "-fx-padding: 7 16 7 16; "
            + "-fx-cursor: hand; "
            + (destaque ? "-fx-font-weight: bold;" : "")
            + (!destaque ? "-fx-border-color: " + COR_LINHA + "; -fx-border-radius: 6;" : "")
        ));
        return btn;
    }

    private void mostrarErro(String titulo, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        a.setTitle(titulo); a.setHeaderText(titulo); a.showAndWait();
    }

    private void mostrarAviso(String msg) {
        Alert a = new Alert(Alert.AlertType.WARNING, msg, ButtonType.OK);
        a.setTitle("Aviso"); a.setHeaderText(null); a.showAndWait();
    }

    // -------------------------------------------------------------------------
    // Callbacks
    // -------------------------------------------------------------------------

    public void setOnNovaCandidatura(Runnable r) { this.onNovaCandidatura = r; }
    public void setOnAbrirCandidatura(java.util.function.Consumer<Integer> c) { this.onAbrirCandidatura = c; }

    // -------------------------------------------------------------------------
    // Linha da tabela (wrapper para PropertyValueFactory)
    // -------------------------------------------------------------------------

    public static class CandidaturaRow {
        private final int indice;
        private final String nomeOrganizacao;
        private final String nif;
        private final String provincia;
        private final String responsavel;
        private final String dataCriacao;
        private final String estado;

        public CandidaturaRow(int indice, Candidatura c) {
            this.indice = indice;
            this.nomeOrganizacao = c.getNomeOrganizacao() != null ? c.getNomeOrganizacao() : "—";
            this.nif = c.getNif() != null ? c.getNif() : "—";
            this.provincia = c.getProvincia() != null ? c.getProvincia() : "—";

            String nome = "";
            if (c.getResp1PrimeiroNome() != null) nome += c.getResp1PrimeiroNome();
            if (c.getResp1UltimoNome() != null) nome += " " + c.getResp1UltimoNome();
            this.responsavel = nome.isEmpty() ? "—" : nome.trim();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("pt", "AO"));
            this.dataCriacao = sdf.format(new Date(c.getTimestampCriacao()));

            boolean submetida = c.isCertezaSubmissao() && c.isDesejaSubmeter();
            this.estado = submetida ? "Submetida" : "Em Progresso";
        }

        public int getIndice() { return indice; }
        public String getNomeOrganizacao() { return nomeOrganizacao; }
        public String getNif() { return nif; }
        public String getProvincia() { return provincia; }
        public String getResponsavel() { return responsavel; }
        public String getDataCriacao() { return dataCriacao; }
        public String getEstado() { return estado; }
    }
}
