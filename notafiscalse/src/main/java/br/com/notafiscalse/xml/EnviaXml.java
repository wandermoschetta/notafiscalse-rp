package br.com.notafiscalse.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.w3c.dom.Document;

import br.com.notafiscalse.config.Configura;

public class EnviaXml {
	
    public String enviaLoteRpsV3(String xmlEnvelopado,Configura configura) throws SOAPException, IOException {
    	String requestSoap = "";
        String pathCertificado =  configura.getCaminhoCertificadoCliente();
        String senhaCertificado = configura.getSenhaCertificadoCliente();
    	MontaXML montaXml = new MontaXML();
        
        System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");    
        System.setProperty("javax.net.ssl.keyStore", pathCertificado);    
        System.setProperty("javax.net.ssl.keyStorePassword", senhaCertificado);  
    	    	    	
    	//requestSoap = montaEnvelopeSoap(xml);
   	    requestSoap = xmlEnvelopado;
    	
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();

        //String url = "https://homologacao.ginfes.com.br/ServiceGinfesImpl?wsdl"; //url do webservice
        String url = configura.getUrlEndPointWebService();
        
        MimeHeaders headers = new MimeHeaders();
        headers.addHeader("Content-Type", "text/xml");
    	
        MessageFactory messageFactory = MessageFactory.newInstance();

        SOAPMessage msg = messageFactory.createMessage(headers, (new ByteArrayInputStream(requestSoap.getBytes())));

        SOAPMessage soapResponse = soapConnection.call(msg, url);
        Document xmlRespostaARequisicao=soapResponse.getSOAPBody().getOwnerDocument();
         
        
    	return  montaXml.passarXMLParaString(xmlRespostaARequisicao, 4);
    }

	

}
