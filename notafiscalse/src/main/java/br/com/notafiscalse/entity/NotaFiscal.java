package br.com.notafiscalse.entity;

import java.util.Date;

public class NotaFiscal{
	/* 
	   Número da Nota Fiscal de Serviço Eletrônica,
       formado pelo ano com 04 (quatro) dígitos e um
       número seqüencial com 11 posições – Formato
       AAAANNNNNNNNNNN. 
    */
    private long numeroNfse; 	
    /* Código de verificação do número da nota */
    
    /* Data de Emissão da Nota Fiscal */
    private Date dataEmissaoNfse;
    /* código de verificação da Nota Fiscal */
    private String codigoVerificacao;
    /* Código de status do RPS
		1 – Normal
		2 – Cancelado 
	*/
	private int statusRps;
    /* Código de natureza da operação
		1 – Tributação no município
		2 - Tributação fora do município
		3 - Isenção
		4 - Imune
		5 –Exigibilidade suspensa por decisão judicial
		6 – Exigibilidade suspensa por procedimento administrativo
	*/
	private int naturezaOperacao;
	
	/* Mês e ano da prestação de serviço. (AAAAMM). */
	private String competencia;
	
	/* Identificação Incentivador Cultural 
		1 - Sim
		2 – Não 
	*/
	private int incentivadorCultural;
	/* Identificação de OptanteSimplesNacional
		1 - Sim
		2 – Não 
	*/
	private int optanteSimplesNacional;
	/* Quantidade de RPS do Lote */
	private int quantidadeRps;
	/* Número do RPS */
	private long numeroRps;
	
	/* Número de série do RPS */
	private String serieRps;
	/* Código de tipo de RPS
		1 - RPS
		2 – Nota Fiscal Conjugada (Mista)
		3 – Cupom
	*/ 
	private int tipoRps;
	
	/* Data Emissão do RPS(AAAA-MM-DD), campo obrigatório apenas para NFS-e geradas pela emissão de RPS */
	private Date dataEmissaoRps;
		
	/* Número do Lote de RPS */
	private long numeroLote;
	/* Número do protocolo de recebimento do RPS */
	private String numeroProtocolo;
	/* Código de situação de lote de RPS
		1 – Não Recebido
		2 – Não Processado
		3 – Processado com Erro
		4 – Processado com Sucesso
	*/
	private int situacaoLoteRps;
	
	/* Código de mensagem de retorno de serviço. */
	private String codigoMensagemAlerta;
	/* Descrição da mensagem de retorno de serviço. */
	private String descricaoMensagemAlerta;
	
	/* Descrição da mensagem de correção do retorno de serviço. */
	private String descricaoCorrecaoAlerta;
	
	/* Código de cancelamento com base na tabela de Erros e alertas. */
	private String codigoCancelamentoNfse;
	
	/* Informações adicionais ao documento. */
	private String outrasInformacoes;
	
	/* Valor monetário.
		Formato: 0.00 (ponto separando casa decimal)
		Ex: 1.234,56 = 1234.56
		1.000,00 = 1000.00
		1.000,00 = 1000 
	*/
	private double valorServicos;
	
	/* Identificação de ISS retido
		1 - Sim
		2 – Não 
	 */
	private int issRetido;
	
	/* Valor do ISS em R$ */
	private double valorIssRetido;
	
	/* Código de item da lista de serviço */
	private String itemListaServico;
	
	/* Código do serviço prestado no próprio município */
	private String codigoTributacaoMunicipio;
	
	/* Alíquota. Valor percentual.
		Formato: 0.0000
		Ex: 1% = 0.01
		25,5% = 0.255
		100% = 1.0000 ou 1
	*/
	private double aliquota;
	
	private double valorDeducoes;
	private double valorPis;
	private double valorCofins;
	private double valorInss;
	private double valorIr;
	private double valorCsll;
	private double outrasRetencoes;
	private double baseCalculo;
	private double valorLiquidoNfse; 
	private double descontoIncondicionado;
	private double descontoCondicionado;
	private Double valorIss;
	
	/* Discriminação do conteúdo da NFS-e */
	private String discriminacao;
	
	/* Código de identificação do município onde servico foi prestado, conforme tabela do IBGE */ 
	private int codigoMunicipioIbge;
	
	/* Número de inscrição municipal */
	private String inscricaoMunicipalPrestador;
	/* Razão Social do contribuinte */
	private String razaoSocialPrestador;
	/* Número CNPJ */
	private String cnpjPrestador;
	/* 	Endereço */
	private String enderecoPrestador;
	/* Número do Endereço */
	private String numeroEnderecoPrestador;
	/* Complemento do Endereço */
	private String complementoEnderecoPrestador;
	/* Bairro */
	private String bairroPrestador;
	/* código do municipio do estabelecimento prestador do serviço(tabela do IBGE) */
	private int cidadePrestador;
	/* Nome da Cidade do Prestador de Serviços */
	private String nomeCidadePrestador;
    /* Sigla da unidade federativa */
	private String ufPrestador;
	/* Número do CEP */
	private int cepPrestador;
	/* E-Mail */
	private  String emailPrestador;
	/* Telefone de contato do prestador de serviços */
	private String telefonePrestador;
	
	/* Número de CPF/CNPJ do tomador */
	private String cpfCnpjTomador;
	/* Indicador de uso de CPF ou CNPJ do tomador
		1 – CPF
		2 – CNPJ
		3 – Não Informado
	*/
	private int indicacaoCpfCnpjTomador;
	 /* Nome ou Razão Social do tomador de serviços */
	private String razaoSocialTomador;
	/* 	Endereço Tomador */
	private String enderecoTomador;
	/* Número do Endereço */
	private String numeroEnderecoTomador;
	/* Complemento do Endereço */
	private String complementoEnderecoTomador;
	/* Bairro */
	private String bairroTomador;
	/* código do municipio do estabelecimento tomador do serviço(tabela do IBGE) */
	private int cidadeTomador;
	/* Nome da cidade do tomador de serviços */
	private String nomeCidadeTomador;
	/* Sigla da unidade federativa do tomador */
	private String ufTomador;
	/* Número do CEP tomador */
	private int cepTomador;
	/* E-Mail tomador */
	private  String emailTomador;
	/* Telefone de contato do tomador*/
	private String telefoneTomador;
		
	
	
	public long getNumeroNfse() {
		return numeroNfse;
	}
	public void setNumeroNfse(long numeroNfse) {
		this.numeroNfse = numeroNfse;
	}
	public Date getDataEmissaoNfse() {
		return dataEmissaoNfse;
	}
	public void setDataEmissaoNfse(Date dataEmissao) {
		this.dataEmissaoNfse = dataEmissao;
	}
	public String getCodigoVerificacao() {
		return codigoVerificacao;
	}
	public void setCodigoVerificacao(String codigoVerificacao) {
		this.codigoVerificacao = codigoVerificacao;
	}
	public int getStatusRps() {
		return statusRps;
	}
	public void setStatusRps(int statusRps) {
		this.statusRps = statusRps;
	}
	public int getNaturezaOperacao() {
		return naturezaOperacao;
	}
	public void setNaturezaOperacao(int naturezaOperacao) {
		this.naturezaOperacao = naturezaOperacao;
	}
			
	public int getIncentivadorCultural() {
		return incentivadorCultural;
	}
	public void setIncentivadorCultural(int incentivadorCultural) {
		this.incentivadorCultural = incentivadorCultural;
	}
	public int getOptanteSimplesNacional() {
		return optanteSimplesNacional;
	}
	public void setOptanteSimplesNacional(int optanteSimplesNacional) {
		this.optanteSimplesNacional = optanteSimplesNacional;
	}
	public int getQuantidadeRps() {
		return quantidadeRps;
	}
	public void setQuantidadeRps(int quantidadeRps) {
		this.quantidadeRps = quantidadeRps;
	}
	public long getNumeroRps() {
		return numeroRps;
	}
	public void setNumeroRps(long numeroRps) {
		this.numeroRps = numeroRps;
	}
	public String getSerieRps() {
		return serieRps;
	}
	public void setSerieRps(String serieRps) {
		this.serieRps = serieRps;
	}
	public int getTipoRps() {
		return tipoRps;
	}
	public void setTipoRps(int tipoRps) {
		this.tipoRps = tipoRps;
	}
	public String getOutrasInformacoes() {
		return outrasInformacoes;
	}
	public void setOutrasInformacoes(String outrasInformacoes) {
		this.outrasInformacoes = outrasInformacoes;
	}
	public double getValorServicos() {
		return valorServicos;
	}
	public void setValorServicos(double valorServicos) {
		this.valorServicos = valorServicos;
	}
		
	public int getIssRetido() {
		return issRetido;
	}
	public void setIssRetido(int issRetido) {
		this.issRetido = issRetido;
	}
	public String getItemListaServico() {
		return itemListaServico;
	}
	public void setItemListaServico(String itemListaServico) {
		this.itemListaServico = itemListaServico;
	}
		
	public double getAliquota() {
		return aliquota;
	}
	public void setAliquota(double aliquota) {
		this.aliquota = aliquota;
	}
	public String getDiscriminacao() {
		return discriminacao;
	}
	public void setDiscriminacao(String discriminacao) {
		this.discriminacao = discriminacao;
	}
	public int getCodigoMunicipioIbge() {
		return codigoMunicipioIbge;
	}
	public void setCodigoMunicipioIbge(int codigoMunicipioIbge) {
		this.codigoMunicipioIbge = codigoMunicipioIbge;
	}
	public String getInscricaoMunicipalPrestador() {
		return inscricaoMunicipalPrestador;
	}
	public void setInscricaoMunicipalPrestador(String inscricaoMunicipalPrestador) {
		this.inscricaoMunicipalPrestador = inscricaoMunicipalPrestador;
	}
	public String getRazaoSocialPrestador() {
		return razaoSocialPrestador;
	}
	public void setRazaoSocialPrestador(String razaoSocialPrestador) {
		this.razaoSocialPrestador = razaoSocialPrestador;
	}
	public String getCnpjPrestador() {
		return cnpjPrestador;
	}
	public void setCnpjPrestador(String cnpjPrestador) {
		this.cnpjPrestador = cnpjPrestador;
	}
	public String getEnderecoPrestador() {
		return enderecoPrestador;
	}
	public void setEnderecoPrestador(String enderecoPrestador) {
		this.enderecoPrestador = enderecoPrestador;
	}
	public String getNumeroEnderecoPrestador() {
		return numeroEnderecoPrestador;
	}
	public void setNumeroEnderecoPrestador(String numeroEnderecoPrestador) {
		this.numeroEnderecoPrestador = numeroEnderecoPrestador;
	}
	public String getComplementoEnderecoPrestador() {
		return complementoEnderecoPrestador;
	}
	public void setComplementoEnderecoPrestador(String complementoEnderecoPrestador) {
		this.complementoEnderecoPrestador = complementoEnderecoPrestador;
	}
	public String getBairroPrestador() {
		return bairroPrestador;
	}
	public void setBairroPrestador(String bairroPrestador) {
		this.bairroPrestador = bairroPrestador;
	}
	public String getUfPrestador() {
		return ufPrestador;
	}
	public void setUfPrestador(String ufPrestador) {
		this.ufPrestador = ufPrestador;
	}
	public int getCepPrestador() {
		return cepPrestador;
	}
	public void setCepPrestador(int cepPrestador) {
		this.cepPrestador = cepPrestador;
	}
	public String getEmailPrestador() {
		return emailPrestador;
	}
	public void setEmailPrestador(String emailPrestador) {
		this.emailPrestador = emailPrestador;
	}
	
	public String getCpfCnpjTomador() {
		return cpfCnpjTomador;
	}
	public void setCpfCnpjTomador(String cpfCnpjTomador) {
		this.cpfCnpjTomador = cpfCnpjTomador;
	}
	public int getIndicacaoCpfCnpjTomador() {
		return indicacaoCpfCnpjTomador;
	}
	public void setIndicacaoCpfCnpjTomador(int indicacaoCpfCnpjTomador) {
		this.indicacaoCpfCnpjTomador = indicacaoCpfCnpjTomador;
	}
		
	public String getRazaoSocialTomador() {
		return razaoSocialTomador;
	}
	public void setRazaoSocialTomador(String razaoSocialTomador) {
		this.razaoSocialTomador = razaoSocialTomador;
	}
	public long getNumeroLote() {
		return numeroLote;
	}
	public void setNumeroLote(long numeroLote) {
		this.numeroLote = numeroLote;
	}
	public String getNumeroProtocolo() {
		return numeroProtocolo;
	}
	public void setNumeroProtocolo(String numeroProtocolo) {
		this.numeroProtocolo = numeroProtocolo;
	}
	public int getSituacaoLoteRps() {
		return situacaoLoteRps;
	}
	public void setSituacaoLoteRps(int situacaoLoteRps) {
		this.situacaoLoteRps = situacaoLoteRps;
	}
	public String getCodigoMensagemAlerta() {
		return codigoMensagemAlerta;
	}
	public void setCodigoMensagemAlerta(String codigoMensagemAlerta) {
		this.codigoMensagemAlerta = codigoMensagemAlerta;
	}
	public String getDescricaoMensagemAlerta() {
		return descricaoMensagemAlerta;
	}
	public void setDescricaoMensagemAlerta(String descricaoMensagemAlerta) {
		this.descricaoMensagemAlerta = descricaoMensagemAlerta;
	}
	public String getCodigoCancelamentoNfse() {
		return codigoCancelamentoNfse;
	}
	public void setCodigoCancelamentoNfse(String codigoCancelamentoNfse) {
		this.codigoCancelamentoNfse = codigoCancelamentoNfse;
	}
	public Date getDataEmissaoRps() {
		return dataEmissaoRps;
	}
	public void setDataEmissaoRps(Date dataEmissaoRps) {
		this.dataEmissaoRps = dataEmissaoRps;
	}
	public int getCidadePrestador() {
		return cidadePrestador;
	}
	public void setCidadePrestador(int cidadePrestador) {
		this.cidadePrestador = cidadePrestador;
	}
	public String getCodigoTributacaoMunicipio() {
		return codigoTributacaoMunicipio;
	}
	public void setCodigoTributacaoMunicipio(String codigoTributacaoMunicipio) {
		this.codigoTributacaoMunicipio = codigoTributacaoMunicipio;
	}
	public double getValorIssRetido() {
		return valorIssRetido;
	}
	public void setValorIssRetido(double valorIssRetido) {
		this.valorIssRetido = valorIssRetido;
	}
	public String getNomeCidadePrestador() {
		return nomeCidadePrestador;
	}
	public void setNomeCidadePrestador(String nomeCidadePrestador) {
		this.nomeCidadePrestador = nomeCidadePrestador;
	}
	public String getTelefonePrestador() {
		return telefonePrestador;
	}
	public void setTelefonePrestador(String telefonePrestador) {
		this.telefonePrestador = telefonePrestador;
	}
	
	public String getEnderecoTomador() {
		return enderecoTomador;
	}
	public void setEnderecoTomador(String enderecoTomador) {
		this.enderecoTomador = enderecoTomador;
	}
	public String getNumeroEnderecoTomador() {
		return numeroEnderecoTomador;
	}
	public void setNumeroEnderecoTomador(String numeroEnderecoTomador) {
		this.numeroEnderecoTomador = numeroEnderecoTomador;
	}
	public String getComplementoEnderecoTomador() {
		return complementoEnderecoTomador;
	}
	public void setComplementoEnderecoTomador(String complementoEnderecoTomador) {
		this.complementoEnderecoTomador = complementoEnderecoTomador;
	}
	public String getBairroTomador() {
		return bairroTomador;
	}
	public void setBairroTomador(String bairroTomador) {
		this.bairroTomador = bairroTomador;
	}
	public int getCidadeTomador() {
		return cidadeTomador;
	}
	public void setCidadeTomador(int cidadeTomador) {
		this.cidadeTomador = cidadeTomador;
	}
	public String getNomeCidadeTomador() {
		return nomeCidadeTomador;
	}
	public void setNomeCidadeTomador(String nomeCidadeTomador) {
		this.nomeCidadeTomador = nomeCidadeTomador;
	}
	public String getUfTomador() {
		return ufTomador;
	}
	public void setUfTomador(String ufTomador) {
		this.ufTomador = ufTomador;
	}
	public int getCepTomador() {
		return cepTomador;
	}
	public void setCepTomador(int cepTomador) {
		this.cepTomador = cepTomador;
	}
	public String getEmailTomador() {
		return emailTomador;
	}
	public void setEmailTomador(String emailTomador) {
		this.emailTomador = emailTomador;
	}
	public String getTelefoneTomador() {
		return telefoneTomador;
	}
	public void setTelefoneTomador(String telefoneTomador) {
		this.telefoneTomador = telefoneTomador;
	}
	public double getValorDeducoes() {
		return valorDeducoes;
	}
	public void setValorDeducoes(double valorDeducoes) {
		this.valorDeducoes = valorDeducoes;
	}
	public double getValorPis() {
		return valorPis;
	}
	public void setValorPis(double valorPis) {
		this.valorPis = valorPis;
	}
	public double getValorCofins() {
		return valorCofins;
	}
	public void setValorCofins(double valorCofins) {
		this.valorCofins = valorCofins;
	}
	public double getValorInss() {
		return valorInss;
	}
	public void setValorInss(double valorInss) {
		this.valorInss = valorInss;
	}
	public double getValorIr() {
		return valorIr;
	}
	public void setValorIr(double valorIr) {
		this.valorIr = valorIr;
	}
	public double getValorCsll() {
		return valorCsll;
	}
	public void setValorCsll(double valorCsll) {
		this.valorCsll = valorCsll;
	}
	public double getOutrasRetencoes() {
		return outrasRetencoes;
	}
	public void setOutrasRetencoes(double outrasRetencoes) {
		this.outrasRetencoes = outrasRetencoes;
	}
	public double getBaseCalculo() {
		return baseCalculo;
	}
	public void setBaseCalculo(double baseCalculo) {
		this.baseCalculo = baseCalculo;
	}
	public double getValorLiquidoNfse() {
		return valorLiquidoNfse;
	}
	public void setValorLiquidoNfse(double valorLiquidoNfse) {
		this.valorLiquidoNfse = valorLiquidoNfse;
	}
	public double getDescontoIncondicionado() {
		return descontoIncondicionado;
	}
	public void setDescontoIncondicionado(double descontoIncondicionado) {
		this.descontoIncondicionado = descontoIncondicionado;
	}
	public double getDescontoCondicionado() {
		return descontoCondicionado;
	}
	public void setDescontoCondicionado(double descontoCondicionado) {
		this.descontoCondicionado = descontoCondicionado;
	}
	public String getCompetencia() {
		return competencia;
	}
	public void setCompetencia(String competencia) {
		this.competencia = competencia;
	}
	public Double getValorIss() {
		return valorIss;
	}
	public void setValorIss(Double valorIss) {
		this.valorIss = valorIss;
	}
	public String getDescricaoCorrecaoAlerta() {
		return descricaoCorrecaoAlerta;
	}
	public void setDescricaoCorrecaoAlerta(String descricaoCorrecaoAlerta) {
		this.descricaoCorrecaoAlerta = descricaoCorrecaoAlerta;
	}
	
	
	
}
