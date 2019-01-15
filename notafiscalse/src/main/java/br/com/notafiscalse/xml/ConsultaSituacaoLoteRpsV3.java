package br.com.notafiscalse.xml;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import br.com.notafiscalse.config.Configura;
import br.com.notafiscalse.entity.NotaFiscal;

public class ConsultaSituacaoLoteRpsV3 {

	private UtilXml util;
	
	public ConsultaSituacaoLoteRpsV3() {
		util = new UtilXml();
	}
		
	
	public String geraXMLConsultaSituacao(NotaFiscal notaFiscal,Configura configura) {
		String xml = "";
		String xmlAssinado = "";
		String xmlGerado = "";
		
		xml = getConteudo(notaFiscal);
		
		AssinarXMLsCertificadoA1 assina = new AssinarXMLsCertificadoA1();
		try {
			xmlAssinado =  assina.assinaConsultaSituacaoNFSe(xml, configura.getCaminhoCertificadoCliente(),configura.getSenhaCertificadoCliente());
		
		}catch(Exception e) {
			e.printStackTrace();
		}		
		
		if(xmlAssinado != null && xmlAssinado != "")
			xmlGerado = ajustaPosicaoAssinatura(xmlAssinado);
		else
			xmlGerado = xmlAssinado;
		
		
		return getEnvelope(xmlGerado,configura);
				
	}
	
	
	private String getConteudo(NotaFiscal notaFiscal) {
	    String conteudo = "";
	    	    		
	    conteudo =  "<ConsultarSituacaoLoteRpsEnvio Id=\""+notaFiscal.getNumeroProtocolo()+"\" xmlns=\"http://www.ginfes.com.br/servico_consultar_situacao_lote_rps_envio_v03.xsd\">";
		conteudo += "<Prestador>";
	    conteudo += "<Cnpj xmlns=\"http://www.ginfes.com.br/tipos_v03.xsd\">"+notaFiscal.getCnpjPrestador()+"</Cnpj>";
        conteudo += "<InscricaoMunicipal xmlns=\"http://www.ginfes.com.br/tipos_v03.xsd\">"+notaFiscal.getInscricaoMunicipalPrestador()+"</InscricaoMunicipal>";
        conteudo += "</Prestador>";
        conteudo += "<Protocolo>"+notaFiscal.getNumeroProtocolo()+"</Protocolo>";
                
        conteudo += "</ConsultarSituacaoLoteRpsEnvio>";
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
    	envelope +="<rec:ConsultarSituacaoLoteRpsV3>";
    	envelope +="<arg0>";
    	envelope += getCabecalho();
    	envelope +="</arg0>";
    	envelope +="<arg1>";
    	envelope += util.parseXml(xml);
        envelope +="</arg1>";
        envelope +="</rec:ConsultarSituacaoLoteRpsV3>";
        envelope +="</soap:Body>"; 
    	envelope +="</soap:Envelope>"; 
    	    	
    	return envelope;
    }
		
	private String ajustaPosicaoAssinatura(String xmlAssinado) {
    	String xmlMontado = "";
    	int qtdBloco1 = xmlAssinado.indexOf("<Signature ");
    	
    	String xml1 =  xmlAssinado.substring(0,qtdBloco1)+"</Protocolo>";
    	String xml2 =  xmlAssinado.substring(qtdBloco1,xmlAssinado.indexOf("</Protocolo>"));
    	
    	xmlMontado = xml1+xml2+"</ConsultarSituacaoLoteRpsEnvio>";
    	    	
    	return xmlMontado;
    	
    }
	
	public void getRespostaSituacaoLote(String xmlResposta,NotaFiscal notaFiscal) {
    	    	
    	try {
                   	  
          Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                  .parse(new InputSource(new StringReader(xmlResposta)));

          NodeList noReturn = doc.getElementsByTagName("return");
          Element elementoReturn = (Element) noReturn.item(0);
          
          String xmlFormatado = elementoReturn.getTextContent();
          
          doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
          .parse(new InputSource(new StringReader(xmlFormatado)));
                    
          NodeList no = doc.getElementsByTagName("ns3:ConsultarSituacaoLoteRpsResposta");
          Node prop = no.item(0);
                   
          
          if(prop.getChildNodes().item(0).getNodeName() != "ns3:NumeroLote") {
        	  notaFiscal.setCodigoMensagemAlerta(prop.getChildNodes().item(0).getTextContent());
        	  notaFiscal.setDescricaoMensagemAlerta(prop.getChildNodes().item(1).getTextContent());
          }else {       	  
        	  notaFiscal.setSituacaoLoteRps(Integer.parseInt(prop.getChildNodes().item(1).getTextContent()));
          }             
        
    	} catch (ParserConfigurationException e) {
               e.printStackTrace();
        } catch (SAXException e) {
               e.printStackTrace();
        } catch (IOException e) {
               e.printStackTrace();
        }
    	  	    	
    	
    }

	
	
	
}
