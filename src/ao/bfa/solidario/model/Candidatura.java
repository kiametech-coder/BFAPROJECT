package ao.bfa.solidario.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * =============================================================================
 *  CLASSE CANDIDATURA — Modelo de Dados Central
 *  Responsável: Pessoa 1
 * =============================================================================
 *  Esta classe é o modelo partilhado por TODOS os membros do grupo.
 *  A Pessoa 1 deve completar todos os campos, tipos e limites.
 *  Os campos abaixo são um esqueleto baseado no formulário BFA Solidário.
 *
 *  ATENÇÃO: Não alterar os nomes dos campos sem avisar toda a equipa,
 *  pois os outros painéis dependem destes getters/setters.
 * =============================================================================
 */
public class Candidatura implements Serializable {

    // =========================================================================
    // SECÇÃO A — Introdução
    // =========================================================================
    private boolean concordo;

    // =========================================================================
    // SECÇÃO B — Detalhes de Contacto  (Pessoa 3)
    // =========================================================================
    private String nomeOrganizacao;       // max 200 chars
    private String nif;                   // max 20 chars
    private String morada;               // max 300 chars
    private String localidade;           // max 100 chars
    private String provincia;            // combobox
    private String site;                 // max 200 chars
    private String facebook;             // max 200 chars

    // Responsável 1
    private String resp1PrimeiroNome;
    private String resp1UltimoNome;
    private String resp1Telemovel;
    private String resp1Funcao;
    private String resp1Email;

    // Responsável 2
    private String resp2PrimeiroNome;
    private String resp2UltimoNome;
    private String resp2Telemovel;
    private String resp2Funcao;
    private String resp2Email;

    // =========================================================================
    // SECÇÃO C — Descrição da Organização  (Pessoa 3)
    // =========================================================================
    // Educação (checkboxes)
    private boolean eduPrimeiraInfancia;
    private boolean eduAlfabetizacao;
    private boolean eduEscolaridadeFormal;
    private boolean eduHabilidadesPraticas;
    private boolean eduEmprego;
    private boolean eduOrientacaoProfissional;
    private boolean eduApoioEducadores;
    private boolean eduInstalacoes;
    private boolean eduOutros;

    // Saúde (checkboxes)
    private boolean saudeDoencas;
    private boolean saudeServicos;
    private boolean saudeDeficiencias;
    private boolean saudeAconselhamento;
    private boolean saudeDoencasTerminais;
    private boolean saudePlaneamentoFamiliar;
    private boolean saudeAguaSaneamento;
    private boolean saudeOutros;

    // Inclusão Social (checkboxes)
    private boolean inclCriancasRua;
    private boolean inclAbrigos;
    private boolean inclTrafico;
    private boolean inclDelinquentes;
    private boolean inclEmpreendedorismo;
    private boolean inclEncontrarCasa;
    private boolean inclRegistos;
    private boolean inclEstabilidadeFamiliar;
    private boolean inclDireitosHumanos;
    private boolean inclOutros;

    // Forma legal
    private String formaLegal;           // Associação / Fundação / Universidade
    private List<String> documentosOrg = new ArrayList<>();

    // Registo
    private boolean registadaMJ;
    private String numeroRegisto;

    // Início actividade
    private String mesInicioC;
    private String anoInicioC;

    // Textos
    private String missaoObjetivo;       // max 150 palavras
    private String estrategiaActividades; // max 300 palavras
    private List<String> provinciasAtua = new ArrayList<>();
    private String comunidades;
    private String historiaExperiencia;
    private boolean subsidiariaInternacional;
    private String cooperaOrganizacoes;  // max 200 palavras

    // =========================================================================
    // SECÇÃO D — Colaboradores e Liderança  (Pessoa 4)
    // =========================================================================
    private String responsavelGestao;    // max 150 palavras
    private String mesInicioD;
    private String anoInicioD;
    private String qualificacoesResponsavel; // max 200 palavras
    private int numColaboradoresTotal;
    private int numColaboradoresTempoInteiro;
    private int numColaboradoresTempoParcial;
    private int numVoluntarios;

    // =========================================================================
    // SECÇÃO E — Experiência e Resultados Passados  (Pessoa 4)
    // =========================================================================
    private String impactoCriancas;      // max 300 palavras
    private String impactoBeneficiariosIndiretos; // max 300 palavras
    private String estrategiaComunicacao; // max 200 palavras

    // =========================================================================
    // SECÇÃO F — Questões Financeiras  (Pessoa 5)
    // =========================================================================
    private double receitaAnual2021;
    private double receitaAnual2022;
    private double fontePub2022;
    private double fonteEmpresas2022;
    private double fonteDoacoes2022;
    private double fonteOrgAngolanas2022;
    private double fonteOrgInternacionais2022;
    private double fonteReceitasProprias2022;
    private double fonteOutros2022;
    private boolean temColaboradorFinanceiro;
    private List<String> documentosFinanceiros = new ArrayList<>();
    private String detalheOutrosFinanceiros;  // max 50 palavras
    private String caminhoDocumentoComprovativo;

    // =========================================================================
    // SECÇÃO G — Proposta  (Pessoa 5)
    // =========================================================================
    private String categoriaCandiatura;
    private List<String> tiposDespesa = new ArrayList<>();
    private String descricaoProjeto;     // max 300 palavras
    private double pedidoBFA;
    private double contribuicoesProprias;
    private double financiamentoAssegurado;
    private double custoTotal;
    private List<String[]> componentesDespesa = new ArrayList<>(); // [descricao, montante]
    private String impactoEsperado;      // max 300 palavras
    private String parcerias;
    private String sustentabilidade;     // max 200 palavras
    private List<String[]> provinciasImplementacao = new ArrayList<>(); // [provincia, comunidade1, comunidade2]

    // =========================================================================
    // SECÇÃO H — Governo da Instituição  (Pessoa 4)
    // =========================================================================
    private boolean temComite;
    private String descricaoSupervisao;
    private boolean temMinutas;

    // =========================================================================
    // SECÇÃO I — Referências  (Pessoa 4)
    // =========================================================================
    private String ref1PrimeiroNome;
    private String ref1UltimoNome;
    private String ref1Telemovel;
    private String ref1Funcao;
    private String ref1Email;

    private String ref2PrimeiroNome;
    private String ref2UltimoNome;
    private String ref2Telemovel;
    private String ref2Funcao;
    private String ref2Email;

    // =========================================================================
    // SECÇÃO J — Termos e Condições  (Pessoa 5)
    // =========================================================================
    private boolean confirmaDadosVerdadeiros;
    private boolean desejaSubmeter;
    private boolean certezaSubmissao;

    // =========================================================================
    // METADADOS (geridos internamente pela Pessoa 2)
    // =========================================================================
    private boolean eliminado = false;
    private long timestampCriacao = System.currentTimeMillis();
    private long timestampUltimaAlteracao = System.currentTimeMillis();

    // =========================================================================
    // GETTERS E SETTERS — Secção A
    // =========================================================================
    public boolean isConcordo() { return concordo; }
    public void setConcordo(boolean concordo) { this.concordo = concordo; }

    // =========================================================================
    // GETTERS E SETTERS — Secção B
    // =========================================================================
    public String getNomeOrganizacao() { return nomeOrganizacao; }
    public void setNomeOrganizacao(String v) { this.nomeOrganizacao = v; }
    public String getNif() { return nif; }
    public void setNif(String v) { this.nif = v; }
    public String getMorada() { return morada; }
    public void setMorada(String v) { this.morada = v; }
    public String getLocalidade() { return localidade; }
    public void setLocalidade(String v) { this.localidade = v; }
    public String getProvincia() { return provincia; }
    public void setProvincia(String v) { this.provincia = v; }
    public String getSite() { return site; }
    public void setSite(String v) { this.site = v; }
    public String getFacebook() { return facebook; }
    public void setFacebook(String v) { this.facebook = v; }

    public String getResp1PrimeiroNome() { return resp1PrimeiroNome; }
    public void setResp1PrimeiroNome(String v) { this.resp1PrimeiroNome = v; }
    public String getResp1UltimoNome() { return resp1UltimoNome; }
    public void setResp1UltimoNome(String v) { this.resp1UltimoNome = v; }
    public String getResp1Telemovel() { return resp1Telemovel; }
    public void setResp1Telemovel(String v) { this.resp1Telemovel = v; }
    public String getResp1Funcao() { return resp1Funcao; }
    public void setResp1Funcao(String v) { this.resp1Funcao = v; }
    public String getResp1Email() { return resp1Email; }
    public void setResp1Email(String v) { this.resp1Email = v; }

    public String getResp2PrimeiroNome() { return resp2PrimeiroNome; }
    public void setResp2PrimeiroNome(String v) { this.resp2PrimeiroNome = v; }
    public String getResp2UltimoNome() { return resp2UltimoNome; }
    public void setResp2UltimoNome(String v) { this.resp2UltimoNome = v; }
    public String getResp2Telemovel() { return resp2Telemovel; }
    public void setResp2Telemovel(String v) { this.resp2Telemovel = v; }
    public String getResp2Funcao() { return resp2Funcao; }
    public void setResp2Funcao(String v) { this.resp2Funcao = v; }
    public String getResp2Email() { return resp2Email; }
    public void setResp2Email(String v) { this.resp2Email = v; }

    // Secção C getters/setters
    public boolean isEduPrimeiraInfancia() { return eduPrimeiraInfancia; }
    public void setEduPrimeiraInfancia(boolean v) { this.eduPrimeiraInfancia = v; }
    public String getMissaoObjetivo() { return missaoObjetivo; }
    public void setMissaoObjetivo(String v) { this.missaoObjetivo = v; }
    public String getEstrategiaActividades() { return estrategiaActividades; }
    public void setEstrategiaActividades(String v) { this.estrategiaActividades = v; }
    public List<String> getProvinciasAtua() { return provinciasAtua; }
    public void setProvinciasAtua(List<String> v) { this.provinciasAtua = v; }
    public String getComunidades() { return comunidades; }
    public void setComunidades(String v) { this.comunidades = v; }
    public String getHistoriaExperiencia() { return historiaExperiencia; }
    public void setHistoriaExperiencia(String v) { this.historiaExperiencia = v; }
    public boolean isSubsidiariaInternacional() { return subsidiariaInternacional; }
    public void setSubsidiariaInternacional(boolean v) { this.subsidiariaInternacional = v; }
    public String getCooperaOrganizacoes() { return cooperaOrganizacoes; }
    public void setCooperaOrganizacoes(String v) { this.cooperaOrganizacoes = v; }
    public String getFormaLegal() { return formaLegal; }
    public void setFormaLegal(String v) { this.formaLegal = v; }
    public List<String> getDocumentosOrg() { return documentosOrg; }
    public void setDocumentosOrg(List<String> v) { this.documentosOrg = v; }
    public boolean isRegistadaMJ() { return registadaMJ; }
    public void setRegistadaMJ(boolean v) { this.registadaMJ = v; }
    public String getNumeroRegisto() { return numeroRegisto; }
    public void setNumeroRegisto(String v) { this.numeroRegisto = v; }
    public String getMesInicioC() { return mesInicioC; }
    public void setMesInicioC(String v) { this.mesInicioC = v; }
    public String getAnoInicioC() { return anoInicioC; }
    public void setAnoInicioC(String v) { this.anoInicioC = v; }

    // Secção D
    public String getResponsavelGestao() { return responsavelGestao; }
    public void setResponsavelGestao(String v) { this.responsavelGestao = v; }
    public String getMesInicioD() { return mesInicioD; }
    public void setMesInicioD(String v) { this.mesInicioD = v; }
    public String getAnoInicioD() { return anoInicioD; }
    public void setAnoInicioD(String v) { this.anoInicioD = v; }
    public String getQualificacoesResponsavel() { return qualificacoesResponsavel; }
    public void setQualificacoesResponsavel(String v) { this.qualificacoesResponsavel = v; }
    public int getNumColaboradoresTotal() { return numColaboradoresTotal; }
    public void setNumColaboradoresTotal(int v) { this.numColaboradoresTotal = v; }
    public int getNumColaboradoresTempoInteiro() { return numColaboradoresTempoInteiro; }
    public void setNumColaboradoresTempoInteiro(int v) { this.numColaboradoresTempoInteiro = v; }
    public int getNumColaboradoresTempoParcial() { return numColaboradoresTempoParcial; }
    public void setNumColaboradoresTempoParcial(int v) { this.numColaboradoresTempoParcial = v; }
    public int getNumVoluntarios() { return numVoluntarios; }
    public void setNumVoluntarios(int v) { this.numVoluntarios = v; }

    // Secção E
    public String getImpactoCriancas() { return impactoCriancas; }
    public void setImpactoCriancas(String v) { this.impactoCriancas = v; }
    public String getImpactoBeneficiariosIndiretos() { return impactoBeneficiariosIndiretos; }
    public void setImpactoBeneficiariosIndiretos(String v) { this.impactoBeneficiariosIndiretos = v; }
    public String getEstrategiaComunicacao() { return estrategiaComunicacao; }
    public void setEstrategiaComunicacao(String v) { this.estrategiaComunicacao = v; }

    // Secção F
    public double getReceitaAnual2021() { return receitaAnual2021; }
    public void setReceitaAnual2021(double v) { this.receitaAnual2021 = v; }
    public double getReceitaAnual2022() { return receitaAnual2022; }
    public void setReceitaAnual2022(double v) { this.receitaAnual2022 = v; }
    public double getFontePub2022() { return fontePub2022; }
    public void setFontePub2022(double v) { this.fontePub2022 = v; }
    public double getFonteEmpresas2022() { return fonteEmpresas2022; }
    public void setFonteEmpresas2022(double v) { this.fonteEmpresas2022 = v; }
    public double getFonteDoacoes2022() { return fonteDoacoes2022; }
    public void setFonteDoacoes2022(double v) { this.fonteDoacoes2022 = v; }
    public double getFonteOrgAngolanas2022() { return fonteOrgAngolanas2022; }
    public void setFonteOrgAngolanas2022(double v) { this.fonteOrgAngolanas2022 = v; }
    public double getFonteOrgInternacionais2022() { return fonteOrgInternacionais2022; }
    public void setFonteOrgInternacionais2022(double v) { this.fonteOrgInternacionais2022 = v; }
    public double getFonteReceitasProprias2022() { return fonteReceitasProprias2022; }
    public void setFonteReceitasProprias2022(double v) { this.fonteReceitasProprias2022 = v; }
    public double getFonteOutros2022() { return fonteOutros2022; }
    public void setFonteOutros2022(double v) { this.fonteOutros2022 = v; }
    public boolean isTemColaboradorFinanceiro() { return temColaboradorFinanceiro; }
    public void setTemColaboradorFinanceiro(boolean v) { this.temColaboradorFinanceiro = v; }
    public List<String> getDocumentosFinanceiros() { return documentosFinanceiros; }
    public void setDocumentosFinanceiros(List<String> v) { this.documentosFinanceiros = v; }
    public String getDetalheOutrosFinanceiros() { return detalheOutrosFinanceiros; }
    public void setDetalheOutrosFinanceiros(String v) { this.detalheOutrosFinanceiros = v; }
    public String getCaminhoDocumentoComprovativo() { return caminhoDocumentoComprovativo; }
    public void setCaminhoDocumentoComprovativo(String v) { this.caminhoDocumentoComprovativo = v; }

    // Secção G
    public String getCategoriaCandiatura() { return categoriaCandiatura; }
    public void setCategoriaCandiatura(String v) { this.categoriaCandiatura = v; }
    public List<String> getTiposDespesa() { return tiposDespesa; }
    public void setTiposDespesa(List<String> v) { this.tiposDespesa = v; }
    public String getDescricaoProjeto() { return descricaoProjeto; }
    public void setDescricaoProjeto(String v) { this.descricaoProjeto = v; }
    public double getPedidoBFA() { return pedidoBFA; }
    public void setPedidoBFA(double v) { this.pedidoBFA = v; }
    public double getContribuicoesProprias() { return contribuicoesProprias; }
    public void setContribuicoesProprias(double v) { this.contribuicoesProprias = v; }
    public double getFinanciamentoAssegurado() { return financiamentoAssegurado; }
    public void setFinanciamentoAssegurado(double v) { this.financiamentoAssegurado = v; }
    public double getCustoTotal() { return custoTotal; }
    public void setCustoTotal(double v) { this.custoTotal = v; }
    public List<String[]> getComponentesDespesa() { return componentesDespesa; }
    public void setComponentesDespesa(List<String[]> v) { this.componentesDespesa = v; }
    public String getImpactoEsperado() { return impactoEsperado; }
    public void setImpactoEsperado(String v) { this.impactoEsperado = v; }
    public String getParcerias() { return parcerias; }
    public void setParcerias(String v) { this.parcerias = v; }
    public String getSustentabilidade() { return sustentabilidade; }
    public void setSustentabilidade(String v) { this.sustentabilidade = v; }
    public List<String[]> getProvinciasImplementacao() { return provinciasImplementacao; }
    public void setProvinciasImplementacao(List<String[]> v) { this.provinciasImplementacao = v; }

    // Secção H
    public boolean isTemComite() { return temComite; }
    public void setTemComite(boolean v) { this.temComite = v; }
    public String getDescricaoSupervisao() { return descricaoSupervisao; }
    public void setDescricaoSupervisao(String v) { this.descricaoSupervisao = v; }
    public boolean isTemMinutas() { return temMinutas; }
    public void setTemMinutas(boolean v) { this.temMinutas = v; }

    // Secção I
    public String getRef1PrimeiroNome() { return ref1PrimeiroNome; }
    public void setRef1PrimeiroNome(String v) { this.ref1PrimeiroNome = v; }
    public String getRef1UltimoNome() { return ref1UltimoNome; }
    public void setRef1UltimoNome(String v) { this.ref1UltimoNome = v; }
    public String getRef1Telemovel() { return ref1Telemovel; }
    public void setRef1Telemovel(String v) { this.ref1Telemovel = v; }
    public String getRef1Funcao() { return ref1Funcao; }
    public void setRef1Funcao(String v) { this.ref1Funcao = v; }
    public String getRef1Email() { return ref1Email; }
    public void setRef1Email(String v) { this.ref1Email = v; }
    public String getRef2PrimeiroNome() { return ref2PrimeiroNome; }
    public void setRef2PrimeiroNome(String v) { this.ref2PrimeiroNome = v; }
    public String getRef2UltimoNome() { return ref2UltimoNome; }
    public void setRef2UltimoNome(String v) { this.ref2UltimoNome = v; }
    public String getRef2Telemovel() { return ref2Telemovel; }
    public void setRef2Telemovel(String v) { this.ref2Telemovel = v; }
    public String getRef2Funcao() { return ref2Funcao; }
    public void setRef2Funcao(String v) { this.ref2Funcao = v; }
    public String getRef2Email() { return ref2Email; }
    public void setRef2Email(String v) { this.ref2Email = v; }

    // Secção J
    public boolean isConfirmaDadosVerdadeiros() { return confirmaDadosVerdadeiros; }
    public void setConfirmaDadosVerdadeiros(boolean v) { this.confirmaDadosVerdadeiros = v; }
    public boolean isDesejaSubmeter() { return desejaSubmeter; }
    public void setDesejaSubmeter(boolean v) { this.desejaSubmeter = v; }
    public boolean isCertezaSubmissao() { return certezaSubmissao; }
    public void setCertezaSubmissao(boolean v) { this.certezaSubmissao = v; }

    // Metadados
    public boolean isEliminado() { return eliminado; }
    public void setEliminado(boolean v) { this.eliminado = v; }
    public long getTimestampCriacao() { return timestampCriacao; }
    public void setTimestampCriacao(long v) { this.timestampCriacao = v; }
    public long getTimestampUltimaAlteracao() { return timestampUltimaAlteracao; }
    public void setTimestampUltimaAlteracao(long v) { this.timestampUltimaAlteracao = v; }

    @Override
    public String toString() {
        return (nomeOrganizacao != null ? nomeOrganizacao : "Nova Candidatura")
                + " | " + (nif != null ? nif : "—");
    }
}
