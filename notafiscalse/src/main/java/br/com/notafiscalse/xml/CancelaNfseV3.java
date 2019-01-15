package br.com.notafiscalse.xml;

import br.com.notafiscalse.config.Configura;
import br.com.notafiscalse.entity.NotaFiscal;

public class CancelaNfseV3 {
	
	private UtilXml util;
	
	public CancelaNfseV3() {
		util = new UtilXml();
	}
	
	public String geraXmlCancelamento(NotaFiscal notaFiscal, Configura configura) {
		String xml = "";
		String xmlAssinado = "";
		String xmlGerado = "";
		
		xml = getConteudo(notaFiscal);
		xmlGerado = getEnvelope(xml, configura);
		
		return xmlGerado;
	}
	
	private String getConteudo(NotaFiscal notaFiscal) {
	    String conteudo = "";
	    	    		
	    conteudo =  "<CancelarNfseEnvio Id=\""+notaFiscal.getNumeroProtocolo()+"\" xmlns=\"http://www.ginfes.com.br/servico_cancelar_nfse_envio_v03.xsd\">";
		conteudo += "<Pedido>";
	    conteudo += "<Numero>"+notaFiscal.getNumeroNfse()+"</Numero>";
		conteudo += "<Cnpj xmlns=\"http://www.ginfes.com.br/tipos_v03.xsd\">"+notaFiscal.getCnpjPrestador()+"</Cnpj>";
        conteudo += "<InscricaoMunicipal xmlns=\"http://www.ginfes.com.br/tipos_v03.xsd\">"+notaFiscal.getInscricaoMunicipalPrestador()+"</InscricaoMunicipal>";
        conteudo += "</Pedido>";
        conteudo += "<CodigoCancelamento>"+notaFiscal.getNumeroNfse()+"</CodigoCancelamento>";
                
        conteudo += "</CancelarNfseEnvio>";
		
        return conteudo;
	}
	
	private String getCabecalho() {
    	String cabecalho = "";
    	
    	cabecalho = "<ns2:cabecalho versao=\"3\" xmlns:ns2=\"http://www.ginfes.com.br/cabecalho_v03.xsd\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">";
    	cabecalho +="<versaoDados>3</versaoDados>";
    	cabecalho +="</ns2:cabecalho>";
    	
    	return cabecalho;
    }
	
	private String getEnvelope(String xml,Configura configura) {
    	String envelope = "";
    	String urlWebService = configura.getUrlWebService();
    	    	    	
    	envelope +="<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:rec=\""+urlWebService+"\">";
    	envelope +="<soap:Header/>";
    	envelope +="<soap:Body>";
    	envelope +="<rec:CancelarNfseEnvio>";
    	envelope +="<arg0>";
    	envelope += getCabecalho();
    	envelope +="</arg0>";
    	envelope +="<arg1>";
    	envelope += util.parseXml(xml);
        envelope +="</arg1>";
        envelope +="</rec:CancelarNfseEnvio>";
        envelope +="</soap:Body>"; 
    	envelope +="</soap:Envelope>"; 
    	    	
    	return envelope;
    }
	
	
   
}
