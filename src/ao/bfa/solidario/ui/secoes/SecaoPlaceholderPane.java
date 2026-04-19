package ao.bfa.solidario.ui.secoes;

import ao.bfa.solidario.model.Candidatura;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * =============================================================================
 *  PLACEHOLDER — Secção ainda não implementada
 * =============================================================================
 *  Exibe um painel visual indicando quem deve implementar a secção.
 *  Deve ser SUBSTITUÍDO pela classe real quando o membro integrar o código.
 *
 *  Em MainApp.java, substituir por exemplo:
 *    formulario.registarSecao(new SecaoBPane());   ← substitui o placeholder "B"
 * =============================================================================
 */
public class SecaoPlaceholderPane implements Secao {

    private final String nome;
    private final String instrucao;
    private final VBox pane;

    public SecaoPlaceholderPane(String nome, String instrucao) {
        this.nome = nome;
        this.instrucao = instrucao;
        this.pane = construirPane();
    }

    private VBox construirPane() {
        VBox vb = new VBox(16);
        vb.setAlignment(Pos.CENTER);
        vb.setPadding(new Insets(60, 40, 40, 40));
        vb.setStyle("-fx-background-color: #F8F8FC;");

        // Caixa de aviso estilizada
        VBox caixa = new VBox(10);
        caixa.setAlignment(Pos.CENTER);
        caixa.setPadding(new Insets(32, 48, 32, 48));
        caixa.setMaxWidth(460);
        caixa.setStyle(
            "-fx-background-color: white; "
            + "-fx-background-radius: 12; "
            + "-fx-border-color: #E0E0EC; "
            + "-fx-border-radius: 12; "
            + "-fx-border-width: 1.5; "
            + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.06), 12, 0, 0, 4);"
        );

        Label icone = new Label("⚙");
        icone.setStyle("-fx-font-size: 40px;");

        Label lblNome = new Label(nome);
        lblNome.setStyle(
            "-fx-font-size: 18px; -fx-font-weight: bold; "
            + "-fx-text-fill: #1A1A2E;"
        );

        Label lblSep = new Label("Esta secção ainda não foi integrada.");
        lblSep.setStyle("-fx-font-size: 13px; -fx-text-fill: #6B6B80;");

        // Caixa de instrução estilizada
        Label lblInst = new Label(instrucao);
        lblInst.setStyle(
            "-fx-font-size: 12px; -fx-text-fill: #C04E10; "
            + "-fx-background-color: #FFF3E0; "
            + "-fx-background-radius: 6; "
            + "-fx-padding: 10 16; "
            + "-fx-font-family: 'Courier New', monospace;"
        );
        lblInst.setWrapText(true);
        lblInst.setMaxWidth(380);

        Label lblDica = new Label("Para integrar: registarSecao(new SuaClassePane()) em MainApp.java");
        lblDica.setStyle("-fx-font-size: 10px; -fx-text-fill: #9B9BAA; -fx-font-style: italic;");
        lblDica.setWrapText(true);

        caixa.getChildren().addAll(icone, lblNome, lblSep, lblInst, lblDica);
        vb.getChildren().add(caixa);
        return vb;
    }

    @Override public VBox getPane() { return pane; }
    @Override public String getNome() { return nome; }
    @Override public String validar() { return null; } // placeholder não valida
    @Override public void preencherDados(Candidatura c) {}
    @Override public void carregarDados(Candidatura c) {}
}
