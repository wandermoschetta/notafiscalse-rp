package br.com.notafiscalse.xml;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import br.com.notafiscalse.config.Configura;
import br.com.notafiscalse.entity.NotaFiscal;
import br.com.notafiscalse.util.Util;

public class MontaXML {
    
	private Util util; 
	
	public String geraXMLEnviarLoteRpsV03(NotaFiscal notaFiscal,Configura configura) {
		String xml = "";
		String xmlAssinado = "";
		util = new Util();
		
		xml = montaConteudoEnviarLoteRpsV3(notaFiscal);
		
		AssinarXMLsCertificadoA1 assina = new AssinarXMLsCertificadoA1();
		try {
			xmlAssinado =  assina.assinaEnviNFe(xml, configura.getCaminhoCertificadoCliente(),configura.getSenhaCertificadoCliente());
		
		}catch(Exception e) {
			e.printStackTrace();
		}		
		
		if(xmlAssinado != null && xmlAssinado != "")
			return ajustaPosicaoAssinaturaXmlV3(xmlAssinado);
		else			
			return xmlAssinado;
		
		
	}
    
	public String geraXMLConsultaSituacaoLoteRpsV03(NotaFiscal notaFiscal,Configura configura) {
		String xml = "";
		String xmlAssinado = "";
		
		xml = montaConteudoConsultaSituacaoLoteRpsV3(notaFiscal);
		
		AssinarXMLsCertificadoA1 assina = new AssinarXMLsCertificadoA1();
		try {
			xmlAssinado =  assina.assinaConsultaSituacaoNFSe(xml, configura.getCaminhoCertificadoCliente(),configura.getSenhaCertificadoCliente());
		
		}catch(Exception e) {
			e.printStackTrace();
		}		
		
		if(xmlAssinado != null && xmlAssinado != "")
			return ajustaPosicaoAssinaturaConsultaSituacaoXmlV3(xmlAssinado);
		else			
			return xmlAssinado;
		
				
	}
	
	public String geraXMLConsultaLoteRpsV03(NotaFiscal notaFiscal,Configura configura) {
		String xml = "";
		String xmlAssinado = "";
		
		xml = montaConteudoConsultaLoteRpsV3(notaFiscal);
		
		
		AssinarXMLsCertificadoA1 assina = new AssinarXMLsCertificadoA1();
		try {
			xmlAssinado =  assina.assinaConsultaLoteNFSe(xml, configura.getCaminhoCertificadoCliente(),configura.getSenhaCertificadoCliente());
		
		}catch(Exception e) {
			e.printStackTrace();
		}		
		
		if(xmlAssinado != null && xmlAssinado != "")
			return ajustaPosicaoAssinaturaConsultaLoteXmlV3(xmlAssinado);
		else			
			return xmlAssinado;
		
			
				
	}
	
	private String montaConteudoConsultaLoteRpsV3(NotaFiscal notaFiscal) {
	    String conteudo = "";
	    	    		
	    conteudo =  "<ConsultarLoteRpsEnvio Id=\""+notaFiscal.getNumeroProtocolo()+"\" xmlns=\"http://www.ginfes.com.br/servico_consultar_lote_rps_envio_v03.xsd\">";
		conteudo += "<Prestador>";
	    conteudo += "<Cnpj xmlns=\"http://www.ginfes.com.br/tipos_v03.xsd\">"+notaFiscal.getCnpjPrestador()+"</Cnpj>";
        conteudo += "<InscricaoMunicipal xmlns=\"http://www.ginfes.com.br/tipos_v03.xsd\">"+notaFiscal.getInscricaoMunicipalPrestador()+"</InscricaoMunicipal>";
        conteudo += "</Prestador>";
        conteudo += "<Protocolo>"+notaFiscal.getNumeroProtocolo()+"</Protocolo>";
                
        conteudo += "</ConsultarLoteRpsEnvio>";
		return conteudo;
	}
	
	
	
	
	private String montaConteudoConsultaSituacaoLoteRpsV3(NotaFiscal notaFiscal) {
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
	
    private String montaCabecalhoEnviarLoteRpsV3() {
    	String cabecalho = "";
    	
    	cabecalho = "<ns2:cabecalho versao=\"3\" xmlns:ns2=\"http://www.ginfes.com.br/cabecalho_v03.xsd\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">";
    	cabecalho +="<versaoDados>3</versaoDados>";
    	cabecalho +="</ns2:cabecalho>";
    	
    	return cabecalho;
    }
    
    private String montaConteudoEnviarLoteRpsV3(NotaFiscal notaFiscal) {
    	String conteudo = "";
    	String lote = "lote_"+notaFiscal.getNumeroLote();
    	String rps = "Rps"+notaFiscal.getNumeroRps();
    	
    	conteudo =  "<ns2:EnviarLoteRpsEnvio xmlns:ns2=\"http://www.ginfes.com.br/servico_enviar_lote_rps_envio_v03.xsd\" xmlns:ns1=\"http://www.ginfes.com.br/tipos_v03.xsd\">";
    	    	
    	conteudo += "<ns2:LoteRps Id=\""+lote+"\">";
    	conteudo +="<ns1:NumeroLote>"+notaFiscal.getNumeroLote()+"</ns1:NumeroLote>";
    	conteudo +="<ns1:Cnpj>"+notaFiscal.getCnpjPrestador()+"</ns1:Cnpj>";
    	conteudo +="<ns1:InscricaoMunicipal>"+notaFiscal.getInscricaoMunicipalPrestador()+"</ns1:InscricaoMunicipal>";
    	conteudo +="<ns1:QuantidadeRps>"+notaFiscal.getQuantidadeRps()+"</ns1:QuantidadeRps>";
    	conteudo = conteudo+"<ns1:ListaRps><ns1:Rps><ns1:InfRps Id=\""+rps+"\">";
    	conteudo = conteudo+"<ns1:IdentificacaoRps>";
    	conteudo = conteudo+"<ns1:Numero>"+notaFiscal.getNumeroRps()+"</ns1:Numero>"; 
    	conteudo = conteudo+"<ns1:Serie>"+notaFiscal.getSerieRps()+"</ns1:Serie>";
    	conteudo = conteudo+"<ns1:Tipo>"+notaFiscal.getTipoRps()+"</ns1:Tipo>"; 
    	conteudo = conteudo+"</ns1:IdentificacaoRps>";
    	 
    	conteudo = conteudo+"<ns1:DataEmissao>"+util.formataData(notaFiscal.getDataEmissaoRps())+"</ns1:DataEmissao>";
    	conteudo = conteudo+"<ns1:NaturezaOperacao>"+notaFiscal.getNaturezaOperacao()+"</ns1:NaturezaOperacao>";
    	conteudo = conteudo+"<ns1:OptanteSimplesNacional>"+notaFiscal.getOptanteSimplesNacional()+"</ns1:OptanteSimplesNacional>";
    	conteudo = conteudo+"<ns1:IncentivadorCultural>"+notaFiscal.getIncentivadorCultural()+"</ns1:IncentivadorCultural>";
    	conteudo = conteudo+"<ns1:Status>"+notaFiscal.getStatusRps()+"</ns1:Status>";
    	
    	conteudo = conteudo+"<ns1:Servico>";
    	conteudo = conteudo+"<ns1:Valores>";
    	conteudo = conteudo+"<ns1:ValorServicos>"+util.formataValor(notaFiscal.getValorServicos())+"</ns1:ValorServicos>";
    	conteudo = conteudo+"<ns1:IssRetido>"+notaFiscal.getIssRetido()+"</ns1:IssRetido>";
    	if(notaFiscal.getIssRetido() == 1) {
    		conteudo = conteudo+"<ns1:ValorIssRetido>"+util.formataValor(notaFiscal.getValorIssRetido())+"</ns1:ValorIssRetido>";
    	}
    	conteudo = conteudo+"</ns1:Valores>";
    	conteudo = conteudo+"<ns1:ItemListaServico>"+notaFiscal.getItemListaServico()+"</ns1:ItemListaServico>";
    	conteudo = conteudo+"<ns1:CodigoTributacaoMunicipio>"+notaFiscal.getCodigoTributacaoMunicipio()+"</ns1:CodigoTributacaoMunicipio>";
    	
    	
    	conteudo = conteudo+"<ns1:Discriminacao>"+notaFiscal.getDiscriminacao()+"</ns1:Discriminacao>";
    	conteudo = conteudo+"<ns1:CodigoMunicipio>"+notaFiscal.getCodigoMunicipioIbge()+"</ns1:CodigoMunicipio>";
    	conteudo = conteudo+"</ns1:Servico>";
    	
    	conteudo = conteudo+"<ns1:Prestador>";
        conteudo = conteudo+"<ns1:Cnpj>"+notaFiscal.getCnpjPrestador()+"</ns1:Cnpj>";
        conteudo = conteudo+"<ns1:InscricaoMunicipal>"+notaFiscal.getInscricaoMunicipalPrestador()+"</ns1:InscricaoMunicipal>";
        conteudo = conteudo+"</ns1:Prestador>";
    	
    	conteudo = conteudo +"<ns1:Tomador>";
    	
        if((notaFiscal.getIndicacaoCpfCnpjTomador() == 1)||(notaFiscal.getIndicacaoCpfCnpjTomador() == 2)) {
    		conteudo = conteudo +"<ns1:IdentificacaoTomador>";
    		conteudo = conteudo +"<ns1:CpfCnpj>";
            if(notaFiscal.getIndicacaoCpfCnpjTomador()==2)
            	conteudo = conteudo+"<ns1:Cnpj>"+notaFiscal.getCpfCnpjTomador()+"</ns1:Cnpj>";
            else
            	conteudo = conteudo+"<ns1:Cpf>"+notaFiscal.getCpfCnpjTomador()+"</ns1:Cpf>";	
            conteudo = conteudo +"</ns1:CpfCnpj>";
            conteudo = conteudo +"</ns1:IdentificacaoTomador>";
            conteudo = conteudo +"<ns1:RazaoSocial>"+notaFiscal.getRazaoSocialTomador()+"</ns1:RazaoSocial>";
        }else {
            conteudo = conteudo +"<ns1:RazaoSocial>Consumidor Final</ns1:RazaoSocial>";
            
        }
        	
        
        conteudo = conteudo +"</ns1:Tomador>";		
	
        conteudo = conteudo +"</ns1:InfRps>";
        conteudo = conteudo +"</ns1:Rps>";
        conteudo = conteudo +"</ns1:ListaRps>";
        conteudo = conteudo +"</ns2:LoteRps>";
        conteudo = conteudo +"</ns2:EnviarLoteRpsEnvio>";
    	
    	return conteudo;
     
    	
    }
    
    public String montaEnvelopeEnviarLoteRpsV3(String xml,Configura configura) {
    	String envelope = "";
    	String urlWebService = configura.getUrlWebService();
    	    	    	
    	envelope +="<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:rec=\""+urlWebService+"\">";
    	envelope +="<soap:Header/>";
    	envelope +="<soap:Body>";
    	envelope +="<rec:RecepcionarLoteRpsV3>";
    	envelope +="<arg0>";
    	envelope += montaCabecalhoEnviarLoteRpsV3();
    	envelope +="</arg0>";
    	envelope +="<arg1>";
    	envelope += parseXml(xml);
        envelope +="</arg1>";
        envelope +="</rec:RecepcionarLoteRpsV3>";
        envelope+="</soap:Body>"; 
    	envelope+="</soap:Envelope>"; 
    	    	
    	return envelope;
    }
   
    public String montaEnvelopeConsultarLoteRpsV3(String xml,Configura configura) {
    	String envelope = "";
    	String urlWebService = configura.getUrlWebService();
    	    	    	
    	envelope +="<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:rec=\""+urlWebService+"\">";
    	envelope +="<soap:Header/>";
    	envelope +="<soap:Body>";
    	envelope +="<rec:ConsultarLoteRpsV3>";
    	envelope +="<arg0>";
    	envelope += montaCabecalhoEnviarLoteRpsV3();
    	envelope +="</arg0>";
    	envelope +="<arg1>";
    	envelope += parseXml(xml);
        envelope +="</arg1>";
        envelope +="</rec:ConsultarLoteRpsV3>";
        envelope+="</soap:Body>"; 
    	envelope+="</soap:Envelope>"; 
    	    	
    	return envelope;
    }
    
    public String montaEnvelopeConsultarSituacaoLoteRpsV3(String xml,Configura configura) {
    	String envelope = "";
    	String urlWebService = configura.getUrlWebService();
    	    	    	
    	envelope +="<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:rec=\""+urlWebService+"\">";
    	envelope +="<soap:Header/>";
    	envelope +="<soap:Body>";
    	envelope +="<rec:ConsultarSituacaoLoteRpsV3>";
    	envelope +="<arg0>";
    	envelope += montaCabecalhoEnviarLoteRpsV3();
    	envelope +="</arg0>";
    	envelope +="<arg1>";
    	envelope += parseXml(xml);
        envelope +="</arg1>";
        envelope +="</rec:ConsultarSituacaoLoteRpsV3>";
        envelope+="</soap:Body>"; 
    	envelope+="</soap:Envelope>"; 
    	    	
    	return envelope;
    }
    
    /* retira a tag <?xml version="1.0" encoding="UTF-8"?>  */
    private String parseXml(String xml) {
   	    String str = "";
    	str = xml.substring(38);
           	
    	return str;
    }     
    
    public String passarXMLParaString(Document xml, int espacosIdentacao){
        try {
            //set up a transformer
            TransformerFactory transfac = TransformerFactory.newInstance();
            transfac.setAttribute("indent-number", new Integer(espacosIdentacao));
            Transformer trans = transfac.newTransformer();
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");

            //create string from xml tree
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            DOMSource source = new DOMSource(xml);
            trans.transform(source, result);
            String xmlString = sw.toString();
            return xmlString;
        }
        catch (TransformerException e) {
            e.printStackTrace();
           
        }
        return null;
    }
        
    
    private String ajustaPosicaoAssinaturaConsultaLoteXmlV3(String xmlAssinado) {
    	String xmlMontado = "";
    	int qtdBloco1 = xmlAssinado.indexOf("<Signature ");
    	
    	String xml1 =  xmlAssinado.substring(0,qtdBloco1)+"</Protocolo>";
    	String xml2 =  xmlAssinado.substring(qtdBloco1,xmlAssinado.indexOf("</Protocolo>"));
    	
    	xmlMontado = xml1+xml2+"</ConsultarLoteRpsEnvio>";
    	    	
    	return xmlMontado;
    	
    }
    
    private String ajustaPosicaoAssinaturaConsultaSituacaoXmlV3(String xmlAssinado) {
    	String xmlMontado = "";
    	int qtdBloco1 = xmlAssinado.indexOf("<Signature ");
    	
    	String xml1 =  xmlAssinado.substring(0,qtdBloco1)+"</Protocolo>";
    	String xml2 =  xmlAssinado.substring(qtdBloco1,xmlAssinado.indexOf("</Protocolo>"));
    	
    	xmlMontado = xml1+xml2+"</ConsultarSituacaoLoteRpsEnvio>";
    	    	
    	return xmlMontado;
    	
    }
     
    
    private String ajustaPosicaoAssinaturaXmlV3(String xmlAssinado) {
    	String xmlMontado = "";
    	int qtdBloco1 = xmlAssinado.indexOf("</ns1:ListaRps>")+15;
    	
    	String xml1 =  xmlAssinado.substring(0,qtdBloco1)+"</ns2:LoteRps>";
    	String xml2 =  xmlAssinado.substring(qtdBloco1,xmlAssinado.indexOf("</ns2:LoteRps>"));
    	
    	xmlMontado = xml1+xml2+"</ns2:EnviarLoteRpsEnvio>";
    	    	
    	return xmlMontado;
    	
    }
    
    
    
    public NotaFiscal getRecepcaoEnvioLoteRps(String xmlResposta,NotaFiscal nota) {
    	
    	getProtocoloEnvioLoteRpsV3(xmlResposta,nota);
    	
    	getDataEmissaoProtocoloEnvioLoteRpsV3(xmlResposta,nota); 
    	    	
    	return nota;
    }
    
    public String getProtocoloEnvioLoteRpsV3(String xmlResposta,NotaFiscal notaFiscal) {
    	String protocolo = "";
    	
    	try {
                   	  
          Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                  .parse(new InputSource(new StringReader(xmlResposta)));

          NodeList noReturn = doc.getElementsByTagName("return");
          Element elementoReturn = (Element) noReturn.item(0);
          
          String xmlFormatado = elementoReturn.getTextContent();
          
          doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
          .parse(new InputSource(new StringReader(xmlFormatado)));
          
          NodeList noErro = doc.getElementsByTagName("ListaMensagemRetorno");
          
          if(noErro.getLength() > 0) {
        	  NodeList no = doc.getElementsByTagName("ns2:MensagemRetorno");
              Node prop = no.item(0);
              
              notaFiscal.setCodigoMensagemAlerta(prop.getChildNodes().item(0).getTextContent());
              notaFiscal.setDescricaoMensagemAlerta(prop.getChildNodes().item(1).getTextContent());
              
          }else {
        	  NodeList no = doc.getElementsByTagName("ns3:EnviarLoteRpsResposta");
              Node prop = no.item(0);
                            
              notaFiscal.setNumeroProtocolo(prop.getChildNodes().item(2).getTextContent());
          }
        	       
             
        } catch (ParserConfigurationException e) {
               e.printStackTrace();
        } catch (SAXException e) {
               e.printStackTrace();
        } catch (IOException e) {
               e.printStackTrace();
        }
    	
    	    	
    	return protocolo;
    }
     
    private Date getDataEmissaoProtocoloEnvioLoteRpsV3(String xmlResposta,NotaFiscal notaFiscal) {
    	String dataEmissaoProtocolo = "";
    	Date data = new Date();
       	    	        
    	try {
    		  Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                      .parse(new InputSource(new StringReader(xmlResposta)));

              NodeList noReturn = doc.getElementsByTagName("return");
              Element elementoReturn = (Element) noReturn.item(0);
              
              String xmlFormatado = elementoReturn.getTextContent();
              
              doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
              .parse(new InputSource(new StringReader(xmlFormatado)));
              
              NodeList noErro = doc.getElementsByTagName("ListaMensagemRetorno");
              
              if(noErro.getLength() > 0) {
            	  NodeList no = doc.getElementsByTagName("ns2:MensagemRetorno");
                  Node prop = no.item(0);
                  
                  notaFiscal.setCodigoMensagemAlerta(prop.getChildNodes().item(0).getTextContent());
                  notaFiscal.setDescricaoMensagemAlerta(prop.getChildNodes().item(1).getTextContent());
                  
              }else {
            	  NodeList no = doc.getElementsByTagName("ns3:EnviarLoteRpsResposta");
                  Node prop = no.item(0);
                  
                  SimpleDateFormat dataFormatada = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                  dataEmissaoProtocolo = prop.getChildNodes().item(1).getTextContent();   
                  data = dataFormatada.parse(dataEmissaoProtocolo);
                  
                  notaFiscal.setDataEmissaoRps(data);
              }
    		
               
        } catch (ParserConfigurationException e) {
               e.printStackTrace();
        } catch (SAXException e) {
               e.printStackTrace();
        } catch (IOException e) {
               e.printStackTrace();
        }
    	catch(Exception e) {
    		   e.printStackTrace();
    	}
           
    	
    	return data;
    }
    
    
    
}
