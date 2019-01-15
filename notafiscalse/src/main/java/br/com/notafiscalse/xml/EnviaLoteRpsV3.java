package br.com.notafiscalse.xml;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import br.com.notafiscalse.util.Util;

public class EnviaLoteRpsV3 {

	private Util util;
	private UtilXml utilXml;
	
	public EnviaLoteRpsV3() {
		this.util = new Util();
		this.utilXml = new UtilXml();
	}
		
	public String geraXMLEnviarLote(NotaFiscal notaFiscal,Configura configura) {
		String xmlConteudo = "";
		String xmlAssinado = "";
		String xmlGerado = "";
				
		xmlConteudo = getConteudo(notaFiscal);
		
		AssinarXMLsCertificadoA1 assina = new AssinarXMLsCertificadoA1();
		try {
			xmlAssinado =  assina.assinaEnviNFe(xmlConteudo, configura.getCaminhoCertificadoCliente(),configura.getSenhaCertificadoCliente());
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
        
        if((notaFiscal.getEnderecoTomador() != null)&&(notaFiscal.getNumeroEnderecoTomador() != null)
                &&(notaFiscal.getBairroTomador() != null)&&(notaFiscal.getCidadeTomador() != 0)
                &&(notaFiscal.getCepTomador() != 0)&&(notaFiscal.getUfTomador() != null))
        {
          conteudo += "<ns1:Endereco>";
          conteudo += "<ns1:Endereco>"+notaFiscal.getEnderecoTomador()+"</ns1:Endereco>";
          conteudo += "<ns1:Numero>"+notaFiscal.getNumeroEnderecoTomador()+"</ns1:Numero>";
          if(notaFiscal.getComplementoEnderecoTomador() != null)
        	  conteudo += "<ns1:Complemento>"+notaFiscal.getComplementoEnderecoTomador()+"</ns1:Complemento>";
          else
        	  conteudo += "<ns1:Complemento></ns1:Complemento>";
          
          conteudo += "<ns1:Bairro>"+notaFiscal.getBairroTomador()+"</ns1:Bairro>";
          conteudo += "<ns1:CodigoMunicipio>"+notaFiscal.getCidadeTomador()+"</ns1:CodigoMunicipio>";
          conteudo += "<ns1:Uf>"+notaFiscal.getUfTomador()+"</ns1:Uf>";
          conteudo += "<ns1:Cep>"+notaFiscal.getCepTomador()+"</ns1:Cep>";
          conteudo += "</ns1:Endereco>";
          
          
        }
        
        
        if(((notaFiscal.getTelefoneTomador() != "")&&(notaFiscal.getTelefoneTomador() != null))
          ||((notaFiscal.getEmailTomador() != "")&&(notaFiscal.getEmailTomador() != null)))
        {
                  conteudo += "<ns1:Contato>";
              	if((notaFiscal.getTelefoneTomador() != "")&&(notaFiscal.getTelefoneTomador() != null))
              	        conteudo += "<ns1:Telefone>"+notaFiscal.getTelefoneTomador()+"</ns1:Telefone>";
              	if((notaFiscal.getEmailTomador() != "")&&(notaFiscal.getEmailTomador() != null))
              		conteudo += "<ns1:Email>"+notaFiscal.getEmailTomador()+"</ns1:Email>";
              	conteudo += "</ns1:Contato>";
              	
        }
                
        conteudo = conteudo +"</ns1:Tomador>";		
	    conteudo = conteudo +"</ns1:InfRps>";
        conteudo = conteudo +"</ns1:Rps>";
        conteudo = conteudo +"</ns1:ListaRps>";
        conteudo = conteudo +"</ns2:LoteRps>";
        conteudo = conteudo +"</ns2:EnviarLoteRpsEnvio>";
    	
    	return conteudo;
         	
    }
    
	private String ajustaPosicaoAssinatura(String xmlAssinado) {
    	String xmlMontado = "";
    	int qtdBloco1 = xmlAssinado.indexOf("</ns1:ListaRps>")+15;
    	
    	String xml1 =  xmlAssinado.substring(0,qtdBloco1)+"</ns2:LoteRps>";
    	String xml2 =  xmlAssinado.substring(qtdBloco1,xmlAssinado.indexOf("</ns2:LoteRps>"));
    	
    	xmlMontado = xml1+xml2+"</ns2:EnviarLoteRpsEnvio>";
    	    	
    	return xmlMontado;
    	
    }
	
	private String getEnvelope(String xml,Configura configura) {
    	String envelope = "";
    	String urlWebService = configura.getUrlWebService();
    	    	    	
    	envelope +="<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:rec=\""+urlWebService+"\">";
    	envelope +="<soap:Header/>";
    	envelope +="<soap:Body>";
    	envelope +="<rec:RecepcionarLoteRpsV3>";
    	envelope +="<arg0>";
    	envelope += getCabecalho();
    	envelope +="</arg0>";
    	envelope +="<arg1>";
    	envelope += utilXml.parseXml(xml);
        envelope +="</arg1>";
        envelope +="</rec:RecepcionarLoteRpsV3>";
        envelope+="</soap:Body>"; 
    	envelope+="</soap:Envelope>"; 
    	    	
    	return envelope;
    }
   
	private String getCabecalho() {
    	String cabecalho = "";
    	
    	cabecalho = "<ns2:cabecalho versao=\"3\" xmlns:ns2=\"http://www.ginfes.com.br/cabecalho_v03.xsd\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">";
    	cabecalho +="<versaoDados>3</versaoDados>";
    	cabecalho +="</ns2:cabecalho>";
    	
    	return cabecalho;
    }
    
    
    public void getRespostaEnvio(String xmlResposta,NotaFiscal notaFiscal) {
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
        	                 
        	  if(doc.getElementsByTagName("ns2:MensagemRetorno") != null) {
        		  NodeList no = doc.getElementsByTagName("ns2:MensagemRetorno");
        		  Node prop = no.item(0);
        		  notaFiscal.setCodigoMensagemAlerta(prop.getChildNodes().item(0).getTextContent());
                  notaFiscal.setDescricaoMensagemAlerta(prop.getChildNodes().item(1).getTextContent());
                  
        	  }else {
        		  NodeList no = doc.getElementsByTagName("p1:MensagemRetorno");
        		  Node prop = no.item(0);
        		  notaFiscal.setCodigoMensagemAlerta(prop.getChildNodes().item(0).getTextContent());
                  notaFiscal.setDescricaoMensagemAlerta(prop.getChildNodes().item(1).getTextContent());
                  
        	  }
        	  
              
          }else {
        	   	 NodeList no = doc.getElementsByTagName("ns3:EnviarLoteRpsResposta");
	             Node prop = no.item(0);
	                        
	              
	              notaFiscal.setNumeroProtocolo(prop.getChildNodes().item(2).getTextContent());
	        	  
	              SimpleDateFormat dataFormatada = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	              dataEmissaoProtocolo = prop.getChildNodes().item(1).getTextContent();   
	              try {
	            	  data = dataFormatada.parse(dataEmissaoProtocolo);
	              }catch(ParseException e) {
	            	  e.printStackTrace();
	              }
	              notaFiscal.setDataEmissaoRps(data);
	        	  
        	        	  
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
