package br.com.notafiscalse.xml;

public class UtilXml {
	
	/* retira a tag <?xml version="1.0" encoding="UTF-8"?>  */
    public String parseXml(String xml) {
   	    String str = "";
    	str = xml.substring(38);
           	
    	return str;
    } 

}
