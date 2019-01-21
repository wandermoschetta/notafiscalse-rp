package br.com.notafiscalse.test;

import java.util.Date;
import java.util.Timer;

import br.com.notafiscalse.config.Configura;
import br.com.notafiscalse.entity.NotaFiscal;
import br.com.notafiscalse.xml.CancelaNfseV3;
import br.com.notafiscalse.xml.ConsultaLoteRpsV3;
import br.com.notafiscalse.xml.ConsultaSituacaoLoteRpsV3;
import br.com.notafiscalse.xml.EnviaXml;

public class TestNotaFiscalSe {
	   private static Timer timer;
	
	   private void testeGeraXmlFinal() {
	    	 System.out.println( "#########################################" );
	         System.out.println( "TESTANDO IMPLEMENTAÇÃO - HOMOLOGAÇÃO" );
	         System.out.println( "Geração do XML Final" );
	         System.out.println( "#########################################" );

	         NotaFiscal nf = new NotaFiscal();
	         addNotaFiscal(nf);
	         
	         Configura cf  = new Configura();
	         addConfiguracao(cf);
	        
	         //MontaXML montaXml = new MontaXML();
	         /*
	         EnviaLoteRpsV3 montaXml = new EnviaLoteRpsV3();
	         
	         String xml = montaXml.geraXMLEnviarLote(nf, cf);
	         System.out.println("XML Gerado e assinado e envelopado:");
	         System.out.println(xml);
	         System.out.println("###############################################");
	                    
	         //salvaArquivoXML(cf,xml);
	                
	         
	         System.out.println("Enviando XMl para : "+cf.getUrlEndPointWebService());
	         System.out.println("");
	         
	         EnviaXml enviar = new EnviaXml();
	         String resultado = "";
	         try {
	        	 resultado = enviar.enviaLoteRpsV3(xml,cf);
	       	     System.out.println(resultado);
	                    	     
	         }catch(Exception e) {
	        	 System.out.println(e.getMessage());
	         }
	                  
	         System.out.println("");
	         System.out.println("");
	        
	         
	         System.out.println("testando pega valor do protocolo no xml ");
	         System.out.println("\n\n");
	                          
	         
	         montaXml.getRespostaEnvio(resultado,nf);
	         
	         if (nf.getNumeroProtocolo() == null) {
	        	 System.out.println("Erro: "+nf.getCodigoMensagemAlerta());
	        	 System.out.println("Mensagem: "+nf.getDescricaoMensagemAlerta());
	         }else {
	        	 System.out.println("Protocolo = "+nf.getNumeroProtocolo());
	        	 System.out.println("Data Emissão = "+nf.getDataEmissaoRps());
	             
	         }
	         
	         
	         
	         System.out.println("\n\n");
	         System.out.println("Aguardando 5 segundos");
	         
	         try {
	        	 Thread.sleep(5000);
	         }catch(Exception e) {
	        	 e.printStackTrace();
	         }
	         System.out.println("OK");
	        
	         System.out.println("\n\n");
	         */
	         
	         
	         /* Consulta situação do lote  
	         ConsultaSituacaoLoteRpsV3 consultarSituacaoLote = new ConsultaSituacaoLoteRpsV3();
	         String xmlSituacaoLote = consultarSituacaoLote.geraXMLConsultaSituacao(nf,cf);
	         System.out.println("XML Gerado para consulta situação:");
	         System.out.println(xmlSituacaoLote);
	         System.out.println("###############################################");
	         System.out.println("\n\n");
	                 
	         System.out.println("Consultando XMl para : "+cf.getUrlEndPointWebService());
	         System.out.println("\n\n");
	         
	         EnviaXml enviarLote = new EnviaXml();
	         String resultadoLote = "";
	         try {
	        	 resultadoLote = enviarLote.enviaLoteRpsV3(xmlSituacaoLote,cf);
	       	     System.out.println(resultadoLote);
	                    	     
	         }catch(Exception e) {
	        	 System.out.println(e.getMessage());
	         }
	         consultarSituacaoLote.getRespostaSituacaoLote(resultadoLote, nf);
	         
	         if(nf.getCodigoMensagemAlerta() != null) {
	           	 System.out.println("Erro: "+nf.getCodigoMensagemAlerta());
	        	 System.out.println("Mensagem: "+nf.getDescricaoMensagemAlerta());
	         }else {
	         
		         System.out.println("\n\n");
		         System.out.println("Código de situação de lote de RPS");
		         System.out.println("  1 – Não Recebido");
		         System.out.println("  2 – Não Processado");
		         System.out.println("  3 – Processado com Erro");
		         System.out.println("  4 – Processado com Sucesso");
		         System.out.println("\n\n");
		         System.out.println("Resposta da consulta lote = "+nf.getSituacaoLoteRps());
		         System.out.println("\n\n");
		         
	         }
	         */
	         
	         /* Consulta do lote   
	         ConsultaLoteRpsV3 consultaLote = new ConsultaLoteRpsV3();
	         
	         String xmlgerado2 = consultaLote.geraXMLConsultaLote(nf,cf);
	         System.out.println("XML Gerado para consulta do Lote:");
	         System.out.println(xmlgerado2);
	         System.out.println("###############################################");
	         System.out.println("\n\n");
	         System.out.println("Consultando XMl para : "+cf.getUrlEndPointWebService());
	         System.out.println("\n\n");
	         
	         EnviaXml enviarConsultaLote = new EnviaXml();
	         String resultadoConsultaLote = "";
	         try {
	        	 resultadoConsultaLote = enviarConsultaLote.enviaLoteRpsV3(xmlgerado2,cf);
	       	     System.out.println(resultadoConsultaLote);
	         }catch(Exception e) {
	        	 System.out.println(e.getMessage());
	         }
	         
	         consultaLote.getRespostaConsultaLote(resultadoConsultaLote,nf);
	         
	         if (nf.getNumeroNfse() == 0) {
	        	 System.out.println("Erro: "+nf.getCodigoMensagemAlerta());
	        	 System.out.println("Mensagem: "+nf.getDescricaoMensagemAlerta());
	         }else {
	        	 System.out.println("Nota Fiscal numero: "+nf.getNumeroNfse());
	        	 System.out.println("Código de verificação: "+nf.getCodigoVerificacao());
	        	 System.out.println("Data da Emissão: "+nf.getDataEmissaoNfse());
	         }
	         */
	                  
	         
	         
	    }
	   	    
	   private void addConfiguracao(Configura config) {
	    	config.setCaminhoCertificadoCliente("/home/cpd3/workspace/projetos/notafiscalse/documentos/notafiscalse/sfrp/certificado/Certificado_SF.pfx");
	        config.setSenhaCertificadoCliente("20242043");
	        config.setCaminhoArquivoEnvioArmazenado("/home/cpd3/workspace/projetos/notafiscalse/documentos/notafiscalse/sfrp/notafiscal");
	        config.setUrlWebService("http://homologacao.ginfes.com.br");
	        config.setUrlEndPointWebService("https://homologacao.ginfes.com.br/ServiceGinfesImpl?wsdl");
	    }
	    
	    
	    private void addNotaFiscal(NotaFiscal notaFiscal) {
	    	    	
	    	/* Dados da Nota/Recibo */
	    	Date dataEmissao = new Date();
	    	
	    	notaFiscal.setNumeroNfse(10751);
	    		    	
	    	notaFiscal.setNumeroProtocolo("7301558");
	    	
	    	notaFiscal.setNumeroRps(10);
	    	notaFiscal.setQuantidadeRps(1);
	    	notaFiscal.setNumeroLote(10);
	    	notaFiscal.setSerieRps("2");
	    	notaFiscal.setTipoRps(1);
	    	notaFiscal.setDataEmissaoRps(dataEmissao);
	    	notaFiscal.setNaturezaOperacao(1);
	    	notaFiscal.setOptanteSimplesNacional(2);
	    	notaFiscal.setIncentivadorCultural(2);
	    	notaFiscal.setStatusRps(1);
	    	
	    	/* Dados do Serviço */
	    	notaFiscal.setValorServicos(50.00);
	    	notaFiscal.setIssRetido(2);
	    	notaFiscal.setItemListaServico("25.03");
	    	notaFiscal.setCodigoTributacaoMunicipio("25.03.00 / 00250300");
	    	notaFiscal.setDiscriminacao("Referente a parcela do mes X do Plano de Assistencia Familiar.");
	    	
	    	/* Dados do tomador */
	    	notaFiscal.setIndicacaoCpfCnpjTomador(3);
	    	notaFiscal.setRazaoSocialTomador("Wander S. Moschetta");
	    	notaFiscal.setEmailTomador("wander@familiaprever.com.br");
	    	
	    	notaFiscal.setCidadeTomador(3543402);   	
	    	    	
	    	
	    	/* Dados do Prestador */
	    	notaFiscal.setInscricaoMunicipalPrestador("11322401");  
	    	notaFiscal.setCnpjPrestador("06204877000180");
	    	notaFiscal.setCodigoMunicipioIbge(3543402);
	    	
	    	
	    	
	    }
	    	    
	    public void testeCancelamento() {
	       
	       System.out.println( "#########################################" );
	       System.out.println( "TESTANDO IMPLEMENTAÇÃO - HOMOLOGAÇÃO" );
	       System.out.println( "Envio do XML de Cancelamento" );
	       System.out.println( "#########################################" );
	       	       	
	       NotaFiscal nf = new NotaFiscal();
	       addNotaFiscal(nf);
	         
	       Configura cf  = new Configura();
	       addConfiguracao(cf);
	       
	       CancelaNfseV3 cancelarNota = new CancelaNfseV3();
	       String xml = cancelarNota.geraXmlCancelamento(nf, cf);
	         
	       EnviaXml enviar = new EnviaXml();
	       String resultado = "";
	        
	       System.out.println("\n\n");
	           
	         
	       try {
	    	   resultado = enviar.enviaLoteRpsV3(xml,cf);
	           System.out.println(resultado);
	                    	     
	       }catch(Exception e) {
	        	 System.out.println(e.getMessage());
	       }
	       
	       
	    	
	    }
	
	
}
