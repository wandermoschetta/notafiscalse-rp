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

public class ConsultaLoteRpsV3 {
	
	private UtilXml utilXml;
	
	public ConsultaLoteRpsV3() {
		this.utilXml = new UtilXml();
	}
	
	
	public String geraXMLConsultaLote(NotaFiscal notaFiscal,Configura configura) {
		String xml = "";
		String xmlAssinado = "";
		String xmlGerado = "";
		
		xml = getConteudo(notaFiscal);
				
		AssinarXMLsCertificadoA1 assina = new AssinarXMLsCertificadoA1();
		try {
			xmlAssinado =  assina.assinaConsultaLoteNFSe(xml, configura.getCaminhoCertificadoCliente(),configura.getSenhaCertificadoCliente());
		
		}catch(Exception e) {
			e.printStackTrace();
		}		
		
		if(xmlAssinado != null && xmlAssinado != "")
			xmlGerado = ajustaPosicaoAssinatura(xmlAssinado);
		else
			xmlGerado = xmlAssinado;
				
		return getEnvelope(xmlGerado,configura);
						
	}
	
	
	
	private String getEnvelope(String xml,Configura configura) {
    	String envelope = "";
    	String urlWebService = configura.getUrlWebService();
    	    	    	
    	envelope +="<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:rec=\""+urlWebService+"\">";
    	envelope +="<soap:Header/>";
    	envelope +="<soap:Body>";
    	envelope +="<rec:ConsultarLoteRpsV3>";
    	envelope +="<arg0>";
    	envelope += getCabecalho();
    	envelope +="</arg0>";
    	envelope +="<arg1>";
    	envelope += utilXml.parseXml(xml);
        envelope +="</arg1>";
        envelope +="</rec:ConsultarLoteRpsV3>";
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
	    
	private String getConteudo(NotaFiscal notaFiscal) {
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
	
	private String ajustaPosicaoAssinatura(String xmlAssinado) {
    	String xmlMontado = "";
    	int qtdBloco1 = xmlAssinado.indexOf("<Signature ");
    	
    	String xml1 =  xmlAssinado.substring(0,qtdBloco1)+"</Protocolo>";
    	String xml2 =  xmlAssinado.substring(qtdBloco1,xmlAssinado.indexOf("</Protocolo>"));
    	
    	xmlMontado = xml1+xml2+"</ConsultarLoteRpsEnvio>";
    	    	
    	return xmlMontado;
    	
    }
	
	public void getRespostaConsultaLote(String xmlResposta,NotaFiscal notaFiscal) {
    	
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
        	  NodeList no = doc.getElementsByTagName("ns4:MensagemRetorno");
              Node prop = no.item(0);
              
              notaFiscal.setCodigoMensagemAlerta(prop.getChildNodes().item(0).getTextContent());
              
              Node prop2 = no.item(0);
              //System.out.println("proximo nó "+prop2.getTextContent());
              //System.out.println("\n child nó "+prop2.getChildNodes().item(1).toString());
              //System.out.println("\n Conteúdo do child nó "+prop2.getChildNodes().item(1).getTextContent());
                          
              notaFiscal.setDescricaoMensagemAlerta(prop2.getChildNodes().item(1).getTextContent());
              /* pega mensagem correção */
              //System.out.println("\n 2 Conteúdo do child nó "+prop2.getChildNodes().item(2).getTextContent());
              notaFiscal.setDescricaoCorrecaoAlerta(prop2.getChildNodes().item(2).getTextContent());
              
              
              
          }else {
        	  NodeList no = doc.getElementsByTagName("ns4:InfNfse");
              Node prop = no.item(0);
              
              notaFiscal.setNumeroNfse(Integer.parseInt(prop.getChildNodes().item(0).getTextContent()));
              notaFiscal.setCodigoVerificacao(prop.getChildNodes().item(1).getTextContent());
              
              Date data = new Date();
              SimpleDateFormat dataFormatada = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
              try {
            	  data = dataFormatada.parse(prop.getChildNodes().item(2).getTextContent());
              }catch(ParseException e) {
            	  e.printStackTrace();
              }
                           
              notaFiscal.setDataEmissaoNfse(data);
                            
              NodeList noIdRps = doc.getElementsByTagName("ns4:IdentificacaoRps");
              Node propIdRps = noIdRps.item(0);
                            
              notaFiscal.setNumeroRps(Integer.parseInt(propIdRps.getChildNodes().item(0).getTextContent()));
              notaFiscal.setSerieRps(propIdRps.getChildNodes().item(1).getTextContent());
              notaFiscal.setTipoRps(Integer.parseInt(propIdRps.getChildNodes().item(2).getTextContent()));
              
              System.out.println("Numero Rps = "+propIdRps.getChildNodes().item(0).getTextContent());
              System.out.println("Série Rps = "+propIdRps.getChildNodes().item(1).getTextContent());
              System.out.println("Tipo Rps = "+propIdRps.getChildNodes().item(2).getTextContent());
              
              
              Date dataRps = new Date();
              SimpleDateFormat dataFormatada2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
              try {
            	  dataRps = dataFormatada2.parse(prop.getChildNodes().item(4).getTextContent()+"T00:00:00");
              }catch(ParseException e) {
            	  e.printStackTrace();
              }
              notaFiscal.setDataEmissaoRps(dataRps);
              
              notaFiscal.setNaturezaOperacao(Integer.parseInt(prop.getChildNodes().item(5).getTextContent()));
              notaFiscal.setOptanteSimplesNacional(Integer.parseInt(prop.getChildNodes().item(7).getTextContent()));
              notaFiscal.setIncentivadorCultural(Integer.parseInt(prop.getChildNodes().item(8).getTextContent()));
              notaFiscal.setCompetencia(prop.getChildNodes().item(9).getTextContent());
              
              System.out.println("Emissão Rps = "+prop.getChildNodes().item(4).getTextContent());
              System.out.println("Natureza Operação = "+prop.getChildNodes().item(5).getTextContent());
              System.out.println("Regime especial de tributação = "+prop.getChildNodes().item(6).getTextContent());
              System.out.println("Optante simples nacional = "+prop.getChildNodes().item(7).getTextContent());
              System.out.println("Incentivador cultural = "+prop.getChildNodes().item(8).getTextContent());
              System.out.println("Competencia = "+prop.getChildNodes().item(9).getTextContent());
              
              NodeList noValores = doc.getElementsByTagName("ns4:Valores");
              Node propValores = noValores.item(0);
              
              notaFiscal.setValorServicos(Double.parseDouble(propValores.getChildNodes().item(0).getTextContent()));
              notaFiscal.setValorDeducoes(Double.parseDouble(propValores.getChildNodes().item(1).getTextContent()));
              notaFiscal.setIssRetido(Integer.parseInt(propValores.getChildNodes().item(2).getTextContent()));
              notaFiscal.setValorIss(Double.parseDouble(propValores.getChildNodes().item(3).getTextContent()));
              notaFiscal.setBaseCalculo(Double.parseDouble(propValores.getChildNodes().item(4).getTextContent()));
              notaFiscal.setAliquota(Double.parseDouble(propValores.getChildNodes().item(5).getTextContent()));
              
              
              System.out.println("Valor servicos = "+propValores.getChildNodes().item(0).getTextContent());
              System.out.println("Valor deduçoes = "+propValores.getChildNodes().item(1).getTextContent());
              System.out.println("ISS retido  = "+propValores.getChildNodes().item(2).getTextContent());
              System.out.println("Valor ISS = "+propValores.getChildNodes().item(3).getTextContent());
              System.out.println("Base calculo = "+propValores.getChildNodes().item(4).getTextContent());
              System.out.println("Aliquota = "+propValores.getChildNodes().item(5).getTextContent());
              
              NodeList noItemLista = doc.getElementsByTagName("ns4:ItemListaServico");
              Node propItemLista = noItemLista.item(0);
              System.out.println("Item lista serviço = "+propItemLista.getChildNodes().item(0).getTextContent());
              
              notaFiscal.setItemListaServico(propItemLista.getChildNodes().item(0).getTextContent());
              
              NodeList noTribMunicipio = doc.getElementsByTagName("ns4:CodigoTributacaoMunicipio");
              Node propTribMunicipio = noTribMunicipio.item(0);
              System.out.println("Codigo Tributacao Municipio = "+propTribMunicipio.getChildNodes().item(0).getTextContent());
              
              notaFiscal.setCodigoTributacaoMunicipio(propTribMunicipio.getChildNodes().item(0).getTextContent());
              
              NodeList noDiscrimina = doc.getElementsByTagName("ns4:Discriminacao");
              Node propDiscrimina = noDiscrimina.item(0);
              System.out.println("Discriminação = "+propDiscrimina.getChildNodes().item(0).getTextContent());
              
              notaFiscal.setDiscriminacao(propDiscrimina.getChildNodes().item(0).getTextContent());
              
              NodeList noCodMunicipio = doc.getElementsByTagName("ns4:CodigoMunicipio");
              Node propCodMunicipio = noCodMunicipio.item(0);
              System.out.println("Código municipio = "+propCodMunicipio.getChildNodes().item(0).getTextContent());
              
              notaFiscal.setCodigoMunicipioIbge(Integer.parseInt(propCodMunicipio.getChildNodes().item(0).getTextContent()));
              
              NodeList noValorCredito = doc.getElementsByTagName("ns4:ValorCredito");
              Node propValorCredito = noValorCredito.item(0);
              System.out.println("Valor Credito = "+propValorCredito.getChildNodes().item(0).getTextContent());
              
              NodeList noPrestador = doc.getElementsByTagName("ns4:IdentificacaoPrestador");
              Node propPrestador = noPrestador.item(0);
              System.out.println("Cnpj Prestador = "+propPrestador.getChildNodes().item(0).getTextContent());
              System.out.println("Insc.Municipal Prestador = "+propPrestador.getChildNodes().item(1).getTextContent());
              
              notaFiscal.setCnpjPrestador(propPrestador.getChildNodes().item(0).getTextContent());
              notaFiscal.setInscricaoMunicipalPrestador(propPrestador.getChildNodes().item(1).getTextContent());
              
              
              NodeList noRazaoSocial = doc.getElementsByTagName("ns4:RazaoSocial");
              Node propRazaoSocial = noRazaoSocial.item(0);
              System.out.println("Razão Social = "+propRazaoSocial.getChildNodes().item(0).getTextContent());
              
              notaFiscal.setRazaoSocialPrestador(propRazaoSocial.getChildNodes().item(0).getTextContent());
              
              NodeList noEndereco = doc.getElementsByTagName("ns4:Endereco");
              Node propEndereco = noEndereco.item(0);
              System.out.println("Endereço = "+propEndereco.getChildNodes().item(0).getTextContent());
              System.out.println("Numero = "+propEndereco.getChildNodes().item(1).getTextContent());
              System.out.println("Bairro = "+propEndereco.getChildNodes().item(2).getTextContent());
              System.out.println("Cod.Municipio = "+propEndereco.getChildNodes().item(3).getTextContent());
              System.out.println("UF = "+propEndereco.getChildNodes().item(4).getTextContent());
              System.out.println("CEP = "+propEndereco.getChildNodes().item(5).getTextContent());
              
              notaFiscal.setEnderecoPrestador(propEndereco.getChildNodes().item(0).getTextContent());
              notaFiscal.setNumeroEnderecoPrestador(propEndereco.getChildNodes().item(1).getTextContent());
              notaFiscal.setBairroPrestador(propEndereco.getChildNodes().item(2).getTextContent());
              notaFiscal.setCidadePrestador(Integer.parseInt(propEndereco.getChildNodes().item(3).getTextContent()));
              notaFiscal.setUfPrestador(propEndereco.getChildNodes().item(4).getTextContent());
              notaFiscal.setCepPrestador(Integer.parseInt(propEndereco.getChildNodes().item(5).getTextContent()));
                           
              
              NodeList noIdTomador = doc.getElementsByTagName("ns4:IdentificacaoTomador");
              Node propIdTomador = noIdTomador.item(0);
              System.out.println("Cpf ou CNPJ = "+propIdTomador.getChildNodes().item(0).getTextContent());
              
              notaFiscal.setCpfCnpjTomador(propIdTomador.getChildNodes().item(0).getTextContent());
                           
              
              NodeList noTomador = doc.getElementsByTagName("ns4:TomadorServico");
              Node propTomador = noTomador.item(0);
              
              System.out.println("Razao Social Tomador = "+propTomador.getChildNodes().item(1).getTextContent());
               
              notaFiscal.setRazaoSocialTomador(propTomador.getChildNodes().item(1).getTextContent());
              
              Node propEndTomador = noTomador.item(0);
              Node propChildEndTomador = propEndTomador.getChildNodes().item(2);
              
              if(propChildEndTomador != null && propChildEndTomador.getChildNodes().item(0) != null) {
              	
	              System.out.println("Endereço Tomador = "+propChildEndTomador.getChildNodes().item(0).getTextContent());
	              System.out.println("Numero Endereço = "+propChildEndTomador.getChildNodes().item(1).getTextContent());
	              System.out.println("Complemento = "+propChildEndTomador.getChildNodes().item(2).getTextContent());
	              System.out.println("Bairro = "+propChildEndTomador.getChildNodes().item(3).getTextContent());
	              System.out.println("Cód.Municipio = "+propChildEndTomador.getChildNodes().item(4).getTextContent());
	              System.out.println("UF = "+propChildEndTomador.getChildNodes().item(5).getTextContent());
	              System.out.println("CEP = "+propChildEndTomador.getChildNodes().item(6).getTextContent());
	              
	              notaFiscal.setEnderecoTomador(propChildEndTomador.getChildNodes().item(0).getTextContent());
	              notaFiscal.setNumeroEnderecoTomador(propChildEndTomador.getChildNodes().item(1).getTextContent());
	              notaFiscal.setComplementoEnderecoTomador(propChildEndTomador.getChildNodes().item(2).getTextContent());
	              notaFiscal.setBairroTomador(propChildEndTomador.getChildNodes().item(3).getTextContent());
	              notaFiscal.setCidadeTomador(Integer.parseInt(propChildEndTomador.getChildNodes().item(4).getTextContent()));
	              notaFiscal.setUfTomador(propChildEndTomador.getChildNodes().item(5).getTextContent());
	              notaFiscal.setCepTomador(Integer.parseInt(propChildEndTomador.getChildNodes().item(6).getTextContent()));
	              
              }
              
              Node propContTomador = noTomador.item(0);
              Node propChildContTomador = propContTomador.getChildNodes().item(3);
              
              if(propChildContTomador != null) {
            	  if (propChildContTomador.getChildNodes().getLength() > 1) 
            	  {
            	    System.out.println("E-mail tomador = "+propChildContTomador.getChildNodes().item(0).getTextContent());
            	    System.out.println("Telefone tomador = "+propChildContTomador.getChildNodes().item(1).getTextContent());
                  
            	    notaFiscal.setEmailTomador(propChildContTomador.getChildNodes().item(0).getTextContent());
            	    notaFiscal.setTelefoneTomador(propChildContTomador.getChildNodes().item(1).getTextContent());
            	  } else {
            	 	System.out.println("E-mail tomador = "+propChildContTomador.getChildNodes().item(0).getTextContent());
                	notaFiscal.setEmailTomador(propChildContTomador.getChildNodes().item(0).getTextContent());
                	
            	  }
            		 
              }
              
              
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
