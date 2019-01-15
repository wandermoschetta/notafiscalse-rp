package br.com.notafiscalse.xml;

import java.io.ByteArrayInputStream;  
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.security.InvalidAlgorithmParameterException;  
import java.security.KeyStore;  
import java.security.NoSuchAlgorithmException;  
import java.security.PrivateKey;  
import java.security.cert.X509Certificate;  
import java.util.ArrayList;  
import java.util.Collections;  
import java.util.Enumeration;  
import java.util.List;  
  
import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.parsers.ParserConfigurationException;  
import javax.xml.transform.Transformer;  
import javax.xml.transform.TransformerException;  
import javax.xml.transform.TransformerFactory;  
import javax.xml.transform.dom.DOMSource;  
import javax.xml.transform.stream.StreamResult;  
  
import org.w3c.dom.Document;  
import org.w3c.dom.NodeList;  
import org.xml.sax.SAXException;

import br.com.notafiscalse.config.Configura;


public class AssinarXMLsCertificadoA1 {

	private static final String INFINUT = "InfInut";  
    private static final String INFCANC = "InfCanc";  
    //private static final String NFE = "ns1:InfRps";  
    private static final String NFE = "ns2:LoteRps";
    private static final String nfseConsultaSituacao = "ConsultarSituacaoLoteRpsEnvio";
    private static final String nfseConsultaLote = "ConsultarLoteRpsEnvio";
     
	private PrivateKey privateKey;  
	private KeyInfo keyInfo;  
	
	
	public String retornaXMLAssinadoCertificadoA1(Configura configuracao) {
		
		String xmlEnviNFSEAssinado = "";
		String caminhoDoCertificadoDoCliente = configuracao.getCaminhoCertificadoCliente();  
        String senhaDoCertificadoDoCliente = configuracao.getSenhaCertificadoCliente();  
        String fileEnviNFe = configuracao.getModeloArquivoEnvioNFSe();
		
        if(configuracao.getCaminhoCertificadoCliente().equals(null))
        	return xmlEnviNFSEAssinado = "ERRO:Caminho do certificado não configurado";
        if(configuracao.getSenhaCertificadoCliente().equals(null))
        	return xmlEnviNFSEAssinado = "ERRO:Senha do certificado não configurado";
        if((fileEnviNFe == null)||(fileEnviNFe == ""))
        	return xmlEnviNFSEAssinado = "ERRO: XML não informado";
        
        print("----------------------------------------------------------");
		print("Iniciando processo de Assinatura XML");
		print("----------------------------------------------------------");
		
		try {
		    info("Caminho do certificado: "+caminhoDoCertificadoDoCliente);
            
            File file = new File(caminhoDoCertificadoDoCliente);
		                
            if(!file.exists()) {
            	error("Certificado não encontrado no caminho especificado.");
            }else {
                info("Caminho conferido, continuando processo...");
                
            	AssinarXMLsCertificadoA1 assinarXMLsCertificadoA1 = new AssinarXMLsCertificadoA1();  
            	
            	try {
                  String xmlEnviNFe = fileEnviNFe;  //lerXML(fileEnviNFe);  
                  //print("Leu XML = "+xmlEnviNFe);
            	                    
                  xmlEnviNFSEAssinado = assinarXMLsCertificadoA1.assinaEnviNFe(xmlEnviNFe, caminhoDoCertificadoDoCliente, senhaDoCertificadoDoCliente);  
                  //info("XML EnviNFe Assinado: " + xmlEnviNFSEAssinado);
                                 
                
            	}catch(IOException io) {
            		error(io.toString());
            	}
            	            	
            }
                        
		}catch(Exception e) {
			error(e.toString());
		}
        		
		return xmlEnviNFSEAssinado;
      			
	}
		
	/** 
     * Assinatura do XML de Consulta Situação de Lote da NFS-e utilizando Certificado 
     * Digital A1. 
     * @param xml 
     * @param certificado 
     * @param senha 
     * @return 
     * @throws Exception 
     */  
	public String assinaConsultaSituacaoNFSe(String xml, String certificado, String senha) throws Exception {  
        Document document = documentFactory(xml);  
        XMLSignatureFactory signatureFactory = XMLSignatureFactory.getInstance("DOM");  
        ArrayList<Transform> transformList = signatureFactory(signatureFactory);  
        loadCertificates(certificado, senha, signatureFactory);  
                
        for (int i = 0; i < document.getDocumentElement().getElementsByTagName("Prestador").getLength(); i++) {  
            assinarConsultaSituacaoNFSe(signatureFactory, transformList, privateKey, keyInfo, document, i);  
                       
        }  
        
        
        return outputXML(document);  
    } 
	
	
	/** 
     * Assinatura do XML de Consulta do Lote da NFS-e utilizando Certificado 
     * Digital A1. 
     * @param xml 
     * @param certificado 
     * @param senha 
     * @return 
     * @throws Exception 
     */  
	public String assinaConsultaLoteNFSe(String xml, String certificado, String senha) throws Exception {  
        Document document = documentFactory(xml);  
        XMLSignatureFactory signatureFactory = XMLSignatureFactory.getInstance("DOM");  
        ArrayList<Transform> transformList = signatureFactory(signatureFactory);  
        loadCertificates(certificado, senha, signatureFactory);  
                
        for (int i = 0; i < document.getDocumentElement().getElementsByTagName("Prestador").getLength(); i++) {  
            assinarConsultaLoteNFSe(signatureFactory, transformList, privateKey, keyInfo, document, i);  
                       
        }  
        
        
        return outputXML(document);  
    }
	
	
	private void assinarConsultaLoteNFSe(XMLSignatureFactory fac,ArrayList<Transform> transformList, PrivateKey privateKey,KeyInfo ki, Document document, int indexNFe) throws Exception {  
       	
        NodeList elements = document.getElementsByTagName(nfseConsultaLote);  
    	
    	org.w3c.dom.Element el = (org.w3c.dom.Element) elements.item(indexNFe);  
        el.setIdAttribute("Id",true);
        
        String id = el.getAttribute("Id");  
  
        Reference ref = fac.newReference("#" + id, fac.newDigestMethod(DigestMethod.SHA1, null), transformList, null, null);  
  
        SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,(C14NMethodParameterSpec) null), fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null), Collections.singletonList(ref));  
  
        XMLSignature signature = fac.newXMLSignature(si, ki);  
                  
        DOMSignContext dsc = new DOMSignContext(privateKey, document.getDocumentElement().getElementsByTagName("Protocolo").item(indexNFe));  
                  
        signature.sign(dsc);
        
    }
	
	
	private void assinarConsultaSituacaoNFSe(XMLSignatureFactory fac,ArrayList<Transform> transformList, PrivateKey privateKey,KeyInfo ki, Document document, int indexNFe) throws Exception {  
       	
        NodeList elements = document.getElementsByTagName(nfseConsultaSituacao);  
    	
    	org.w3c.dom.Element el = (org.w3c.dom.Element) elements.item(indexNFe);  
        el.setIdAttribute("Id",true);
        
        String id = el.getAttribute("Id");  
  
        Reference ref = fac.newReference("#" + id, fac.newDigestMethod(DigestMethod.SHA1, null), transformList, null, null);  
  
        SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,(C14NMethodParameterSpec) null), fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null), Collections.singletonList(ref));  
  
        XMLSignature signature = fac.newXMLSignature(si, ki);  
                  
        DOMSignContext dsc = new DOMSignContext(privateKey, document.getDocumentElement().getElementsByTagName("Protocolo").item(indexNFe));  
                  
        signature.sign(dsc);
        
    }
	
	
	
	/** 
     * Assinatura do XML de Envio de Lote da NFS-e utilizando Certificado 
     * Digital A1. 
     * @param xml 
     * @param certificado 
     * @param senha 
     * @return 
     * @throws Exception 
     */  
	
	public String assinaEnviNFe(String xml, String certificado, String senha) throws Exception {  
        Document document = documentFactory(xml);  
        XMLSignatureFactory signatureFactory = XMLSignatureFactory.getInstance("DOM");  
        ArrayList<Transform> transformList = signatureFactory(signatureFactory);  
        loadCertificates(certificado, senha, signatureFactory);  
          
        for (int i = 0; i < document.getDocumentElement().getElementsByTagName(NFE).getLength(); i++) {  
            assinarNFe(signatureFactory, transformList, privateKey, keyInfo, document, i);  
                       
        }  
                
        return outputXML(document);  
    } 
	
    
    private void assinarNFe(XMLSignatureFactory fac,ArrayList<Transform> transformList, PrivateKey privateKey,KeyInfo ki, Document document, int indexNFe) throws Exception {  
           	
        NodeList elements = document.getElementsByTagName("ns1:InfRps");  
    	
    	org.w3c.dom.Element el = (org.w3c.dom.Element) elements.item(indexNFe);  
        el.setIdAttribute("Id",true);
        
        String id = el.getAttribute("Id");  
  
        Reference ref = fac.newReference("#" + id, fac.newDigestMethod(DigestMethod.SHA1, null), transformList, null, null);  
  
        SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,(C14NMethodParameterSpec) null), fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null), Collections.singletonList(ref));  
  
        XMLSignature signature = fac.newXMLSignature(si, ki);  
                  
        DOMSignContext dsc = new DOMSignContext(privateKey, document.getDocumentElement().getElementsByTagName(NFE).item(indexNFe));  
                  
        signature.sign(dsc);
        
    }  
  
    
    /** 
     * Assintaruda do XML de Cancelamento da NFS-e utilizando Certificado 
     * Digital A1. 
     * @param xml 
     * @param certificado 
     * @param senha 
     * @return 
     * @throws Exception 
     */  
    public String assinaCancNFe(String xml, String certificado, String senha) throws Exception {  
        return assinaCancelametoInutilizacao(xml, certificado, senha, INFCANC);  
    }  
  
    /** 
     * Assinatura do XML de Inutilizacao de sequenciais da NFS-e utilizando 
     * Certificado Digital A1. 
     * @param xml 
     * @param certificado 
     * @param senha 
     * @return 
     * @throws Exception 
     */  
    public String assinaInutNFe(String xml, String certificado, String senha) throws Exception {  
        return assinaCancelametoInutilizacao(xml, certificado, senha, INFINUT);  
    }  
        
    
    
    private String assinaCancelametoInutilizacao(String xml, String certificado, String senha, String tagCancInut) throws Exception {  
        Document document = documentFactory(xml);  
  
        XMLSignatureFactory signatureFactory = XMLSignatureFactory.getInstance("DOM");  
        ArrayList<Transform> transformList = signatureFactory(signatureFactory);  
        loadCertificates(certificado, senha, signatureFactory);  
  
        NodeList elements = document.getElementsByTagName(tagCancInut);  
        org.w3c.dom.Element el = (org.w3c.dom.Element) elements.item(0);  
        el.setIdAttribute("Id",true);
        
        String id = el.getAttribute("Id");  
  
        Reference ref = signatureFactory.newReference("#" + id, signatureFactory.newDigestMethod(DigestMethod.SHA1, null),  
                transformList, null, null);  
  
        SignedInfo si = signatureFactory.newSignedInfo(signatureFactory.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,(C14NMethodParameterSpec) null), signatureFactory.newSignatureMethod(SignatureMethod.RSA_SHA1, null),  
                Collections.singletonList(ref));  
  
        XMLSignature signature = signatureFactory.newXMLSignature(si, keyInfo);  
  
        DOMSignContext dsc = new DOMSignContext(privateKey, document.getFirstChild());  
        signature.sign(dsc);  
         
        
        return outputXML(document);  
    } 
    
    
    
	private void loadCertificates(String certificado, String senha,XMLSignatureFactory signatureFactory) throws Exception {  
  
        InputStream entrada = new FileInputStream(certificado);  
        KeyStore ks = KeyStore.getInstance("pkcs12");  
        try {  
            ks.load(entrada, senha.toCharArray());  
        } catch (IOException e) {  
            throw new Exception("Senha do Certificado Digital incorreta ou Certificado inválido.");  
        }  
  
        KeyStore.PrivateKeyEntry pkEntry = null;  
        Enumeration<String> aliasesEnum = ks.aliases();  
        while (aliasesEnum.hasMoreElements()) {  
            String alias = (String) aliasesEnum.nextElement();  
            if (ks.isKeyEntry(alias)) {  
                pkEntry = (KeyStore.PrivateKeyEntry) ks.getEntry(alias,  
                        new KeyStore.PasswordProtection(senha.toCharArray()));  
                privateKey = pkEntry.getPrivateKey();  
                break;  
            }  
        }  
  
        X509Certificate cert = (X509Certificate) pkEntry.getCertificate();  
        info("SubjectDN: " + cert.getSubjectDN().toString());  
  
        KeyInfoFactory keyInfoFactory = signatureFactory.getKeyInfoFactory();  
        List<X509Certificate> x509Content = new ArrayList<X509Certificate>();  
  
        x509Content.add(cert);  
        
		X509Data x509Data = keyInfoFactory.newX509Data(x509Content);  
        keyInfo = keyInfoFactory.newKeyInfo(Collections.singletonList(x509Data));  
    } 
    
    
    private ArrayList<Transform> signatureFactory(XMLSignatureFactory signatureFactory) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {  
        ArrayList<Transform> transformList = new ArrayList<Transform>();  
        TransformParameterSpec tps = null;  
        Transform envelopedTransform = signatureFactory.newTransform(Transform.ENVELOPED, tps);  
        Transform c14NTransform = signatureFactory.newTransform("http://www.w3.org/TR/2001/REC-xml-c14n-20010315", tps);  
  
        transformList.add(envelopedTransform);  
        transformList.add(c14NTransform);  
        return transformList;  
    }   
  
    private Document documentFactory(String xml) throws SAXException,IOException, ParserConfigurationException {  
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        factory.setNamespaceAware(true);  
        Document document = factory.newDocumentBuilder().parse(new ByteArrayInputStream(xml.getBytes()));  
        return document;  
    }  
        
    private String outputXML(Document doc) throws TransformerException {  
        ByteArrayOutputStream os = new ByteArrayOutputStream();  
        TransformerFactory tf = TransformerFactory.newInstance();  
        Transformer trans = tf.newTransformer();  
        trans.transform(new DOMSource(doc), new StreamResult(os));  
        String xml = os.toString();  
        if ((xml != null) && (!"".equals(xml))) {  
            xml = xml.replaceAll("\\r\\n", "");  
            xml = xml.replaceAll(" standalone=\"no\"", "");  
        }  
        return xml;  
    }  
        
    /*private static String lerXML(String fileXML) throws IOException {  
        String linha = "";  
        StringBuilder xml = new StringBuilder();  
  
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileXML)));  
        while ((linha = in.readLine()) != null) {  
            xml.append(linha);  
        }  
        in.close();  
  
        return xml.toString();  
    } 
	*/	
	
	/**
	 * Log Mensagem
	 * @param mensagem
	 *
	 */
	private static void print(String mensagem) {
		System.out.println(mensagem);		
	}
	
	/** 
     * Log ERROR. 
     *  
     * @param error 
     */  
    private static void error(String error) {  
        System.out.println("| ERROR: " + error);  
    }  
  
    /** 
     * Log INFO. 
     *  
     * @param info 
     */  
    private static void info(String info) {  
        System.out.println("| INFO: " + info);  
    }  
  
	
}
