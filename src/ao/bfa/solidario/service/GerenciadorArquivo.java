package ao.bfa.solidario.service;

import ao.bfa.solidario.model.Candidatura;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * =============================================================================
 *  GERENCIADOR DE ARQUIVO — Persistência com RandomAccessFile
 *  Responsável: Pessoa 1 (estrutura base feita pela Pessoa 2)
 * =============================================================================
 *
 *  MAPA DE OFFSETS DO REGISTO (16979 bytes por registo):
 *
 *  Offset   Bytes  Campo
 *  ------   -----  ----------------------------
 *  0        4      tamanhoRegisto (sempre 16979)
 *  4        1      eliminado (0=não, 1=sim)
 *  5        1      concordo  (Sec.A)
 *  6        8      timestampCriacao  (long)
 *  14       8      timestampUltimaAlteracao (long)
 *  22       200    nomeOrganizacao
 *  222      20     nif
 *  242      300    morada
 *  542      100    localidade
 *  642      100    provincia
 *  742      200    site
 *  942      200    facebook
 *  1142     100    resp1PrimeiroNome
 *  1242     100    resp1UltimoNome
 *  1342     30     resp1Telemovel
 *  1372     100    resp1Funcao
 *  1472     150    resp1Email
 *  1622     100    resp2PrimeiroNome
 *  1722     100    resp2UltimoNome
 *  1822     30     resp2Telemovel
 *  1852     100    resp2Funcao
 *  1952     150    resp2Email
 *  2102     600    missaoObjetivo
 *  2702     1200   estrategiaActividades
 *  3902     1000   historiaExperiencia
 *  4902     800    cooperaOrganizacoes
 *  5702     200    comunidades
 *  5902     100    formaLegal
 *  6002     1      registadaMJ
 *  6003     50     numeroRegisto
 *  6053     10     mesInicioC
 *  6063     4      anoInicioC
 *  6067     1      subsidiariaInternacional
 *  6068     9      checkboxes Educação (1 byte cada)
 *  6077     8      checkboxes Saúde
 *  6085     10     checkboxes Inclusão Social
 *  6095     4      numProvinciasAtua (int)  [não usado, reservado]
 *  6099     180    provinciasAtua (18 x 1 byte = flag por província)
 *  6279     600    responsavelGestao
 *  6879     10     mesInicioD
 *  6889     4      anoInicioD
 *  6893     800    qualificacoesResponsavel
 *  7693     4      numColaboradoresTotal (int)
 *  7697     4      numColaboradoresTempoInteiro (int)
 *  7701     4      numColaboradoresTempoParcial (int)
 *  7705     4      numVoluntarios (int)
 *  7709     1200   impactoCriancas
 *  8909     1200   impactoBeneficiariosIndiretos
 *  10109    800    estrategiaComunicacao
 *  10909    8      receitaAnual2021 (double)
 *  10917    8      receitaAnual2022 (double)
 *  10925    72     fontes (9 x double = 72 bytes)
 *  10997    1      temColaboradorFinanceiro
 *  10998    200    detalheOutrosFinanceiros
 *  11198    300    caminhoDocumentoComprovativo
 *  11498    100    categoriaCandiatura
 *  11598    1200   descricaoProjeto
 *  12798    8      pedidoBFA (double)
 *  12806    8      contribuicoesProprias (double)
 *  12814    8      financiamentoAssegurado (double)
 *  12822    8      custoTotal (double)
 *  12830    1200   impactoEsperado
 *  14030    400    parcerias
 *  14430    800    sustentabilidade
 *  15230    1      temComite
 *  15231    800    descricaoSupervisao
 *  16031    1      temMinutas
 *  16032    100    ref1PrimeiroNome
 *  16132    100    ref1UltimoNome
 *  16232    30     ref1Telemovel
 *  16262    100    ref1Funcao
 *  16362    150    ref1Email
 *  16512    100    ref2PrimeiroNome
 *  16612    100    ref2UltimoNome
 *  16712    30     ref2Telemovel
 *  16742    100    ref2Funcao
 *  16842    150    ref2Email
 *  16992    1      confirmaDadosVerdadeiros
 *  16993    1      desejaSubmeter
 *  16994    1      certezaSubmissao
 *  TOTAL = 16995 bytes
 * =============================================================================
 */
public class GerenciadorArquivo {

    // -------------------------------------------------------------------------
    // Tamanho do registo e offsets
    // -------------------------------------------------------------------------
    public static final int RECORD_SIZE = 16995;

    private static final int OFF_TAMANHO          = 0;
    private static final int OFF_ELIMINADO         = 4;
    private static final int OFF_CONCORDO          = 5;
    private static final int OFF_TS_CRIACAO        = 6;
    private static final int OFF_TS_ALTERACAO      = 14;
    private static final int OFF_NOME_ORG          = 22;
    private static final int OFF_NIF               = 222;
    private static final int OFF_MORADA            = 242;
    private static final int OFF_LOCALIDADE        = 542;
    private static final int OFF_PROVINCIA         = 642;
    private static final int OFF_SITE              = 742;
    private static final int OFF_FACEBOOK          = 942;
    private static final int OFF_RESP1_PNOME       = 1142;
    private static final int OFF_RESP1_UNOME       = 1242;
    private static final int OFF_RESP1_TEL         = 1342;
    private static final int OFF_RESP1_FUNC        = 1372;
    private static final int OFF_RESP1_EMAIL       = 1472;
    private static final int OFF_RESP2_PNOME       = 1622;
    private static final int OFF_RESP2_UNOME       = 1722;
    private static final int OFF_RESP2_TEL         = 1822;
    private static final int OFF_RESP2_FUNC        = 1852;
    private static final int OFF_RESP2_EMAIL       = 1952;
    private static final int OFF_MISSAO            = 2102;
    private static final int OFF_ESTRATEGIA        = 2702;
    private static final int OFF_HISTORIA          = 3902;
    private static final int OFF_COOPERA           = 4902;
    private static final int OFF_COMUNIDADES       = 5702;
    private static final int OFF_FORMA_LEGAL       = 5902;
    private static final int OFF_REGISTADA_MJ      = 6002;
    private static final int OFF_NUM_REGISTO       = 6003;
    private static final int OFF_MES_INICIO_C      = 6053;
    private static final int OFF_ANO_INICIO_C      = 6063;
    private static final int OFF_SUBSIDIARIA       = 6067;
    private static final int OFF_CHK_EDU           = 6068;  // 9 bytes
    private static final int OFF_CHK_SAU           = 6077;  // 8 bytes
    private static final int OFF_CHK_INC           = 6085;  // 10 bytes
    private static final int OFF_NUM_PROVS_RSV     = 6095;  // 4 bytes (reservado)
    private static final int OFF_PROVS_FLAGS       = 6099;  // 18 bytes (1 por província)
    private static final int OFF_RESP_GESTAO       = 6279;
    private static final int OFF_MES_INICIO_D      = 6879;
    private static final int OFF_ANO_INICIO_D      = 6889;
    private static final int OFF_QUALIFICACOES     = 6893;
    private static final int OFF_NUM_COLAB         = 7693;
    private static final int OFF_NUM_COLAB_INT     = 7697;
    private static final int OFF_NUM_COLAB_PARC    = 7701;
    private static final int OFF_NUM_VOLUNT        = 7705;
    private static final int OFF_IMPACTO_CRIAN     = 7709;
    private static final int OFF_IMPACTO_INDIR     = 8909;
    private static final int OFF_ESTR_COM          = 10109;
    private static final int OFF_RECEITA_2021      = 10909;
    private static final int OFF_RECEITA_2022      = 10917;
    private static final int OFF_FONTES            = 10925;  // 9 × 8 = 72 bytes
    private static final int OFF_TEM_FINANCEIRO    = 10997;
    private static final int OFF_DETALHE_OUTROS    = 10998;
    private static final int OFF_CAMINHO_DOC       = 11198;
    private static final int OFF_CATEGORIA         = 11498;
    private static final int OFF_DESC_PROJETO      = 11598;
    private static final int OFF_PEDIDO_BFA        = 12798;
    private static final int OFF_CONTRIB_PROP      = 12806;
    private static final int OFF_FIN_ASSEG         = 12814;
    private static final int OFF_CUSTO_TOTAL       = 12822;
    private static final int OFF_IMPACTO_ESP       = 12830;
    private static final int OFF_PARCERIAS         = 14030;
    private static final int OFF_SUSTENTAB         = 14430;
    private static final int OFF_TEM_COMITE        = 15230;
    private static final int OFF_DESC_SUPERV       = 15231;
    private static final int OFF_TEM_MINUTAS       = 16031;
    private static final int OFF_REF1_PNOME        = 16032;
    private static final int OFF_REF1_UNOME        = 16132;
    private static final int OFF_REF1_TEL          = 16232;
    private static final int OFF_REF1_FUNC         = 16262;
    private static final int OFF_REF1_EMAIL        = 16362;
    private static final int OFF_REF2_PNOME        = 16512;
    private static final int OFF_REF2_UNOME        = 16612;
    private static final int OFF_REF2_TEL          = 16712;
    private static final int OFF_REF2_FUNC         = 16742;
    private static final int OFF_REF2_EMAIL        = 16842;
    private static final int OFF_CONFIRMA          = 16992;
    private static final int OFF_DESEJA_SUB        = 16993;
    private static final int OFF_CERTEZA_SUB       = 16994;

    // -------------------------------------------------------------------------
    // Tamanhos dos campos string (em bytes)
    // -------------------------------------------------------------------------
    private static final int SZ_NOME_ORG   = 200;
    private static final int SZ_NIF        = 20;
    private static final int SZ_MORADA     = 300;
    private static final int SZ_LOCALIDADE = 100;
    private static final int SZ_PROVINCIA  = 100;
    private static final int SZ_SITE       = 200;
    private static final int SZ_FACEBOOK   = 200;
    private static final int SZ_NOME       = 100;
    private static final int SZ_TELEMOVEL  = 30;
    private static final int SZ_FUNCAO     = 100;
    private static final int SZ_EMAIL      = 150;
    private static final int SZ_MISSAO     = 600;
    private static final int SZ_ESTRATEGIA = 1200;
    private static final int SZ_HISTORIA   = 1000;
    private static final int SZ_COOPERA    = 800;
    private static final int SZ_COMUNIDADES= 200;
    private static final int SZ_FORMA_LEGAL= 100;
    private static final int SZ_NUM_REG    = 50;
    private static final int SZ_MES        = 10;
    private static final int SZ_ANO        = 4;
    private static final int SZ_RESP_GESTAO= 600;
    private static final int SZ_QUALIF     = 800;
    private static final int SZ_IMPACTO    = 1200;
    private static final int SZ_ESTR_COM   = 800;
    private static final int SZ_DETALHE    = 200;
    private static final int SZ_CAMINHO    = 300;
    private static final int SZ_CATEGORIA  = 100;
    private static final int SZ_DESC_PROJ  = 1200;
    private static final int SZ_IMP_ESP    = 1200;
    private static final int SZ_PARCERIAS  = 400;
    private static final int SZ_SUSTENTAB  = 800;
    private static final int SZ_DESC_SUPERV= 800;

    /** 18 províncias de Angola — índice corresponde ao bit no ficheiro */
    public static final String[] PROVINCIAS = {
        "Bengo","Benguela","Bié","Cabinda","Cuando Cubango",
        "Kuanza Norte","Kuanza Sul","Cunene","Huambo","Huíla",
        "Luanda","Lunda Norte","Lunda Sul","Malanje",
        "Moxico","Namibe","Uíge","Zaire"
    };

    // -------------------------------------------------------------------------
    // Estado
    // -------------------------------------------------------------------------
    private File arquivoActual;

    public GerenciadorArquivo() {}

    public void setArquivo(File arquivo)  { this.arquivoActual = arquivo; }
    public File getArquivo()              { return arquivoActual; }
    public boolean temArquivoDefinido()   { return arquivoActual != null; }

    // -------------------------------------------------------------------------
    // API CRUD
    // -------------------------------------------------------------------------

    /** Número total de registos no ficheiro (incluindo eliminados) */
    public int contarRegistos() throws IOException {
        if (arquivoActual == null || !arquivoActual.exists()) return 0;
        return (int)(arquivoActual.length() / RECORD_SIZE);
    }

    /** Adiciona candidatura no fim do ficheiro */
    public void adicionarCandidatura(Candidatura c) throws IOException {
        garantirFicheiroExiste();
        try (RandomAccessFile raf = new RandomAccessFile(arquivoActual, "rw")) {
            escreverCandidatura(raf, c, raf.length());
        }
    }

    /** Actualiza a candidatura no índice n (contando só não eliminados) */
    public void atualizarCandidatura(int indice, Candidatura c) throws IOException {
        long pos = encontrarPosicaoNaoEliminado(indice);
        if (pos < 0) throw new IOException("Candidatura não encontrada no índice " + indice);
        try (RandomAccessFile raf = new RandomAccessFile(arquivoActual, "rw")) {
            escreverCandidatura(raf, c, pos);
        }
    }

    /** Retorna lista de candidaturas não eliminadas */
    public List<Candidatura> listarCandidaturas() throws IOException {
        List<Candidatura> lista = new ArrayList<>();
        if (arquivoActual == null || !arquivoActual.exists()) return lista;
        try (RandomAccessFile raf = new RandomAccessFile(arquivoActual, "r")) {
            int total = (int)(raf.length() / RECORD_SIZE);
            for (int i = 0; i < total; i++) {
                long pos = (long) i * RECORD_SIZE;
                raf.seek(pos + OFF_ELIMINADO);
                if (raf.readByte() == 0) {
                    lista.add(lerCandidatura(raf, pos));
                }
            }
        }
        return lista;
    }

    /** Soft delete: marca o registo como eliminado */
    public void apagarCandidatura(int indice) throws IOException {
        long pos = encontrarPosicaoNaoEliminado(indice);
        if (pos < 0) throw new IOException("Candidatura não encontrada no índice " + indice);
        try (RandomAccessFile raf = new RandomAccessFile(arquivoActual, "rw")) {
            raf.seek(pos + OFF_ELIMINADO);
            raf.writeByte(1);
        }
    }

    // -------------------------------------------------------------------------
    // Escrita de um registo completo
    // -------------------------------------------------------------------------

    public void escreverCandidatura(RandomAccessFile raf, Candidatura c, long posicao) throws IOException {
        byte[] buf = new byte[RECORD_SIZE];  // zeros por defeito

        escInt   (buf, OFF_TAMANHO,        RECORD_SIZE);
        escByte  (buf, OFF_ELIMINADO,      (byte)(c.isEliminado()  ? 1 : 0));
        escByte  (buf, OFF_CONCORDO,       (byte)(c.isConcordo()   ? 1 : 0));
        escLong  (buf, OFF_TS_CRIACAO,     c.getTimestampCriacao());
        escLong  (buf, OFF_TS_ALTERACAO,   c.getTimestampUltimaAlteracao());

        // — Secção B —
        escStr(buf, OFF_NOME_ORG,   SZ_NOME_ORG,    c.getNomeOrganizacao());
        escStr(buf, OFF_NIF,        SZ_NIF,          c.getNif());
        escStr(buf, OFF_MORADA,     SZ_MORADA,       c.getMorada());
        escStr(buf, OFF_LOCALIDADE, SZ_LOCALIDADE,   c.getLocalidade());
        escStr(buf, OFF_PROVINCIA,  SZ_PROVINCIA,    c.getProvincia());
        escStr(buf, OFF_SITE,       SZ_SITE,         c.getSite());
        escStr(buf, OFF_FACEBOOK,   SZ_FACEBOOK,     c.getFacebook());
        escStr(buf, OFF_RESP1_PNOME,SZ_NOME,         c.getResp1PrimeiroNome());
        escStr(buf, OFF_RESP1_UNOME,SZ_NOME,         c.getResp1UltimoNome());
        escStr(buf, OFF_RESP1_TEL,  SZ_TELEMOVEL,    c.getResp1Telemovel());
        escStr(buf, OFF_RESP1_FUNC, SZ_FUNCAO,       c.getResp1Funcao());
        escStr(buf, OFF_RESP1_EMAIL,SZ_EMAIL,        c.getResp1Email());
        escStr(buf, OFF_RESP2_PNOME,SZ_NOME,         c.getResp2PrimeiroNome());
        escStr(buf, OFF_RESP2_UNOME,SZ_NOME,         c.getResp2UltimoNome());
        escStr(buf, OFF_RESP2_TEL,  SZ_TELEMOVEL,    c.getResp2Telemovel());
        escStr(buf, OFF_RESP2_FUNC, SZ_FUNCAO,       c.getResp2Funcao());
        escStr(buf, OFF_RESP2_EMAIL,SZ_EMAIL,        c.getResp2Email());

        // — Secção C textos —
        escStr(buf, OFF_MISSAO,      SZ_MISSAO,      c.getMissaoObjetivo());
        escStr(buf, OFF_ESTRATEGIA,  SZ_ESTRATEGIA,  c.getEstrategiaActividades());
        escStr(buf, OFF_HISTORIA,    SZ_HISTORIA,    c.getHistoriaExperiencia());
        escStr(buf, OFF_COOPERA,     SZ_COOPERA,     c.getCooperaOrganizacoes());
        escStr(buf, OFF_COMUNIDADES, SZ_COMUNIDADES, c.getComunidades());
        escStr(buf, OFF_FORMA_LEGAL, SZ_FORMA_LEGAL, c.getFormaLegal());
        escByte(buf, OFF_REGISTADA_MJ,(byte)(c.isRegistadaMJ() ? 1 : 0));
        escStr(buf, OFF_NUM_REGISTO, SZ_NUM_REG,     c.getNumeroRegisto());
        escStr(buf, OFF_MES_INICIO_C,SZ_MES,         c.getMesInicioC());
        escStr(buf, OFF_ANO_INICIO_C,SZ_ANO,         c.getAnoInicioC());
        escByte(buf, OFF_SUBSIDIARIA,(byte)(c.isSubsidiariaInternacional() ? 1 : 0));

        // — Checkboxes C.1 (Pessoa 3 completa os índices) —
        escByte(buf, OFF_CHK_EDU + 0, (byte)(c.isEduPrimeiraInfancia() ? 1 : 0));
        //escByte(buf, OFF_CHK_EDU + 1, (byte)(c.isEduAlfabetizacao()    ? 1 : 0));
        // OFF_CHK_EDU + 2..8 : Pessoa 3 adiciona

        // — Províncias onde actua —
        List<String> provs = c.getProvinciasAtua();
        for (int i = 0; i < PROVINCIAS.length; i++) {
            boolean flag = provs != null && provs.contains(PROVINCIAS[i]);
            buf[OFF_PROVS_FLAGS + i] = (byte)(flag ? 1 : 0);
        }

        // — Secção D —
        escStr(buf, OFF_RESP_GESTAO,  SZ_RESP_GESTAO, c.getResponsavelGestao());
        escStr(buf, OFF_MES_INICIO_D, SZ_MES,         c.getMesInicioD());
        escStr(buf, OFF_ANO_INICIO_D, SZ_ANO,         c.getAnoInicioD());
        escStr(buf, OFF_QUALIFICACOES,SZ_QUALIF,      c.getQualificacoesResponsavel());
        escInt(buf,  OFF_NUM_COLAB,    c.getNumColaboradoresTotal());
        escInt(buf,  OFF_NUM_COLAB_INT,c.getNumColaboradoresTempoInteiro());
        escInt(buf,  OFF_NUM_COLAB_PARC,c.getNumColaboradoresTempoParcial());
        escInt(buf,  OFF_NUM_VOLUNT,   c.getNumVoluntarios());

        // — Secção E —
        escStr(buf, OFF_IMPACTO_CRIAN, SZ_IMPACTO,   c.getImpactoCriancas());
        escStr(buf, OFF_IMPACTO_INDIR, SZ_IMPACTO,   c.getImpactoBeneficiariosIndiretos());
        escStr(buf, OFF_ESTR_COM,      SZ_ESTR_COM,  c.getEstrategiaComunicacao());

        // — Secção F —
        escDouble(buf, OFF_RECEITA_2021,  c.getReceitaAnual2021());
        escDouble(buf, OFF_RECEITA_2022,  c.getReceitaAnual2022());
        escDouble(buf, OFF_FONTES,        c.getFontePub2022());
        escDouble(buf, OFF_FONTES + 8,    c.getFonteEmpresas2022());
        escDouble(buf, OFF_FONTES + 16,   c.getFonteDoacoes2022());
        escDouble(buf, OFF_FONTES + 24,   c.getFonteOrgAngolanas2022());
        escDouble(buf, OFF_FONTES + 32,   c.getFonteOrgInternacionais2022());
        escDouble(buf, OFF_FONTES + 40,   c.getFonteReceitasProprias2022());
        escDouble(buf, OFF_FONTES + 48,   c.getFonteOutros2022());
        escByte  (buf, OFF_TEM_FINANCEIRO,(byte)(c.isTemColaboradorFinanceiro() ? 1 : 0));
        escStr(buf, OFF_DETALHE_OUTROS,  SZ_DETALHE,  c.getDetalheOutrosFinanceiros());
        escStr(buf, OFF_CAMINHO_DOC,     SZ_CAMINHO,  c.getCaminhoDocumentoComprovativo());

        // — Secção G —
        escStr(buf, OFF_CATEGORIA,   SZ_CATEGORIA,   c.getCategoriaCandiatura());
        escStr(buf, OFF_DESC_PROJETO,SZ_DESC_PROJ,   c.getDescricaoProjeto());
        escDouble(buf, OFF_PEDIDO_BFA,   c.getPedidoBFA());
        escDouble(buf, OFF_CONTRIB_PROP, c.getContribuicoesProprias());
        escDouble(buf, OFF_FIN_ASSEG,    c.getFinanciamentoAssegurado());
        escDouble(buf, OFF_CUSTO_TOTAL,  c.getCustoTotal());
        escStr(buf, OFF_IMPACTO_ESP, SZ_IMP_ESP,     c.getImpactoEsperado());
        escStr(buf, OFF_PARCERIAS,   SZ_PARCERIAS,   c.getParcerias());
        escStr(buf, OFF_SUSTENTAB,   SZ_SUSTENTAB,   c.getSustentabilidade());

        // — Secção H —
        escByte(buf, OFF_TEM_COMITE,  (byte)(c.isTemComite()   ? 1 : 0));
        escStr(buf, OFF_DESC_SUPERV,  SZ_DESC_SUPERV, c.getDescricaoSupervisao());
        escByte(buf, OFF_TEM_MINUTAS, (byte)(c.isTemMinutas()  ? 1 : 0));

        // — Secção I —
        escStr(buf, OFF_REF1_PNOME, SZ_NOME,      c.getRef1PrimeiroNome());
        escStr(buf, OFF_REF1_UNOME, SZ_NOME,      c.getRef1UltimoNome());
        escStr(buf, OFF_REF1_TEL,   SZ_TELEMOVEL, c.getRef1Telemovel());
        escStr(buf, OFF_REF1_FUNC,  SZ_FUNCAO,    c.getRef1Funcao());
        escStr(buf, OFF_REF1_EMAIL, SZ_EMAIL,     c.getRef1Email());
        escStr(buf, OFF_REF2_PNOME, SZ_NOME,      c.getRef2PrimeiroNome());
        escStr(buf, OFF_REF2_UNOME, SZ_NOME,      c.getRef2UltimoNome());
        escStr(buf, OFF_REF2_TEL,   SZ_TELEMOVEL, c.getRef2Telemovel());
        escStr(buf, OFF_REF2_FUNC,  SZ_FUNCAO,    c.getRef2Funcao());
        escStr(buf, OFF_REF2_EMAIL, SZ_EMAIL,     c.getRef2Email());

        // — Secção J —
        escByte(buf, OFF_CONFIRMA,   (byte)(c.isConfirmaDadosVerdadeiros() ? 1 : 0));
        escByte(buf, OFF_DESEJA_SUB, (byte)(c.isDesejaSubmeter()           ? 1 : 0));
        escByte(buf, OFF_CERTEZA_SUB,(byte)(c.isCertezaSubmissao()         ? 1 : 0));

        raf.seek(posicao);
        raf.write(buf);
    }

    // -------------------------------------------------------------------------
    // Leitura de um registo completo
    // -------------------------------------------------------------------------

    public Candidatura lerCandidatura(RandomAccessFile raf, long posicao) throws IOException {
        byte[] buf = new byte[RECORD_SIZE];
        raf.seek(posicao);
        raf.readFully(buf);

        Candidatura c = new Candidatura();

        c.setEliminado(buf[OFF_ELIMINADO] == 1);
        c.setConcordo(buf[OFF_CONCORDO] == 1);
        c.setTimestampCriacao(lerLong(buf, OFF_TS_CRIACAO));
        c.setTimestampUltimaAlteracao(lerLong(buf, OFF_TS_ALTERACAO));

        c.setNomeOrganizacao(lerStr(buf, OFF_NOME_ORG,   SZ_NOME_ORG));
        c.setNif             (lerStr(buf, OFF_NIF,        SZ_NIF));
        c.setMorada          (lerStr(buf, OFF_MORADA,     SZ_MORADA));
        c.setLocalidade      (lerStr(buf, OFF_LOCALIDADE, SZ_LOCALIDADE));
        c.setProvincia       (lerStr(buf, OFF_PROVINCIA,  SZ_PROVINCIA));
        c.setSite            (lerStr(buf, OFF_SITE,       SZ_SITE));
        c.setFacebook        (lerStr(buf, OFF_FACEBOOK,   SZ_FACEBOOK));
        c.setResp1PrimeiroNome(lerStr(buf,OFF_RESP1_PNOME,SZ_NOME));
        c.setResp1UltimoNome (lerStr(buf, OFF_RESP1_UNOME,SZ_NOME));
        c.setResp1Telemovel  (lerStr(buf, OFF_RESP1_TEL,  SZ_TELEMOVEL));
        c.setResp1Funcao     (lerStr(buf, OFF_RESP1_FUNC, SZ_FUNCAO));
        c.setResp1Email      (lerStr(buf, OFF_RESP1_EMAIL,SZ_EMAIL));
        c.setResp2PrimeiroNome(lerStr(buf,OFF_RESP2_PNOME,SZ_NOME));
        c.setResp2UltimoNome (lerStr(buf, OFF_RESP2_UNOME,SZ_NOME));
        c.setResp2Telemovel  (lerStr(buf, OFF_RESP2_TEL,  SZ_TELEMOVEL));
        c.setResp2Funcao     (lerStr(buf, OFF_RESP2_FUNC, SZ_FUNCAO));
        c.setResp2Email      (lerStr(buf, OFF_RESP2_EMAIL,SZ_EMAIL));

        c.setMissaoObjetivo           (lerStr(buf, OFF_MISSAO,      SZ_MISSAO));
        c.setEstrategiaActividades    (lerStr(buf, OFF_ESTRATEGIA,  SZ_ESTRATEGIA));
        c.setHistoriaExperiencia      (lerStr(buf, OFF_HISTORIA,    SZ_HISTORIA));
        c.setCooperaOrganizacoes      (lerStr(buf, OFF_COOPERA,     SZ_COOPERA));
        c.setComunidades              (lerStr(buf, OFF_COMUNIDADES, SZ_COMUNIDADES));
        c.setFormaLegal               (lerStr(buf, OFF_FORMA_LEGAL, SZ_FORMA_LEGAL));
        c.setRegistadaMJ              (buf[OFF_REGISTADA_MJ] == 1);
        c.setNumeroRegisto            (lerStr(buf, OFF_NUM_REGISTO, SZ_NUM_REG));
        c.setMesInicioC               (lerStr(buf, OFF_MES_INICIO_C,SZ_MES));
        c.setAnoInicioC               (lerStr(buf, OFF_ANO_INICIO_C,SZ_ANO));
        c.setSubsidiariaInternacional (buf[OFF_SUBSIDIARIA] == 1);

        // Checkboxes EDU (índices 0-1 preenchidos; Pessoa 3 completa)
        c.setEduPrimeiraInfancia(buf[OFF_CHK_EDU + 0] == 1);
        //c.setEduAlfabetizacao   (buf[OFF_CHK_EDU + 1] == 1);

        // Províncias onde actua
        List<String> provs = new ArrayList<>();
        for (int i = 0; i < PROVINCIAS.length; i++) {
            if (buf[OFF_PROVS_FLAGS + i] == 1) provs.add(PROVINCIAS[i]);
        }
        c.setProvinciasAtua(provs);

        c.setResponsavelGestao         (lerStr(buf, OFF_RESP_GESTAO,  SZ_RESP_GESTAO));
        c.setMesInicioD                (lerStr(buf, OFF_MES_INICIO_D, SZ_MES));
        c.setAnoInicioD                (lerStr(buf, OFF_ANO_INICIO_D, SZ_ANO));
        c.setQualificacoesResponsavel  (lerStr(buf, OFF_QUALIFICACOES,SZ_QUALIF));
        c.setNumColaboradoresTotal     (lerInt(buf, OFF_NUM_COLAB));
        c.setNumColaboradoresTempoInteiro(lerInt(buf,OFF_NUM_COLAB_INT));
        c.setNumColaboradoresTempoParcial(lerInt(buf,OFF_NUM_COLAB_PARC));
        c.setNumVoluntarios            (lerInt(buf, OFF_NUM_VOLUNT));

        c.setImpactoCriancas              (lerStr(buf, OFF_IMPACTO_CRIAN, SZ_IMPACTO));
        c.setImpactoBeneficiariosIndiretos(lerStr(buf, OFF_IMPACTO_INDIR, SZ_IMPACTO));
        c.setEstrategiaComunicacao        (lerStr(buf, OFF_ESTR_COM,      SZ_ESTR_COM));

        c.setReceitaAnual2021         (lerDouble(buf, OFF_RECEITA_2021));
        c.setReceitaAnual2022         (lerDouble(buf, OFF_RECEITA_2022));
        c.setFontePub2022             (lerDouble(buf, OFF_FONTES));
        c.setFonteEmpresas2022        (lerDouble(buf, OFF_FONTES + 8));
        c.setFonteDoacoes2022         (lerDouble(buf, OFF_FONTES + 16));
        c.setFonteOrgAngolanas2022    (lerDouble(buf, OFF_FONTES + 24));
        c.setFonteOrgInternacionais2022(lerDouble(buf,OFF_FONTES + 32));
        c.setFonteReceitasProprias2022(lerDouble(buf, OFF_FONTES + 40));
        c.setFonteOutros2022          (lerDouble(buf, OFF_FONTES + 48));
        c.setTemColaboradorFinanceiro (buf[OFF_TEM_FINANCEIRO] == 1);
        c.setDetalheOutrosFinanceiros (lerStr(buf, OFF_DETALHE_OUTROS, SZ_DETALHE));
        c.setCaminhoDocumentoComprovativo(lerStr(buf,OFF_CAMINHO_DOC,  SZ_CAMINHO));

        c.setCategoriaCandiatura      (lerStr(buf, OFF_CATEGORIA,    SZ_CATEGORIA));
        c.setDescricaoProjeto         (lerStr(buf, OFF_DESC_PROJETO, SZ_DESC_PROJ));
        c.setPedidoBFA                (lerDouble(buf, OFF_PEDIDO_BFA));
        c.setContribuicoesProprias    (lerDouble(buf, OFF_CONTRIB_PROP));
        c.setFinanciamentoAssegurado  (lerDouble(buf, OFF_FIN_ASSEG));
        c.setCustoTotal               (lerDouble(buf, OFF_CUSTO_TOTAL));
        c.setImpactoEsperado          (lerStr(buf, OFF_IMPACTO_ESP,  SZ_IMP_ESP));
        c.setParcerias                (lerStr(buf, OFF_PARCERIAS,    SZ_PARCERIAS));
        c.setSustentabilidade         (lerStr(buf, OFF_SUSTENTAB,    SZ_SUSTENTAB));

        c.setTemComite             (buf[OFF_TEM_COMITE]  == 1);
        c.setDescricaoSupervisao   (lerStr(buf, OFF_DESC_SUPERV, SZ_DESC_SUPERV));
        c.setTemMinutas            (buf[OFF_TEM_MINUTAS] == 1);

        c.setRef1PrimeiroNome(lerStr(buf, OFF_REF1_PNOME, SZ_NOME));
        c.setRef1UltimoNome  (lerStr(buf, OFF_REF1_UNOME, SZ_NOME));
        c.setRef1Telemovel   (lerStr(buf, OFF_REF1_TEL,   SZ_TELEMOVEL));
        c.setRef1Funcao      (lerStr(buf, OFF_REF1_FUNC,  SZ_FUNCAO));
        c.setRef1Email       (lerStr(buf, OFF_REF1_EMAIL, SZ_EMAIL));
        c.setRef2PrimeiroNome(lerStr(buf, OFF_REF2_PNOME, SZ_NOME));
        c.setRef2UltimoNome  (lerStr(buf, OFF_REF2_UNOME, SZ_NOME));
        c.setRef2Telemovel   (lerStr(buf, OFF_REF2_TEL,   SZ_TELEMOVEL));
        c.setRef2Funcao      (lerStr(buf, OFF_REF2_FUNC,  SZ_FUNCAO));
        c.setRef2Email       (lerStr(buf, OFF_REF2_EMAIL, SZ_EMAIL));

        c.setConfirmaDadosVerdadeiros(buf[OFF_CONFIRMA]    == 1);
        c.setDesejaSubmeter          (buf[OFF_DESEJA_SUB]  == 1);
        c.setCertezaSubmissao        (buf[OFF_CERTEZA_SUB] == 1);

        return c;
    }

    // -------------------------------------------------------------------------
    // Helper de posição
    // -------------------------------------------------------------------------

    private long encontrarPosicaoNaoEliminado(int indice) throws IOException {
        if (arquivoActual == null || !arquivoActual.exists()) return -1;
        try (RandomAccessFile raf = new RandomAccessFile(arquivoActual, "r")) {
            int total = (int)(raf.length() / RECORD_SIZE);
            int cnt = 0;
            for (int i = 0; i < total; i++) {
                long pos = (long) i * RECORD_SIZE;
                raf.seek(pos + OFF_ELIMINADO);
                if (raf.readByte() == 0) {
                    if (cnt == indice) return pos;
                    cnt++;
                }
            }
        }
        return -1;
    }

    private void garantirFicheiroExiste() throws IOException {
        if (arquivoActual == null) throw new IOException("Nenhum ficheiro definido.");
        if (!arquivoActual.exists()) arquivoActual.createNewFile();
    }

    // -------------------------------------------------------------------------
    // Primitivos — escrita no buffer
    // -------------------------------------------------------------------------

    private void escInt(byte[] b, int off, int v) {
        b[off]=(byte)(v>>24); b[off+1]=(byte)(v>>16); b[off+2]=(byte)(v>>8); b[off+3]=(byte)v;
    }
    private void escLong(byte[] b, int off, long v) {
        for (int i=7;i>=0;i--) b[off+(7-i)]=(byte)(v>>(i*8));
    }
    private void escDouble(byte[] b, int off, double v) { escLong(b, off, Double.doubleToLongBits(v)); }
    private void escByte(byte[] b, int off, byte v)     { b[off] = v; }
    private void escStr(byte[] b, int off, int max, String s) {
        if (s == null || s.isEmpty()) return;
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        int len = Math.min(bytes.length, max);
        System.arraycopy(bytes, 0, b, off, len);
    }

    // -------------------------------------------------------------------------
    // Primitivos — leitura do buffer
    // -------------------------------------------------------------------------

    private int lerInt(byte[] b, int off) {
        return ((b[off]&0xFF)<<24)|((b[off+1]&0xFF)<<16)|((b[off+2]&0xFF)<<8)|(b[off+3]&0xFF);
    }
    private long lerLong(byte[] b, int off) {
        long v=0; for(int i=0;i<8;i++) v=(v<<8)|(b[off+i]&0xFF); return v;
    }
    private double lerDouble(byte[] b, int off) { return Double.longBitsToDouble(lerLong(b, off)); }
    private String lerStr(byte[] b, int off, int max) {
        int len=0; while(len<max && b[off+len]!=0) len++;
        return new String(b, off, len, StandardCharsets.UTF_8);
    }
}
